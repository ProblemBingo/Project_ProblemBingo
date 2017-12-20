package Client;

import javax.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Data.Info;
import Main.MainPage;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws Exception {
		/* Game BGM */
		try {
			File file = new File("C:\\Users\\GENIE\\eclipse-workspace\\Network_termP\\src\\image\\bgm.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		Info info = new Info();// socket information
		MainPage mp = new MainPage(info);// Executing main page
	}
}