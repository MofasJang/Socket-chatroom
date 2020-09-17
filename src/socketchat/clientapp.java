package socketchat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.text.DefaultCaret;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;


public class clientapp {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void WinMain() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					clientapp window = new clientapp();
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
	public clientapp() {
		initialize();
	}
		
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\\u4E0B\u8F7D\u4E1C\u897F\\QQ.png"));
		frame.setTitle("\u804A\u5929\u5BA4\u5BA2\u6237\u7AEF");
		frame.setBounds(100, 100, 644, 421);
		frame.setName("clientapp");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setTabSize(2);
		textArea.setRows(11);
		textArea.setColumns(40);
		textArea.append("请输入你的名字\n");
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setRows(4);
		textArea_1.setColumns(50);
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.add(textArea_1);
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		client.setwin(textArea);
		
		JLabel lblNewLabel = new JLabel("\u804A\u5929\u6D88\u606F");
		lblNewLabel.setFont(new Font("华文楷体", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		JButton btnNewButton = new JButton("\u53D1\u9001");		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String text=textArea_1.getText();
				if(text!="") client.buffer=text;
				textArea_1.setText("");
			}
		});
		
		
		panel_1.add(btnNewButton);
	}
	
}
