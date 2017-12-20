package Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Bingo.chatClient;
import Data.Data;
import Data.Info;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Category extends JFrame {
	private JPanel contentPane;// Category Panel
	private Info info;

	/* Category Frame */
	public Category(Info info) {
		this.info = info;
		setTitle("category");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocation(550, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setVisible(true);
		addWindowListener(new cateExit(info));
		
		/* Category : common sense button */
		JButton com_button = new JButton(
				new ImageIcon(Category.class.getResource("/image/KakaoTalk_20171106_170553549.png")));
		com_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if ((JButton) obj == com_button) {
					info.dto.setCate("common_sense");
					new chatClient(info);
					dispose();
				}
			}
		});
		com_button.setBounds(107, 141, 243, 124);
		contentPane.setLayout(null);
		contentPane.add(com_button);
		com_button.setBorderPainted(false);
		com_button.setContentAreaFilled(false);
		com_button.setFocusPainted(false);
		
		/* Category : entertainment button */
		JButton enter_button = new JButton(
				new ImageIcon(Category.class.getResource("/image/KakaoTalk_20171106_171218168.png")));
		enter_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if ((JButton) obj == enter_button) {
					info.dto.setCate("entertainment");
					new chatClient(info);
					dispose();
				}
			}
		});
		enter_button.setBounds(420, 129, 243, 136);
		contentPane.add(enter_button);
		enter_button.setBorderPainted(false);
		enter_button.setContentAreaFilled(false);
		enter_button.setFocusPainted(false);
		
		/* Category : history button */
		JButton his_button = new JButton(
				new ImageIcon(Category.class.getResource("/image/KakaoTalk_20171106_171142261.png")));
		his_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if ((JButton) obj == his_button) {
					info.dto.setCate("history");
					new chatClient(info);
					dispose();
				}
			}
		});
		his_button.setBounds(420, 280, 243, 124);
		contentPane.add(his_button);
		his_button.setBorderPainted(false);
		his_button.setContentAreaFilled(false);
		his_button.setFocusPainted(false);
		
		/* Category : child button */
		JButton child_button = new JButton(
				new ImageIcon(Category.class.getResource("/image/KakaoTalk_20171106_171142408.png")));
		child_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if ((JButton) obj == child_button) {
					info.dto.setCate("for_child");
					new chatClient(info);
					dispose();
				}
			}
		});
		child_button.setBounds(108, 277, 242, 127);
		contentPane.add(child_button);
		child_button.setBorderPainted(false);
		child_button.setContentAreaFilled(false);
		child_button.setFocusPainted(false);
		
		/* Category : exit button */
		JButton exit_button = new JButton(new ImageIcon(Category.class.getResource("/image/\uADF8\uB9BC3.png")));
		exit_button.setBorderPainted(false);
		exit_button.setContentAreaFilled(false);
		exit_button.setFocusPainted(false);
		exit_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exit_button.setBounds(313, 427, 151, 101);
		contentPane.add(exit_button);

		JLabel cate_label = new JLabel("");
		cate_label.setIcon(new ImageIcon(Category.class.getResource("/image/\uAE00\uC5282.PNG")));
		cate_label.setBounds(460, 37, 210, 65);
		contentPane.add(cate_label);

		JLabel title_label = new JLabel("");
		title_label.setIcon(new ImageIcon(Category.class.getResource("/image/\uAE00\uC5281.PNG")));
		title_label.setBounds(107, 22, 339, 89);
		contentPane.add(title_label);

		JLabel bg_label = new JLabel("");
		bg_label.setIcon(new ImageIcon(Category.class.getResource("/image/\uBC30\uACBD.png")));
		bg_label.setBounds(0, 0, 782, 553);
		contentPane.add(bg_label);
	}
}

/* Exit selecting category window */
class cateExit implements WindowListener {
	Info info;

	public cateExit(Info info) {
		this.info = info;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		int exit = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "Log Out", JOptionPane.YES_NO_OPTION);
		if (exit == 0) {
			try {
				info.dataout.writeObject(new Data("EXIT", info.dto.getName()));
				info.dataout.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			System.exit(0);
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}
}
