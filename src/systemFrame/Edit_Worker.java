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
import javax.swing.DefaultComboBoxModel;
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
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import myModule.Frame_Edit;
import myModule.LimitWord_JTF;

public class Edit_Worker extends Frame_Edit implements ActionListener {

	private static final long serialVersionUID = 1L;

	JPanel pl_Frame, pl_Img, pl_User, pl_Info, pl_Depart, pl_Button, pl_Menu;

	JPanel pl_InfoLText, pl_InfoLLabel, pl_InfoRText, pl_InfoRLabel;
	JPanel pl_DepartLText, pl_DepartLLabel, pl_DepartRLabel, pl_DepartRText;
	JPanel pl_UserLabel, pl_UserText;

	JComboBox<String> cb_PName, cb_WRight, cb_EEdu, cb_JName, cb_DName, cb_WSex;

	JLabel jl_WNo, jl_WName, jl_DName, jl_PName, jl_JName, jl_EEdu, jl_WNative, jl_WEnter, jl_WRight, jl_WPassword,
			jl_WSex, jl_Tel, jl_WIdentity, jl_WBirth;

	JButton bt_Add, bt_Save, bt_Delet, bt_up, bt_down, bt_Alter, bt_Cancel;

	JTextField text_WIdentity, text_WTel, text_WPassword, text_WEnter, text_WNo, text_WNative, text_WName, text_WBirth;

	Connection con;

	Frame_Main main;

	String temp_WNo, temp_WName, temp_DName, temp_PName, temp_JName, temp_EEdu, temp_WNative, temp_WEnter, temp_WRight,
			temp_WPassword, temp_WSex, temp_WTel, temp_WIdentity, temp_WBirth;

	String wno, wenter, month, edu, pname, wright, jname, dname, wsex, dno, wbirth; // 方便引用
	String wnative, wpd, wid, wname, wtel;// 方便引用

	String newNo;

	String[] data;

	int Status = 0;

	int tempfirst;

	public Edit_Worker(Connection con, Frame_Main main, Boolean view) {
		this.con = con;
		this.main = main;
		GUI_Init();
		setComboboxtiem();
		setFrameIcon();
		setSize(580, 540);
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
		Border border_Depart = BorderFactory.createTitledBorder(lineBorder, "部门职业", TitledBorder.LEFT,
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

		jl_EEdu = new JLabel("学   历：", JLabel.CENTER);
		jl_EEdu.setFont(font_label);
		pl_InfoRLabel.add(jl_EEdu);

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
		text_WName.setColumns(8);
		pl_InfoRText.add(text_WName);

		cb_WSex = new JComboBox<String>();
		cb_WSex.setFont(font_text);
		pl_InfoRText.add(cb_WSex);

		text_WNative = new JTextField();
		text_WNative.setFont(font_text);
		text_WName.setColumns(8);
		pl_InfoRText.add(text_WNative);

		cb_EEdu = new JComboBox<String>();
		cb_EEdu.setFont(font_text);
		pl_InfoRText.add(cb_EEdu);

		/* <-----------------------部门面板-----------------------> */

		pl_Depart = new JPanel(null);
		pl_Depart.setBorder(border_Depart);
		pl_Depart.setBounds(X, 215, WIDTH, 120);
		pl_Frame.add(pl_Depart);

		int y_departlabel = 30, hight_departlabel = 90, width_departlabel = 70;
		int y_departtext = 30, hight_departtext = 150, width_departtext = 70;

		/* <-------小分界线-------> */

		pl_DepartLLabel = new JPanel(new GridLayout(2, 1, 0, 10));
		pl_DepartLLabel.setBounds(10, y_departlabel, hight_departlabel, width_departlabel);
		pl_Depart.add(pl_DepartLLabel);

		jl_JName = new JLabel("职   务：", JLabel.CENTER);
		jl_JName.setHorizontalAlignment(SwingConstants.CENTER);
		jl_JName.setFont(font_label);
		pl_DepartLLabel.add(jl_JName);

		jl_PName = new JLabel("职   称：", JLabel.CENTER);
		jl_PName.setHorizontalAlignment(SwingConstants.CENTER);
		jl_PName.setFont(font_label);
		pl_DepartLLabel.add(jl_PName);

		/* <-------小分界线-------> */

		pl_DepartRLabel = new JPanel(new GridLayout(2, 1, 0, 10));
		pl_DepartRLabel.setBounds(260, y_departlabel, hight_departlabel, width_departlabel);
		pl_Depart.add(pl_DepartRLabel);

		jl_DName = new JLabel("所属部门：", JLabel.CENTER);
		jl_DName.setHorizontalAlignment(SwingConstants.CENTER);
		jl_DName.setFont(font_label);
		pl_DepartRLabel.add(jl_DName);

		jl_WEnter = new JLabel("入职年份：", JLabel.CENTER);
		jl_WEnter.setFont(font_label);
		pl_DepartRLabel.add(jl_WEnter);

		/* <-------小分界线-------> */

		pl_DepartLText = new JPanel(new GridLayout(2, 1, 0, 10));
		pl_DepartLText.setBounds(100, y_departtext, hight_departtext, width_departtext);
		pl_Depart.add(pl_DepartLText);

		cb_JName = new JComboBox<String>();
		cb_JName.setFont(font_text);
		pl_DepartLText.add(cb_JName);

		cb_PName = new JComboBox<String>();
		cb_PName.setFont(font_text);
		pl_DepartLText.add(cb_PName);

		/* <-------小分界线-------> */

		pl_DepartRText = new JPanel(new GridLayout(2, 1, 0, 10));
		pl_DepartRText.setBounds(350, y_departtext, hight_departtext, width_departtext);
		pl_Depart.add(pl_DepartRText);

		cb_DName = new JComboBox<String>();
		cb_DName.setFont(font_text);
		pl_DepartRText.add(cb_DName);

		text_WEnter = new JTextField();
		text_WEnter.setFont(font_text);
		text_WEnter.setDocument(new LimitWord_JTF(4));
		pl_DepartRText.add(text_WEnter);
		text_WEnter.setColumns(10);

		/* <-----------------------按钮面板-----------------------> */

		pl_Menu = new JPanel(new GridLayout(2, 2, 10, 10));
		pl_Menu.setBounds(330, 370, 80, 80);
		pl_Frame.add(pl_Menu);

		bt_Add = new JButton("");
		bt_Add.setFont(font_button);
		bt_Add.setFocusPainted(false);
		bt_Add.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/add.png")));
		bt_Add.addActionListener(this);
		bt_Add.setBackground(Color.WHITE);
		bt_Add.setOpaque(false);
		pl_Menu.add(bt_Add);

		bt_Delet = new JButton("");
		bt_Delet.setFocusPainted(false);
		bt_Delet.setFont(font_button);
		bt_Delet.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/del.png")));
		bt_Delet.addActionListener(this);
		bt_Delet.setBackground(Color.WHITE);
		bt_Delet.setOpaque(false);
		pl_Menu.add(bt_Delet);

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
		pl_User.setBounds(X, 345, 305, 120);
		pl_Frame.add(pl_User);

		int y_user = 30, width_user = 70;
		/* <-------小分界线-------> */

		pl_UserLabel = new JPanel(new GridLayout(2, 1, 0, 10));
		pl_UserLabel.setBounds(10, y_user, 90, width_user);
		pl_User.add(pl_UserLabel);

		jl_WRight = new JLabel("权   限：", JLabel.CENTER);
		jl_WRight.setFont(font_label);
		pl_UserLabel.add(jl_WRight);

		jl_WPassword = new JLabel("密   码：", JLabel.CENTER);
		jl_WPassword.setFont(font_label);
		pl_UserLabel.add(jl_WPassword);

		/* <-------小分界线-------> */

		pl_UserText = new JPanel(new GridLayout(2, 1, 0, 10));
		pl_UserText.setBounds(100, y_user, 180, width_user);
		pl_User.add(pl_UserText);

		cb_WRight = new JComboBox<String>();
		cb_WRight.setFont(font_text);
		pl_UserText.add(cb_WRight);

		text_WPassword = new JTextField();
		text_WPassword.setDocument(new LimitWord_JTF(16));
		text_WPassword.setFont(font_text);
		pl_UserText.add(text_WPassword);

		/* <-----------------------上下按钮-----------------------> */

		pl_Button = new JPanel(new GridLayout(2, 1, 10, 10));
		pl_Button.setBounds(422, 370, 105, 80);
		pl_Frame.add(pl_Button);

		bt_up = new JButton("上一条");
		bt_up.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/up.png")));
		bt_up.setHorizontalTextPosition(JButton.RIGHT);
		bt_up.setVerticalTextPosition(JButton.CENTER);
		bt_up.setFocusPainted(false);
		bt_up.setBackground(Color.WHITE);
		bt_up.setOpaque(false);
		bt_up.setFont(font_button);
		bt_up.addActionListener(this);
		pl_Button.add(bt_up);

		bt_down = new JButton("下一条");
		bt_down.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/down.png")));
		bt_down.setHorizontalTextPosition(JButton.RIGHT);
		bt_down.setVerticalTextPosition(JButton.CENTER);
		bt_down.setBackground(Color.WHITE);
		bt_down.setFocusPainted(false);
		bt_down.setOpaque(false);
		bt_down.setFont(font_button);
		bt_down.addActionListener(this);
		pl_Button.add(bt_down);

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
				text_WEnter.setText(rs1.getString("WEnter"));
				text_WNative.setText(rs1.getString("WNative"));
				text_WBirth.setText(rs1.getString("WBirth"));
				cb_PName.setSelectedItem(rs1.getString("PName"));
				cb_EEdu.setSelectedItem(rs1.getString("EEdu"));
				cb_JName.setSelectedItem(rs1.getString("JName"));
				cb_DName.setSelectedItem(rs1.getString("DName"));
				cb_WSex.setSelectedItem(rs1.getString("WSex"));
				temp_WName = rs1.getString("WName");
				temp_DName = rs1.getString("DName");
				temp_PName = rs1.getString("PName");
				temp_JName = rs1.getString("JName");
				temp_EEdu = rs1.getString("EEdu");
				temp_WNative = rs1.getString("WNative");
				temp_WEnter = rs1.getString("WEnter");
				temp_WSex = rs1.getString("WSex");
				temp_WTel = rs1.getString("WTel");
				temp_WIdentity = rs1.getString("WIdentity");
				temp_WBirth = rs1.getString("WBirth");
			}
			while (rs2.next()) {
				text_WPassword.setText(rs2.getString("WPassword"));
				cb_WRight.setSelectedItem(rs2.getString("WRight"));
				temp_WRight = rs2.getString("WRight");
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
			setButtonView(false, false, false, true, false, false);
		} else if (model == 0) {
			setTextView(true);
			setData();
			Status = 0;
			setButtonView(false, false, true, true, false, false);
		} else if (model == 2) {
			setData();
			getInfomation();
			judgeUpDown();
		}
	}

	private void setComboboxtiem() {
		cb_JName.setModel(
				getModel(DB_Apply.getSString(DB_Apply.getSVector(DB_Apply.getResult(con, main.judge.sql_Job))), 1));
		cb_PName.setModel(
				getModel(DB_Apply.getSString(DB_Apply.getSVector(DB_Apply.getResult(con, main.judge.sql_Postion))), 1));
		cb_EEdu.setModel(getModel(
				DB_Apply.getSString(DB_Apply.getSVector(DB_Apply.getResult(con, main.judge.sql_Education))), 1));
		cb_DName.setModel(getModel(
				DB_Apply.getSString(DB_Apply.getSVector(DB_Apply.getResult(con, main.judge.sql_Department))), 2));

		cb_DName.addItem("");
		cb_DName.setSelectedItem("");

		cb_EEdu.addItem("");
		cb_EEdu.setSelectedItem("");

		cb_JName.addItem("");
		cb_JName.setSelectedItem("");

		cb_PName.addItem("");
		cb_PName.setSelectedItem("");

		cb_WSex.setModel(new DefaultComboBoxModel<String>(new String[] { "男", "女", "" }));
		cb_WSex.setSelectedItem("");

		cb_WRight.setModel(new DefaultComboBoxModel<String>(new String[] { "管理员", "普通用户", "" }));
		cb_WRight.setSelectedItem("");
	}

	/* *************主要功能************* */

	public void insertData() {
		try {
			getData();
			getDNo();
			String sql1 = "insert into SalSys_Worker\r\n" + "select '" + wno + "','" + wname + "','" + wsex + "','"
					+ wbirth + "','" + wnative + "','" + edu + "','" + wtel + "'," + wenter + ",'" + wid + "','" + dno
					+ "','" + pname + "','" + jname + "'";
			String sql2 = "insert into SalSys_Users\r\n" + "select '" + wno + "','" + wpd + "','" + wright + "'";
//			System.err.println(sql1);
//			System.err.println(sql2);
			DB_Apply.InsertData(con, sql1, this);
			DB_Apply.InsertData(con, sql2, this);

		} catch (Exception e) {
			setTextNull();
			setButtonView(false, false, false, true, false, false);
			JOptionPane.showMessageDialog(null, e.getMessage(), "插入失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void updateData() {
		try {
			getData();
			getDNo();
			String sql1 = "UPDATE SalSys_Worker" + "\nSET" + "\nWName='" + wname + "' ,\nWEnter='" + wenter
					+ "' ,\nWIdentity='" + wid + "' ,\nWNative='" + wnative + "' ,\nWBirth='" + wbirth + "',\nWTel='"
					+ wtel + "',\nWSex='" + wsex + "',\nEEdu='" + edu + "' ,\nJName='" + jname + "' ,\nPName='" + pname
					+ "' ,\nDNo='" + dno + "'\nWHERE WNo='" + wno + "'";
			String sql2 = "UPDATE SalSys_Users" + "\nSET" + "\nWPassword='" + wpd + "' ,\nWRight='" + wright
					+ "'\nWHERE WNo='" + wno + "'";
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

	public void delData() {
		try {
			getInfomation();
			String sql = "DELETE from SalSys_Worker Where WNo='" + wno + "' ";
//			System.err.println(sql);
			DB_Apply.DelData(con, sql, this);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "删除失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void Up() {
		getInfomation();
		int j = 0;
		for (; j < data.length; j++) {
			if (wno.equals(data[j]))
				break;
		}
		setNo(data[--j], 2);
	}

	private void Down() {
		getInfomation();
		int j = 0;
		for (; j < data.length; j++) {
			if (wno.equals(data[j]))
				break;
		}
		setNo(data[++j], 2);
	}

	/* *************辅助方法************* */

	private void getInfomation() {
		wno = text_WNo.getText();
		wenter = text_WEnter.getText();
		wbirth = text_WBirth.getText();
		edu = String.valueOf(cb_EEdu.getSelectedItem());
		pname = String.valueOf(cb_PName.getSelectedItem());
		wright = String.valueOf(cb_WRight.getSelectedItem());
		jname = String.valueOf(cb_JName.getSelectedItem());
		dname = String.valueOf(cb_DName.getSelectedItem());
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

	private void getDNo() {
		String sql = "select * from SalSys_Department where DName='" + String.valueOf(cb_DName.getSelectedItem()) + "'";
		ResultSet rs = DB_Apply.getResult(con, sql);
		try {
			while (rs.next())
				dno = (rs.getString("Dno"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "从数据库获取失败，无法获取", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
//		System.out.println(dno);
	}

	private void setTextView(Boolean b) {
		text_WName.setEditable(b);
		text_WNative.setEditable(b);
		text_WTel.setEditable(b);
		text_WIdentity.setEditable(b);
		text_WPassword.setEditable(b);
		text_WBirth.setEditable(b);
		text_WEnter.setEditable(b);
		cb_WRight.setEnabled(b);
		cb_PName.setEnabled(b);
		cb_JName.setEnabled(b);
		cb_DName.setEnabled(b);
		cb_EEdu.setEnabled(b);
		cb_WSex.setEnabled(b);
	}

	private void setTextNull() {
		cb_DName.setSelectedItem("");
		cb_EEdu.setSelectedItem("");
		cb_JName.setSelectedItem("");
		cb_PName.setSelectedItem("");
		cb_WSex.setSelectedItem("");
		cb_WRight.setSelectedItem("");
		text_WEnter.setText("");
		text_WIdentity.setText("");
		text_WName.setText("");
		text_WNative.setText("");
		text_WPassword.setText("");
		text_WBirth.setText("");
		text_WTel.setText("");
	}

	private void setButtonView(Boolean add, Boolean alter, Boolean del, Boolean save, Boolean up, Boolean down) {
		bt_Add.setEnabled(add);
		bt_Alter.setEnabled(alter);
		bt_Delet.setEnabled(del);
		bt_Save.setEnabled(save);
		bt_up.setEnabled(up);
		bt_down.setEnabled(down);
	}

	private void judgeUpDown() {
//		for (String s : data) 
//			System.out.println(s);
		if (wno.equals(data[0])) {
			bt_up.setEnabled(false);
		} else {
			bt_up.setEnabled(true);
		}
		if (wno.equals(data[data.length - 1])) {
			bt_down.setEnabled(false);
		} else {
			bt_down.setEnabled(true);
		}
	}

	private boolean judgeData() {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		int nowyear = Integer.valueOf(dateFormat.format(date));

		/* --------------------年份判断-------------------- */
		getData();
//		System.out.println("当前status：" + Status);
		try {
			int year = Integer.valueOf(wenter);
			if (year < 1900 || year > nowyear) {
				JOptionPane.showMessageDialog(null, "入职年份输入错误，区间应为1900至今", "输入错误", JOptionPane.ERROR_MESSAGE);
				if (Status == 1) {
//					setTextNull();
					setTextView(true);
					setButtonView(false, false, false, true, false, false);
					Status = 1;
				}
				return false;
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "入职年份输入错误，正确格式为1900至今", "输入错误", JOptionPane.ERROR_MESSAGE);
			if (Status == 1) {
//				setTextNull();
				setTextView(true);
				setButtonView(false, false, false, true, false, false);
				Status = 1;
			}
			ex.printStackTrace();
			return false;
		}

		if (wright.equals("")) {
			JOptionPane.showMessageDialog(null, "权限不能为空", "选择错误", JOptionPane.ERROR_MESSAGE);
			if (Status == 1) {
//				setTextNull();
				setTextView(true);
				setButtonView(false, false, false, true, false, false);
				Status = 1;
			}
			return false;
		}

		/* --------------------电话判断-------------------- */

		if (wtel.length() < 11) {
			JOptionPane.showMessageDialog(null, "电话号码应为11位", "输入错误", JOptionPane.ERROR_MESSAGE);
			if (Status == 1) {
//				setTextNull();
				setTextView(true);
				setButtonView(false, false, false, true, false, false);
				Status = 1;
			}
			return false;
		}

		/* --------------------身份证判断-------------------- */
		if (wid.length() < 18) {
			JOptionPane.showMessageDialog(null, "身份证号应为18位", "输入错误", JOptionPane.ERROR_MESSAGE);
			if (Status == 1) {
//				setTextNull();
				setTextView(true);
				setButtonView(false, false, false, true, false, false);
				Status = 1;
			}
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
				if (Status == 1) {
//					setTextNull();
					setTextView(true);
					setButtonView(false, false, false, true, false, false);
					Status = 1;
				}
				return false;
			}
			/* <-------小分界线-------> */
			try {
//				System.out.println(wbirth.substring(5, 7));
				int month = Integer.valueOf(wbirth.substring(5, 7));
				if (month < 1 || month > 12) {
					JOptionPane.showMessageDialog(null, "出生月份输入错误，区间应为1到12", "输入错误", JOptionPane.ERROR_MESSAGE);
					if (Status == 1) {
//						setTextNull();
						setTextView(true);
						setButtonView(false, false, false, true, false, false);
						Status = 1;
					}
					return false;
				}
				try {
//					System.out.println(wbirth.substring(8, wbirth.length()));
					int day = Integer.valueOf(wbirth.substring(8, wbirth.length()));
					if (day < 1 || day > 31) {
						JOptionPane.showMessageDialog(null, "出生日期输入错误，区间应为1到31", "输入错误", JOptionPane.ERROR_MESSAGE);
						if (Status == 1) {
//							setTextNull();
							setTextView(true);
							setButtonView(false, false, false, true, false, false);
							Status = 1;
						}
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
					if (Status == 1) {
//						setTextNull();
						setTextView(true);
						setButtonView(false, false, false, true, false, false);
						Status = 1;
					}
					return false;
				}
				try {
//					System.out.println(wbirth.substring(7, wbirth.length()));
					int day = Integer.valueOf(wbirth.substring(7, wbirth.length()));
					if (day < 1 || day > 31) {
						JOptionPane.showMessageDialog(null, "出生日期输入错误，区间应为1到31", "输入错误", JOptionPane.ERROR_MESSAGE);
						if (Status == 1) {
//							setTextNull();
							setTextView(true);
							setButtonView(false, false, false, true, false, false);
							Status = 1;
						}
						return false;
					}
				} catch (Exception ex) {
					System.out.println("???");
				}
			}

			if (Integer.valueOf(wenter) <= year) {
				JOptionPane.showMessageDialog(null, "入职年份不应小于出生年份之前", "输入错误", JOptionPane.ERROR_MESSAGE);
				if (Status == 1) {
//					setTextNull();
					setTextView(true);
					setButtonView(false, false, false, true, false, false);
					Status = 1;
				}
				return false;
			}

		} else {
			JOptionPane.showMessageDialog(null, "出生日期格式错误，应为YYYY-MM-DD", "输入错误", JOptionPane.ERROR_MESSAGE);
			if (Status == 1) {
//				setTextNull();
				setTextView(true);
				setButtonView(false, false, false, true, false, false);
				Status = 1;
			}
			return false;
		}

		return true;
	}

	private DefaultComboBoxModel<String> getModel(String[] str, int status) {

		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>();

		String[] item;

		if (status == 0) {
			item = new String[str.length];
		} else if (status == 1) {
			item = new String[str.length / 2];
		} else {
			item = new String[str.length / 4];
		}

		convertStr(str, item, status);

		int count = item.length;

		for (int i = 0; i < count; i++)
			comboBoxModel.addElement(item[i]);

		return comboBoxModel;
	}

	private void convertStr(String[] str, String[] item, int status) {
		if (status == 0) {
			for (int i = 0, j = 0; i < str.length; i++) {
				item[j++] = str[i];
			}
		} else if (status == 1) {
			for (int i = 0, j = 0; i < str.length; i++) {
				if (i % 2 == 0) {
					item[j++] = str[i];
				}
			}
		} else {
			for (int i = 0, j = 0; i < str.length; i++) {
				if (i % 4 == 1) {
					item[j++] = str[i];
				}
			}
		}
	}

	private void updateMain() {
		main.setReport(DB_Apply.getDVector(DB_Apply.getResult(con, main.judge.sql_Worker)), main.judge.V_Worker);
	}

	/* *************主类引用************* */

	public void setInsertModel(ResultSet rs) {
		getResult(rs);
		setNo(newNo, 1);
	}

	public void setAlterModel(String[] s, ResultSet rs) {
		getResult(rs);
		setNo(s[0], 0);
	}

	public void delWNo(String[] s) {
//		System.out.println("传过来" + s[0]);
		for (int i = 0; i < s.length; i++) {
//				System.out.println("传过来" + s[i]);
			String no = String.valueOf(s[i]);
			String sql = "DELETE from SalSys_Worker Where WNo='" + no + "'";
//				System.err.println(sql);
			DB_Apply.DelData(con, sql, this);
			updateMain();
		}
	}

	public void getResult(ResultSet rs) {

		String last = null;
		String first = null;

		int count = 0;
		try {
			while (rs.next()) {
				if (count++ == 0)
					first = rs.getString(1);
				else
					last = rs.getString(1);
			}

			data = new String[count];
			rs.beforeFirst();

			int i = 0;
			while (rs.next()) 
				data[i++] = rs.getString(1);

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		tempfirst = Integer.parseInt(first);

		for (int j = 0; j < data.length; j++, tempfirst++) {
//			System.out.println(tempfirst + "sd " + data[j]);
			if (Integer.parseInt(data[j]) != tempfirst) {
				last = String.valueOf(--tempfirst);
				break;
			}
		}

		tempfirst = Integer.parseInt(first);

		newNo = String.valueOf((Integer.parseInt(last) + 1));
//		System.out.println(newNo);
	}

	/* *************方法实现************* */

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根

		/* --------------------修改-------------------- */

		if (e.getSource() == bt_Alter) {
			Status = 0;
			setTextView(true);
			setButtonView(false, false, false, true, true, true);
		}

		/* --------------------保存-------------------- */

		if (e.getSource() == bt_Save) {
			getData();
			if (cb_DName.getSelectedItem().equals("") || cb_WSex.getSelectedItem().equals("")
					|| cb_EEdu.getSelectedItem().equals("") || cb_JName.getSelectedItem().equals("")
					|| cb_PName.getSelectedItem().equals("") || cb_WRight.getSelectedItem().equals("")) {
				System.out.println("有选项为空");
				setTextNull();
			} else if (text_WBirth.getText().equals(temp_WBirth) && text_WEnter.getText().equals(temp_WEnter)
					&& text_WIdentity.getText().equals(temp_WIdentity) && text_WName.getText().equals(temp_WName)
					&& text_WNative.getText().equals(temp_WNative) && text_WPassword.getText().equals(temp_WPassword)
					&& text_WTel.getText().equals(temp_WTel) && cb_DName.getSelectedItem().equals(temp_DName)
					&& cb_WSex.getSelectedItem().equals(temp_WSex) && cb_EEdu.getSelectedItem().equals(temp_EEdu)
					&& cb_JName.getSelectedItem().equals(temp_JName) && cb_PName.getSelectedItem().equals(temp_PName)
					&& cb_WRight.getSelectedItem().equals(temp_WRight)) {
				setTextView(false);
				setButtonView(true, true, true, false, true, true);
				//?
			} else {
				if (judgeData()) {
					if (Status == 1) {
						insertData();
						updateMain();
						System.out.println("获得" + wno);
						Status = 0;
						getResult(DB_Apply.getResult(con, main.judge.sql_Worker));
						setButtonView(true, true, true, false, true, true);
						judgeUpDown();
						setTextView(false);
					} else if (Status == 0) {
						int f = JOptionPane.showConfirmDialog(null, "该操作将会影响该员工相关的数据，是否更新信息？");
						if (f == 0) {
							updateData();
							updateMain();
						} else {
							setData();
						}
						getResult(DB_Apply.getResult(con, main.judge.sql_Worker));
						setButtonView(true, true, true, false, true, true);
						judgeUpDown();
						setTextView(false);
					}
				} else {
					setData();
				}
			}
		}

		/* --------------------删除-------------------- */

		if (e.getSource() == bt_Delet) {
			int f = JOptionPane.showConfirmDialog(null, "该操作将会影响该员工所有记录，是否删除信息？");
			if (f == 0) {
				delData();
				updateMain();
			} else {
				setData();
			}
		}

		/* --------------------添加-------------------- */

		if (e.getSource() == bt_Add) {
			setNo(newNo, 1);
		}

		/* --------------------方向-------------------- */

		if (e.getSource() == bt_up) {
			Up();
		}

		if (e.getSource() == bt_down) {
			Down();
		}

		/* --------------------取消-------------------- */
		
		if (e.getSource() == bt_Cancel) {
			dispose();
		}
	}

}
