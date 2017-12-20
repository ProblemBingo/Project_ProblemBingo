package Bingo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Data.Data;
import Data.Info;

public class Quiz extends JFrame {
	Info info;
	public static boolean bingo = false;
	private int pro_num;
	private String problem;
	private String category;
	private JButton qBut;

	JPanel panel1 = new JPanel();
	JPanel p = new JPanel();

	JPanel problemBorderPanel = new JPanel();
	JPanel ProblemPanel = new JPanel();

	JPanel answerBorderPanel = new JPanel();
	JPanel answerPanel = new JPanel();

	JTextArea questionField = new JTextArea();// question field
	JTextField answerField = new JTextField(100);// answer field
	JButton answerButton = new JButton("Submit");// submit button
	BtnPanel btnPanel;

	public Quiz(BtnPanel btnPanel, JButton qBut, int qNum, String problem, String category, Info info) {
		this.btnPanel = btnPanel;
		this.info = info;
		this.pro_num = qNum;
		this.problem = problem;
		this.qBut = qBut;

		// Quiz window
		TitledBorder border1 = new TitledBorder(new LineBorder(Color.black, 1), " < ���� " + pro_num + " > ");
		TitledBorder border2 = new TitledBorder(new LineBorder(Color.black, 1), " < �� ���� > ");

		setTitle("Quiz");
		setPreferredSize(new Dimension(600 + 7, 440));
		setLocation(550, 200);
		setResizable(false);
		pack();
		setVisible(true);
		panel1.setBackground(new Color(255, 182, 193));

		panel1.setLayout(null);
		p.setBackground(new Color(255, 182, 193));

		// panel border
		p.setLayout(null);
		p.setBounds(10, 10, 580, 380);
		p.setBorder(null);
		problemBorderPanel.setBackground(new Color(255, 192, 203));

		// problem border
		problemBorderPanel.setLayout(null);
		problemBorderPanel.setBounds(20, 20, 540, 250);
		border1.setTitleJustification(TitledBorder.LEFT);
		border1.setTitlePosition(TitledBorder.TOP);
		problemBorderPanel.setBorder(border1);
		questionField.setFont(new Font("a�ɰ���", Font.PLAIN, 20));
		questionField.setBackground(new Color(255, 240, 245));

		// question field
		questionField.setBounds(0, 0, 500, 215); // problem panel
		questionField.setEditable(false);
		questionField.setLineWrap(true);
		questionField.setText(problem);
		ProblemPanel.setLayout(null);
		ProblemPanel.setBounds(20, 20, 500, 215);
		// ProblemPanel.setBackground(Color.WHITE); // �г��� �����ϱ� ���� �ӽ������� �־����
		ProblemPanel.add(questionField);

		answerBorderPanel.setBackground(new Color(255, 182, 193));

		// �� �Է� �����κ�
		answerBorderPanel.setLayout(null);
		answerBorderPanel.setBounds(20, 282, 540, 60);
		border2.setTitleJustification(TitledBorder.LEFT);
		border2.setTitlePosition(TitledBorder.TOP);
		answerBorderPanel.setBorder(null);

		// ���� �Է��ϴ� �κ�
		answerField.setBounds(20, 20, 400, 30);
		answerButton.setBackground(new Color(240, 128, 128));
		answerButton.setFont(new Font("a�ɰ���", Font.PLAIN, 17));
		answerButton.setBounds(425, 20, 100, 30);

		// submit button action
		ActionListener buttonlistner = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String answer = answerField.getText();
				getAnswer(answer, category, qNum);
			}

		};

		// submit event
		answerButton.addActionListener(buttonlistner);

		problemBorderPanel.add(ProblemPanel);
		answerBorderPanel.add(answerField);
		answerBorderPanel.add(answerButton);

		p.add(problemBorderPanel);
		p.add(answerBorderPanel);

		panel1.add(p);
		getContentPane().add(panel1);
		revalidate();
	}

	/* Getting answer */
	private void getAnswer(String answer, String category, int qNum) {
		Data data = new Data("ANSWER", answer, category, qNum, info.dto.getName());

		try {
			info.dataout.writeObject(data);
			info.dataout.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Checking answer
	 * valid 1 : correct answer
	 * valid 2 : wrong answer
	 * valid 3 : late answer
	 * valid 4 : timeout
	 * */
	public void setAlert(int valid) {

		if (valid == 1) {
			dispose();
			btnPanel.unBlockButton(pro_num);
			JOptionPane.showMessageDialog(null, "Correct!", "Answering check", JOptionPane.INFORMATION_MESSAGE);
		} else if (valid == 2) {
			JOptionPane.showMessageDialog(null, "Wrong answer!", "Answering check", JOptionPane.INFORMATION_MESSAGE);
		} else if (valid == 3) {
			dispose();
			btnPanel.blockButton();
			JOptionPane.showMessageDialog(null, "�ٸ������ ���� ����!", "Answering check", JOptionPane.INFORMATION_MESSAGE);
		} else if (valid == 4) {
			dispose();
			JOptionPane.showMessageDialog(null, "�̰� ������?", "Answering check", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}