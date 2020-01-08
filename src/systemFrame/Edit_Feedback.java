package systemFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import myModule.Frame_Edit;
import myModule.HintListener_JTA;

public class Edit_Feedback extends Frame_Edit implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	JPanel pl_Frame, pl_Img, pl_Check, pl_Feedback, pl_Button;
	JPanel pl_CheckLabelL, pl_CheckLabelR, pl_CheckTextL, pl_CheckTextR;

	JLabel jl_Absent, jl_Leave, jl_Evection, jl_Late;
	JTextField text_Late, text_Absent, text_Leave, text_Evection;
	JTextArea text_Message;

	Connection con;

	String wno, year, month, fno; // 方便引用

	JButton bt_Submit, bt_Cancel;

	String EvectionDay, AbsentDay, LeaveDay, LateDay;

	static final String INFO = "在此处输入反馈信息（不超过30个字）";
	
	public Edit_Feedback(Connection con, Frame_Main main) {
		this.con = con;
		setTitle("考勤反馈");
		setSize(492, 390);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getFNo();
	}

	private void GUI_Init() {

		pl_Img = new JPanel(new BorderLayout());
		pl_Img.setBorder(new EmptyBorder(25, 25, 15, 25));
		pl_Img.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				JPanel jp = (JPanel) arg0.getSource();
				jp.requestFocus();
			}
		});
		getContentPane().add(pl_Img);

		pl_Frame = new JPanel(null);
		pl_Frame.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				JPanel jp = (JPanel) arg0.getSource();
				jp.requestFocus();
			}
		});
		pl_Img.add(pl_Frame);

		Font font_label = new Font("微软雅黑", 1, 17);
		Font font_text = new Font("微软雅黑", 0, 14);
		Font font_button = new Font("微软雅黑", 1, 14);

		Border lineBorder = BorderFactory.createLineBorder(Color.black);
		Border border_check = BorderFactory.createTitledBorder(lineBorder, "考勤：" + year + " 年 " + month + " 月",
				TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, font_label);
		Border border_feedback = BorderFactory.createTitledBorder(lineBorder, "回复", TitledBorder.LEFT,
				TitledBorder.DEFAULT_POSITION, font_label);

		int X = 0, WIDTH = 435;
		int x = 20, y = 30, height = 80;

		/* <-----------------------考勤面板-----------------------> */

		pl_Check = new JPanel(null);
		pl_Check.setBorder(border_check);
		pl_Check.setBounds(X, 0, WIDTH, 130);
		pl_Frame.add(pl_Check);

		/* <-------小分界线-------> */

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
		text_Evection.setEditable(false);
		text_Evection.setHorizontalAlignment(JTextField.CENTER);
		pl_CheckTextL.add(text_Evection);

		text_Absent = new JTextField();
		text_Absent.setFont(font_text);
		text_Absent.setEditable(false);
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
		text_Leave.setEditable(false);
		text_Leave.setHorizontalAlignment(JTextField.CENTER);
		pl_CheckTextR.add(text_Leave);

		text_Late = new JTextField();
		text_Late.setFont(font_text);
		text_Late.setEditable(false);
		text_Late.setHorizontalAlignment(JTextField.CENTER);
		pl_CheckTextR.add(text_Late);

		/* <-----------------------反馈面板-----------------------> */

		pl_Feedback = new JPanel(null);
		pl_Feedback.setBorder(border_feedback);
		pl_Feedback.setBounds(X, 132, WIDTH, 130);
		pl_Frame.add(pl_Feedback);

		text_Message = new JTextArea();
		text_Message.setBounds(x, y, 395, 80);
		text_Message.setFont(font_text);
		text_Message.addFocusListener(new HintListener_JTA(text_Message, INFO));
		text_Message.setText(INFO);
		text_Message.setLineWrap(true);
//		text_Message.setDocument(new LimitWord_JTF(30));
//		setWrapStyleWord
		pl_Feedback.add(text_Message);

		/* <-----------------------按钮面板-----------------------> */

		pl_Button = new JPanel(new GridLayout(1, 2, 50, 0));
		pl_Button.setBounds(108, 280, 220, 35);
		pl_Frame.add(pl_Button);

		bt_Submit = new JButton("提交");
		bt_Submit.addActionListener(this);
		bt_Submit.setFont(font_button);
		bt_Submit.setFocusPainted(false);
		pl_Button.add(bt_Submit);

		bt_Cancel = new JButton("取消");
		bt_Cancel.addActionListener(this);
		bt_Cancel.setFont(font_button);
		bt_Cancel.setFocusPainted(false);
		bt_Cancel.addActionListener(this);
		bt_Cancel.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		pl_Button.add(bt_Cancel);

	}

	private void getFNo() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		fno = dateFormat.format(date);
	}

	public void setData() {
		String sql = "select * from V_SalCheck Where WNo='" + wno + "' AND SYear=" + year + " AND SMonth=" + month;
		ResultSet rs = DB_Apply.getResult(con, sql);

		try {
			if (rs.next()) {
				rs = DB_Apply.getResult(con, sql); // 如果有记录则重新导入
				while (rs.next()) {
					EvectionDay = rs.getString("EvectionDay");
					AbsentDay = rs.getString("AbsentDay");
					LeaveDay = rs.getString("LeaveDay");
					LateDay = rs.getString("LateDay");
					text_Evection.setText(EvectionDay);
					text_Absent.setText(AbsentDay);
					text_Leave.setText(LeaveDay);
					text_Late.setText(LateDay);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "从数据库获取失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	public void transferWNo(String[] n, String[] y, String[] m) {
		wno = n[0];
		year = y[0];
		month = m[0];
		GUI_Init();
		setData();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if (e.getSource() == bt_Cancel) {
			dispose();
		}
		if (e.getSource() == bt_Submit) {

			int option = JOptionPane.showConfirmDialog(this, "确定提交吗? ", "提示 ", JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				try {
					String message="";
					if(text_Message.getText().equals(INFO)) {
						message="";
					}else {
						message=text_Message.getText();
					}
					String sql = "INSERT INTO SalSys_Feedback (FNo,WNo,CheckYear,CheckMonth,FMessage,Symbol)\n"
							+ "VALUES ('" + fno + "'," + wno + "," + year + "," + month + ",'" + message+ "','未处理')";
					System.err.println(sql);
					DB_Apply.InsertData(con, sql, this);
				} catch (Exception ek) {
					JOptionPane.showMessageDialog(null, ek.getMessage(), "插入失败", JOptionPane.ERROR_MESSAGE);
					ek.printStackTrace();
				}
				dispose();
			}
		}
	}
}
