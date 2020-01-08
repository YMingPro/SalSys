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

public class Edit_Department extends Frame_Edit implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;

	JPanel pl_Frame, pl_Button, pl_Img, pl_Info, pl_Menu, pl_InfoLabel, pl_InfoText;

	JButton bt_Add, bt_Alter, bt_Delet, bt_Save, bt_Cancel;

	JComboBox<String> cb_DNo;

	JLabel jl_DNo, jl_DName;

	JTextField Name;

	Connection con;

	Frame_Main main;

	String newNo, temp_DName, dno, dname;

	int STATUS = 0;

	public Edit_Department(Connection con, Frame_Main main, Boolean view) {
		this.con = con;
		this.main = main;
		GUI_Init();
		setNo();
		setFrameIcon();
		setSize(380, 220);
		setTitle("部门设置");
		setLocationRelativeTo(null);
		setVisible(view);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/* *************初始界面************* */

	private void GUI_Init() {

		pl_Img = new JPanel(new BorderLayout());
		pl_Img.setBorder(new EmptyBorder(15, 15, 15, 15));
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
		Font font_button = new Font("微软雅黑", 1, 14);

		Border lineBorder = BorderFactory.createLineBorder(Color.black);
		Border border_depart = BorderFactory.createTitledBorder(lineBorder, "部门", TitledBorder.LEFT,
				TitledBorder.DEFAULT_POSITION, font_label);

		/* <-----------------------信息面板-----------------------> */

		pl_Info = new JPanel(null);
		pl_Info.setBorder(border_depart);
		pl_Info.setBounds(2, 0, 340, 103);
		pl_Frame.add(pl_Info);

		int y_info = 25, width_info = 65;

		/* <-------小分界线-------> */

		pl_InfoLabel = new JPanel(new GridLayout(2, 0, 0, 10));
		pl_InfoLabel.setBounds(15, y_info, 120, width_info);
		pl_Info.add(pl_InfoLabel);

		jl_DNo = new JLabel("部  门  号：", JLabel.CENTER);
		jl_DNo.setFont(font_label);
		pl_InfoLabel.add(jl_DNo);

		jl_DName = new JLabel("部  门  名：", JLabel.CENTER);
		jl_DName.setFont(font_label);
		pl_InfoLabel.add(jl_DName);

		/* <-------小分界线-------> */

		pl_InfoText = new JPanel(new GridLayout(2, 0, 0, 10));
		pl_InfoText.setBounds(150, 25, 120, width_info);
		pl_Info.add(pl_InfoText);

		cb_DNo = new JComboBox<String>();
		cb_DNo.setFont(font_combox);
		cb_DNo.addItemListener(this);
		pl_InfoText.add(cb_DNo);

		Name = new JTextField();
		Name.setFont(font_combox);
		Name.setEditable(false);
		pl_InfoText.add(Name);

		/* <-----------------------右侧按钮-----------------------> */

		pl_Button = new JPanel(new GridLayout(2, 1, 0, 5));
		pl_Button.setBounds(288, 25, 30, 65);
		pl_Frame.add(pl_Button);

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

		/* <-----------------------底部按钮-----------------------> */

		pl_Menu = new JPanel(new GridLayout(1, 3, 20, 0));
		pl_Menu.setBounds(59, 115, 226, 30);
		pl_Frame.add(pl_Menu);

		bt_Add = new JButton("添加");
		bt_Add.setFont(font_button);
		bt_Add.setFocusPainted(false);
		bt_Add.addActionListener(this);
		pl_Menu.add(bt_Add);

		bt_Delet = new JButton("删除");
		bt_Delet.setFocusPainted(false);
		bt_Delet.setFont(font_button);
		bt_Delet.addActionListener(this);
		bt_Delet.setEnabled(false);
		pl_Menu.add(bt_Delet);

	}

	private void setFrameIcon() {
		Toolkit tool = getToolkit();
		Image image = tool.getImage(ClassLoader.getSystemResource("image/setupicon.png"));
		setIconImage(image);
	}
	
	/* *************设置数据************* */

	public void setNo() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		ResultSet rs = DB_Apply.getResult(con, "select DNo from V_Department");
		try {
			while (rs.next())
				model.addElement(rs.getString("DNo"));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "从数据库获取失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		cb_DNo.setModel(model);
		setName();
	}

	public void setName() {
		getInfomation();
		ResultSet rs = DB_Apply.getResult(con, "select DName from V_Department Where DNo='" + dno + "'");
		try {
			while (rs.next()) {
				Name.setText(rs.getString("DName"));
				temp_DName = rs.getString("DName");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "从数据库获取失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void setComboItem(Boolean status) {
		if (status) {
			cb_DNo.addItem(newNo);
			cb_DNo.setSelectedItem(newNo);
		} else {
			cb_DNo.removeItem(newNo);
		}
	}

	/* *************辅助方法************* */

	private void getInfomation() {
		dno = String.valueOf(cb_DNo.getSelectedItem());
		dname = Name.getText();
	}

	private void updateMain() {
		main.setReport(DB_Apply.getDVector(DB_Apply.getResult(con, main.judge.sql_Department)),
				main.judge.V_Department);
	}

	private void setButtonView(Boolean add, Boolean alter, Boolean save) {
		bt_Add.setEnabled(add);
		bt_Alter.setEnabled(alter);
		bt_Save.setEnabled(save);
	}

	private void setTextView(Boolean name, Boolean dno) {
		Name.setEditable(name);
		cb_DNo.setEnabled(dno);
	}

	/* *************主要功能************* */

	private void insertData() {
		try {
			setComboItem(true);
			getInfomation();
			String sql = "INSERT INTO SalSys_Department (DNo,DName) VALUES ('" + dno + "','" + dname + "')";
//			System.err.println(sql);
			DB_Apply.InsertData(con, sql, this);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "插入失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void updateData() {
		try {
			getInfomation();
			String sql = "UPDATE SalSys_Department SET DName='" + dname + "' WHERE DNo='" + dno + "'";
//			System.err.println(sql);
			DB_Apply.UpdateData(con, sql, this);
		} catch (Exception e) {
			setData();
			JOptionPane.showMessageDialog(null, e.getMessage(), "修改失败", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/* *************主类引用************* */

	public void transferDNo(String[] s, ResultSet rs) {
		getResult(rs);
//		System.out.println("传过来" + s[0]);
		cb_DNo.setSelectedItem(s[0]);
		setName();
	}

	public void getResult(ResultSet rs) {
		String last = null;
		try {
			while (rs.next()) {
				last = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		newNo = String.valueOf("00" + (Long.parseLong(last) + 1));
	}

	/* *************方法实现************* */

	public void actionPerformed(ActionEvent e) {

		/* --------------------添加-------------------- */

		if (e.getSource() == bt_Add) {
			STATUS = 2;
			setComboItem(true);
			setButtonView(false, false, true);
			setTextView(true, false);
		}

		/* --------------------修改-------------------- */

		if (e.getSource() == bt_Alter) {
			STATUS = 1;
			setButtonView(false, false, true);
			setTextView(true, true);
		}

		/* --------------------保存-------------------- */

		if (e.getSource() == bt_Save) {
			if (STATUS == 1) {
				if (Name.getText().equals(temp_DName)) {
				} else {
					int f = JOptionPane.showConfirmDialog(null, "该操作将会影响所属部门的员工的相关的数据，是否更新信息？");
					if (f == 0) {
						updateData();
						updateMain();
					} else {
						setName();
					}
				}
			} else if (STATUS == 2) {

				int f = JOptionPane.showConfirmDialog(null, "确定是否添加新部门？");
				if (f == 0) {
					insertData();
					updateMain();
					JOptionPane.showConfirmDialog(null, "添加成功!有员工的部门才能在部门信息中显示！");
				} else {
					setName();
					setComboItem(false);
					cb_DNo.setSelectedIndex(0);
				}
			}
			setButtonView(true, true, false);
			setTextView(false, true);
		}
		
		if (e.getSource() == bt_Cancel) {
			dispose();
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO 自动生成的方法存根
		if (e.getSource() == cb_DNo) {
			setName();
		}
	}

}
