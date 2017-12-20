package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Client.Client;
import Data.DTO;
import Data.Data;
import Data.Info;

public class Login extends JFrame {

	/* Login Frame */
	public Login(Info info) {
		setTitle("LogIn");
		setSize(790, 575);
		setLocation(550, 200);
		setResizable(false);

		JPanel contentPane = new JPanel();
		getContentPane().add(contentPane);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setVisible(true);
		contentPane.setLayout(null);

		JTextArea name = new JTextArea();
		name.setText("");
		name.setForeground(Color.WHITE);
		name.setFont(new Font("궁서", Font.BOLD, 30));
		name.setBackground(Color.LIGHT_GRAY);
		name.setLocation(275, 265);
		name.setSize(222, 46);
		contentPane.add(name);

		/* Back button */
		JButton log_back = new JButton(new ImageIcon(Login.class.getResource("/image/log_back.png")));
		log_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if ((JButton) obj == log_back) {
					dispose();
				}
			}
		});

		log_back.setBorderPainted(false);
		log_back.setContentAreaFilled(false);
		log_back.setFocusPainted(false);
		log_back.setBounds(438, 399, 173, 95);
		contentPane.add(log_back);

		/* Login button */
		JButton log_start = new JButton(new ImageIcon(Login.class.getResource("/image/log_start.png")));
		log_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if ((JButton) obj == log_start) {
					boolean valid = false;
					info.dto.setName(name.getText());
					/* Checking duplicate name */
					try {
						info.dataout.writeObject(new Data("ENTER", info.dto));
						Data data = (Data) info.datain.readObject();
						if (data.getProtocol().equals("LOGIN")) {
							valid = true;
						} else if (data.getProtocol().equals("DUPLOGIN")) {
							JOptionPane.showMessageDialog(null, "다른 이름!!", "Login Error!",
									JOptionPane.INFORMATION_MESSAGE);
							name.setText("");
						}
					} catch (IOException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}

					if (valid) {
						info.out.println(name.getText());
						info.dto.setName(name.getText());
						new Mode(info);
						dispose();
					}
				}
			}
		});
		log_start.setBorderPainted(false);
		log_start.setContentAreaFilled(false);
		log_start.setFocusPainted(false);
		log_start.setBounds(159, 400, 173, 86);
		contentPane.add(log_start);

		/* Background image */
		JLabel backimage = new JLabel();
		backimage.setForeground(Color.BLACK);
		backimage.setIcon(new ImageIcon(Login.class.getResource("/image/lg_main.png")));
		backimage.setBounds(0, -12, 782, 553);
		contentPane.add(backimage);
	}
}
