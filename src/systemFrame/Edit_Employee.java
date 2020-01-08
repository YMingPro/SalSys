package systemFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import myModule.Frame_Edit;
import myModule.LimitWord_JTF;

public class Edit_Employee extends Frame_Edit implements ActionListener {

	private static final long serialVersionUID = 1L;

	JPanel pl_Frame, pl_Img, pl_User, pl_Info, pl_Menu;

	JPanel pl_InfoLText, pl_InfoLLabel, pl_InfoRText, pl_InfoRLabel;
	JPanel pl_UserLabel, pl_UserText;

	JComboBox<String> cb_WSex;

	JLabel jl_WNo, jl_WName, jl_WNative, jl_WPassword, jl_WSex, jl_Tel, jl_WIdentity, jl_WBirth;

	JButton bt_Save, bt_Alter,bt_Cancel;

	JTextField text_WIdentity, text_WTel, text_WPassword, text_WNo, text_WNative, text_WName, text_WBirth;

	Connection con;

	Frame_Main main;

	String temp_WNo, temp_WName, temp_WNative, temp_WPassword, temp_WSex, temp_WTel, temp_WIdentity, temp_WBirth;

	String wno, month, wsex, wbirth; // 方便引用
	String wnative, wpd, wid, wname, wtel;// 方便引用

	int Status = 0;

	public Edit_Employee(Connection con, Frame_Main main, Boolean view) {
		this.con = con;
		this.main = main;
		GUI_Init();
		setComboboxtiem();
		setFrameIcon();
		setSize(580, 366);
		setTitle("员工设置");
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(view);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/* *************初始界面************* */

	void GUI_Init() {

		pl_Img = new JPanel(new BorderLayout());
		pl_Img.setBorder(new EmptyBorder(15, 20, 15, 20));
		getContentPane().add(pl_Img);

		pl_Frame = new JPanel(null);
		pl_Img.add(pl_Frame);

		bt_Cancel = new JButton("");
		bt_Cancel.addActionListener(this);
		bt_Cancel.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		pl_Frame.add(bt_Cancel);
		
		Font font_label = new Font("微软雅黑", 1, 17);
		Font font_text = new Font("微软雅黑", 0, 14);
		Font font_button = new Font("微软雅黑", 1, 14);

		Border lineBorder = BorderFactory.createLineBorder(Color.black);
		Border border_Info = BorderFactory.createTitledBorder(lineBorder, "基本信息", TitledBorder.LEFT,
				TitledBorder.DEFAULT_POSITION, font_label);
		Border border_User = BorderFactory.createTitledBorder(lineBorder, "权限设置", TitledBorder.LEFT,
				TitledBorder.DEFAULT_POSITION, font_label);

		int X = 4, WIDTH = 525;

		/* <-----------------------信息面板-----------------------> */

		pl_Info = new JPanel(null);
		pl_Info.setBorder(border_Info);
		pl_Info.setBounds(X, 0, WIDTH, 206);
		pl_Frame.add(pl_Info);

		int y_infolabel = 30, hight_infolabel = 90, width_infolabel = 155;
		int y_infotext = 30, hight_infotext = 150, width_infotext = 155;

		/* <-------小分界线-------> */

		pl_InfoLLabel = new JPanel(new GridLayout(4, 1, 0, 10));
		pl_InfoLLabel.setBounds(10, y_infolabel, hight_infolabel, width_infolabel);
		pl_Info.add(pl_InfoLLabel);

		jl_WNo = new JLabel("员工号：", JLabel.CENTER);
		jl_WNo.setFont(font_label);
		pl_InfoLLabel.add(jl_WNo);

		jl_WBirth = new JLabel("生   日：", JLabel.CENTER);
		jl_WBirth.setFont(font_label);
		pl_InfoLLabel.add(jl_WBirth);

		jl_Tel = new JLabel("电   话：", JLabel.CENTER);
		jl_Tel.setFont(font_label);
		pl_InfoLLabel.add(jl_Tel);

		jl_WIdentity = new JLabel("身份证：", JLabel.CENTER);
		jl_WIdentity.setFont(font_label);
		pl_InfoLLabel.add(jl_WIdentity);

		/* <-------小分界线-------> */

		pl_InfoRLabel = new JPanel(new GridLayout(4, 1, 0, 10));
		pl_InfoRLabel.setBounds(260, y_infolabel, hight_infolabel, width_infolabel);
		pl_Info.add(pl_InfoRLabel);

		jl_WName = new JLabel("姓   名：", JLabel.CENTER);
		jl_WName.setFont(font_label);
		pl_InfoRLabel.add(jl_WName);

		jl_WSex = new JLabel("性   别：", JLabel.CENTER);
		jl_WSex.setFont(font_label);
		pl_InfoRLabel.add(jl_WSex);

		jl_WNative = new JLabel("籍   贯：", JLabel.CENTER);
		jl_WNative.setFont(font_label);
		pl_InfoRLabel.add(jl_WNative);

		/* <-------小分界线-------> */

		pl_InfoLText = new JPanel(new GridLayout(4, 1, 0, 10));
		pl_InfoLText.setBounds(100, y_infotext, hight_infotext, width_infotext);
		pl_Info.add(pl_InfoLText);

		text_WNo = new JTextField();
		text_WNo.setFont(font_text);
		text_WNo.setDocument(new LimitWord_JTF(6));
		text_WNo.setEditable(false);
		pl_InfoLText.add(text_WNo);

		text_WBirth = new JTextField();
		text_WBirth.setFont(font_text);
		text_WNo.setDocument(new LimitWord_JTF(10));
		pl_InfoLText.add(text_WBirth);
		text_WBirth.setColumns(10);

		text_WTel = new JTextField();
		pl_InfoLText.add(text_WTel);
		text_WTel.setColumns(10);
		text_WTel.setDocument(new LimitWord_JTF(11));
		text_WTel.setFont(font_text);

		text_WIdentity = new JTextField();
		pl_InfoLText.add(text_WIdentity);
		text_WIdentity.setFont(font_text);
		text_WIdentity.setDocument(new LimitWord_JTF(18));

		/* <-------小分界线-------> */

		pl_InfoRText = new JPanel(new GridLayout(4, 1, 0, 10));
		pl_InfoRText.setBounds(350, y_infotext, hight_infotext, width_infotext);
		pl_Info.add(pl_InfoRText);

		text_WName = new JTextField();
		text_WName.setFont(font_text);
		text_WName.setColumns(10);
		pl_InfoRText.add(text_WName);

		cb_WSex = new JComboBox<String>();
		cb_WSex.setFont(font_text);
		pl_InfoRText.add(cb_WSex);

		text_WNative = new JTextField();
		text_WNative.setFont(font_text);
		pl_InfoRText.add(text_WNative);

		/* <-----------------------按钮面板-----------------------> */

		pl_Menu = new JPanel(new GridLayout(1, 2, 30, 0));
		pl_Menu.setBounds(384, 240, 121, 43);
		pl_Frame.add(pl_Menu);

		bt_Save = new JButton("");
		bt_Save.setFont(font_button);
		bt_Save.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/save.png")));
		bt_Save.setFocusPainted(false);
		bt_Save.addActionListener(this);
		bt_Save.setBackground(Color.WHITE);
		bt_Save.setOpaque(false);
		pl_Menu.add(bt_Save);

		bt_Alter = new JButton("");
		bt_Alter.setFont(font_button);
		bt_Alter.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/edit.png")));
		bt_Alter.setFocusPainted(false);
		bt_Alter.addActionListener(this);
		bt_Alter.setBackground(Color.WHITE);
		bt_Alter.setOpaque(false);
		pl_Menu.add(bt_Alter);

		/* <-----------------------权限面板-----------------------> */

		pl_User = new JPanel((LayoutManager) null);
		pl_User.setBorder(border_User);
		pl_User.setBounds(4, 215, 350, 80);
		pl_Frame.add(pl_User);

		int y_user = 30, width_user = 30;

		jl_WPassword = new JLabel("密   码：", JLabel.CENTER);
		jl_WPassword.setBounds(10, y_user, 90, width_user);
		jl_WPassword.setFont(font_label);
		pl_User.add(jl_WPassword);

		text_WPassword = new JTextField();
		text_WPassword.setBounds(100, 30, 214, 30);
		text_WPassword.setDocument(new LimitWord_JTF(16));
		text_WPassword.setFont(font_text);
		pl_User.add(text_WPassword);

	}

	private void setFrameIcon() {
		Toolkit tool = getToolkit();
		Image image = tool.getImage(ClassLoader.getSystemResource("image/setupicon.png"));
		setIconImage(image);
	}
	
	/* *************设置数据************* */

	public void setData() {
		getInfomation();

		String sql1 = "select * from V_Worker Where WNo='" + wno + "' ";
		String sql2 = "select * from SalSys_Users Where WNo='" + wno + "' ";

//		System.err.println(sql1);
//		System.err.println(sql2);

		ResultSet rs1 = DB_Apply.getResult(con, sql1);
		ResultSet rs2 = DB_Apply.getResult(con, sql2);

		try {
			while (rs1.next()) {
				text_WName.setText(rs1.getString("WName"));
				text_WIdentity.setText(rs1.getString("WIdentity"));
				text_WTel.setText(rs1.getString("WTel"));
				text_WNative.setText(rs1.getString("WNative"));
				text_WBirth.setText(rs1.getString("WBirth"));
				cb_WSex.setSelectedItem(rs1.getString("WSex"));
				temp_WName = rs1.getString("WName");
				temp_WNative = rs1.getString("WNative");
				temp_WSex = rs1.getString("WSex");
				temp_WTel = rs1.getString("WTel");
				temp_WIdentity = rs1.getString("WIdentity");
				temp_WBirth = rs1.getString("WBirth");
			}
			while (rs2.next()) {
				text_WPassword.setText(rs2.getString("WPassword"));
				temp_WPassword = rs2.getString("WPassword");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "从数据库获取失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void setNo(String no, int model) {
		text_WNo.setText(no);
		if (model == 1) {
			setTextView(true);
			setTextNull();
			Status = 1;
			setButtonView(false, true);
		} else if (model == 0) {
			setTextView(true);
			setData();
			Status = 0;
			setButtonView(false, true);
		}
	}

	private void setComboboxtiem() {
		cb_WSex.addItem("男");
		cb_WSex.addItem("女");
		cb_WSex.addItem("");
		cb_WSex.setSelectedItem("");
	}

	/* *************主要功能************* */

	public void updateData() {
		try {
			getData();
			String sql1 = "UPDATE SalSys_Worker" + "\nSET" + "\nWName='" + wname + "' ,\nWIdentity='" + wid
					+ "' ,\nWNative='" + wnative + "' ,\nWBirth='" + wbirth + "',\nWTel='" + wtel + "',\nWSex='" + wsex
					+ "'\nWHERE WNo='" + wno + "'";
			String sql2 = "UPDATE SalSys_Users" + "\nSET" + "\nWPassword='" + wpd + "'\nWHERE WNo='" + wno + "'";
			DB_Apply.UpdateData(con, sql1, this);
			DB_Apply.UpdateData(con, sql2, this);
//			System.err.println(sql1);
//			System.err.println(sql2);
		} catch (Exception e) {
			setData();
			JOptionPane.showMessageDialog(null, e.getMessage(), "修改失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/* *************辅助方法************* */

	private void getInfomation() {
		wno = text_WNo.getText();
		wbirth = text_WBirth.getText();
		wsex = String.valueOf(cb_WSex.getSelectedItem());
	}

	private void getData() {
		wid = text_WIdentity.getText();
		wname = text_WName.getText();
		wnative = text_WNative.getText();
		wpd = text_WPassword.getText();
		wtel = text_WTel.getText();
		getInfomation();
	}

	private void setTextView(Boolean b) {
		text_WName.setEditable(b);
		text_WNative.setEditable(b);
		text_WTel.setEditable(b);
		text_WIdentity.setEditable(b);
		text_WPassword.setEditable(b);
		text_WBirth.setEditable(b);
		cb_WSex.setEnabled(b);
	}

	private void setTextNull() {
		cb_WSex.setSelectedItem("");
		text_WIdentity.setText("");
		text_WName.setText("");
		text_WNative.setText("");
		text_WPassword.setText("");
		text_WBirth.setText("");
		text_WTel.setText("");
	}

	private void setButtonView(Boolean alter, Boolean save) {
		bt_Alter.setEnabled(alter);
		bt_Save.setEnabled(save);
	}

	private boolean judgeData() {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		int nowyear = Integer.valueOf(dateFormat.format(date));

		getData();

		/* --------------------电话判断-------------------- */

		if (wtel.length() < 11) {
			JOptionPane.showMessageDialog(null, "电话号码应为11位", "输入错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		/* --------------------身份证判断-------------------- */
		if (wid.length() < 18) {
			JOptionPane.showMessageDialog(null, "身份证号应为18位", "输入错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		/* --------------------生日判断-------------------- */

		String regex = "\\d{4}-\\d{1,2}-\\d{1,2}";
		Pattern pattern = Pattern.compile(regex);
		Matcher isbirth = pattern.matcher(wbirth);
		if (isbirth.matches()) {
//			System.out.println(wbirth.substring(0, 4));
			int year = Integer.valueOf(wbirth.substring(0, 4));
			if (year < 1900 || year > nowyear) {
				JOptionPane.showMessageDialog(null, "出生年份输入错误，区间应为1900至今", "输入错误", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			/* <-------小分界线-------> */
			try {
//				System.out.println(wbirth.substring(5, 7));
				int month = Integer.valueOf(wbirth.substring(5, 7));
				if (month < 1 || month > 12) {
					JOptionPane.showMessageDialog(null, "出生月份输入错误，区间应为1到12", "输入错误", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				try {
//					System.out.println(wbirth.substring(8, wbirth.length()));
					int day = Integer.valueOf(wbirth.substring(8, wbirth.length()));
					if (day < 1 || day > 31) {
						JOptionPane.showMessageDialog(null, "出生日期输入错误，区间应为1到31", "输入错误", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				} catch (Exception e) {
					System.out.println("???");
				}

			} catch (Exception e) {
//				System.out.println(wbirth.substring(5, 6));
				int month = Integer.valueOf(wbirth.substring(5, 6));
				if (month < 1 || month > 12) {
					JOptionPane.showMessageDialog(null, "出生月份输入错误，区间应为1到12", "输入错误", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				try {
//					System.out.println(wbirth.substring(7, wbirth.length()));
					int day = Integer.valueOf(wbirth.substring(7, wbirth.length()));
					if (day < 1 || day > 31) {
						JOptionPane.showMessageDialog(null, "出生日期输入错误，区间应为1到31", "输入错误", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				} catch (Exception ex) {
					System.out.println("???");
				}
			}

		} else {
			JOptionPane.showMessageDialog(null, "出生日期格式错误，应为YYYY-MM-DD", "输入错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		/* --------------------生日判断-------------------- */

		return true;
	}

	private void updateMain() {
		main.setReport(DB_Apply.getDVector(DB_Apply.getResult(con, main.judge.sql_Worker)), main.judge.V_Worker);
	}
	
	/* *************主类引用************* */

	public void setAlterModel(String[] s, ResultSet rs) {
		setNo(s[0], 0);
	}

	/* *************方法实现************* */

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根

		/* --------------------修改-------------------- */

		if (e.getSource() == bt_Alter) {
			Status = 0;
			setTextView(true);
			setButtonView(false, true);
		}

		/* --------------------保存-------------------- */

		if (e.getSource() == bt_Save) {
			getData();
			if (cb_WSex.getSelectedItem().equals("")) {
				System.out.println("有选项为空");
				setTextNull();
			} else if (text_WBirth.getText().equals(temp_WBirth) && text_WIdentity.getText().equals(temp_WIdentity)
					&& text_WName.getText().equals(temp_WName) && text_WNative.getText().equals(temp_WNative)
					&& text_WPassword.getText().equals(temp_WPassword) && text_WTel.getText().equals(temp_WTel)
					&& cb_WSex.getSelectedItem().equals(temp_WSex)) {
			} else {
				if (judgeData()) {
					int f = JOptionPane.showConfirmDialog(null, "该操作将会影响该员工相关的数据，是否更新信息？");
					if (f == 0) {
						updateData();
						updateMain();
					} else {
						setData();
					}
				} else {
					setData();
				}
			}
			setButtonView(true, false);
			setTextView(false);
		}
		
		if (e.getSource() == bt_Cancel) {
			dispose();
		}

	}

}
