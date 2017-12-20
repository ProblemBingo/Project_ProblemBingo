package Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Info {
	public Socket Socket = null;
	public Socket ChatSoc = null;
	public PrintWriter out = null;
	public BufferedReader in = null;
	public ObjectInputStream datain = null;
	public ObjectOutputStream dataout = null;
	public DTO dto = new DTO();

	public Info() {
		try {
			String serverAddress = java.net.InetAddress.getLocalHost().getHostAddress();
			Socket = new Socket(serverAddress, 9001);
			ChatSoc = new Socket(serverAddress, 9002);
			// Socket = new Socket("192.168.43.100", 9001);
			// ChatSoc = new Socket("192.168.43.100", 9002);
			out = new PrintWriter(ChatSoc.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(ChatSoc.getInputStream()));
			dataout = new ObjectOutputStream(Socket.getOutputStream());
			datain = new ObjectInputStream(Socket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setDTO(DTO dto) {
		this.dto = dto;
	}

	public DTO getDTO() {
		return dto;
	}
}
