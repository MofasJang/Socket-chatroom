package socketchat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.text.DefaultCaret;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class serverapp {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void WinMain() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					serverapp window = new serverapp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public serverapp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\\u4E0B\u8F7D\u4E1C\u897F\\QQ.png"));
		frame.setTitle("\u804A\u5929\u5BA4\u670D\u52A1\u5668");
		frame.setBounds(100, 100, 684, 463);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		JTextArea txtrEdfgAedfAa = new JTextArea();
		scrollPane.setViewportView(txtrEdfgAedfAa);
		txtrEdfgAedfAa.setRows(15);
		txtrEdfgAedfAa.setLineWrap(true);
		txtrEdfgAedfAa.setColumns(25);
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JLabel lblNewLabel = new JLabel("\u5B9E\u65F6\u6D88\u606F");
		lblNewLabel.setFont(new Font("华文楷体", Font.PLAIN, 15));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1);
		
		JTextArea txtrSklg = new JTextArea();
		scrollPane_1.setViewportView(txtrSklg);
		txtrSklg.setRows(15);
		txtrSklg.setColumns(25);
		scrollPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		DefaultCaret caret = (DefaultCaret)txtrEdfgAedfAa.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		DefaultCaret caret1 = (DefaultCaret)txtrSklg.getCaret();
		caret1.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		server.setwin(txtrEdfgAedfAa);
		sqltest.setwin(txtrSklg);
		
		JLabel lblNewLabel_1 = new JLabel("\u5386\u53F2\u8BB0\u5F55");
		lblNewLabel_1.setFont(new Font("华文楷体", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_1.setColumnHeaderView(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JTextArea textArea = new JTextArea();
		textArea.setRows(4);
		textArea.setColumns(40);
		panel_1.add(textArea);
		
		JButton btnNewButton = new JButton("\u53D1\u9001");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String text=textArea.getText();
				if(text!="" && text!= null) server.buffer=text;
				textArea.setText("");
			}
		});
		panel_1.add(btnNewButton);
	}

}
