package Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import Data.DTO;
import Data.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameServer {

	/* Server for bingo game. */
	private static final int PORT = 9001;// The port that the server listens on.

	public static void main(String[] args) throws Exception {
		System.out.println("The game server is running.");
		ServerSocket listener = new ServerSocket(PORT);
		try {
			while (true) {
				new Handler(listener.accept()).start();
			}
		} finally {
			listener.close();
		}
	}

	private static class Handler extends Thread {
		/* JDBC : Variable for connecting DB */
		private static final String url = "jdbc:mysql://localhost:3306/problem_bingo";
		private static final String id = "root";
		private static final String password = "12345";
		private static final String JDBC_Driver = "org.gjt.mm.mysql.Driver";
		private Connection conn = null;
		private Statement stmt = null;
		private ResultSet rs = null;
		private String result = null;

		private Socket socket;
		private ObjectInputStream inFromClient = null;
		private ObjectOutputStream outToClient = null;

		private static Hashtable<ObjectOutputStream, DTO> user = new Hashtable<ObjectOutputStream, DTO>();
		private static ArrayList<Integer> rsp = new ArrayList<Integer>(); // Variable for Rock-Paper-Scissor

		public Handler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				inFromClient = new ObjectInputStream(socket.getInputStream());
				outToClient = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				Class.forName(JDBC_Driver);
				conn = DriverManager.getConnection(url, id, password); // Connect DB
				stmt = conn.createStatement();
			} catch (ClassNotFoundException e) {
				System.err.print("ClassNotFoundException");
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
			}

			try {
				while (true) {
					Data data = (Data) inFromClient.readObject();
					String protocol = data.getProtocol();

					if (protocol.equals("ENTER")) { // Enter the game
						DTO dto = data.getDTO();
						if (!user.containsKey(outToClient)) {
							synchronized (user) {
								user.put(outToClient, dto);
							}
							outToClient.writeObject(new Data("LOGIN"));
							outToClient.flush();
						} else {
							outToClient.writeObject(new Data("DUPLOGIN"));
							outToClient.flush();
						}
					} else if (protocol.equals("UPDATE")) { // Rock-Paper-Scissor
						int RPS = data.getRSP();
						synchronized (user) {
							user.get(outToClient).setRPS(RPS);
							System.out.println(user.get(outToClient).getRPS());
						}
						synchronized (rsp) {
							if (rsp.size() < 2) { // Restriction on the number of persons (two user for
													// Rock-Paper-Scissor)
								rsp.add(RPS);
								if (rsp.size() == 2) {
									checkCondition();
									rsp.clear();
								}
							}
						}
					} else if (protocol.equals("CHOOSE")) {// checking category

						String category = data.getCategory();
						int qNum = data.getNum();
						Data tmp = getQuestion(category, qNum);

						synchronized (user) {
							for (ObjectOutputStream stream : user.keySet()) {
								stream.writeObject(tmp);
								stream.flush();
							}
						}
					} else if (protocol.equals("ANSWER")) {// checking answer for bingo problem
						Data tmp = getAnswer(data.getAnswer(), data.getCategory(), data.getNum(), data.getName());
						if (tmp.getProtocol().equals("CHECK")) {
							synchronized (user) {
								for (ObjectOutputStream stream : user.keySet()) {
									stream.writeObject(tmp);
									stream.flush();
								}
							}
						} else if (tmp.getProtocol().equals("FAIL")) {// wrong answer
							outToClient.writeObject(tmp);
							outToClient.flush();
						}
					} else if (protocol.equals("ITEM")) {// change of item status
						Data tmp = getItem(data.getCategory(), data.getNum());
						outToClient.writeObject(tmp);
						outToClient.flush();
					} else if (protocol.equals("HEART")) {// item1 : Revive one rival bingo
						for (ObjectOutputStream stream : user.keySet()) {
							if (stream != outToClient) {
								stream.writeObject(new Data("HEART"));
								stream.flush();
							}
						}
					} else if (protocol.equals("ICE")) {// item2 : Don't click bottom row of the bingo hall.
						for (ObjectOutputStream stream : user.keySet()) {
							if (stream != outToClient) {
								Data tmp = new Data("ICE");
								stream.writeObject(tmp);
								stream.flush();
							}
						}
					} else if (protocol.equals("FINISH")) { // Finishing bingo
						for (ObjectOutputStream stream : user.keySet()) {
							if (stream != outToClient) {
								Data tmp = new Data("LOSE");
								stream.writeObject(tmp);
								stream.flush();
							}
						}
					} else if (protocol.equals("EXIT")) { // Exit from game
						String name = data.getName();

						synchronized (user) {
							if (user.containsKey(outToClient))
								user.remove(outToClient);
						}
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/* checking who win Rock-Paper-Scissor */
		private void checkCondition() {
			try {
				for (ObjectOutputStream stream : user.keySet()) {
					if (stream != outToClient) {
						if (user.get(stream).getRPS() == user.get(outToClient).getRPS()) {
							outToClient.writeObject(new Data("RSP", 0));
							outToClient.flush();
							stream.writeObject(new Data("RSP", 0));
							stream.flush();
						} else if (user.get(outToClient).getRPS() > 0) {
							if (user.get(outToClient).getRPS() > user.get(stream).getRPS()) {
								outToClient.writeObject(new Data("RSP", 1));
								outToClient.flush();
								stream.writeObject(new Data("RSP", -1));
								stream.flush();
							} else {
								outToClient.writeObject(new Data("RSP", -1));
								outToClient.flush();
								stream.writeObject(new Data("RSP", 1));
								stream.flush();
							}
						} else if (user.get(outToClient).getRPS() == 0) {
							if (user.get(stream).getRPS() == 2) {
								outToClient.writeObject(new Data("RSP", 1));
								outToClient.flush();
								stream.writeObject(new Data("RSP", -1));
								stream.flush();
							} else {
								outToClient.writeObject(new Data("RSP", -1));
								outToClient.flush();
								stream.writeObject(new Data("RSP", 1));
								stream.flush();
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/* Get problem from DB */
		private Data getQuestion(String category, int qNum) {
			String problem = null;

			try {
				rs = stmt.executeQuery("select * from " + category + " where pro_num=" + qNum);

				while (rs.next()) {
					problem = rs.getString("problem");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			Data data = new Data("QUESTION", qNum, problem, category);

			return data;
		}

		/* Get answer from DB */
		private Data getAnswer(String answer, String category, int qNum, String name) {
			String db_answer = null;
			Data data;

			try {
				rs = stmt.executeQuery("select * from " + category + " where pro_num=" + qNum);

				while (rs.next()) {
					db_answer = rs.getString("answer");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (db_answer.equalsIgnoreCase(answer.trim())) {
				data = new Data("CHECK", name, qNum, category);
			} else {
				data = new Data("FAIL");
			}

			return data;
		}

		/* Get item status from DB */
		private Data getItem(String category, int qNum) {
			int db_item = 0;
			Data data;

			try {
				rs = stmt.executeQuery("select * from " + category + " where pro_num=" + qNum);

				while (rs.next()) {
					db_item = rs.getInt("item");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			data = new Data("ITEM", db_item, category, qNum);

			return data;
		}
	}
}
