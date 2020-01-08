package systemFrame;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.awt.event.ActionEvent;

public class Edit_SetupDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	JPanel contentPanel, buttonPanel, infoPanel, labelPanel, ImgPanel;

	JTextField text_Content, text_Money;

	JButton okButton, cancelButton;

	JComboBox<String> cb_Item;

	JLabel jl_Item, jl_Content, jl_Money;

	Connection con;

	String content;

	Double money;

	public Edit_SetupDialog(Connection con) {
		this.con = con;
		GUI_Init();
		setFrameIcon();
		setBounds(100, 100, 275, 250);
		setTitle("添加项目");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	/* *************初始界面************* */

	private void GUI_Init() {

		Font font_label = new Font("微软雅黑", 1, 17);
		Font font_combox = new Font("微软雅黑", 0, 14);
		Font font_txt = new Font("微软雅黑", 0, 14);
		Font font_button = new Font("微软雅黑", 1, 14);

		ImgPanel = new JPanel(new BorderLayout());
		ImgPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(ImgPanel);

		contentPanel = new JPanel(null);
		contentPanel.setLayout(null);
		ImgPanel.add(contentPanel);

		/* <-----------------------标签面板-----------------------> */

		infoPanel = new JPanel(new GridLayout(3, 1, 0, 15));
		infoPanel.setBounds(90, 15, 140, 130);
		contentPanel.add(infoPanel);

		cb_Item = new JComboBox<String>();
		cb_Item.setFont(font_combox);
		infoPanel.add(cb_Item);

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		String[] item = { "职务标准", "职称标准", "学历标准" };
		for (String s : item)
			model.addElement(s);
		cb_Item.setModel(model);

		text_Content = new JTextField();
		text_Content.setFont(font_txt);
		infoPanel.add(text_Content);

		text_Money = new JTextField();
		text_Money.setFont(font_txt);
		infoPanel.add(text_Money);

		/* <-----------------------输入面板-----------------------> */

		labelPanel = new JPanel(new GridLayout(3, 1, 0, 15));
		labelPanel.setBounds(17, 15, 60, 130);
		contentPanel.add(labelPanel);

		jl_Item = new JLabel("项 目：");
		jl_Item.setFont(font_label);
		labelPanel.add(jl_Item);

		jl_Content = new JLabel("内 容：");
		jl_Content.setFont(font_label);
		labelPanel.add(jl_Content);

		jl_Money = new JLabel("金 额：");
		jl_Money.setFont(font_label);
		labelPanel.add(jl_Money);

		/* <-----------------------按钮面板-----------------------> */

		buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
		buttonPanel.setBounds(51, 160, 150, 30);
		contentPanel.add(buttonPanel);

		okButton = new JButton("添加");
		okButton.setFont(font_button);
		okButton.setFocusPainted(false);
		okButton.addActionListener(this);
		buttonPanel.add(okButton);
//		getRootPane().setDefaultButton(okButton);

		cancelButton = new JButton("取消");
		cancelButton.setFont(font_button);
		cancelButton.setFocusPainted(false);
		cancelButton.addActionListener(this);
		cancelButton.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		buttonPanel.add(cancelButton);

	}

	private void setFrameIcon() {
		Toolkit tool = getToolkit();
		Image image = tool.getImage(ClassLoader.getSystemResource("image/setupicon.png"));
		setIconImage(image);
	}
	
	/* *************辅助方法************* */

	private boolean getInformation() {
		try {
			content = text_Content.getText();
			money = Double.parseDouble(text_Money.getText());
			return true;
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "金额应为数字", "输入错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	/* *************主要功能************* */

	private void insertData(String view) {
		String sql = "insert into " + view + "\r\n" + "select '" + content + "','" + money + "'";
//		System.err.println(sql);
		DB_Apply.InsertData(con, sql, null);
		JOptionPane.showMessageDialog(null, "添加成功", "提示信息", JOptionPane.INFORMATION_MESSAGE);
	}

	/* *************方法实现************* */

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if (e.getSource() == cancelButton) {
			dispose();
		}
		if (e.getSource() == okButton) {
			if (getInformation()) {
				if (cb_Item.getSelectedItem().equals("职务标准")) {
					insertData("SalSys_Job");
				} else if (cb_Item.getSelectedItem().equals("职称标准")) {
					insertData("SalSys_Position");
				} else if (cb_Item.getSelectedItem().equals("学历标准")) {
					insertData("SalSys_Education");
				}
			}
		}
	}

}