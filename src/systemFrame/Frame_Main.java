package systemFrame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;

import java.sql.Connection;
import java.sql.ResultSet;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import myModule.HintListener_JTF;
import myModule.LimitWord_JTF;
import myModule.Panel_Home;
import myModule.Panel_Transparent;
import systemFunction.OutExcel;
import systemFunction.SQLSearch;

public class Frame_Main extends JFrame implements ActionListener, ItemListener, ListSelectionListener {

	private static final long serialVersionUID = 1L;

	// 文字提示
	static final String hint1 = "请输入员工号或姓名";
	static final String hint2 = "请输入部门号或部门名、年月份";
	static final String hint3 = "请输入员工号或姓名、年月份";
	static final String hint4 = "请输入年月份";
	static final String hint5 = "请输入部门号或部门名";
	static final String hint6 = "请输入员工号或姓名、年份";

	// 弹窗变量
	Timer timer;
	Point location;
	Integer taskHeight, numFeedback;
	Insets screenInsets;
	Display_Message window;
	Dimension screenSize, windowSize;

	// 主体框架
	String[] value = null, M = null, Y = null, item = null; // 辅助传值
	String userName, userRight, userID; // 用户基本信息
	String greet, login_Time, menu_Title, message_count; // 顶部提示文字

	JLabel jl_Greeting, jl_Title; // 顶部

	JPanel pl_Top, pl_Bar, pl_Menu, pl_Img; // 主要底层面板

	JButton bt_Add, bt_Del, bt_Alter, bt_Search, bt_Print, bt_Export, bt_Feedback; // 功能按钮面板
	JButton bt_Worker, bt_Department, bt_Check, bt_Salary, bt_Setup, bt_Exit, bt_Logout; // 左侧菜单面板
	JButton bt_Message;

	public JComboBox<String> comboBox;

	JTextField input;

	JScrollPane scrollPane;

	JTable table;
	DefaultTableModel tableModel;
	DefaultComboBoxModel<String> comboBoxModel;

	int biaoji = 0;
	Boolean closing = false;
	// 工具变量
	DB_Judge judge;
	public Connection con;
	HintListener_JTF hint_listner1, hint_listner2, hint_listner3, hint_listner4, hint_listner5, hint_listner6;

	public Frame_Main(Connection con, String userName, String userRight, String userID) {
		this.con = con;
		this.userID = userID;
		this.userName = userName;
		this.userRight = userRight;
		Thread_Init();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				doExit(e);
			}
		});
	}

	/* *************线程设置************* */

	protected void Thread_Init() {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(0);
					setGreet();
					GUI_Init();
					setFrameIcon();
					setButtonView(userRight);
					setTitle("工资管理系统");
					setSize(1280, 800);
					setVisible(true);
					setResizable(false);
					setLocationRelativeTo(null);
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
					if (checkMessage()) {
						MessageOpen();
						Thread.sleep(10000);
						if (window.isActive() && closing == false) {
							MessageClose();
							Thread.sleep(2000);
							window.dispose();
//							doClose();
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}.start();
	}

	/* *************初始界面************* */

	@SuppressWarnings("serial")
	private void GUI_Init() {

		judge = new DB_Judge(userID, userRight);

		/* <-----------------------背景面板-----------------------> */

		pl_Img = new Panel_Home("image/main.jpg");
		pl_Img.setLayout(null);
		pl_Img.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				JPanel jp = (JPanel) arg0.getSource();
				jp.requestFocus();
			}
		});
		pl_Img.setBorder(new EmptyBorder(99, 211, 98, 196));
		getContentPane().add(pl_Img);

		/* <-----------------------左侧菜单面板-----------------------> */

		pl_Menu = new Panel_Transparent(0.1f);
		pl_Menu.setLayout(null);
		pl_Menu.setBackground(null);
		pl_Menu.setOpaque(false);
		pl_Menu.setBounds(0, 0, 150, 800);
		pl_Img.add(pl_Menu);

		Font font_bt = new Font("微软雅黑", 1, 15);

		bt_Worker = new JButton("员 工 管 理");
		bt_Worker.setFont(font_bt);
		bt_Worker.setBounds(0, 20, 150, 100);
		bt_Worker.setFocusPainted(false);
		bt_Worker.setForeground(Color.WHITE);
		bt_Worker.setBackground(Color.WHITE);
		bt_Worker.setOpaque(false);
		bt_Worker.setBorderPainted(false);
		bt_Worker.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/worker.png")));
		bt_Worker.setHorizontalTextPosition(JButton.CENTER);
		bt_Worker.setVerticalTextPosition(JButton.BOTTOM);
		bt_Worker.addActionListener(this);
		pl_Menu.add(bt_Worker);

		bt_Department = new JButton("部 门 管 理");
		bt_Department.setFont(font_bt);
		bt_Department.setBounds(0, 140, 150, 100);
		bt_Department.setFocusPainted(false);
		bt_Department.setForeground(Color.WHITE);
		bt_Department.setBackground(Color.WHITE);
		bt_Department.setOpaque(false);
		bt_Department.setBorderPainted(false);
		bt_Department.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/department.png")));
		bt_Department.setHorizontalTextPosition(JButton.CENTER);
		bt_Department.setVerticalTextPosition(JButton.BOTTOM);
		bt_Department.addActionListener(this);
		pl_Menu.add(bt_Department);

		bt_Check = new JButton("考 勤 管 理");
		bt_Check.setFont(font_bt);
		bt_Check.setBounds(0, 260, 150, 100);
		bt_Check.setFocusPainted(false);
		bt_Check.setForeground(Color.WHITE);
		bt_Check.setBackground(Color.WHITE);
		bt_Check.setOpaque(false);
		bt_Check.setBorderPainted(false);
		bt_Check.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/check.png")));
		bt_Check.setHorizontalTextPosition(JButton.CENTER);
		bt_Check.setVerticalTextPosition(JButton.BOTTOM);
		bt_Check.addActionListener(this);
		pl_Menu.add(bt_Check);

		bt_Salary = new JButton("工 资 管 理");
		bt_Salary.setFont(font_bt);
		bt_Salary.setBounds(0, 380, 150, 100);
		bt_Salary.setFocusPainted(false);
		bt_Salary.setForeground(Color.WHITE);
		bt_Salary.setBackground(Color.WHITE);
		bt_Salary.setOpaque(false);
		bt_Salary.setBorderPainted(false);
		bt_Salary.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/salary.png")));
		bt_Salary.setHorizontalTextPosition(JButton.CENTER);
		bt_Salary.setVerticalTextPosition(JButton.BOTTOM);
//		bt_Salary.setContentAreaFilled(false); 
		bt_Salary.addActionListener(this);
		pl_Menu.add(bt_Salary);

		bt_Setup = new JButton("工 资 设 置");
		bt_Setup.setFont(font_bt);
		bt_Setup.setBounds(0, 500, 150, 100);
		bt_Setup.setFocusPainted(false);
		bt_Setup.setForeground(Color.WHITE);
		bt_Setup.setBackground(Color.WHITE);
		bt_Setup.setOpaque(false);
		bt_Setup.setBorderPainted(false);
		bt_Setup.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/setup.png")));
		bt_Setup.setHorizontalTextPosition(JButton.CENTER);
		bt_Setup.setVerticalTextPosition(JButton.BOTTOM);
		bt_Setup.addActionListener(this);
		pl_Menu.add(bt_Setup);

		bt_Logout = new JButton("注 销");
		bt_Logout.setFont(font_bt);
		bt_Logout.setBounds(0, 620, 150, 55);
		bt_Logout.setFocusPainted(false);
		bt_Logout.setForeground(Color.WHITE);
		bt_Logout.setBackground(Color.WHITE);
		bt_Logout.setOpaque(false);
		bt_Logout.setBorderPainted(false);
		bt_Logout.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/logout.png")));
		bt_Logout.setHorizontalTextPosition(JButton.RIGHT);
		bt_Logout.setVerticalTextPosition(JButton.CENTER);
		bt_Logout.addActionListener(this);
		pl_Menu.add(bt_Logout);

		bt_Exit = new JButton("退 出");
		bt_Exit.setFont(font_bt);
		bt_Exit.setBounds(0, 695, 150, 55);
		bt_Exit.setFocusPainted(false);
		bt_Exit.setForeground(Color.WHITE);
		bt_Exit.setBackground(Color.WHITE);
		bt_Exit.setOpaque(false);
		bt_Exit.setBorderPainted(false);
		bt_Exit.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/exit.png")));
		bt_Exit.setHorizontalTextPosition(JButton.RIGHT);
		bt_Exit.setVerticalTextPosition(JButton.CENTER);
		bt_Exit.addActionListener(this);
		bt_Exit.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		pl_Menu.add(bt_Exit);

		/* <-----------------------顶部信息面板-----------------------> */

		pl_Top = new JPanel();
		pl_Top.setLayout(null);
		pl_Top.setBackground(null);
		pl_Top.setOpaque(false);
		pl_Top.setBounds(160, 5, 1105, 60);
		pl_Img.add(pl_Top);

		jl_Greeting = new JLabel(greet, JLabel.RIGHT);
		jl_Greeting.setBounds(530, 17, 556, 25);
		jl_Greeting.setFont(new Font("微软雅黑", 0, 15));
		jl_Greeting.setForeground(Color.WHITE);
		pl_Top.add(jl_Greeting);

		menu_Title = new String("欢迎使用工资管理系统");
		jl_Title = new JLabel(menu_Title, SwingConstants.LEFT);
		jl_Title.setBounds(20, 12, 309, 35);
		jl_Title.setFont(new Font("微软雅黑", 1, 30));
		jl_Title.setForeground(Color.WHITE);
		pl_Top.add(jl_Title);

		getFeedback();
		bt_Message = new JButton(message_count);
		bt_Message.setFont(new Font("微软雅黑", 0, 15));
		bt_Message.setBounds(450, 17, 130, 25);
		bt_Message.setFocusPainted(false);
		bt_Message.setForeground(Color.WHITE);
		bt_Message.setBackground(Color.WHITE);
		bt_Message.setOpaque(false);
		bt_Message.setBorderPainted(false);
		bt_Message.setIcon(new ImageIcon(ClassLoader.getSystemResource("image/message.png")));
//		bt_Message.setContentAreaFilled(false);
		bt_Message.addActionListener(this);
		pl_Top.add(bt_Message);

		/* <-----------------------功能按钮面板-----------------------> */

		pl_Bar = new JPanel();
		pl_Bar.setLayout(null);
		pl_Bar.setBackground(null);
		pl_Bar.setOpaque(false);
		pl_Bar.setBounds(160, 65, 1105, 40);
		pl_Img.add(pl_Bar);

		int h = 30, y = 5;
		Font font_menu = new Font("微软雅黑", 1, 13);

		bt_Add = new JButton("添加");
		bt_Add.setFont(font_menu);
		bt_Add.setBounds(20, y, 80, h);
		bt_Add.addActionListener(this);
		bt_Add.setFocusPainted(false);
		pl_Bar.add(bt_Add);

		bt_Alter = new JButton("修改");
		bt_Alter.setFont(font_menu);
		bt_Alter.setBounds(115, y, 80, h);
		bt_Alter.addActionListener(this);
		bt_Alter.setFocusPainted(false);
		pl_Bar.add(bt_Alter);

		bt_Del = new JButton("删除");
		bt_Del.setFont(font_menu);
		bt_Del.setBounds(210, y, 80, h);
		bt_Del.addActionListener(this);
		bt_Del.setFocusPainted(false);
		bt_Del.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		pl_Bar.add(bt_Del);

		bt_Feedback = new JButton("反馈");
		bt_Feedback.setFont(font_menu);
		bt_Feedback.setBounds(20, y, 80, h);
		bt_Feedback.setFocusPainted(false);
		bt_Feedback.addActionListener(this);
//		pl_Bar.add(bt_Feedback);

		/* <-------小分界线-------> */

		comboBox = new JComboBox<String>();
		comboBox.setFont(font_menu);
		comboBoxModel = new DefaultComboBoxModel<String>();
		item = new String[] { "工资总表", "月工资", "部门工资", "年终奖", "加班工资" };
		comboBoxModel.addElement(item[0]);
		comboBoxModel.addElement(item[1]);
		comboBoxModel.addElement(item[2]);
		comboBoxModel.addElement(item[3]);
		comboBoxModel.addElement(item[4]);
		if (userRight.equalsIgnoreCase("普通用户")) {
			comboBoxModel.removeElement(item[1]);
			comboBoxModel.removeElement(item[2]);
		}
		comboBox.setModel(comboBoxModel);
		comboBox.addItemListener(this);
		comboBox.setBounds(380, y, 120, h);
		comboBox.setVisible(false);
		pl_Bar.add(comboBox);

		/* <-------小分界线-------> */

		input = new JTextField();
		input.setFont(new Font("微软雅黑", 0, 13));
		input.setBounds(585, y, 185, h);
		input.setDocument(new LimitWord_JTF(16));
		input.setBorder(null);
		input.setText(hint1);
		input.addActionListener(this);
		pl_Bar.add(input);

		hint_listner1 = new HintListener_JTF(input, hint1);
		hint_listner2 = new HintListener_JTF(input, hint2);
		hint_listner3 = new HintListener_JTF(input, hint3);
		hint_listner4 = new HintListener_JTF(input, hint4);
		hint_listner5 = new HintListener_JTF(input, hint5);
		hint_listner6 = new HintListener_JTF(input, hint6);

		input.addFocusListener(hint_listner1);

		bt_Search = new JButton("查找");
		bt_Search.setFont(font_menu);
		bt_Search.setBounds(770, y, 80, h);
		bt_Search.addActionListener(this);
		bt_Search.setFocusPainted(false);
		pl_Bar.add(bt_Search);

		/* <-------小分界线-------> */

		bt_Export = new JButton("导出");
		bt_Export.setFont(font_menu);
		bt_Export.setBounds(890, y, 80, h);
		bt_Export.addActionListener(this);
		bt_Export.setFocusPainted(false);
		pl_Bar.add(bt_Export);

		bt_Print = new JButton("打印");
		bt_Print.setFont(font_menu);
		bt_Print.setBounds(1004, y, 80, h);
		bt_Print.addActionListener(this);
		bt_Print.setFocusPainted(false);
		pl_Bar.add(bt_Print);

		/* <-----------------------表格信息面板-----------------------> */

		table = new JTable(null) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setOpaque(false);
		table.setFont(new Font("微软雅黑", 0, 15));

		scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.white);
		scrollPane.setOpaque(false);

	}

	private void setGreet() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		login_Time = dateFormat.format(date);
		greet = "欢迎你，" + userName + "        身份：" + userRight + "        本次登录时间：" + login_Time;
	}

	private void setFrameIcon() {
		Toolkit tool = getToolkit();
		Image image = tool.getImage(ClassLoader.getSystemResource("image/main.png"));
		setIconImage(image);
	}

	private void setButtonView(String userRight) {
		setBtView_AADC(false, false, false, false);
		if (userRight.equalsIgnoreCase("普通用户")) {
			pl_Bar.remove(bt_Add);
			pl_Bar.remove(bt_Del);
			pl_Bar.remove(bt_Alter);
			pl_Top.remove(bt_Message);
			bt_Alter.setLocation(20, 5);

			pl_Menu.remove(bt_Department);
			pl_Menu.remove(bt_Setup);

			bt_Check.setLocation(0, 140);
			bt_Salary.setLocation(0, 260);
			comboBox.setLocation(20, 5);
			setBtView_Search(false);
		}
	}

	/* *************弹窗界面************* */

	public void setClosing(Boolean b) {
		closing=b;
	}
	
	private void getFeedback() {
		ResultSet rs = DB_Apply.getResult(con, judge.sql_Feedback);
		numFeedback = 0;
		try {
			while (rs.next()) {
				if (String.valueOf(rs.getString("Symbol")).equals("未处理"))
					numFeedback++;
			}
			if (numFeedback > 0)
				message_count = " " + String.valueOf(numFeedback) + "条信息";
			else
				message_count = "暂无消息";
		} catch (Exception e) {
		}
	}

	private Boolean checkMessage() {
		Boolean STATUS = false;
		if (userRight.equals("管理员")) {
			window = new Display_Message(numFeedback, userName, this);
			if (numFeedback > 0) {
				STATUS = true;
			}
		}
		return STATUS;
	}

	private void InitMessage() {
		window.setVisible(true);
		window.setAlwaysOnTop(true);
		screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		taskHeight = screenInsets.bottom;
		screenSize = getToolkit().getScreenSize();// 获取屏幕大小
		windowSize = window.getSize();// 获取信息窗体大小
		location = new Point();
	}

	private void MessageOpen() {
		timer = new Timer(3, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				location.y -= 1;
				if (window.isShowing() && location.y > screenSize.height - windowSize.height - taskHeight)
					window.setLocation(location);
				else {
					Timer source = (Timer) e.getSource();
					source.stop();
				}
			}
		});
		InitMessage();
		location.x = screenSize.width - windowSize.width;
		location.y = screenSize.height;
		timer.start();
	}

	private void MessageClose() {
		timer = new Timer(3, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				location.y += 1;
				if (window.isShowing() && location.y <= screenSize.height) {
					window.setLocation(location);
				} else {
					Timer source = (Timer) e.getSource();
					source.stop();
				}
			}
		});
		InitMessage();
		location.x = screenSize.width - windowSize.width;// 初始化窗体位置
		location.y = screenSize.height - windowSize.height - taskHeight;
		timer.start();// 启动Timer控件
	}

	private void doClose() {
		try {
			biaoji++;//第一次点上面的按钮的作用
			closing = true;
			MessageClose();
			Thread.sleep(1000);
			window.dispose();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/* *************设置视图************* */

	public void updateFeedback() {
		getFeedback();
		bt_Message.setText(message_count);
	}

	private void setBtView_Search(Boolean b) {
		bt_Search.setVisible(b);
		input.setVisible(b);
	}

	private void setBtView_Feedback(int status) {
		if (status == 1) {
			pl_Bar.add(bt_Feedback);
			pl_Bar.remove(bt_Alter);
		} else if (status == 0) {
			pl_Bar.remove(bt_Feedback);
			pl_Bar.add(bt_Alter);
		} else if (status == -1) {
			pl_Bar.remove(bt_Feedback);
			pl_Bar.remove(bt_Alter);
		}
	}

	private void setBtView_AADC(Boolean b1, Boolean b2, Boolean b3, Boolean b4) {
		bt_Add.setEnabled(b1);
		bt_Alter.setEnabled(b2);
		bt_Del.setEnabled(b3);
		comboBox.setVisible(b4);
	}

	private void setInputHint(String hint, HintListener_JTF h) {
		input.setText(hint);
		input.setForeground(Color.GRAY);
		input.removeFocusListener(hint_listner1);
		input.removeFocusListener(hint_listner2);
		input.removeFocusListener(hint_listner3);
		input.removeFocusListener(hint_listner4);
		input.removeFocusListener(hint_listner5);
		input.removeFocusListener(hint_listner6);
		input.addFocusListener(h);
	}

	/* *************设置表格************* */

	public void setReport(Vector<Vector<String>> vector, Vector<String> title) {
		tableModel = new DefaultTableModel();
		tableModel.setDataVector(vector, title);
		if (tableModel.getRowCount() == 0) {
			JOptionPane.showMessageDialog(null, "没有查找到符合条件的结果!", "错误", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		table.setModel(tableModel);
		table.setRowHeight(25);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);// 在所有的调整大小操作中，按比例调整所有的列。
		table.getSelectionModel().addListSelectionListener(this);

		JTableHeader header = table.getTableHeader();
		setupTableHeader(header, new Font("微软雅黑", 1, 15), 40);

		scrollPane.setViewportView(table);
		scrollPane.setBounds(180, 125, 1065, 610);
		scrollPane.setBackground(Color.orange);
		scrollPane.setOpaque(false);

		pl_Img.add(scrollPane);
		pl_Img.repaint();

		FitTableColumns(table);
		FitTableProperty(table);
	}

	public void setupTableHeader(JTableHeader header, Font headerFont, int headerHeigth) {
		header.setFont(headerFont);
		header.setPreferredSize(new Dimension(header.getWidth(), headerHeigth));
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
	}

	public void FitTableColumns(JTable myTable) {
		JTableHeader header = myTable.getTableHeader();
		int rowCount = myTable.getRowCount();
		@SuppressWarnings("rawtypes")
		Enumeration columns = myTable.getColumnModel().getColumns();
		while (columns.hasMoreElements()) {
			TableColumn column = (TableColumn) columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			int width = (int) myTable.getTableHeader().getDefaultRenderer()
					.getTableCellRendererComponent(myTable, column.getIdentifier(), false, false, -1, col)
					.getPreferredSize().getWidth();
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) myTable.getCellRenderer(row, col)
						.getTableCellRendererComponent(myTable, myTable.getValueAt(row, col), false, false, row, col)
						.getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			header.setResizingColumn(column);

			column.setWidth(width + myTable.getIntercellSpacing().width + 10);

		}
	}

	public void FitTableProperty(JTable table) {
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (row % 2 == 0)
					setBackground(Color.white);
				else if (row % 2 == 1)
					setBackground(new Color(238, 238, 238));
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};

		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
		}
	}

	/* *************辅助方法************* */

	private String[] getTableHead(JTable table) {
		int column = table.getColumnCount();
		String[] head = new String[column];
		for (int i = 0; i < column; i++)
			head[i] = table.getColumnName(i);
		return head;
	}

	private String[][] getTableData(JTable table) {
		TableModel tableModel = table.getModel();
		String data[][] = new String[tableModel.getRowCount()][tableModel.getColumnCount()];
		for (int row = 0; row < tableModel.getRowCount(); row++) {
			for (int col = 0; col < tableModel.getColumnCount(); col++) {
				data[row][col] = (String) tableModel.getValueAt(row, col);
			}
		}
		return data;
	}

	private void convertStr(String[] s, String[] y, String[] m) {
		value = new String[s.length];
		M = new String[s.length];
		Y = new String[s.length];
		for (int i = 0; i < s.length; i++) {
			value[i] = s[i];
			Y[i] = y[i];
			M[i] = m[i];
//			System.out.println(s[i] + "-" + y[i] + "-" + m[i]);
//			System.out.println(value[i] + "-" + Y[i] + "-" + M[i]);
		}
	}

	private void setDataNull() {
		value = null;
		M = null;
		Y = null;
	}

	/* *************方法重现************* */

	public void actionPerformed(ActionEvent e) {

		/* <-----------------------左侧菜单部分-----------------------> */

		if (e.getSource() == bt_Worker || e.getSource() == bt_Check || e.getSource() == bt_Department
				|| e.getSource() == bt_Salary || e.getSource() == bt_Setup || e.getSource() == bt_Exit
				|| e.getSource() == bt_Logout) {
			doMenuAction(e);
		}

		/* <-----------------------顶部功能栏部分-----------------------> */

		if (e.getSource() == bt_Message) {
			doMessageAction();
			/*new Thread() {
				public void run() {
					try {
						if (biaoji == 0&&checkMessage()) {
							doClose();
						}
						biaoji++;
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

			}.start();*/
			window.dispose();
		}

		if (e.getSource() == bt_Add) {
			doAddAction();
		}

		if (e.getSource() == bt_Alter) {
			doAlterAction();
		}

		if (e.getSource() == bt_Del) {
			doDelAction();
		}

		if (e.getSource() == bt_Feedback) {
			if (value != null) {
				if (value.length == 1)
					new Edit_Feedback(con, this).transferWNo(value, Y, M);
				else if (value.length > 1)
					JOptionPane.showMessageDialog(null, "反馈暂时不可多选", "错误", JOptionPane.WARNING_MESSAGE);

			} else {
				JOptionPane.showMessageDialog(null, "请选择要反馈的信息", "错误", JOptionPane.WARNING_MESSAGE);
			}
		}

		if (e.getSource() == bt_Search || e.getSource() == input) {
			SQLSearch.search(jl_Title, input, con, judge, this);
		}

		/* ****************** 打印 ****************** */
		if (e.getSource() == bt_Print) {
			try {
				if (table.getRowCount() != 0)
					table.print();
				else
					JOptionPane.showMessageDialog(null, "当前表格为空", "错误", JOptionPane.WARNING_MESSAGE);
			} catch (PrinterException e1) {
				e1.printStackTrace();
			}
		}

		/* ****************** 导出 ****************** */
		if (e.getSource() == bt_Export) {
			try {
				if (table.getRowCount() != 0) {
					new OutExcel(getTableData(table), getTableHead(table));
				} else
					JOptionPane.showMessageDialog(null, "当前表格为空", "错误", JOptionPane.WARNING_MESSAGE);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void doMenuAction(ActionEvent e) {

		if (e.getSource() == bt_Worker) {
			jl_Title.setText("员工信息");
			setInputHint(hint1, hint_listner1);
			setBtView_AADC(true, true, true, false);
			setDataNull();
			if (userRight.equalsIgnoreCase("普通用户")) {
				setBtView_AADC(false, true, false, false);
				setBtView_Search(false);
				setBtView_Feedback(0);
			}
			setReport(DB_Apply.getDVector(DB_Apply.getResult(con, judge.sql_Worker)), judge.V_Worker);
		}

		if (e.getSource() == bt_Check) {
			jl_Title.setText("考勤信息");
			setDataNull();
			setInputHint(hint3, hint_listner3);
			setBtView_AADC(true, true, true, false);
			if (userRight.equalsIgnoreCase("普通用户")) {
				setInputHint(hint4, hint_listner4);
				setBtView_Search(true);
				setBtView_Feedback(1);
			}
			setReport(DB_Apply.getDVector(DB_Apply.getResult(con, judge.sql_Check)), judge.V_SalCheck);
		}

		if (e.getSource() == bt_Department) {
			jl_Title.setText("部门信息");
			setDataNull();
			comboBox.setVisible(false);
			setBtView_AADC(true, true, false, false);
			setInputHint(hint5, hint_listner5);
			setReport(DB_Apply.getDVector(DB_Apply.getResult(con, judge.sql_Department)), judge.V_Department);
		}

		if (e.getSource() == bt_Salary) {
			jl_Title.setText("工资报表");
			setDataNull();
			comboBox.setSelectedIndex(0);
			setBtView_AADC(false, false, false, true);
			setInputHint(hint3, hint_listner3);
			setReport(DB_Apply.getDVector(DB_Apply.getResult(con, judge.sql_SalTotal)), judge.V_SalTotal);
			if (userRight.equalsIgnoreCase("普通用户")) {
				setInputHint(hint4, hint_listner4);
				setBtView_Feedback(-1);
				setBtView_Search(true);
			}
		}

		if (e.getSource() == bt_Setup) {
			new Edit_Setup(con, judge);
		}

		if (e.getSource() == bt_Exit) {
			/*
			 * int option = JOptionPane.showConfirmDialog(null, "是否退出系统？", "退出",
			 * JOptionPane.YES_NO_OPTION); if (option == 0) {
			 */
			System.exit(0);
			/*
			 * try { con.close(); } catch (SQLException ex) { ex.printStackTrace(); } }
			 */
		}

		if (e.getSource() == bt_Logout) {
			int option = JOptionPane.showConfirmDialog(null, "是否注销当前登录？", "注销", JOptionPane.YES_NO_OPTION);
			if (option == 0) {
				dispose();
				new Login_User(con);
			}
		}
	}

	public void doAddAction() {
		if (jl_Title.getText().equals("考勤信息"))
			new Edit_Check(con, this, true);

		if (jl_Title.getText().equals("工资报表"))
			if (comboBox.getSelectedItem().equals(item[4]))
				new Edit_OveTime(con, this, true);

		if (jl_Title.getText().equals("部门信息"))
			new Edit_Department(con, this, true).getResult(DB_Apply.getResult(con, "select * from SalSys_Department"));

		if (jl_Title.getText().equals("员工信息"))
			new Edit_Worker(con, this, true).setInsertModel(DB_Apply.getResult(con, "select * from SalSys_Worker"));
	}

	public void doAlterAction() {
		if (jl_Title.getText().equals("考勤信息")) {
			if (value != null) {
				if (value.length == 1)
					new Edit_Check(con, this, true).transferWNo(value, Y, M);
				else if (value.length > 1)
					JOptionPane.showMessageDialog(null, "修改不可多选", "错误", JOptionPane.WARNING_MESSAGE);

			} else {
				new Edit_Check(con, this, true);
			}
		}

		if (jl_Title.getText().equals("工资报表")) {
			if (comboBox.getSelectedItem().equals(item[4])) {
				if (value != null) {
					if (value.length == 1)
						new Edit_OveTime(con, this, true).transferWNo(value, Y, M);
					else if (value.length > 1)
						JOptionPane.showMessageDialog(null, "修改不可多选", "错误", JOptionPane.WARNING_MESSAGE);
				} else {
					new Edit_OveTime(con, this, true);
				}
			}
		}

		if (jl_Title.getText().equals("部门信息")) {
			if (value != null) {
				if (value.length == 1)
					new Edit_Department(con, this, true).transferDNo(value,
							DB_Apply.getResult(con, "select * from SalSys_Department"));
				else if (value.length > 1)
					JOptionPane.showMessageDialog(null, "修改不可多选", "错误", JOptionPane.WARNING_MESSAGE);
			} else {
				new Edit_Department(con, this, true)
						.getResult(DB_Apply.getResult(con, "select * from SalSys_Department"));
			}
		}

		if (jl_Title.getText().equals("员工信息")) {
			if (userRight.equals("管理员")) {
				if (value != null) {
					if (value.length == 1)
						new Edit_Worker(con, this, true).setAlterModel(value,
								DB_Apply.getResult(con, "select * from SalSys_Worker"));
					else if (value.length > 1)
						JOptionPane.showMessageDialog(null, "修改不可多选", "错误", JOptionPane.WARNING_MESSAGE);
				}
			} else if (userRight.equals("普通用户")) {
				new Edit_Employee(con, this, true).setAlterModel(value,
						DB_Apply.getResult(con, "select * from SalSys_Worker"));
			}
		}
	}

	public void doDelAction() {
		if (jl_Title.getText().equals("考勤信息")) {
			if (value != null) {
				int f = JOptionPane.showConfirmDialog(null, "该操作将会影响所选员工相关的数据（包括加班记录），是否删除信息？");
				if (f == 0) {
					new Edit_Check(con, this, false).delWNo(value, Y, M);
					System.out.println("haha");
				}
			}
		}

		if (jl_Title.getText().equals("工资报表")) {
			if (comboBox.getSelectedItem().equals(item[4])) {
				if (value != null) {
					int f = JOptionPane.showConfirmDialog(null, "该操作将会影响所选员工相关的数据（包括考勤记录），是否删除信息？");
					if (f == 0) {
						new Edit_OveTime(con, this, false).delWNo(value, Y, M);
						System.out.println("haha");
					}
				}
			}
		}

		if (jl_Title.getText().equals("员工信息")) {
			if (value != null) {
				int f = JOptionPane.showConfirmDialog(null, "该操作将会影响所选员工所有数据，是否删除信息？");
				if (f == 0) {
					new Edit_Worker(con, this, false).delWNo(value);
					System.out.println("haha");
				}
			}
		}
	}

	public void doExit(WindowEvent e) {
		int option = JOptionPane.showConfirmDialog(Frame_Main.this, "确定退出系统? ", "提示 ", JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION)
			if (e.getWindow() == Frame_Main.this) {
				System.exit(0);
			} else {
				return;
			}
	}

	public void itemStateChanged(ItemEvent arg0) {
		input.setText("");
		scrollPane.setBounds(0, 0, 0, 0);
		scrollPane.repaint();

		if (comboBox.getSelectedItem().equals(item[4])) {
			setInputHint(hint4, hint_listner4);
			setDataNull();
			if (userRight.equals("管理员")) {
				setBtView_AADC(true, true, true, true);
				setInputHint(hint3, hint_listner3);
			}
			setReport(DB_Apply.getDVector(DB_Apply.getResult(con, judge.sql_SalOveTime)), judge.V_SalOveTime);

		} else if (comboBox.getSelectedItem().equals(item[0])) {
			setInputHint(hint4, hint_listner4);
			setDataNull();
			if (userRight.equals("管理员")) {
				setBtView_AADC(false, false, false, true);
				setInputHint(hint3, hint_listner3);
			}
			setReport(DB_Apply.getDVector(DB_Apply.getResult(con, judge.sql_SalTotal)), judge.V_SalTotal);

		} else if (comboBox.getSelectedItem().equals(item[3])) {
			setInputHint(hint4, hint_listner4);
			setDataNull();
			if (userRight.equals("管理员")) {
				setBtView_AADC(false, false, false, true);
				setInputHint(hint6, hint_listner6);
			}
			setReport(DB_Apply.getDVector(DB_Apply.getResult(con, judge.sql_SalAnnual)), judge.V_SalAnnual);

		} else if (comboBox.getSelectedItem().equals(item[2])) {
			setDataNull();
			if (userRight.equals("管理员")) {

				setBtView_AADC(false, false, false, true);
				setInputHint(hint2, hint_listner2);
			}
			setReport(DB_Apply.getDVector(DB_Apply.getResult(con, judge.sql_SalDepart)), judge.V_SalDepart);

		} else if (comboBox.getSelectedItem().equals(item[1])) {
			setDataNull();
			if (userRight.equals("管理员")) {
				setBtView_AADC(false, false, false, true);
				setInputHint(hint4, hint_listner4);
			}
			setReport(DB_Apply.getDVector(DB_Apply.getResult(con, judge.sql_SalMonth)), judge.V_SalMonth);
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) { // 支持拖动多选
			int[] rows = table.getSelectedRows();
			int count = rows.length;
			String[] WNo = new String[count];
			String[] Month = new String[count];
			String[] Year = new String[count];
			for (int i = 0; i < WNo.length; i++) {
				WNo[i] = String.valueOf(table.getValueAt(rows[i], 0));
				Month[i] = String.valueOf(table.getValueAt(rows[i], 3));
				Year[i] = String.valueOf(table.getValueAt(rows[i], 2));
			}
			convertStr(WNo, Year, Month);
		}
	}

	public void doMessageAction() {
		Edit_Message eidt_message= Edit_Message.getInstance(con, this, judge);
	}

}
