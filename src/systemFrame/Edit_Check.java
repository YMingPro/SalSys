package systemFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import myModule.Frame_Edit;
import myModule.LimitWord_JTF;

public class Edit_Check extends Frame_Edit implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;

	JPanel pl_Frame, pl_Img, pl_Info, pl_Button, pl_Date, pl_Check;
	JPanel pl_CheckLabel, pl_CheckText, pl_InfoLabel, pl_InfoText;

	JComboBox<String> cb_WNo, cb_SMonth;

	JLabel jl_Absent, jl_Leave, jl_Evection, jl_Late;
	JLabel jl_WNo, jl_WName, jl_SYear, jl_SMonth, Name;

	JButton bt_Save, bt_Alter, bt_Delet, bt_Cancel;

	JTextField text_Late, text_Absent, text_Leave, text_Evection, text_Year;

	Box b_Year, b_Month;

	Connection con;

	Frame_Main main;

	String temp_Absent, temp_Leave, temp_Evection, temp_Late; // 缓存对比
	String wno, year, month; // 方便引用

	int Evection, Absent, Leave, Late; // 方便引用
	int tip = 0, Status = 0; // tip控制错误提示次数为1,status控制 Save状态

	public Edit_Check(Connection con, Frame_Main main, Boolean view) {
		this.con = con;
		this.main = main;
		GUI_Init();
		setNo();
		setFrameIcon();
		setSize(340, 560);
		setTitle("考勤设置");
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(view);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/* *************初始界面************* */

	private void GUI_Init() {

		pl_Img = new JPanel(new BorderLayout());
		pl_Img.setBorder(new EmptyBorder(25, 25, 25, 25));
		getContentPane().add(pl_Img);

		pl_Frame = new JPanel(null);
		pl_Img.add(pl_Frame);

		bt_Cancel = new JButton("");
		bt_Cancel.addActionListener(this);
		bt_Cancel.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		pl_Frame.add(bt_Cancel);

		Font font_label = new Font("微软雅黑", 1, 17);
		Font font_combox = new Font("微软雅黑", 0, 14);
		Font font_text = new Font("微软雅黑", 0, 14);

		Border lineBorder = BorderFactory.createLineBorder(Color.black);
		Border border_info = BorderFactory.createTitledBorder(lineBorder, "信息", TitledBorder.LEFT,
				TitledBorder.DEFAULT_POSITION, font_label);
		Border border_date = BorderFactory.createTitledBorder(lineBorder, "日期", TitledBorder.LEFT,
				TitledBorder.DEFAULT_POSITION, font_label);
		Border border_check = BorderFactory.createTitledBorder(lineBorder, "考勤", TitledBorder.LEFT,
				TitledBorder.DEFAULT_POSITION, font_label);

		int X = 0, WIDTH = 284;

		/* <-----------------------信息面板-----------------------> */

		pl_Info = new JPanel(null);
		pl_Info.setBorder(border_info);
		pl_Info.setBounds(X, 0, WIDTH, 97);
		pl_Frame.add(pl_Info);

		int y_info = 25, hight_info = 120, width_info = 65;

		/* <-------小分界线-------> */

		pl_InfoLabel = new JPanel(new GridLayout(2, 0, 0, 10));
		pl_InfoLabel.setBounds(5, y_info, hight_info, width_info);
		pl_Info.add(pl_InfoLabel);

		jl_WNo = new JLabel("员  工  号：", JLabel.CENTER);
		jl_WNo.setFont(font_label);
		pl_InfoLabel.add(jl_WNo);

		jl_WName = new JLabel("姓       名：", JLabel.CENTER);
		jl_WName.setFont(font_label);
		pl_InfoLabel.add(jl_WName);

		/* <-------小分界线-------> */

		pl_InfoText = new JPanel(new GridLayout(2, 0, 0, 10));
		pl_InfoText.setBounds(145, y_info, hight_info, width_info);
		pl_Info.add(pl_InfoText);

		cb_WNo = new JComboBox<String>();
		cb_WNo.setFont(font_combox);
		cb_WNo.addItemListener(this);
		pl_InfoText.add(cb_WNo);

		Name = new JLabel("", JLabel.CENTER);
		Name.setFont(font_combox);
		Name.setBorder(lineBorder);
		pl_InfoText.add(Name);

		/* <-----------------------日期面板-----------------------> */

		pl_Date = new JPanel(null);
		pl_Date.setBorder(border_date);
		pl_Date.setBounds(X, 120, WIDTH, 80);
		pl_Frame.add(pl_Date);

		int y_date = 30, hight_date = 120, width_date = 30;

		/* <-------小分界线-------> */

		b_Year = Box.createHorizontalBox();
		b_Year.setBounds(20, y_date, hight_date, width_date);
		pl_Date.add(b_Year);

		jl_SYear = new JLabel("年份：", JLabel.CENTER);
		jl_SYear.setFont(font_label);
		b_Year.add(jl_SYear);

		text_Year = new JTextField();
		text_Year.setFont(font_combox);
		text_Year.setDocument(new LimitWord_JTF(4));
		text_Year.addActionListener(this);
		b_Year.add(text_Year);

		/* <-------小分界线-------> */

		b_Month = Box.createHorizontalBox();
		b_Month.setBounds(145, y_date, hight_date, width_date);
		pl_Date.add(b_Month);

		jl_SMonth = new JLabel("月份：", JLabel.CENTER);
		jl_SMonth.setFont(font_label);
		b_Month.add(jl_SMonth);

		cb_SMonth = new JComboBox<String>();
		cb_SMonth.setFont(font_combox);
		cb_SMonth.addItemListener(this);
		b_Month.add(cb_SMonth);

		cb_SMonth.addItem(String.valueOf(""));
		for (int i = 1; i <= 12; i++)
			cb_SMonth.addItem(String.valueOf(i));

		/* <-----------------------考勤面板-----------------------> */

		pl_Check = new JPanel(null);
		pl_Check.setBorder(border_check);
		pl_Check.setBounds(X, 220, WIDTH, 249);
		pl_Frame.add(pl_Check);

		pl_CheckLabel = new JPanel(new GridLayout(4, 1, 0, 10));
		pl_CheckLabel.setBounds(23, 30, 80, 200);
		pl_Check.add(pl_CheckLabel);

		jl_Evection = new JLabel("出差天数");
		jl_Evection.setFont(font_label);
		pl_CheckLabel.add(jl_Evection);

		jl_Absent = new JLabel("旷工次数");
		jl_Absent.setFont(font_label);
		pl_CheckLabel.add(jl_Absent);

		jl_Leave = new JLabel("请假天数");
		jl_Leave.setFont(font_label);
		pl_CheckLabel.add(jl_Leave);

		jl_Late = new JLabel("迟到次数");
		jl_Late.setFont(font_label);
		pl_CheckLabel.add(jl_Late);

		/* <-------小分界线-------> */

		pl_CheckText = new JPanel(new GridLayout(4, 1, 0, 20));
		pl_CheckText.setBounds(115, 35, 100, 190);
		pl_Check.add(pl_CheckText);

		text_Evection = new JTextField();
		text_Evection.setFont(font_text);
		text_Evection.setEditable(false);
		pl_CheckText.add(text_Evection);

		text_Absent = new JTextField();
		text_Absent.setFont(font_text);
		text_Absent.setEditable(false);
		pl_CheckText.add(text_Absent);

		text_Leave = new JTextField();
		text_Leave.setFont(font_text);
		text_Leave.setEditable(false);
		pl_CheckText.add(text_Leave);

		text_Late = new JTextField();
		text_Late.setFont(font_text);
		text_Late.setEditable(false);
		pl_CheckText.add(text_Late);

		/* <-------小分界线-------> */

		pl_Button = new JPanel(new GridLayout(3, 1, 0, 20));
		pl_Button.setBounds(230, 60, 30, 135);
		pl_Check.add(pl_Button);

		bt_Alter = new JButton("");
		bt_Alter.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/edit.png")));
		bt_Alter.setBackground(Color.WHITE);
		bt_Alter.setOpaque(false);
		bt_Alter.setBorderPainted(false);
		bt_Alter.setFocusPainted(false);
		bt_Alter.addActionListener(this);
		pl_Button.add(bt_Alter);

		bt_Save = new JButton("");
		bt_Save.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/save.png")));
		bt_Save.setBackground(Color.WHITE);
		bt_Save.setOpaque(false);
		bt_Save.setBorderPainted(false);
		bt_Save.setFocusPainted(false);
		bt_Save.addActionListener(this);
		bt_Save.setEnabled(false);
		pl_Button.add(bt_Save);

		bt_Delet = new JButton("");
		bt_Delet.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/del.png")));
		bt_Delet.setBackground(Color.WHITE);
		bt_Delet.setOpaque(false);
		bt_Delet.setBorderPainted(false);
		bt_Delet.setFocusPainted(false);
		bt_Delet.addActionListener(this);
		pl_Button.add(bt_Delet);

	}

	private void setFrameIcon() {
		Toolkit tool = getToolkit();
		Image image = tool.getImage(ClassLoader.getSystemResource("image/setupicon.png"));
		setIconImage(image);
	}

	/* *************设置数据************* */

	public void setNo() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		ResultSet rs = DB_Apply.getResult(con, "select WNo from SalSys_Worker");
		try {
			while (rs.next())
				model.addElement(rs.getString("WNo"));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "从数据库获取失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		cb_WNo.setModel(model);
		setName();
	}

	public void setName() {
		getInfomation();
		String sql = "select WName from V_Worker Where WNo='" + wno + "'";
		ResultSet rs = DB_Apply.getResult(con, sql);
		try {
			while (rs.next())
				Name.setText(rs.getString("WName"));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "从数据库获取失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	PreparedStatement pstmt;
	ResultSet rs ;
	public void setData() {
		getInfomation();

		if (year.equals("") || month.equals("")) {
		} else {
			if (judgeData()) {
				String sql = "select * from V_SalCheck Where WNo='" + wno + "' AND SYear=" + year + " AND SMonth="+ month;
//				String sql = "select * from V_SalCheck Where WNo= ? AND SYear= ? AND SMonth= ? ";
//				System.err.println(sql);
//				try {
//					pstmt = con.prepareStatement(sql);
//					pstmt.setString(1, wno);
//					pstmt.setString(2, wno);
//					pstmt.setString(3, month);
//					
//				} catch (Exception e) {
//					// TODO: handle exception
//					System.out.println("error");
//				}
				ResultSet rs = DB_Apply.getResult(con, sql);
				
				try {
//					rs = pstmt.getResultSet();
					if (rs.next()) {
						rs = DB_Apply.getResult(con, sql); // 如果有记录则重新导入
						Status = 0; // 影响Save,编辑-->保存
						while (rs.next()) {
							String EvectionDay = rs.getString("EvectionDay");
							String AbsentDay = rs.getString("AbsentDay");
							String LeaveDay = rs.getString("LeaveDay");
							String LateDay = rs.getString("LateDay");
							temp_Absent = AbsentDay;
							temp_Leave = LeaveDay;
							temp_Evection = EvectionDay;
							temp_Late = LateDay;
							text_Evection.setText(EvectionDay);
							text_Absent.setText(AbsentDay);
							text_Leave.setText(LeaveDay);
							text_Late.setText(LateDay);
//							System.out.printf("EvectionDay%s\tip", EvectionDay);
//							System.out.printf("AbsentDay%s\tip", AbsentDay);
//							System.out.printf("LeaveDay%s\tip", LeaveDay);
//							System.out.printf("LateDay%s\tip\n", LateDay);
						}
					} else {
						Status = 1; // 影响Save，添加->保存
						setTextNull();
						++tip;
						if (tip < 2) {
							JOptionPane.showMessageDialog(null, "查询错误无该记录", "查询错误", JOptionPane.ERROR_MESSAGE);
//					System.out.println("查询不到");
						} else {
							tip = 0;
						}
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "从数据库获取失败", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}
	}

	/* *************辅助方法************* */

	private void setTextNull() {
		text_Evection.setText("");
		text_Absent.setText("");
		text_Leave.setText("");
		text_Late.setText("");
	}

	private void setTextView(Boolean b) {
		text_Leave.setEditable(b);
		text_Late.setEditable(b);
		text_Evection.setEditable(b);
		text_Absent.setEditable(b);
	}

	private void getInfomation() {
		wno = String.valueOf(cb_WNo.getSelectedItem());
		year = String.valueOf(text_Year.getText());
		month = String.valueOf(cb_SMonth.getSelectedItem());
	}

	private void getData() {
		try {
			Evection = Integer.valueOf(text_Evection.getText());
			Absent = Integer.valueOf(text_Absent.getText());
			Leave = Integer.valueOf(text_Leave.getText());
			Late = Integer.valueOf(text_Late.getText());
			getInfomation();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "请检查输入格式，应为整形！", "获取失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private boolean judgeData() {
		try {
			int year = Integer.valueOf(text_Year.getText());
			if (year < 1900 || year > 2900) {
				JOptionPane.showMessageDialog(null, "年份输入错误，区间应为1900到2900", "输入错误", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "年份输入错误，正确格式为1900到2900", "输入错误", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
			Status = -1;
			return false;
		}
		return true;
	}

	private void updateMain() {
		main.setReport(DB_Apply.getDVector(DB_Apply.getResult(con, main.judge.sql_Check)), main.judge.V_SalCheck);
	}

	/* *************主要功能************* */

	private void insertData() {
		try {
			getData();
			String sql = "INSERT INTO SalSys_CheckStat (WNo,CheckYear,CheckMonth,AbsentDay,EvectionDay,LeaveDay,LateDay)\n"
					+ "VALUES ('" + wno + "'," + year + "," + month + "," + Absent + "," + Evection + "," + Leave + ","
					+ Late + ")";
//			System.err.println(sql);
			DB_Apply.InsertData(con, sql, this);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "插入失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			setTextNull();
		}
	}

	private void updateData() {
		try {
			getData();
			String sql = "UPDATE SalSys_CheckStat SET EvectionDay=" + Evection + " ,AbsentDay=" + Absent + " ,LeaveDay="
					+ Leave + " ,LateDay=" + Late + " WHERE WNo='" + wno + "' AND CheckYear=" + year
					+ " AND CheckMonth=" + month;
//			System.err.println(sql);
			DB_Apply.UpdateData(con, sql, this);
		} catch (Exception e) {
			setData();
			JOptionPane.showMessageDialog(null, e.getMessage(), "修改失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void delData() {
		getInfomation();
		String sql = "DELETE from SalSys_CheckStat Where WNo='" + wno + "' AND CheckYear=" + year + " AND CheckMonth="
				+ month;
//			System.err.println(sql);
		DB_Apply.DelData(con, sql, this);
	}

	/* *************主类引用************* */

	public void transferWNo(String[] n, String[] y, String[] m) {
		System.out.println("传过来" + n[0] + "-" + y[0] + "-" + m[0]);
		cb_WNo.setSelectedItem(n[0]);
		setName();
		cb_SMonth.setSelectedItem(m[0]);
		text_Year.setText(y[0]);
		setData();
	}

	public void delWNo(String[] n, String[] y, String[] m) {
//		System.out.println("传过来" + s[0]+ "-" + y[0] + "-" + m[0]);
		cb_WNo.setSelectedItem(n[0]);
		cb_SMonth.setSelectedItem(m[0]);
		text_Year.setText(y[0]);

		try {
			for (int i = 0; i < n.length; i++) {
				String sql = "DELETE from SalSys_CheckStat Where WNo='" + n[i] + "' AND CheckYear=" + y[i]
						+ " AND CheckMonth=" + m[i];
//				System.err.println(sql);
				DB_Apply.DelData(con, sql, this);
			}
			updateMain();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "删除失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/* *************方法实现************* */

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == text_Year) {
			setData();
		}

		/* --------------------修改-------------------- */

		if (e.getSource() == bt_Alter) {
			setTextView(true);
			bt_Alter.setEnabled(false);
			bt_Save.setEnabled(true);
		}

		/* --------------------保存-------------------- */

		if (e.getSource() == bt_Save) {
			getInfomation();
			if (year.equals("") || month.equals("")) {
				setTextNull();
			} else if (text_Absent.getText().equals(temp_Absent) && text_Leave.getText().equals(temp_Leave)
					&& text_Evection.getText().equals(temp_Evection) && text_Late.getText().equals(temp_Late)) {
			} else {
				if (Status == 1) {
					Status = 0;
					insertData();
					updateMain();
				} else if (Status == 0) {
					int f = JOptionPane.showConfirmDialog(null, "该操作将会影响该员工相关的数据，是否更新信息？");
					if (f == 0) {
						updateData();
						updateMain();
					} else {
						setData();
					}
				}
			}
			setTextView(false);
			bt_Alter.setEnabled(true);
			bt_Save.setEnabled(false);
		}

		/* --------------------删除-------------------- */

		if (e.getSource() == bt_Delet) {
			getInfomation();
			if (year.equals("") || month.equals("")) {
			} else {
				int f = JOptionPane.showConfirmDialog(null, "该操作将会影响该员工相关的数据（包括考勤记录），是否删除信息？");
				if (f == 0) {
					delData();
					updateMain();
				} else {
					setData();
				}
			}
		}
		if (e.getSource() == bt_Cancel) {
			dispose();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == cb_WNo) {
			setName();
			setData();
		}
		if (e.getSource() == cb_SMonth) {
			setData();
		}
	}

}
