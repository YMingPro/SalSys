package systemFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import myModule.LimitWord_JTF;
import myModule.Panel_Home;
import myModule.Panel_Transparent;

public class Login_DB_plus extends JFrame implements ActionListener, ItemListener {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_DB_plus frame = new Login_DB_plus();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	private static final long serialVersionUID = 1L;

	private Panel_Transparent pl_Login;
	private JPanel pl_Img, jp_left, jp_right;

	private JLabel jl_Title, jl_Topic, Status;
	private JLabel server_Name, db_Name, db_User, db_pwd;

	private JTextField text_ServerName, text_DBName, text_User;
	private JPasswordField text_Password;

	private JButton bt_Login;

	private Connection con;

	private int STATU = 0;

	JRadioButton localConnect, remoteConnect;

	String connectName;

	public Login_DB_plus() {
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
		jl_Topic.setBounds(77, 115, 189, 40);
		pl_Login.add(jl_Topic);

		Status = new JLabel("当前状态：未登录", JLabel.LEFT);
		Status.setFont(new Font("等线", 1, 13));
		Status.setForeground(Color.white);
		Status.setBounds(30, 565, 284, 22);
		pl_Login.add(Status);

		/* <-----------------------连接选项-----------------------> */

		localConnect = new JRadioButton("本地连接");
		localConnect.setBounds(140, 230, 85, 23);
		localConnect.setFont(new Font("等线", 1, 15));
		localConnect.setOpaque(false);
		localConnect.setFocusPainted(false);
		localConnect.setForeground(Color.white);
		localConnect.setSelected(true);
		localConnect.addItemListener(this);
		pl_Login.add(localConnect);

		remoteConnect = new JRadioButton("远程连接");
		remoteConnect.setBounds(225, 230, 85, 23);
		remoteConnect.setFont(new Font("等线", 1, 15));
		remoteConnect.setOpaque(false);
		remoteConnect.setFocusPainted(false);
		remoteConnect.setForeground(Color.white);
		remoteConnect.addItemListener(this);
		pl_Login.add(remoteConnect);

		ButtonGroup group = new ButtonGroup();
		group.add(localConnect);
		group.add(remoteConnect);

		/* <-----------------------标签面板-----------------------> */

		Font labelfont = new Font("迷你简卡通", 1, 25);
		Font textfont = new Font("等线", 1, 18);

		jp_left = new JPanel(new GridLayout(4, 1, 0, 30));
		jp_left.setBackground(null);
		jp_left.setOpaque(false);
		jp_left.setBounds(29, 185, 105, 274);
		pl_Login.add(jp_left);

		server_Name = new JLabel("服务器名", JLabel.CENTER);
		server_Name.setForeground(Color.white);
		server_Name.setFont(labelfont);
		server_Name.setBounds(0, 0, 104, 35);
		jp_left.add(server_Name);

		db_Name = new JLabel("数据库名", JLabel.CENTER);
		db_Name.setForeground(Color.white);
		db_Name.setFont(labelfont);
		db_Name.setBounds(0, 0, 104, 35);
		jp_left.add(db_Name);

		db_User = new JLabel("用  户  名", JLabel.CENTER);
		db_User.setForeground(Color.white);
		db_User.setFont(labelfont);
		db_User.setBounds(0, 0, 78, 35);
		jp_left.add(db_User);

		db_pwd = new JLabel("密       码", JLabel.CENTER);
		db_pwd.setForeground(Color.white);
		db_pwd.setFont(labelfont);
		db_pwd.setBounds(0, 0, 76, 35);
		jp_left.add(db_pwd);

		/* <-----------------------输入面板-----------------------> */

		jp_right = new JPanel(new GridLayout(4, 1, 0, 40));
		jp_right.setBackground(null);
		jp_right.setOpaque(false);
		jp_right.setBounds(144, 190, 169, 267);
		pl_Login.add(jp_right);

		text_ServerName = new JTextField();
		text_ServerName.setBorder(null);
		text_ServerName.setDocument(new LimitWord_JTF(16));
		text_ServerName.setForeground(Color.gray);
		text_ServerName.setFont(textfont);
		text_ServerName.setEditable(false);
		jp_right.add(text_ServerName);

		text_DBName = new JTextField();
		text_DBName.setBorder(null);
		text_DBName.setDocument(new LimitWord_JTF(16));
		text_DBName.setForeground(Color.gray);
		text_DBName.setFont(textfont);
		jp_right.add(text_DBName);

		text_User = new JTextField();
		text_User.setBorder(null);
		text_User.setDocument(new LimitWord_JTF(16));
		text_User.setForeground(Color.gray);
		text_User.setFont(textfont);
		jp_right.add(text_User);

		text_Password = new JPasswordField();
		text_Password.addActionListener(this);
		text_Password.setBorder(null);
		text_Password.setDocument(new LimitWord_JTF(16));
		text_Password.setForeground(Color.gray);
		text_Password.setFont(textfont);
		jp_right.add(text_Password);

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
//			text_ServerName.setText("192.168.8.100");
		text_ServerName.setText("localhost");
		text_DBName.setText("SalSys");
		text_User.setText("sa");
		text_Password.setText("123456");
		pl_Login.add(new JTextField());

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
		String userName = text_User.getText();
		String userPwd = String.valueOf(text_Password.getPassword());

		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(100);
					Status.setText("当前状态：检查配置中，请稍后......");
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
							Status.setText("当前状态：尚未配置好JDBC驱动包");
							return;
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					} catch (Exception ex) {
						// TODO: handle exception
					}
					return;
				}

				/* <-------小分界线-------> */

				if (STATU == 1) {
					try {
						Thread.sleep(100);
						Status.setText("当前状态：登录中，请稍后......");
						Thread.sleep(100);
						con = DriverManager.getConnection(dbURL, userName, userPwd);
						Thread.sleep(100);
						Status.setText("当前状态：连接数据库成功");
						dispose();
					} catch (SQLException e) {
						try {
							Thread.sleep(100);
							JOptionPane.showMessageDialog(null, "请检查是否输入错误！", "登录失败", JOptionPane.WARNING_MESSAGE);
							Thread.sleep(100);
							Status.setText("当前状态：连接失败");
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
		if (matches(text_ServerName.getText()) || text_ServerName.getText().equals("localhost")) {
			Connect();
		} else {
			JOptionPane.showMessageDialog(null, "服务器名不是一个合法的IP地址！", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public boolean matches(String text) {
		if (text != null && !text.isEmpty()) {
			// 定义正则表达式
			String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
					+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
					+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
			if (text.matches(regex)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO 自动生成的方法存根
		if (e.getSource() == localConnect) {
			text_ServerName.setEditable(false);
			text_ServerName.setText("localhost");
		}
		if (e.getSource() == remoteConnect) {
			text_ServerName.setEditable(true);
			text_ServerName.requestFocus();
			text_ServerName.setText("");
		}
	}

	public void doExit(WindowEvent e) {
		// TODO 自动生成的方法存根
		int option = JOptionPane.showConfirmDialog(Login_DB_plus.this, "确定退出系统? ", "提示 ", JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION)
			if (e.getWindow() == Login_DB_plus.this) {
				System.exit(0);
			} else {
				return;
			}
	}
}
