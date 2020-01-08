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

public class Edit_OveTime extends Frame_Edit implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;

	JPanel pl_Frame, pl_Button, pl_Img, pl_Info, pl_Date, pl_Ove;
	JPanel pl_OveLabel, pl_OveText, pl_InfoLabel, pl_InfoText;

	JComboBox<String> cb_WNo, cb_SMonth;

	JLabel jl_Holiday, jl_Workday;

	JLabel jl_WNo, jl_WName, jl_Year, jl_Month, Name;

	JButton bt_Save, bt_Alter, bt_Delet, bt_Cancel;

	JTextField text_Holiday, text_Weekday, text_Year;

	Connection con;

	Frame_Main main;

	Box b_Year, b_Month;

	String temp_Holiday, temp_Weekday;// 缓存对比
	String wno, year, month; // 方便引用

	int tip = 0, Status = 0; // tip控制错误提示次数为1,status控制 Save状态
	int Holiday, Weekday; // 方便引用

	public Edit_OveTime(Connection con, Frame_Main main, Boolean view) {
		this.con = con;
		this.main = main;
		GUI_Init();
		setNo();
		setFrameIcon();
		setSize(410, 460);
		setTitle("加班设置");
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
		Font font_txt = new Font("微软雅黑", 0, 14);

		Border lineBorder = BorderFactory.createLineBorder(Color.black);
		Border border_info = BorderFactory.createTitledBorder(lineBorder, "信息", TitledBorder.LEFT,
				TitledBorder.DEFAULT_POSITION, font_label);
		Border border_date = BorderFactory.createTitledBorder(lineBorder, "日期", TitledBorder.LEFT,
				TitledBorder.DEFAULT_POSITION, font_label);
		Border border_check = BorderFactory.createTitledBorder(lineBorder, "加班", TitledBorder.LEFT,
				TitledBorder.DEFAULT_POSITION, font_label);

		int X = 2, WIDTH = 350;

		/* <-----------------------信息面板-----------------------> */

		pl_Info = new JPanel(null);
		pl_Info.setBorder(border_info);
		pl_Info.setBounds(X, 0, WIDTH, 100);
		pl_Frame.add(pl_Info);

		int y_info = 25, width_info = 65;

		/* <-------小分界线-------> */

		pl_InfoLabel = new JPanel(new GridLayout(2, 0, 0, 10));
		pl_InfoLabel.setBounds(36, y_info, 120, width_info);
		pl_Info.add(pl_InfoLabel);

		jl_WNo = new JLabel("员  工  号：", JLabel.CENTER);
		jl_WNo.setFont(font_label);
		pl_InfoLabel.add(jl_WNo);

		jl_WName = new JLabel("姓       名：", JLabel.CENTER);
		jl_WName.setFont(font_label);
		pl_InfoLabel.add(jl_WName);

		/* <-------小分界线-------> */

		pl_InfoText = new JPanel(new GridLayout(2, 0, 0, 10));
		pl_InfoText.setBounds(192, y_info, 120, width_info);
		pl_Info.add(pl_InfoText);

		cb_WNo = new JComboBox<String>();
		cb_WNo.setFont(font_combox);
		cb_WNo.addItemListener(this);
		pl_InfoText.add(cb_WNo);

		Name = new JLabel("", JLabel.CENTER);
		Name.setFont(font_combox);
		Name.setBorder(BorderFactory.createEtchedBorder());
		pl_InfoText.add(Name);

		/* <-----------------------日期面板-----------------------> */

		pl_Date = new JPanel(null);
		pl_Date.setBorder(border_date);
		pl_Date.setBounds(X, 120, WIDTH, 80);
		pl_Frame.add(pl_Date);

		int y_date = 30, hight_date = 130, width_date = 30;

		/* <-------小分界线-------> */

		b_Year = Box.createHorizontalBox();
		b_Year.setBounds(30, y_date, hight_date, width_date);
		pl_Date.add(b_Year);

		jl_Year = new JLabel("年份：", JLabel.CENTER);
		jl_Year.setFont(font_label);
		b_Year.add(jl_Year);

		text_Year = new JTextField();
		text_Year.setFont(font_combox);
		text_Year.addActionListener(this);
		text_Year.setDocument(new LimitWord_JTF(4));
		b_Year.add(text_Year);

		/* <-------小分界线-------> */

		b_Month = Box.createHorizontalBox();
		b_Month.setBounds(190, y_date, hight_date, width_date);
		pl_Date.add(b_Month);

		jl_Month = new JLabel("月份：", JLabel.CENTER);
		jl_Month.setFont(font_label);
		b_Month.add(jl_Month);

		cb_SMonth = new JComboBox<String>();
		cb_SMonth.setFont(font_combox);
		cb_SMonth.addItemListener(this);
		b_Month.add(cb_SMonth);

		cb_SMonth.addItem(String.valueOf(""));
		for (int i = 1; i <= 12; i++)
			cb_SMonth.addItem(String.valueOf(i));

		/* <-----------------------加班面板-----------------------> */

		pl_Ove = new JPanel(null);
		pl_Ove.setBorder(border_check);
		pl_Ove.setBounds(X, 220, WIDTH, 145);
		pl_Frame.add(pl_Ove);

		/* <-------小分界线-------> */

		pl_OveLabel = new JPanel(new GridLayout(2, 1, 0, 10));
		pl_OveLabel.setBounds(23, 30, 153, 90);
		pl_Ove.add(pl_OveLabel);

		jl_Holiday = new JLabel("节假日加班（天数）");
		jl_Holiday.setFont(font_label);
		pl_OveLabel.add(jl_Holiday);

		jl_Workday = new JLabel("工作日加班（小时）");
		jl_Workday.setFont(font_label);
		pl_OveLabel.add(jl_Workday);

		/* <-------小分界线-------> */

		pl_OveText = new JPanel(new GridLayout(2, 1, 0, 20));
		pl_OveText.setBounds(186, 36, 100, 84);
		pl_Ove.add(pl_OveText);

		text_Holiday = new JTextField();
		text_Holiday.setFont(font_txt);
		text_Holiday.setEditable(false);
		pl_OveText.add(text_Holiday);

		text_Weekday = new JTextField();
		text_Weekday.setFont(font_txt);
		text_Weekday.setEditable(false);
		pl_OveText.add(text_Weekday);

		/* <-----------------------按钮面板-----------------------> */

		pl_Button = new JPanel(new GridLayout(3, 1, 0, 10));
		pl_Button.setBounds(302, 25, 30, 110);
		pl_Ove.add(pl_Button);

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
		ResultSet rs = DB_Apply.getResult(con, "select WNo from V_Worker");
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
		ResultSet rs = DB_Apply.getResult(con, "select WName from V_Worker Where WNo='" + wno + "'");
		try {
			while (rs.next())
				Name.setText(rs.getString("WName"));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "从数据库获取失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void setData() {
		getInfomation();

		if (year.equals("") || month.equals("")) {
		} else {
			if (judgeData()) {
				String sql = "select * from V_SalOveTime  Where WNo='" + wno + "' AND SYear=" + year + " AND SMonth="
						+ month;
//				System.err.println(sql);
				ResultSet rs = DB_Apply.getResult(con, sql);
				try {
					if (rs.next()) {
						rs = DB_Apply.getResult(con, sql);// 如果有记录则重新导入
						Status = 0;// 影响Save,编辑-->保存
						while (rs.next()) {
							String THoliday = rs.getString("THoliday");
							String TWeekday = rs.getString("TWeekday");
							temp_Holiday = THoliday;
							temp_Weekday = TWeekday;
							text_Holiday.setText(THoliday);
							text_Weekday.setText(TWeekday);
//							System.out.printf("THolidy=%s\t", THoliday);
//							System.out.printf("TWeekday=%s\t", TWeekday);
						}
					} else {
						Status = 1;// 影响Save，添加->保存
						setTextNull();
						++tip;
						if (tip < 2) {
							JOptionPane.showMessageDialog(null, "查询错误无该记录", "查询错误", JOptionPane.ERROR_MESSAGE);
//							System.out.println("查询不到");
						} else {
							tip = 0;
						}
					}
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					JOptionPane.showMessageDialog(null, e.getMessage(), "从数据库获取失败", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}
	}

	/* *************辅助方法************* */

	private void setTextNull() {
		text_Holiday.setText("");
		text_Weekday.setText("");
	}

	private void setTextView(Boolean b) {
		text_Holiday.setEditable(b);
		text_Weekday.setEditable(b);
	}

	private void getInfomation() {
		wno = String.valueOf(cb_WNo.getSelectedItem());
		year = String.valueOf(text_Year.getText());
		month = String.valueOf(cb_SMonth.getSelectedItem());
	}

	private void getData() {
		try {
			Holiday = Integer.valueOf(text_Holiday.getText());
			Weekday = Integer.valueOf(text_Weekday.getText());
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
		main.setReport(DB_Apply.getDVector(DB_Apply.getResult(con, main.judge.sql_SalOveTime)),
				main.judge.V_SalOveTime);
	}

	/* *************主要功能************* */

	private void insertData() {
		try {
			getData();
			String sql = "INSERT INTO SalSys_CheckStat (WNo,CheckYear,CheckMonth,THoliday,TWeekday) VALUES ('" + wno
					+ "'," + year + "," + month + "," + Holiday + "," + Weekday + ")";
//			System.err.println(sql);
			DB_Apply.InsertData(con, sql, this);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "插入失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void updateData() {
		try {
			getData();
			String sql = "UPDATE SalSys_CheckStat SET THoliday=" + Holiday + " ,TWeekday=" + Weekday + " WHERE WNo='"
					+ wno + "' AND CheckYear=" + year + " AND CheckMonth=" + month;
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

	public void transferWNo(String[] s, String[] y, String[] m) {
//		System.out.println("传过来" + s[0] + "-" + y[0] + "-" + m[0]);
		cb_WNo.setSelectedItem(s[0]);
		setName();
		cb_SMonth.setSelectedItem(m[0]);
		text_Year.setText(y[0]);
		setData();
	}

	public void delWNo(String[] s, String[] y, String[] m) {
//		System.out.println("传过来" + s[0] + "-" + y[0] + "-" + m[0]);
		cb_WNo.setSelectedItem(s[0]);
		cb_SMonth.setSelectedItem(m[0]);
		text_Year.setText(y[0]);

		try {
			for (int i = 0; i < s.length; i++) {
				String sql = "DELETE from SalSys_CheckStat Where WNo='" + s[i] + "' AND CheckYear=" + y[i]
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
				text_Holiday.setText("");
				text_Weekday.setText("");
			} else if (text_Holiday.getText().equals(temp_Holiday) && text_Weekday.getText().equals(temp_Weekday)) {
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
		// TODO 自动生成的方法存根
		if (e.getSource() == cb_WNo) {
			setName();
			setData();
		}
		if (e.getSource() == cb_SMonth) {
			setData();
		}
	}

}
