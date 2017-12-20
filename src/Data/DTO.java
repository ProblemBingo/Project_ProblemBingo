package Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

public class DTO implements Serializable {
	private String name = null;
	private int size = 0;
	private String category = null;
	private int RPS;

	/* Get RPS from user */
	public int getRPS() {
		return RPS;
	}

	/* Setting RPS */
	public void setRPS(int RPS) {
		this.RPS = RPS;
	}

	/* Setting Name */
	public void setName(String name) {
		this.name = name;
	}

	/* getting Name */
	public String getName() {
		return name;
	}

	/* Getting bingo size */
	public int getSize() {
		return size;
	}

	/* Setting bingo size */
	public void setSize(int size) {
		this.size = size;
	}

	/* Getting category */
	public String getCate() {
		return category;
	}
	
	/* Setting category */
	public void setCate(String category) {
		this.category = category;
	}
}
