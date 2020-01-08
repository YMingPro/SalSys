package systemFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import myModule.Panel_Home;

public class Display_Message extends JFrame {

	private static final long serialVersionUID = 1L;

	String timeSign, userName;

	int numFeedback;

	Frame_Main main;

	public Display_Message(int num, String userName, Frame_Main main) {
		this.userName = userName;
		numFeedback = num;
		this.main = main;
		setSize(359, 228);// 设置窗体大小
		setUndecorated(true);
		setResizable(false);
		setGreet();
		GUI_Init();
		addMouseListener(new MouseAdapter() {// 添加鼠标事件监听器
			@Override
			public void mousePressed(MouseEvent e) {
				do_this_mousePressed(e);// 调用鼠标事件处理方法
			}
		});
	}

	private void setGreet() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
		int hour = Integer.valueOf(dateFormat.format(date));
		System.out.println(hour);
		if (hour >= 0 && hour < 6) {
			timeSign = "凌晨好！";
		} else if (hour >= 6 && hour < 11) {
			timeSign = "上午好！";
		} else if (hour >= 11 && hour < 14) {
			timeSign = "中午好！";
		} else if (hour >= 14 && hour < 19) {
			timeSign = "下午好！";
		} else {
			timeSign = "晚上好！";
		}
	}

	
	JPanel pl_Img, pl_Message;
	JLabel jl_Title;
	JTextArea text_Info;
	JButton bt_Close;

	private void GUI_Init() {
		// TODO 自动生成的方法存根
		String message = userName + "," + timeSign + "\n您有" + numFeedback + "条反馈信息尚未处理！\n请点此查看！";

		pl_Img = new Panel_Home("image/back.jpg");// 创建背景面板
		pl_Img.setLayout(new BorderLayout());
//		pl_Img.setBorder(new EmptyBorder(5, 20, 20, 20));
		getContentPane().add(pl_Img);

		pl_Message = new JPanel(null);
		pl_Message.setBackground(null);
		pl_Message.setOpaque(false);
		pl_Img.add(pl_Message);

		jl_Title = new JLabel("通  知", JLabel.CENTER);
		jl_Title.setBounds(129, 5, 100, 28);
		jl_Title.setFont(new Font("微软雅黑", 1, 20));
		jl_Title.setForeground(Color.WHITE);
		jl_Title.setBackground(Color.WHITE);
		jl_Title.setOpaque(false);
		pl_Message.add(jl_Title);

//		JPanel pl_Message = new Panel_Transparent(0.0f);
//		pl_Message.setLayout(null);
//		pl_Message.setBackground(null);
//		pl_Message.setOpaque(false);
//		pl_Message.setbound
//		panel.add(pl_Message);

		text_Info = new JTextArea(message);
		text_Info.setFont(new Font("微软雅黑", 1, 17));
		text_Info.setBackground(Color.WHITE);
		text_Info.setOpaque(false);
		text_Info.setEditable(false);
		text_Info.setBounds(59, 84, 241, 73);
		text_Info.setLineWrap(true);
		text_Info.addMouseListener(new MouseAdapter() {// 添加鼠标事件监听器
			@Override
			public void mousePressed(MouseEvent e) {
				do_this_mousePressed(e);// 调用鼠标事件处理方法
			}
		});
		pl_Message.add(text_Info);

		bt_Close = new JButton();
		bt_Close.setLocation(340, 3);
		bt_Close.setFocusPainted(false);
		bt_Close.setSize(15, 15);
		bt_Close.setForeground(Color.WHITE);
		bt_Close.setBackground(Color.WHITE);
		bt_Close.setOpaque(false);
		bt_Close.setBorderPainted(false);
		bt_Close.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/close.png")));
//		bt_Close.setContentAreaFilled(false);
		bt_Close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				dispose();
			}
		});
		pl_Message.add(bt_Close);

	}

	protected void do_this_mousePressed(MouseEvent e) {// 鼠标事件处理方法
		main.setClosing(true);
		main.doMessageAction();
		dispose();// 鼠标单击，则销毁这个窗体
	}
}
