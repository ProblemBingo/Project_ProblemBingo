package Bingo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Data.Data;
import Data.Info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class chatClient extends JFrame {
	Info info;
	static String name;
	public int qNum;
	JPanel Panel;
	private JTextField txtDdd;
	private volatile int heartCount = 0;
	private volatile int iceCount = 0;
	private volatile int crownCount = 0;
	JButton btnCrown;
	JButton btnIce;
	JButton btnHeart;

	public chatClient(Info info) {
		this.info = info;
		BtnPanel bp = new BtnPanel(info, this);
		ChatPanel cp = new ChatPanel(info);

		Thread t_btn = new Thread(bp);
		t_btn.start();
		Thread t_ch = new Thread(cp);
		t_ch.start();

		name = info.dto.getName(); // user name
		setPreferredSize(new Dimension(1200, 800));
		setResizable(false);
		setLocation(366, 90);
		setTitle("Game");
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new clientExit(info));
		setVisible(true);

		Panel = new JPanel();
		Panel.setLayout(null);

		// Show user name
		txtDdd = new JTextField();
		txtDdd.setEditable(false);
		txtDdd.setText(name);
		txtDdd.setFont(new Font("a꽃가람", Font.BOLD, 22));
		txtDdd.setBackground(Color.PINK);
		txtDdd.setHorizontalAlignment(SwingConstants.CENTER);
		txtDdd.setBounds(5, 102, 124, 37);
		Panel.add(txtDdd);
		txtDdd.setColumns(12);

		Panel.add(bp);
		getContentPane().add(Panel);
		Panel.add(cp);

		// Show user character image
		JLabel mario = new JLabel();
		mario.setIcon(new ImageIcon(chatClient.class.getResource("/image/plum.png")));
		mario.setBounds(14, 0, 107, 126);
		Panel.add(mario);

		// item button (ice)
		btnIce = new JButton("");
		btnIce.setBorder(new LineBorder(new Color(255, 182, 193), 5, true));
		btnIce.setBackground(Color.WHITE);
		btnIce.setIcon(new ImageIcon(chatClient.class.getResource("/image/ice.png")));
		btnIce.setBounds(24, 556, 78, 75);
		Panel.add(btnIce);
		btnIce.setEnabled(false);
		btnIce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				useItem(2, bp); // using item
			}
		});

		// item button (heart)
		btnHeart = new JButton("");
		btnHeart.setIcon(new ImageIcon(chatClient.class.getResource("/image/angel-heart-with-an-halo.png")));
		btnHeart.setBorder(new LineBorder(new Color(255, 182, 193), 5, true));
		btnHeart.setBackground(Color.WHITE);
		btnHeart.setBounds(24, 469, 78, 75);
		Panel.add(btnHeart);
		btnHeart.setEnabled(false);
		btnHeart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				useItem(1, bp);// using item
			}
		});

		// item button (crown)
		btnCrown = new JButton("");
		btnCrown.setIcon(new ImageIcon(chatClient.class.getResource("/image/royal-crown-with-three-picks.png")));
		btnCrown.setBorder(new LineBorder(new Color(255, 182, 193), 5, true));
		btnCrown.setBackground(Color.WHITE);
		btnCrown.setBounds(24, 643, 78, 75);
		Panel.add(btnCrown);
		btnCrown.setEnabled(false);
		btnCrown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				useItem(3, bp);// using item
			}
		});

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(chatClient.class.getResource("/image/mission.png")));
		label.setBounds(858, 591, 322, 174);
		Panel.add(label);

		JLabel background = new JLabel();
		background.setIcon(new ImageIcon(chatClient.class.getResource("/image/\uBC30\uACBD\uC0C9.PNG")));
		background.setBounds(0, 0, 1256, 1000);
		Panel.add(background);
		new RSP(info); // Determine who will go first
	}

	/* After using item, modify its status */
	private void useItem(int item, BtnPanel panel) {
		switch (item) {
		case 1:
			if (heartCount > 0) {
				heartCount--;
				Data data = new Data("HEART");
				try {
					info.dataout.writeObject(data);
					info.dataout.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (heartCount == 0) // No item, button inactive
					btnHeart.setEnabled(false);
			}
			break;
		case 2:
			if (iceCount > 0) {
				iceCount--;
				try {
					info.dataout.writeObject(new Data("ICE"));
					info.dataout.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (iceCount == 0)// No item, button inactive
					btnIce.setEnabled(false);
			}
			break;
		case 3:
			if (crownCount > 0) {
				crownCount--;
				panel.crown();

				if (crownCount == 0)// No item, button inactive
					btnCrown.setEnabled(false);
			}
			break;
		}
	}

	/* After getting item, modify its status */
	public void modifyItem(int item) {
		switch (item) {
		case 1:
			heartCount++;
			btnHeart.setEnabled(true);
			break;
		case 2:
			iceCount++;
			btnIce.setEnabled(true);
			break;
		case 3:
			crownCount++;
			btnCrown.setEnabled(true);
			break;
		default:
			break;
		}
	}
}

/* Bingo GUI */
class BtnPanel extends JPanel implements Runnable {
	static String name;
	int qNum;
	int num;
	Info info;
	JPanel BingoPanel1;
	JButton[] Bingo_B = new JButton[26];
	chatClient client;
	TitledBorder border1 = new TitledBorder(new LineBorder(Color.yellow, 0));

	Quiz myquiz;
	HashMap<Integer, JButton> btnList = new HashMap<Integer, JButton>();
	HashMap<Integer, JButton> remainList = new HashMap<Integer, JButton>();

	public BtnPanel(Info info, chatClient client) {
		this.info = info;
		this.client = client;

		// bingo border
		border1.setTitleJustification(TitledBorder.LEFT);
		border1.setTitlePosition(TitledBorder.TOP);

		setLayout(null);
		setBounds(122, 60, 700, 671);
		setBorder(border1);
		setBackground(new Color(255, 239, 213));

		BingoPanel1 = new JPanel();
		BingoPanel1.setBackground(new Color(245, 222, 179));
		BingoPanel1.setLayout(null);
		BingoPanel1.setBounds(25, 14, 642, 641);

		Random k = new Random();

		int size = info.dto.getSize();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int num = k.nextInt(49) + 1; // Random bingo button
				while (btnList.containsKey(num)) {
					num = k.nextInt(49) + 1;
				}
				Bingo_B[j + i * size] = new JButton(String.valueOf(num));
				Bingo_B[j + i * size].setLayout(null);
				Bingo_B[j + i * size].setBounds(j * (640 / size), i * (640 / size), 600 / size, 600 / size);
				BingoPanel1.add(Bingo_B[j + i * size]);
				Bingo_B[j + i * size].setBackground(new Color(255, 255, 255));
				Bingo_B[j + i * size].setFont(new Font("a꽃가람", Font.BOLD, 30));
				Bingo_B[j + i * size].addActionListener(new ButtonListener(num, info.dto.getCate()));
				btnList.put(num, Bingo_B[j + i * size]);
				remainList.put(num, Bingo_B[j + i * size]);

			}
		}

		add(BingoPanel1);
		setVisible(true);
		blockButton();
	}

	/* item : inactive button randomly */
	public void getICE() {
		HashMap<Integer, JButton> iceList = new HashMap<Integer, JButton>();
		HashMap<Integer, JButton> uniceList = new HashMap<Integer, JButton>();
		for (int i = 20; i < 25; i++) {
			int num = Integer.parseInt(Bingo_B[i].getText());
			if (remainList.containsKey(num)) {
				iceList.put(num, Bingo_B[i]);
				remainList.remove(num);
			} else {
				uniceList.put(num, Bingo_B[i]);
			}

			Bingo_B[i].setBackground(Color.BLUE);
		}
		Timer timer = new Timer();
		TimerTask tk = new TimerTask() {
			@Override
			public void run() {
				for (int num : iceList.keySet()) {
					remainList.put(num, iceList.get(num));
					iceList.get(num).setBackground(Color.WHITE);
				}
				for (int num : uniceList.keySet()) {
					uniceList.get(num).setBackground(Color.RED);
				}
			}
		};
		timer.schedule(tk, 10000);
	}

	/* item : remove bingo randomly in mine */
	public void crown() {
		Random k = new Random();
		while (true) {
			int num = k.nextInt(49) + 1;
			if (remainList.containsKey(num)) {
				remainList.get(num).setBackground(Color.RED);
				remainList.get(num).setEnabled(false);
				remainList.remove(num);
				break;
			}
		}
	}

	// checking winner
	public void getWinner() {
		int[][] mission = { { 0, 2, 4, 6, 8 }, { 0, 3, 5, 6, 9, 10, 12, 15 }, { 0, 4, 6, 8, 12, 16, 18, 20, 24 } };
		boolean valid = true;
		if (info.dto.getSize() == 3) { // three bingo
			for (int i : mission[0]) {
				for (JButton btn : remainList.values()) {
					if (btn == Bingo_B[i]) {
						valid = false;
					}
				}
			}
		} else if (info.dto.getSize() == 4) {// four bingo
			for (int i : mission[1]) {
				for (JButton btn : remainList.values()) {
					if (btn == Bingo_B[i]) {
						valid = false;
					}
				}
			}
		} else if (info.dto.getSize() == 5) {// five bingo
			for (int i : mission[2]) {
				for (JButton btn : remainList.values()) {
					if (btn == Bingo_B[i]) {
						valid = false;
					}
				}
			}
		}
		if (valid) {
			System.out.println("true");
			Data data = new Data("FINISH");
			try {
				info.dataout.writeObject(data);
				info.dataout.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "너가 이겼어!!", "당신의 결과는?", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}

	/* Getting item */
	private void getItem(String category, int qNum) {
		Data data = new Data("ITEM", category, qNum);

		try {
			info.dataout.writeObject(data);
			info.dataout.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Getting category and problem number */
	private class ButtonListener implements ActionListener {
		private int qNum;
		private String category;

		public ButtonListener(int qNum, String category) {
			this.qNum = qNum;
			this.category = category;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			getQuestion(qNum, category);
		}
	}

	@Override
	public void run() {
		while (true) {
			Data data = null;
			try {
				data = (Data) info.datain.readObject();
				String protocol = data.getProtocol();

				if (protocol.equals("QUESTION")) { // getting problem
					setQuestion(data);
				} else if (protocol.equals("CHECK")) {// checking answer
					if (data.getName().equals(info.dto.getName())) {
						myquiz.setAlert(1);
						getItem(info.dto.getCate(), qNum);
						getWinner();
					} else {
						myquiz.setAlert(3);
					}
				} else if (protocol.equals("RSP")) {// turn using RPS
					if (data.getRSP() == 1) {
						unBlockButton();
					} else if (data.getRSP() == 0) {
						new RSP(info);
					}
				} else if (protocol.equals("HEART")) {// Using item
					heartButton();
				} else if (protocol.equals("ICE")) {// Using item
					getICE();
				} else if (protocol.equals("FAIL")) {
					myquiz.setAlert(2);
				} else if (protocol.equals("ITEM")) {
					client.modifyItem(data.getItem());
				} else if (protocol.equals("LOSE")) {// Lose game
					JOptionPane.showMessageDialog(null, "너가 졌어ㅠㅠ", "당신의 결과는?", JOptionPane.INFORMATION_MESSAGE);
				}

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/* Execute quiz class : getting problem */
	public void setQuestion(Data data) {
		String problem = data.getProblem();
		int qNum = data.getNum();
		String category = data.getCategory();

		myquiz = new Quiz(this, (JButton) btnList.get(qNum), qNum, problem, category, info);
		blockButton();
	}

	/* inactive bingo button */
	public void blockButton() {
		for (int value : remainList.keySet()) {
			if (remainList.get(value).isEnabled())
				remainList.get(value).setEnabled(false);
		}
	}

	/* active bingo button */
	public void unBlockButton(int qNum) {
		if (remainList.containsKey(qNum)) {
			remainList.get(qNum).setBackground(Color.RED);
			if (remainList.get(qNum).isEnabled())
				remainList.get(qNum).setEnabled(false);
			remainList.remove(qNum);
		}
		for (int value : remainList.keySet()) {
			if (!remainList.get(value).isEnabled())
				remainList.get(value).setEnabled(true);
		}
	}

	/* active bingo button */
	public void unBlockButton() {
		for (int value : remainList.keySet()) {
			remainList.get(value).setEnabled(true);
		}
	}

	/* Differentiate active and inactive button */
	public void removeBtn(int qNum) {
		if (remainList.containsKey(qNum)) {
			remainList.remove(qNum);
		}
	}

	/* Using item, revive button randomly */
	public void heartButton() {
		Random k = new Random();
		boolean valid = true;
		while (valid) {
			int num = k.nextInt(49) + 1;
			if (btnList.containsKey(num) && !remainList.containsKey(num)) {
				for (JButton button : btnList.values()) {
					if (Integer.parseInt(button.getText()) == num) {
						remainList.put(num, button);
						button.setBackground(Color.WHITE);
						valid = false;
						break;
					}
				}
			}
		}
	}

	/* Getting question */
	public void getQuestion(int qNum, String category) {
		this.qNum = qNum;
		try {
			info.dataout.writeObject(new Data("CHOOSE", category, qNum));
			info.dataout.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/* chat GUI */
class ChatPanel extends JPanel implements Runnable {
	static String name;
	public int qNum;
	Info info;
	JPanel borderPanel2;
	TitledBorder border2 = new TitledBorder(new LineBorder(Color.black, 1));

	JPanel textFieldPanel;
	JTextField textField = new JTextField(40); // message input field
	JTextArea messageArea = new JTextArea(8, 20); // message output field
	JScrollPane js = new JScrollPane(messageArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); // scroll function

	public ChatPanel(Info info) {
		this.info = info;
		name = info.dto.getName();
		// chat border
		border2.setTitleJustification(TitledBorder.LEFT);
		border2.setTitlePosition(TitledBorder.TOP);
		messageArea.setEditable(false);

		setLayout(null);
		setBounds(858, 107, 322, 492);
		setBorder(border2);

		textFieldPanel = new JPanel();
		textFieldPanel.setLayout(null);
		textFieldPanel.setBounds(10, 20, 300, 425);
		js.setBounds(0, 0, 300, 430);
		textFieldPanel.add(js);
		add(textFieldPanel);
		textField.setBounds(10, 450, 294, 30);
		add(textField);

		// event of sending message
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// send message to server
				info.out.println(textField.getText());
				textField.setText("");
			}
		});
		setVisible(true);
	}

	// run method
	public void run() {
		while (true) {
			String line;
			try {
				line = info.in.readLine();
				if (line.startsWith("NAMEACCEPTED")) {/* agree to use chat */
					textField.setEditable(true);
				} else if (line.startsWith("MESSAGE")) { /* print the message */
					messageArea.append(line.substring(8) + "\n");
				} else if (line.startsWith("ENTRANCE")) { /* client enter */
					messageArea.append(line.substring(9) + "\n");
				} else if (line.startsWith("EXIT")) { /* client log out */
					messageArea.append(line.substring(5) + "\n");
				}
				js.getVerticalScrollBar().setValue(js.getVerticalScrollBar().getMaximum());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class clientExit implements WindowListener {
	Info info;

	public clientExit(Info info) {
		this.info = info;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		int exit = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "Log Out", JOptionPane.YES_NO_OPTION);
		if (exit == 0) {
			try {
				info.dataout.writeObject(new Data("EXIT", info.dto.getName()));
				info.dataout.flush();
				info.Socket.close();
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