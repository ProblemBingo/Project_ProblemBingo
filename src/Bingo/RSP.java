package Bingo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Data.Data;
import Data.Info;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class RSP extends JFrame {

	private JPanel contentPane; // RSP panel
	Info info;

	public RSP(Info info) {
		this.info = info;
		setTitle("rock-paper-siccors");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 356);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setVisible(true);
		setLocation(650, 300);

		/* Scissor button */
		JButton Scissor = new JButton("");
		Scissor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setRPS(0);
				dispose();
			}
		});
		Scissor.setIcon(new ImageIcon(RSP.class.getResource("/image/\uAC00\uC704.png")));
		Scissor.setBounds(44, 152, 171, 139);
		contentPane.add(Scissor);

		/* Rock button */
		JButton Rock = new JButton("");// Rock button
		Rock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setRPS(1);
				dispose();
			}
		});
		Rock.setIcon(new ImageIcon(RSP.class.getResource("/image/\uBC14\uC704.png")));
		Rock.setBounds(212, 152, 165, 139);
		contentPane.add(Rock);

		/* Paper button */
		JButton Paper = new JButton("");
		Paper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setRPS(2);
				dispose();
			}
		});
		Paper.setIcon(new ImageIcon(RSP.class.getResource("/image/\uBCF4.png")));
		Paper.setBounds(377, 152, 160, 139);
		contentPane.add(Paper);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(RSP.class.getResource("/image/\uAC00\uC704\uBC14\uC704.png")));
		lblNewLabel.setBounds(0, 0, 573, 321);
		contentPane.add(lblNewLabel);
	}

	/* Setting RPS status */
	private void setRPS(int num) {
		info.dto.setRPS(num);
		Data data = new Data("UPDATE", num);

		try {
			info.dataout.writeObject(data);
			info.dataout.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}