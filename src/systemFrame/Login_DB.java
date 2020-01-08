package systemFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import myModule.LimitWord_JTF;
import myModule.Panel_Home;
import myModule.Panel_Transparent;

public class Login_DB extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel pl_Img, jp_Left, jp_Right, pl_Login;

	private JLabel jl_Title, jl_Topic, jl_StatusBar;
	private JLabel jl_ServerName, jl_DBName, jl_User, jl_Pwd;

	private JTextField text_ServerName, text_DBName, text_User;
	private JPasswordField text_Password;

	private JButton bt_Login;

	private Connection con;

	private int STATU = 0;

	public Login_DB() {
		GUI_Init();
		setFrameIcon();
		setUp();
		setTitle("连接数据库");
		setSize(450, 750);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				doExit(e);
			}
		});
		
	}

	/* *************初始界面************* */
	private void GUI_Init() {

		pl_Img = new Panel_Home("image/db.jpg");
		pl_Img.setLayout(new BorderLayout());
		pl_Img.setBorder(new EmptyBorder(50, 50, 50, 50));
		getContentPane().add(pl_Img);

		pl_Login = new Panel_Transparent(0.3F);
		pl_Login.setLayout(null);
		pl_Login.setBackground(null);
		pl_Login.setOpaque(false);
		pl_Login.setBounds(0, 0, 400, 300);
		pl_Img.add(pl_Login);

		/* <-----------------------标题/状态-----------------------> */

		jl_Title = new JLabel("工资管理系统", JLabel.CENTER);
		jl_Title.setForeground(Color.white);
		jl_Title.setFont(new Font("方正新舒体简体", 1, 43));
		jl_Title.setBounds(30, 52, 284, 50);
		pl_Login.add(jl_Title);

		jl_Topic = new JLabel("数据库连接", JLabel.CENTER);
		jl_Topic.setForeground(Color.white);
		jl_Topic.setFont(new Font("迷你简卡通", 1, 30));
		jl_Topic.setBounds(77, 119, 189, 40);
		pl_Login.add(jl_Topic);

		jl_StatusBar = new JLabel("当前状态：未登录", JLabel.LEFT);
		jl_StatusBar.setFont(new Font("等线", 1, 13));
		jl_StatusBar.setForeground(Color.white);
		jl_StatusBar.setBounds(30, 565, 284, 22);
		pl_Login.add(jl_StatusBar);

		/* <-----------------------标签面板-----------------------> */

		Font labelfont = new Font("迷你简卡通", 1, 25);
		Font textfont = new Font("等线", 1, 18);

		jp_Left = new JPanel(new GridLayout(4, 1, 0, 30));
		jp_Left.setBackground(null);
		jp_Left.setOpaque(false);
		jp_Left.setBounds(29, 185, 105, 274);
		pl_Login.add(jp_Left);

		jl_ServerName = new JLabel("服务器名", JLabel.CENTER);
		jl_ServerName.setForeground(Color.white);
		jl_ServerName.setFont(labelfont);
		jl_ServerName.setBounds(0, 0, 104, 35);
		jp_Left.add(jl_ServerName);

		jl_DBName = new JLabel("数据库名", JLabel.CENTER);
		jl_DBName.setForeground(Color.white);
		jl_DBName.setFont(labelfont);
		jl_DBName.setBounds(0, 0, 104, 35);
		jp_Left.add(jl_DBName);

		jl_User = new JLabel("用  户  名", JLabel.CENTER);
		jl_User.setForeground(Color.white);
		jl_User.setFont(labelfont);
		jl_User.setBounds(0, 0, 78, 35);
		jp_Left.add(jl_User);

		jl_Pwd = new JLabel("密       码", JLabel.CENTER);
		jl_Pwd.setForeground(Color.white);
		jl_Pwd.setFont(labelfont);
		jl_Pwd.setBounds(0, 0, 76, 35);
		jp_Left.add(jl_Pwd);

		/* <-----------------------输入面板-----------------------> */

		jp_Right = new JPanel(new GridLayout(4, 1, 0, 40));
		jp_Right.setBackground(null);
		jp_Right.setOpaque(false);
		jp_Right.setBounds(144, 190, 169, 267);
		pl_Login.add(jp_Right);

		text_ServerName = new JTextField();
		text_ServerName.setBorder(null);
		text_ServerName.setDocument(new LimitWord_JTF(16));
		text_ServerName.setForeground(Color.gray);
		text_ServerName.setFont(textfont);
		jp_Right.add(text_ServerName);

		text_DBName = new JTextField();
		text_DBName.setBorder(null);
		text_DBName.setDocument(new LimitWord_JTF(16));
		text_DBName.setForeground(Color.gray);
		text_DBName.setFont(textfont);
		jp_Right.add(text_DBName);

		text_User = new JTextField();
		text_User.setBorder(null);
		text_User.setDocument(new LimitWord_JTF(16));
		text_User.setForeground(Color.gray);
		text_User.setFont(textfont);
		jp_Right.add(text_User);

		text_Password = new JPasswordField();
		text_Password.addActionListener(this);
		text_Password.setBorder(null);
		text_Password.setDocument(new LimitWord_JTF(16));
		text_Password.setForeground(Color.gray);
		text_Password.setFont(textfont);
		jp_Right.add(text_Password);

		/* <-----------------------按钮面板-----------------------> */

		bt_Login = new JButton("连    接");
		bt_Login.addActionListener(this);
		bt_Login.setForeground(Color.WHITE);
		bt_Login.setFocusPainted(false);
		bt_Login.setFont(new Font("迷你简卡通", 1, 22));
		bt_Login.setBackground(new Color(0, 130, 220));
		bt_Login.setBounds(29, 510, 285, 45);
		bt_Login.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		pl_Login.add(bt_Login);
		

	}

	private void setUp() {
//		text_ServerName.setText("192.168.8.100");
		text_ServerName.setText("localhost");
		text_DBName.setText("SalSys");
		text_User.setText("sa");
		text_Password.setText("123456");
		pl_Login.add(new JTextField());
//		bt_Login.doClick();
	}

	private void setFrameIcon() {
		Toolkit tool = getToolkit();
		Image image = tool.getImage(ClassLoader.getSystemResource("image/dbicon.png"));
		setIconImage(image);
	}

	/* *************连接验证************* */

	private void Connect() {
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//		String dbURL = "jdbc:sqlserver://localhost:1433;" + "instanceName=" + text_ServerName.getText() + ";DatabaseName="
		String dbURL = "jdbc:sqlserver://" + text_ServerName.getText() + ":1433;DatabaseName=" + text_DBName.getText();
//		String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=SalSys";
		System.out.println(dbURL);
		String userName = text_User.getText();
		String userPwd = String.valueOf(text_Password.getPassword());

		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(100);
					jl_StatusBar.setText("当前状态：检查配置中，请稍后......");
					Thread.sleep(200);
					Class.forName(driverName).newInstance();
					STATU = 1;
				} catch (InterruptedException e) {
					JOptionPane.showMessageDialog(null, e.getStackTrace(), "提示", JOptionPane.INFORMATION_MESSAGE);
				} catch (InstantiationException | IllegalAccessException e) {
					JOptionPane.showMessageDialog(null, e.getStackTrace(), "提示", JOptionPane.INFORMATION_MESSAGE);
				} catch (ClassNotFoundException e) {
					try {
						try {
							Thread.sleep(100);
							JOptionPane.showMessageDialog(null, "请检查是否配置好JDBC驱动包！", "数据库驱动加载失败",
									JOptionPane.WARNING_MESSAGE);
							Thread.sleep(100);
							jl_StatusBar.setText("当前状态：尚未配置好JDBC驱动包");
							return;
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					} catch (Exception ex) {
					}
					return;
				}

				/* <-------小分界线-------> */

				if (STATU == 1) {
					try {
						Thread.sleep(100);
						jl_StatusBar.setText("当前状态：登录中，请稍后......");
						Thread.sleep(100);
						con = DriverManager.getConnection(dbURL, userName, userPwd);
						Thread.sleep(100);
						jl_StatusBar.setText("当前状态：连接数据库成功");
						dispose();
					} catch (SQLException e) {
						try {
							Thread.sleep(100);
							JOptionPane.showMessageDialog(null, "请检查是否输入错误！", "登录失败", JOptionPane.WARNING_MESSAGE);
							Thread.sleep(100);
							jl_StatusBar.setText("当前状态：连接失败");
							return;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} catch (InterruptedException e) {
						JOptionPane.showMessageDialog(null, e.getStackTrace(), "提示", JOptionPane.INFORMATION_MESSAGE);
					}
					new Login_User(con);
				}
			}
		}.start();
	}

	/* *************方法实现************* */

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO 自动生成的方法存根
		Connect();
	}

	public void doExit(WindowEvent e) {
		// TODO 自动生成的方法存根
		int option = JOptionPane.showConfirmDialog(Login_DB.this, "确定退出系统? ", "提示 ", JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION)
			if (e.getWindow() == Login_DB.this) {
				System.exit(0);
			} else {
				return;
			}
	}

}
