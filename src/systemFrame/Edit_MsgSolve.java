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
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Edit_MsgSolve extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	JPanel pl_Frame, pl_Img, pl_Info, pl_Check,pl_button;
	JPanel pl_CheckLabel, pl_CheckText, pl_InfoLabel, pl_InfoText;
	JPanel pl_CheckLabelL, pl_CheckLabelR, pl_CheckTextL, pl_CheckTextR,pl_DateLabel,pl_DateText;
	
	JLabel jl_Absent, jl_Leave, jl_Evection, jl_Late;
	JLabel jl_WNo, jl_WName, jl_SYear, jl_SMonth ;

	JButton bt_Save, bt_Cancel;

	JTextField text_Late, text_Absent, text_Leave, text_Evection ;
	JTextField text_WName,text_Year,text_WNo, text_Month;
	
	Box b_Year, b_Month;

	Connection con;

	Frame_Main main;

	String temp_Absent, temp_Leave, temp_Evection, temp_Late; // 缓存对比
	String wno, year, month; // 方便引用
	Edit_Message message;
	Boolean confirm = false;
	int Evection, Absent, Leave, Late; // 方便引用
	int tip = 0, Status = 0; // tip控制错误提示次数为1,status控制 Save状态

	private static Edit_MsgSolve instance;
	
	String[] value = new String[1], M = new String[1], Y = new String[1], item = null; // 辅助传值
	
	private Edit_MsgSolve(Connection con, Frame_Main main, Boolean view, Edit_Message message,String[] n, String[] y, String[] m) {
		super.setModal(true);
		this.con = con;
		this.main = main;
		this.message = message;
		GUI_Init();
		setFrameIcon();
		transferWNo(n, y, m);
	
		setTitle("考勤处理");
		
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO 自动生成的方法存根
				instance=null;
				super.windowClosed(arg0);
			}
		});
		setSize(492, 376);
		setLocationRelativeTo(null);
		setVisible(view);
	}

	
	
	
	public static  Edit_MsgSolve getInstance(Connection con, Frame_Main main, Boolean view, Edit_Message message,String[] n, String[] y, String[] m) {

		if (instance == null) {
			instance = new Edit_MsgSolve(con,main,view,message,n, y, m);
		}
		return instance;
	}
	
	
	/* *************初始界面************* */

	private void GUI_Init() {

		pl_Img = new JPanel(new BorderLayout());
		pl_Img.setBorder(new EmptyBorder(25, 25, 15, 25));
		getContentPane().add(pl_Img);

		pl_Frame = new JPanel(null);
		pl_Img.add(pl_Frame);

		Font font_label = new Font("微软雅黑", 1, 17);
		Font font_combox = new Font("微软雅黑", 0, 14);
		Font font_text = new Font("微软雅黑", 0, 14);

		Border lineBorder = BorderFactory.createLineBorder(Color.black);
		Border border_info = BorderFactory.createTitledBorder(lineBorder, "信息", TitledBorder.LEFT,
				TitledBorder.DEFAULT_POSITION, font_label);
		Border border_check = BorderFactory.createTitledBorder(lineBorder, "考勤", TitledBorder.LEFT,
				TitledBorder.DEFAULT_POSITION, font_label);

		/* <-----------------------信息左边面板-----------------------> */

		pl_Info = new JPanel(null);
		pl_Info.setBorder(border_info);
		pl_Info.setBounds(0, 0, 435, 128);
		pl_Frame.add(pl_Info);

		int y_info = 25, hight_info = 120, width_info = 65;

		/* <-------小分界线-------> */

		pl_InfoLabel = new JPanel(new GridLayout(2, 0, 0, 10));
		pl_InfoLabel.setBounds(20, 30, 80, 80);
		pl_Info.add(pl_InfoLabel);

		jl_WNo = new JLabel("员 工 号：", JLabel.CENTER);
		jl_WNo.setFont(font_label);
		pl_InfoLabel.add(jl_WNo);

		jl_WName = new JLabel("姓     名：", JLabel.CENTER);
		jl_WName.setFont(font_label);
		pl_InfoLabel.add(jl_WName);

		/* <-------小分界线-------> */

		pl_InfoText = new JPanel(new GridLayout(2, 0, 0, 15));
		pl_InfoText.setBounds(110, 30, 100, 80);
		pl_Info.add(pl_InfoText);

		text_WNo = new JTextField("");
		text_WNo.setHorizontalAlignment(JTextField.CENTER);
		text_WNo.setEditable(false);
		text_WNo.setFont(font_combox);
		text_WNo.setBorder(lineBorder);
		pl_InfoText.add(text_WNo);

		text_WName = new JTextField("", JLabel.CENTER);
		text_WName.setHorizontalAlignment(JTextField.CENTER);
		text_WName.setEditable(false);
		text_WName.setFont(font_combox);
		text_WName.setBorder(lineBorder);
		pl_InfoText.add(text_WName);
		
		/* <-------小分界线-------> */
		
		pl_DateLabel = new JPanel(new GridLayout(2, 0, 0, 10));
		pl_DateLabel.setBounds(225, 30,80, 80);
		pl_Info.add(pl_DateLabel);
		
		jl_SYear = new JLabel("年     份：", JLabel.CENTER);
		jl_SYear.setFont(font_label);
		pl_DateLabel.add(jl_SYear);

		jl_SMonth = new JLabel("月     份：", JLabel.CENTER);
		jl_SMonth.setFont(font_label);
		pl_DateLabel.add(jl_SMonth);
		
		/* <-------小分界线-------> */
		
		pl_DateText = new JPanel(new GridLayout(2, 0, 0, 15));
		pl_DateText.setBounds(315, 30,100, 80);
		pl_Info.add(pl_DateText);
		
		text_Year = new  JTextField("", JLabel.CENTER);
		text_Year.setHorizontalAlignment(JTextField.CENTER);
		text_Year.setEditable(false);
		text_Year.setFont(font_combox);
		text_Year.setBorder(lineBorder);
		pl_DateText.add(text_Year);
		
		text_Month = new JTextField("", JLabel.CENTER);
		text_Month.setHorizontalAlignment(JTextField.CENTER);
		text_Month.setEditable(false);
		text_Month.setFont(font_combox);
		text_Month.setBorder(lineBorder);
		pl_DateText.add(text_Month);


		

		/* <-------小分界线-------> */

		/* <-----------------------考勤面板-----------------------> */

		pl_Check = new JPanel(null);
		pl_Check.setBorder(border_check);
		pl_Check.setBounds(0, 128, 435, 128);
		pl_Frame.add(pl_Check);

		/* <-------小分界线-------> */
		int x = 20, y = 30, height = 80;
		pl_CheckLabelL = new JPanel(new GridLayout(2, 1, 0, 10));
		pl_CheckLabelL.setBounds(x, y, 80, height);
		pl_Check.add(pl_CheckLabelL);

		jl_Evection = new JLabel("出差天数");
		jl_Evection.setFont(font_label);
		pl_CheckLabelL.add(jl_Evection);

		jl_Absent = new JLabel("旷工次数");
		jl_Absent.setFont(font_label);
		pl_CheckLabelL.add(jl_Absent);

		/* <-------小分界线-------> */

		pl_CheckTextL = new JPanel(new GridLayout(2, 1, 0, 15));
		pl_CheckTextL.setBounds(110, y, 100, height);
		pl_Check.add(pl_CheckTextL);

		text_Evection = new JTextField();
		text_Evection.setFont(font_text);
		text_Evection.setHorizontalAlignment(JTextField.CENTER);
		pl_CheckTextL.add(text_Evection);

		text_Absent = new JTextField();
		text_Absent.setFont(font_text);
		text_Absent.setHorizontalAlignment(JTextField.CENTER);
		pl_CheckTextL.add(text_Absent);

		/* <-------小分界线-------> */

		pl_CheckLabelR = new JPanel(new GridLayout(2, 1, 0, 10));
		pl_CheckLabelR.setBounds(225, y, 80, 80);
		pl_Check.add(pl_CheckLabelR);

		jl_Leave = new JLabel("请假天数");
		jl_Leave.setFont(font_label);
		pl_CheckLabelR.add(jl_Leave);

		jl_Late = new JLabel("迟到次数");
		jl_Late.setFont(font_label);
		pl_CheckLabelR.add(jl_Late);

		/* <-------小分界线-------> */

		pl_CheckTextR = new JPanel(new GridLayout(2, 1, 0, 15));
		pl_CheckTextR.setBounds(315, y, 100, height);
		pl_Check.add(pl_CheckTextR);

		text_Leave = new JTextField();
		text_Leave.setFont(font_text);
		text_Leave.setHorizontalAlignment(JTextField.CENTER);
		pl_CheckTextR.add(text_Leave);

		text_Late = new JTextField();
		text_Late.setFont(font_text);
		text_Late.setHorizontalAlignment(JTextField.CENTER);
		pl_CheckTextR.add(text_Late);

		
		pl_button =new JPanel(new GridLayout(1,2,50,0));
		pl_button.setBounds(63, 270, 300, 31);
		pl_Frame.add(pl_button);
		
		
		
		bt_Save = new JButton("提交");
//		bt_Save.setBounds(63, 249, 65, 31);
//		bt_Save.setBorderPainted(false);
		bt_Save.setFocusPainted(false);
		bt_Save.addActionListener(this);
		pl_button.add(bt_Save);

		bt_Cancel = new JButton("取消");
		bt_Cancel.addActionListener(this);
		bt_Cancel.setFocusPainted(false);
//		bt_Cancel.setBorderPainted(false);
//		bt_Cancel.setBounds(214, 247, 65, 31);
		bt_Cancel.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		pl_button.add(bt_Cancel);

	}

	private void setFrameIcon() {
		Toolkit tool = getToolkit();
		Image image = tool.getImage(ClassLoader.getSystemResource("image/setupicon.png"));
		setIconImage(image);
	}

	/* *************设置数据************* */

	

	public void setName() {
		getInfomation();
		String sql = "select WName from V_Worker Where WNo='" + wno + "'";
		ResultSet rs = DB_Apply.getResult(con, sql);
		try {
			while (rs.next())
				text_WName.setText(rs.getString("WName"));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "从数据库获取失败", JOptionPane.ERROR_MESSAGE);
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
	
	public void setData() {
		getInfomation();

		if (year.equals("") || month.equals("")) {
		} else {
			if (judgeData()) {
				String sql = "select * from V_SalCheck Where WNo='" + wno + "' AND SYear=" + year + " AND SMonth="
						+ month;
//				System.err.println(sql);
				ResultSet rs = DB_Apply.getResult(con, sql);
				try {
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

	public Boolean getConfirm() {
		return confirm;
	}

	private void setTextNull() {
		text_Evection.setText("");
		text_Absent.setText("");
		text_Leave.setText("");
		text_Late.setText("");
	}

	private void getInfomation() {
		wno = String.valueOf(text_WNo.getText());
		year = String.valueOf(text_Year.getText());
		month = String.valueOf(text_Month.getText());
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

	

	private void updateMain() {
		main.setReport(DB_Apply.getDVector(DB_Apply.getResult(con, main.judge.sql_Check)), main.judge.V_SalCheck);
	}

	/* *************主要功能************* */



	private void updateData() {
		try {
			getData();
			String sql = "UPDATE SalSys_CheckStat SET EvectionDay=" + Evection + " ,AbsentDay=" + Absent + " ,LeaveDay="
					+ Leave + " ,LateDay=" + Late + " WHERE WNo='" + wno + "' AND CheckYear=" + year
					+ " AND CheckMonth=" + month;
//			System.err.println(sql);
//			DB_Apply.UpdateDialog(con, sql, this);
			confirm = true;
			message.doConfirm();

		} catch (Exception e) {
			setData();
			JOptionPane.showMessageDialog(null, e.getMessage(), "修改失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	

	/* *************主类引用************* */

	public void transferWNo(String[] n, String[] y, String[] m) {
//		System.out.println("传过来" + s[0]+ "-" + y[0] + "-" + m[0]);
		text_WNo.setText(n[0]);
		setName();
		text_Month.setText(m[0]);
		text_Year.setText(y[0]);
		setData();
	}



	/* *************方法实现************* */

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == text_Year) {
			setData();
		}

		/* --------------------保存-------------------- */

		if (e.getSource() == bt_Save) {
			getInfomation();
			if (year.equals("") || month.equals("")) {
				setTextNull();
			} else {
				if (Status == 1) {
					Status = 0;
					updateMain();
				} else if (Status == 0) {
					int f = JOptionPane.showConfirmDialog(this, "确定提交处理吗? ", "提示 ", JOptionPane.YES_NO_OPTION);
					if (f == 0) {
						updateData();
//						updateMain();
						dispose();

					} else {
//						setData();
						
					}
				}
			}
		}
		if (e.getSource() == bt_Cancel) {
			dispose();
		}
	}



}
