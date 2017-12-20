package Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Data.Data;
import Data.Info;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class Mode extends JFrame {
	private JPanel contentPane; // Mode panel

	/* Mode Frame */
	public Mode(Info info) {
		setTitle("Mode");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocation(360, 90);
		setSize(800, 600);
		setLocation(550, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		addWindowListener(new btnExit(info));
		setVisible(true);

		/* Three bingo button */
		JButton three_button = new JButton();
		three_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if ((JButton) obj == three_button) {
					info.dto.setSize(3);
					new Category(info);
					dispose();
				}
			}
		});
		three_button.setIcon(new ImageIcon(Mode.class.getResource("/image/3x3.png")));
		three_button.setBounds(135, 142, 245, 134);
		three_button.setBorderPainted(false);
		three_button.setContentAreaFilled(false);
		three_button.setFocusPainted(false);
		contentPane.add(three_button);

		/* Four bingo button */
		JButton four_button = new JButton();
		four_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if ((JButton) obj == four_button) {
					info.dto.setSize(4);
					new Category(info);
					dispose();
				}
			}
		});
		four_button.setIcon(new ImageIcon(Mode.class.getResource("/image/4x4.png")));
		four_button.setBounds(400, 142, 245, 134);
		four_button.setBorderPainted(false);
		four_button.setContentAreaFilled(false);
		contentPane.add(four_button);

		/* Five bingo button */
		JButton five_button = new JButton();
		five_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if ((JButton) obj == five_button) {
					info.dto.setSize(5);
					new Category(info);
					dispose();
				}
			}
		});
		five_button.setIcon(new ImageIcon(Mode.class.getResource("/image/5x5.png")));
		five_button.setBounds(265, 276, 245, 134);
		five_button.setBorderPainted(false);
		five_button.setContentAreaFilled(false);
		contentPane.add(five_button);

		/* Exit button */
		JButton exit = new JButton();
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exit.setIcon(new ImageIcon(Mode.class.getResource("/image/exit_1.png")));
		exit.setBounds(314, 429, 152, 101);
		exit.setBorderPainted(false);
		exit.setContentAreaFilled(false);
		contentPane.add(exit);

		JLabel icon_img = new JLabel("");
		icon_img.setIcon(new ImageIcon(Mode.class.getResource("/image/Luigi_Artwork_-_Super_Mario_3D_World.png")));
		icon_img.setBounds(598, 348, 170, 193);
		contentPane.add(icon_img);

		JLabel button_bg = new JLabel("");
		button_bg.setIcon(new ImageIcon(Mode.class.getResource("/image/\uADF8\uB9BC5.png")));
		button_bg.setBounds(0, -214, 796, 1009);
		contentPane.add(button_bg);
	}
}

/* Exit selecting mode window */
class btnExit implements WindowListener {
	Info info;

	public btnExit(Info info) {
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
