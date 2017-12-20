package Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Data.Info;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame {
	
	/* Main Frame */
	public MainPage(Info info) {
		setTitle("¹®Á¦Àû ºù°í");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 800);
		setResizable(false);
		setLocation(360, 90);
		getContentPane().setLayout(null);
		setVisible(true);

		/* Start button */
		JButton login_button = new JButton(new ImageIcon(MainPage.class.getResource("/image/login.png")));
		login_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if ((JButton) obj == login_button) {
					new Login(info);
					dispose();
				}
			}
		});
		login_button.setBorderPainted(false);
		login_button.setContentAreaFilled(false);
		login_button.setFocusPainted(false);
		login_button.setBounds(447, 575, 242, 116);
		getContentPane().add(login_button);

		/* Rule button */
		JButton rule_button = new JButton(new ImageIcon(MainPage.class.getResource("/image/rule.png")));
		rule_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if ((JButton) obj == rule_button) {
					new GameRule();
				}
			}
		});
		rule_button.setBorderPainted(false);
		rule_button.setContentAreaFilled(false);
		rule_button.setFocusPainted(false);
		rule_button.setBounds(190, 575, 243, 116);
		getContentPane().add(rule_button);
		
		/* Exit button */
		JButton exit_button = new JButton(new ImageIcon(MainPage.class.getResource("/image/exit.png")));
		exit_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exit_button.setBorderPainted(false);
		exit_button.setContentAreaFilled(false);
		exit_button.setFocusPainted(false);
		exit_button.setBounds(703, 575, 251, 116);
		getContentPane().add(exit_button);
		
		/* Background image */
		JLabel main_bg = new JLabel("");
		main_bg.setBounds(0, 0, 1200, 800);
		getContentPane().add(main_bg);
		main_bg.setIcon(new ImageIcon(MainPage.class.getResource("/image/main.jpg")));
	}
}
