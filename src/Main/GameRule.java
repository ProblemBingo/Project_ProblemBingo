package Main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;

public class GameRule extends JFrame {

	private JPanel contentPane;/* Rule Panel */

	/* Rule Frame */
	public GameRule() {
		setVisible(true);
		setBackground(Color.WHITE);
		setTitle("rule");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1010, 800);
		setLocation(450, 90);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JButton rule_back = new JButton(new ImageIcon(GameRule.class.getResource("/image/log_back.png")));
		rule_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		rule_back.setBorderPainted(false);
		rule_back.setContentAreaFilled(false);
		rule_back.setFocusPainted(false);
		rule_back.setBounds(394, 614, 207, 127);
		contentPane.add(rule_back);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setIcon(new ImageIcon(GameRule.class.getResource("/image/gamerule.png")));
		lblNewLabel.setBounds(0, 0, 1000, 800);
		contentPane.add(lblNewLabel);
	}
}
