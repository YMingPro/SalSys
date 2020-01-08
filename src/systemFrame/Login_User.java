package systemFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import myModule.Button_Round;
import myModule.HintListener_JPF;
import myModule.HintListener_JTF;
import myModule.LimitWord_JTF;
import myModule.Panel_Home;
import systemFunction.VerticationCode;

public class Login_User extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JLabel jl_Title, jl_Slogan, jl_StatusBar, jl_Code;

	private JPanel pl_Img, pl_Login, pl_Code;

	private JTextField text_UserID, text_Code;
	private JPasswordField text_userPWD;

	private Button_Round bt_Login;

	private Connection con;

	private String code;

	static final String INFO_USERNAME = "请输入用户名";
	static final String INFO_USERPSD = "请输入密码";
	static final String INFO_CODE = "验证码";
	static char defaultChar;

	JOptionPane optionpane;

	public Login_User(Connection con) {
		this.con = con;
		GUI_Init();
		setFrameIcon();
		getPicture(jl_Code, pl_Code);
		setTitle("用户登录");
		setSize(1094, 615);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setup();
		
	}

	/* *************初始界面************* */

	private void setup() {
		text_UserID.setText("200001");
		text_userPWD.setText("1234");
		text_Code.setText(code);
//		bt_Login.doClick();
	}

	private void GUI_Init() {

		pl_Img = new Panel_Home("image/user.jpg");
		pl_Img.setLayout(new BorderLayout());
		pl_Img.setBorder(new EmptyBorder(99, 211, 98, 196));
		pl_Img.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				JPanel jp = (JPanel) arg0.getSource();
				jp.requestFocus();
			}
		});
		getContentPane().add(pl_Img);

		pl_Login = new JPanel(null);
		pl_Login.setOpaque(false);
		pl_Login.setBounds(0, 0, 400, 300);
		pl_Login.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				JPanel jp = (JPanel) arg0.getSource();
				jp.requestFocus();
			}
		});
		pl_Img.add(pl_Login);

		Font font = new Font("", 0, 13);

		/* <-----------------------标题/状态-----------------------> */

		jl_Title = new JLabel("工资管理系统", JLabel.CENTER);
		jl_Title.setBounds(390, 55, 236, 40);
		jl_Title.setFont(new Font("方正兰亭准黑_GBK", 0, 35));
		pl_Login.add(jl_Title);

		jl_Slogan = new JLabel("一款让工资管理更方便的系统", JLabel.LEFT);
		jl_Slogan.setBounds(405, 105, 165, 21);
		jl_Slogan.setFont(new Font("方正兰亭准黑_GBK", 0, 12));
		jl_Slogan.setForeground(Color.gray);
		pl_Login.add(jl_Slogan);

		jl_StatusBar = new JLabel("● 请登录！");
		jl_StatusBar.setBounds(15, 358, 291, 21);
		jl_StatusBar.setFont(new Font("微软雅黑", 0, 12));
		jl_StatusBar.setForeground(Color.gray);
		pl_Login.add(jl_StatusBar);

		/* <-----------------------輸入面板-----------------------> */

		text_UserID = new JTextField();
		text_UserID.setBounds(440, 146, 160, 30);
		text_UserID.setFont(font);
		text_UserID.setBorder(null);
		text_UserID.setDocument(new LimitWord_JTF(16));
		text_UserID.addFocusListener(new HintListener_JTF(text_UserID, INFO_USERNAME));
		text_UserID.setText(INFO_USERNAME);
		pl_Login.add(text_UserID);

		text_userPWD = new JPasswordField();
		text_userPWD.setBounds(440, 187, 160, 30);
		text_userPWD.setFont(font);
		defaultChar = text_userPWD.getEchoChar();
		text_userPWD.setEchoChar('\0');
		text_userPWD.setBorder(null);
		text_userPWD.setDocument(new LimitWord_JTF(16));
		text_userPWD.addFocusListener(new HintListener_JPF(text_userPWD, defaultChar, INFO_USERPSD));
		text_userPWD.setText(INFO_USERPSD);
		pl_Login.add(text_userPWD);

		text_Code = new JTextField();
		text_Code.setBounds(441, 227, 85, 30);
		text_Code.setFont(font);
		text_Code.setBorder(null);
		text_Code.setForeground(Color.gray);
		text_Code.setDocument(new LimitWord_JTF(4));
		text_Code.addFocusListener(new HintListener_JTF(text_Code, INFO_CODE));
		text_Code.setText(INFO_CODE);
		pl_Login.add(text_Code);

		/* <-----------------------验证码面板-----------------------> */

		pl_Code = new JPanel(null);
		pl_Code.setBounds(530, 226, 80, 30);
		pl_Code.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 验证码图片单击事件
				if (e.getClickCount() == 1) {
					// 重新获取验证码
					getPicture(jl_Code, pl_Code);
				}
			}
		});
		pl_Login.add(pl_Code);

		jl_Code = new JLabel();
		jl_Code.setBounds(0, 0, 80, 30);
		pl_Code.add(jl_Code);

		/* <-----------------------按钮面板-----------------------> */

		bt_Login = new Button_Round("登 录");
		bt_Login.setColor(new Color(114, 167, 252), new Color(79, 147, 254));
		bt_Login.setAngle(35, 35);
		bt_Login.setForeground(Color.WHITE);
		bt_Login.setBounds(412, 285, 195, 40);
		bt_Login.setFocusPainted(false);
		bt_Login.setFont(new Font("微软雅黑", 1, 20));
		bt_Login.addActionListener(this);
		bt_Login.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		pl_Login.add(bt_Login);
		pl_Login.add(new JTextField());

	}

	private void setFrameIcon() {
		Toolkit tool = getToolkit();
		Image image = tool.getImage(ClassLoader.getSystemResource("image/usericon.png"));
		setIconImage(image);
	}

	/* *************获取验证码************* */

	private void getPicture(JLabel label, JPanel panel) {
		Object[] obj = VerticationCode.createImage();
		code = obj[0].toString();
//		System.out.println("1:" + code);
		ImageIcon img = new ImageIcon((BufferedImage) obj[1]);
		label.setIcon((Icon) img);
		panel.add(label);
		text_Code.setText(INFO_CODE);
		text_Code.setForeground(Color.gray);
	}

	/* *************连接验证************* */

	private void Confirm() {

		Statement statement = null;
		ResultSet rs = null;

		String pwd, no, right, name;
		String codelower = code.toLowerCase(), codeuper = code.toUpperCase();

		String codeNum=text_Code.getText().toLowerCase();
		
		
		jl_StatusBar.setText("● 登录中，请稍后......");

//		if (text_Code.getText().equals(codelower) || text_Code.getText().equals(codeuper)) {
			if (codeNum.equals(codelower)) {

			try {
				if (con == null)
					return;
				statement = con.createStatement();
				rs = statement.executeQuery(
						"select A.WNo,WName,WPassword,WRight from SalSys_Users A,SalSys_Worker B where A.WNo=B.WNo AND A.WNo="
								+ text_UserID.getText().trim());
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "用户名不能为空或不存在该用户！", "错误", JOptionPane.WARNING_MESSAGE);
				getPicture(jl_Code, pl_Code);
				jl_StatusBar.setText("● 登录失败（用户名为空或用户不存在）");
				return;
			}

			/* <-------小分界线-------> */

			try {
				if (rs.next()) {
					pwd = rs.getString("WPassword");
					no = rs.getString("WNo");
					right = rs.getString("WRight");
					name = rs.getString("WName");

					if (!pwd.equals(String.valueOf(text_userPWD.getPassword()))
							|| !no.equals(text_UserID.getText().trim())) {
						JOptionPane.showMessageDialog(null, "用户名或密码错误！", "错误", JOptionPane.WARNING_MESSAGE);
						getPicture(jl_Code, pl_Code);
						jl_StatusBar.setText("● 登录失败（用户名或输入错误）");
						return;
					}

					/* <-------小分界线-------> */

					if (right.equals("管理员")) {
						jl_StatusBar.setText("● 登录成功（管理员）");
						new Frame_Main(con, name, right, no);
						this.dispose();
					} else if (right.equals("普通用户")) {
						jl_StatusBar.setText("● 登录成功（普通用户）");
						new Frame_Main(con, name, right, no);
						this.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "不存在该用户！", "提示", JOptionPane.INFORMATION_MESSAGE);
					jl_StatusBar.setText("● 登录失败（不存在该用户）");
					getPicture(jl_Code, pl_Code);
					return;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showConfirmDialog(null, "输入错误！");
				getPicture(jl_Code, pl_Code);
				jl_StatusBar.setText("● 登录失败");
				return;
			}
		} else {
			JOptionPane.showMessageDialog(null, "验证码为空或者验证码错误！", "错误", JOptionPane.WARNING_MESSAGE);
			jl_StatusBar.setText("● 登录中断（验证码为空或者验证码错误）");
			getPicture(jl_Code, pl_Code);
		}
	}

	/* *************方法实现************* */

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO 自动生成的方法存根
		Confirm();
	}
}
