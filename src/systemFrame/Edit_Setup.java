package systemFrame;

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

public class Edit_Setup extends JFrame implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;

	JPanel pl_Frame, pl_label, pl_combox, pl_text, pl_button;
	JLabel jl_Money, jl_Item;
	JLabel jl_BounsPenalty, jl_JobBS, jl_PostitionBS, jl_EduBS, jl_Tax, jl_Allowance;
	JTextField text_BounsPenalty, text_JobBS, text_PositionBS, text_EduBS, text_Tax, text_Allowance;
	JComboBox<String> cb_BounsPenalty, cb_JobBS, cb_PositionBS, cb_EduBS, cb_Tax, cb_Allowance;
	JButton[] bt_alterSave = new JButton[6];
	JButton bt_add, bt_cancel;
	int[] STATUS = new int[6];

	Connection con;

	DB_Judge judge;

	String AItem, table;
	Double money;

	public Edit_Setup(Connection con, DB_Judge judge) {
		this.con = con;
		this.judge = judge;
		GUI_Init();
		setData();
		setFrameIcon();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(500, 500);
		setLocationRelativeTo(null);
		setTitle("工资公式设置");
	}

	/* *************初始界面************* */

	void GUI_Init() {
		Font font_label = new Font("微软雅黑", 1, 18);
		Font font_combox = new Font("微软雅黑", 0, 14);
		Font font_text = new Font("微软雅黑", 0, 14);
		Font font_title = new Font("微软雅黑", 1, 20);
		Font font_button = new Font("微软雅黑", 1, 14);

		pl_Frame = new JPanel(null);
		getContentPane().add(pl_Frame);

		int y_title = 30, w_title = 50, h_title = 25;

		jl_Item = new JLabel("项   目", JLabel.CENTER);
		jl_Item.setFont(font_title);
		jl_Item.setBounds(191, y_title, 58, 25);
		pl_Frame.add(jl_Item);

		jl_Money = new JLabel("金 额", JLabel.CENTER);
		jl_Money.setBounds(340, y_title, w_title, h_title);
		jl_Money.setFont(font_title);
		pl_Frame.add(jl_Money);

		/*******************************************************/

		pl_label = new JPanel();
		pl_label.setBounds(45, 60, 100, 300);
		pl_label.setOpaque(false);
		pl_label.setLayout(new GridLayout(6, 1));
		pl_Frame.add(pl_label);

		jl_BounsPenalty = new JLabel("奖惩标准：");
		jl_BounsPenalty.setFont(font_label);
		pl_label.add(jl_BounsPenalty);

		jl_JobBS = new JLabel("职位标准：");
		jl_JobBS.setFont(font_label);
		pl_label.add(jl_JobBS);

		jl_PostitionBS = new JLabel("职称标准：");
		jl_PostitionBS.setFont(font_label);
		pl_label.add(jl_PostitionBS);

		jl_EduBS = new JLabel("学历标准：");
		jl_EduBS.setFont(font_label);
		pl_label.add(jl_EduBS);

		jl_Allowance = new JLabel("补贴项目：");
		jl_Allowance.setFont(font_label);
		pl_label.add(jl_Allowance);

		jl_Tax = new JLabel("税率标准：");
		jl_Tax.setFont(font_label);
		pl_label.add(jl_Tax);

		/*******************************************************/

		pl_combox = new JPanel();
		pl_combox.setBounds(150, 70, 140, 285);
		pl_combox.setOpaque(false);
		pl_combox.setLayout(new GridLayout(6, 1, 0, 20));
		pl_Frame.add(pl_combox);

		cb_BounsPenalty = new JComboBox<String>();
		cb_BounsPenalty.setFont(font_combox);
		cb_BounsPenalty.addItemListener(this);
		pl_combox.add(cb_BounsPenalty);

		cb_JobBS = new JComboBox<String>();
		cb_JobBS.setFont(font_combox);
		cb_JobBS.addItemListener(this);
		pl_combox.add(cb_JobBS);

		cb_PositionBS = new JComboBox<String>();
		cb_PositionBS.setFont(font_combox);
		cb_PositionBS.addItemListener(this);
		pl_combox.add(cb_PositionBS);

		cb_EduBS = new JComboBox<String>();
		cb_EduBS.setFont(font_combox);
		cb_EduBS.addItemListener(this);
		pl_combox.add(cb_EduBS);

		cb_Allowance = new JComboBox<String>();
		cb_Allowance.setFont(font_combox);
		cb_Allowance.addItemListener(this);
		pl_combox.add(cb_Allowance);

		cb_Tax = new JComboBox<String>();
		cb_Tax.setFont(font_combox);
		cb_Tax.addItemListener(this);
		pl_combox.add(cb_Tax);

		/*******************************************************/

		pl_text = new JPanel();
		pl_text.setBounds(315, 70, 100, 285);
		pl_text.setOpaque(false);
		pl_text.setLayout(new GridLayout(6, 1, 0, 20));
		pl_Frame.add(pl_text);

		text_BounsPenalty = new JTextField();
		text_BounsPenalty.setEditable(false);
		text_BounsPenalty.setFont(font_text);
		pl_text.add(text_BounsPenalty);

		text_JobBS = new JTextField();
		text_JobBS.setFont(font_text);
		text_JobBS.setEditable(false);
		pl_text.add(text_JobBS);

		text_PositionBS = new JTextField();
		text_PositionBS.setFont(font_text);
		text_PositionBS.setEditable(false);
		pl_text.add(text_PositionBS);

		text_EduBS = new JTextField();
		text_EduBS.setFont(font_text);
		text_EduBS.setEditable(false);
		pl_text.add(text_EduBS);

		text_Allowance = new JTextField();
		text_Allowance.setFont(font_text);
		text_Allowance.setEditable(false);
		pl_text.add(text_Allowance);

		text_Tax = new JTextField();
		text_Tax.setFont(font_text);
		text_Tax.setEditable(false);
		pl_text.add(text_Tax);

		/*******************************************************/

		pl_button = new JPanel();
		pl_button.setBounds(121, 390, 251, 30);
		pl_button.setOpaque(false);
		pl_button.setLayout(new GridLayout(1, 3, 80, 0));
		pl_Frame.add(pl_button);

		bt_add = new JButton("添加");
		bt_add.addActionListener(this);
		bt_add.setFont(font_button);
		bt_add.setFocusPainted(false);
		pl_button.add(bt_add);

		bt_cancel = new JButton("取消");
		bt_cancel.addActionListener(this);
		bt_cancel.setFont(font_button);
		bt_cancel.setFocusPainted(false);
		bt_cancel.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		pl_button.add(bt_cancel);

		/*******************************************************/

		for (int i = 0; i < 6; i++) {
			bt_alterSave[i] = new JButton("");
			bt_alterSave[i].setBounds(425, 73 + 50 * i, 26, 26);
			bt_alterSave[i].setIcon(new ImageIcon(ClassLoader.getSystemResource("image/edit.png")));
			bt_alterSave[i].setBackground(Color.WHITE);
			bt_alterSave[i].setOpaque(false);
			bt_alterSave[i].setBorderPainted(false);
			bt_alterSave[i].setFocusPainted(false);
			bt_alterSave[i].addActionListener(this);
			pl_Frame.add(bt_alterSave[i]);
			STATUS[i] = 0;
		}

		/*******************************************************/

		cb_BounsPenalty.setModel(
				getModel(DB_Apply.getSString(DB_Apply.getSVector(DB_Apply.getResult(con, judge.sql_SetWage))), 1));
		cb_JobBS.setModel(
				getModel(DB_Apply.getSString(DB_Apply.getSVector(DB_Apply.getResult(con, judge.sql_Job))), 1));
		cb_PositionBS.setModel(
				getModel(DB_Apply.getSString(DB_Apply.getSVector(DB_Apply.getResult(con, judge.sql_Postion))), 1));
		cb_EduBS.setModel(
				getModel(DB_Apply.getSString(DB_Apply.getSVector(DB_Apply.getResult(con, judge.sql_Education))), 1));
		cb_Allowance.setModel(getModel(DB_Apply.getSString(judge.SalSys_Allowance), 0));
		cb_Tax.setModel(getModel(DB_Apply.getSString(DB_Apply.getSVector(DB_Apply.getResult(con, judge.sql_Tax))), 1));

	}

	private void setFrameIcon() {
		Toolkit tool = getToolkit();
		Image image = tool.getImage(ClassLoader.getSystemResource("image/setupicon.png"));
		setIconImage(image);
	}
	
	/* *************设置数据************* */

	private void setData() {
		text_BounsPenalty.setText(getData(cb_BounsPenalty, "SMoney", "SalSys_SetWage", "SItem"));
		text_JobBS.setText(getData(cb_JobBS, "JMoney", "SalSys_Job", "JName"));
		text_PositionBS.setText(getData(cb_PositionBS, "PMoney", "SalSys_Position", "PName"));
		text_EduBS.setText(getData(cb_EduBS, "EMoney", "SalSys_Education", "EEdu"));

		// 津贴设置
		int temp = cb_Allowance.getSelectedIndex();
		int count = DB_Apply.getSString(DB_Apply.getSVector(DB_Apply.getResult(con, judge.sql_SetWage))).length;
		String[] s = new String[count];
		convertStr(DB_Apply.getSString(DB_Apply.getSVector(DB_Apply.getResult(con, judge.sql_Allowance))), s, 0);
		text_Allowance.setText(s[temp]);
		text_Tax.setText(getData(cb_Tax, "Rate", "SalSys_Tax", "Std"));

	}

	/* *************主要功能************* */

	public void updateModel1(String tb, String na, String mo) {
		try {
			String sql = "UPDATE " + tb + "\nSET" + "\n" + mo + "='" + money + "'\nWHERE " + na + "='" + table + "'";
//			System.err.println(sql);
			DB_Apply.UpdateData(con, sql, null);
			JOptionPane.showMessageDialog(null, "修改成功", "提示信息", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "更新失败", "提示信息", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void updateModel2() {
		if (table.equals("交通补贴")) {
			AItem = "ATransport";
		} else if (table.equals("全勤补贴")) {
			AItem = "APresent";
		} else if (table.equals("住房补贴")) {
			AItem = "AHouse";
		} else if (table.equals("伙食补贴")) {
			AItem = "ADiet";
		}
		try {
			String sql = "UPDATE SalSys_Allowance\r\n" + "SET " + AItem + "=" + money;
			DB_Apply.UpdateData(con, sql, null);
			JOptionPane.showMessageDialog(null, "修改成功", "提示信息", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "更新失败", "提示信息", JOptionPane.ERROR_MESSAGE);
		}
	}

	/* *************辅助方法************* */

	private void convertStr(String[] s, String[] m, int status) {
		if (status == 0) {
			for (int i = 0, j = 0; i < s.length; i++) {
				m[j++] = s[i];
			}
		} else {
			for (int i = 0, j = 0; i < s.length; i++) {
				if (i % 2 == 0) {
					m[j++] = s[i];
				}
			}
		}
	}

	private String getData(JComboBox<String> cb, String Money, String Table, String Item) {
		String item = (String) cb.getSelectedItem();
		String money = DB_Apply.SearchMoney(
				DB_Apply.getResult(con, "select " + Money + " from " + Table + " where " + Item + "='" + item + "'"));
		return money;
	}

	private DefaultComboBoxModel<String> getModel(String[] s, int status) {

		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>();

		String[] item;

		if (status == 0) {
			item = new String[s.length];
		} else {
			item = new String[s.length / 2];
		}

		convertStr(s, item, status);

		int count = item.length;

		for (int i = 0; i < count; i++)
			comboBoxModel.addElement(item[i]);

		return comboBoxModel;
	}

	private void changeButton(int num) {
		if (STATUS[num] == 0) {
			STATUS[num] = 1;
			switch (num) {
			case 0:
				text_BounsPenalty.setEditable(true);
				break;
			case 1:
				text_JobBS.setEditable(true);
				break;
			case 2:
				text_PositionBS.setEditable(true);
				break;
			case 3:
				text_EduBS.setEditable(true);
				break;
			case 4:
				text_Allowance.setEditable(true);
				break;
			case 5:
				text_Tax.setEditable(true);
				break;
			}
			bt_alterSave[num].setIcon(new ImageIcon(ClassLoader.getSystemResource("image/save.png")));
		} else if (STATUS[num] == 1) {
			STATUS[num] = 0;
			switch (num) {
			case 0:
				if (getInformation(cb_BounsPenalty, text_BounsPenalty)) {
					updateModel1("SalSys_SetWage", "SItem", "SMoney");
				}
				text_BounsPenalty.setEditable(false);
				break;
			case 1:
				if (getInformation(cb_JobBS, text_JobBS)) {
					updateModel1("SalSys_Job", "JName", "JMoney");
				}
				text_JobBS.setEditable(false);
				break;
			case 2:
				if (getInformation(cb_PositionBS, text_PositionBS)) {
					updateModel1("SalSys_Position", "PName", "PMoney");
				}
				text_PositionBS.setEditable(false);
				break;
			case 3:
				if (getInformation(cb_EduBS, text_EduBS)) {
					updateModel1("SalSys_Education", "EEdu", "EMoney");
				}
				text_EduBS.setEditable(false);
				break;
			case 4:
				if (getInformation(cb_Allowance, text_Allowance)) {
					updateModel2();
				}
				text_Allowance.setEditable(false);
				break;
			case 5:
				if (getInformation(cb_Tax, text_Tax)) {
					updateModel1("SalSys_Tax", "Std", "Rate");
				}
				text_Tax.setEditable(false);
				break;
			}
			bt_alterSave[num].setIcon(new ImageIcon(ClassLoader.getSystemResource("image/edit.png")));
		}

	}

	private boolean getInformation(JComboBox<String> cb, JTextField text) {
		try {
			table = String.valueOf(cb.getSelectedItem());
			money = Double.parseDouble(text.getText());
			return true;
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "金额应为数字", "输入错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	/* *************方法实现************* */

	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根

		if (e.getSource() == bt_alterSave[0]) {

			changeButton(0);
		}
		if (e.getSource() == bt_alterSave[1]) {

			changeButton(1);
		}
		if (e.getSource() == bt_alterSave[2]) {

			changeButton(2);
		}
		if (e.getSource() == bt_alterSave[3]) {

			changeButton(3);
		}
		if (e.getSource() == bt_alterSave[4]) {

			changeButton(4);
		}
		if (e.getSource() == bt_alterSave[5]) {

			changeButton(5);
		}
		if (e.getSource() == bt_cancel) {
			this.dispose();
		}
		if (e.getSource() == bt_add) {
			new Edit_SetupDialog(con);
		}
	}

	public void itemStateChanged(ItemEvent e) {
		// TODO 自动生成的方法存根
		if (e.getSource() == cb_BounsPenalty) {
			text_BounsPenalty.setText(getData(cb_BounsPenalty, "SMoney", "SalSys_SetWage", "SItem"));
		}
		if (e.getSource() == cb_JobBS) {
			text_JobBS.setText(getData(cb_JobBS, "JMoney", "SalSys_Job", "JName"));
		}
		if (e.getSource() == cb_PositionBS) {
			text_PositionBS.setText(getData(cb_PositionBS, "PMoney", "SalSys_Position", "PName"));
		}
		if (e.getSource() == cb_EduBS) {
			text_EduBS.setText(getData(cb_EduBS, "EMoney", "SalSys_Education", "EEdu"));
		}
		if (e.getSource() == cb_Allowance) {
			int temp = cb_Allowance.getSelectedIndex();
			int count = DB_Apply.getSString(DB_Apply.getSVector(DB_Apply.getResult(con, judge.sql_SetWage))).length;
			String[] s = new String[count];
			convertStr(DB_Apply.getSString(DB_Apply.getSVector(DB_Apply.getResult(con, judge.sql_Allowance))), s, 0);
			/*
			 * DB_Apply .SearchInt(DB_Apply.getResult(con,
			 * "select "+(String)cb_Allowance.getSelectedItem()+" from SalSys_Allowance"))
			 */
			text_Allowance.setText(s[temp]);
		}
		if (e.getSource() == cb_Tax) {
//			changeButton(1);
			text_Tax.setText(getData(cb_Tax, "Rate", "SalSys_Tax", "Std"));
		}
	}

}
