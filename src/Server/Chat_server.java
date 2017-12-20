package Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Chat_server {

	/* Server for chat. */
	private static final int PORT = 9002; // The port that the server listens on.

	private static HashSet<String> names = new HashSet<String>();
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

	public static void main(String[] args) throws Exception {
		System.out.println("The chat server is running.");
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
		private String name;
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;

		public Handler(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {

				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				while (true) {
					name = in.readLine();
					System.out.println(name);
					if (name == null) {
						return;
					}
					synchronized (names) {
						if (!names.contains(name)) {
							names.add(name);
							break;
						}
					}
				}
				out.println("NAMEACCEPTED");
				writers.add(out);
				for (PrintWriter writer : writers) {
					writer.println("ENTRANCE " + name + "¥‘¿Ã ¿‘¿Â«œºÃΩ¿¥œ¥Ÿ."); // Enter to chat
				}
				while (true) {
					String input = in.readLine();
					if (input == null) {
						return;
					}
					for (PrintWriter writer : writers) {
						writer.println("MESSAGE " + name + ": " + input); // chat message
					}
				}
			} catch (IOException e) {
				System.out.println(e);
			} finally {
				for (PrintWriter writer : writers) {
					writer.println("EXIT " + name + "¥‘¿Ã ≈¿Â«œºÃΩ¿¥œ¥Ÿ."); // exit from chat
				}
				if (name != null) {
					names.remove(name);
				}
				if (out != null) {
					writers.remove(out);
				}
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
}