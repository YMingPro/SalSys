package systemFrame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import myModule.Frame_Edit;

public class Edit_Message extends Frame_Edit {

	private static final long serialVersionUID = 1L;

	private static Edit_Message instance;

	String[] value = new String[1], M = new String[1], Y = new String[1], item = null; // 辅助传值

	Connection con;
	Frame_Main main;
	DB_Judge judge;
	JScrollPane scrollPane;

	JPanel pl_Img;
	JTable table;
	DefaultTableModel tableModel;
	DefaultComboBoxModel<String> comboBoxModel;
	Vector<Vector<String>> vector;
	Vector<String> title;
	String fno, wno;
	int no, select;
	int[] gettip;
	int STATUE_forupdate = 0, statue;

	MyRender render = new MyRender();

	private Edit_Message(Connection con, Frame_Main main, DB_Judge judge) {
//		super.setModal(true);
		this.con = con;
		this.main = main;
		this.judge = judge;
		setTitle("反馈处理");
		setAlwaysOnTop(true);
		setResizable(false);

//		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		GUI_init();
		setReport(DB_Apply.getDVector(DB_Apply.getResult(con, judge.sql_Feedback)), judge.V_Feedback, 1);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO 自动生成的方法存根
				instance = null;
				super.windowClosed(arg0);
			}
		});
		setSize(1007, 621);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static synchronized Edit_Message getInstance(Connection con, Frame_Main main, DB_Judge judge) {

		if (instance == null) {
			instance = new Edit_Message(con, main, judge);
		}
		return instance;
	}

	@SuppressWarnings("serial")
	public void GUI_init() {
		pl_Img = new JPanel();
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

		/* <-----------------------表格信息面板-----------------------> */

		table = new JTable(null) {

			public boolean isCellEditable(int row, int column) {
				if (column == 5) {
					return true;
				} else {
					return false;
				}

			}

		};

		table.setOpaque(false);
		table.setFont(new Font("微软雅黑", 0, 15));

		scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.white);
		scrollPane.setOpaque(false);

	}

	public void doConfirm() {
		String sql = "UPDATE SalSys_Feedback SET Symbol='已处理' WHERE FNo='" + fno + "'AND WNo='" + wno + "'";
		DB_Apply.UpdateDialog(con, sql);
		setData();
		main.updateFeedback();
	}

	private void setWidth() {
		TableColumn firsetColumn = table.getColumnModel().getColumn(0);
		setsize(firsetColumn, 90);
		TableColumn secondColumn = table.getColumnModel().getColumn(1);
		setsize(secondColumn, 70);
		TableColumn thirdColumn = table.getColumnModel().getColumn(2);
		setsize(thirdColumn, 80);
		TableColumn fifColumn = table.getColumnModel().getColumn(4);
		setsize(fifColumn, 70);
		TableColumn lastColumn = table.getColumnModel().getColumn(5);
		setsize(lastColumn, 80);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);

	}

	private void setsize(TableColumn Column, int num) {
		Column.setPreferredWidth(num);
		Column.setMaxWidth(num);
		Column.setMinWidth(num);
	}

	/***********************************/

	public void setData() {
//		tableModel = new mytable(0);
//		tableModel =new DefaultTableModel();
		setReport(DB_Apply.getDVector(DB_Apply.getResult(con, judge.sql_Feedback)), judge.V_Feedback, 0);
	}

	public void setReport(Vector<Vector<String>> vector, Vector<String> title, int statue) {
		this.vector = vector;
		this.title = title;
		tableModel = new DefaultTableModel(vector, title);
//		tableModel.setDataVector(vector, title);
		if (tableModel.getRowCount() == 0) {
			JOptionPane.showMessageDialog(null, "没有查找到符合条件的结果!", "错误", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		table.setModel(tableModel);
		table.setRowHeight(30);
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);// 在所有的调整大小操作中，按比例调整所有的列。
//		table.getSelectionModel().addListSelectionListener(this);

		JTableHeader header = table.getTableHeader();
		main.setupTableHeader(header, new Font("微软雅黑", 1, 17), 40);
//		if (statue == 1) {
			table.getColumnModel().getColumn(5).setCellEditor(render);// 设置编辑器
			table.getColumnModel().getColumn(5).setCellRenderer(render);
//		}
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO 自动生成的方法存根
				int[] selection = table.getSelectedRows();
				for (int i = 0; i < selection.length; i++) {
					selection[i] = table.convertRowIndexToModel(selection[i]);
				}
				no = selection[0];

				fno = String.valueOf(table.getValueAt(no, 0));
				wno = String.valueOf(table.getValueAt(no, 1));
				Y[0] = String.valueOf(table.getValueAt(no, 2)).substring(0, 4);
				String temp = String.valueOf(table.getValueAt(no, 2));
				M[0] = String
						.valueOf(Integer.valueOf(String.valueOf(table.getValueAt(no, 2)).substring(5, temp.length())));
				value[0] = wno;

			}

		});
		scrollPane.setViewportView(table);
		scrollPane.setBounds(10, 10, 981, 572);
		scrollPane.setBackground(Color.orange);
		scrollPane.setOpaque(false);

		pl_Img.add(scrollPane);
		pl_Img.repaint();

//		main.FitTableColumns(table);
//		main.FitTableProperty(table);
		setWidth();
		setred();
	}

	private void setred() {
		for (int temo = 0; temo < table.getRowCount(); temo++) {
			if (String.valueOf(table.getValueAt(temo, 4)).equals("未处理")) {
				table.setValueAt("<html><strong><font color='red'>未处理</font><strong>", temo, 4);
//				System.out.println("weichuli");
			} else if (String.valueOf(table.getValueAt(temo, 4)).equals("已处理")) {
//				System.out.println("yichuli");
//				this.new MyRender().dore();

//				table.getCellRenderer(temo, 5).
			} else {
//				System.out.println("meichuli");
			}
		}
	}

	class MyRender extends AbstractCellEditor implements TableCellRenderer, ActionListener, TableCellEditor {

		private static final long serialVersionUID = 1L;
		private JButton button = new JButton("处理");
//		JPanel panel1 = new JPanel(new FlowLayout());

		public MyRender() {
//			panel1 = new JPanel(new FlowLayout());
//			panel1.add(button);
			button.addActionListener(this);
			button.setForeground(Color.black);
			button.setBounds(412, 285, 195, 40);
			button.setFocusPainted(false);
			button.setFont(new Font("微软雅黑", 1, 12));
		}

		

		@Override
		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table1, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			// TODO Auto-generated method stub
			return button;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Edit_MsgSolve.getInstance(con, main, true, Edit_Message.this, value, Y, M);
		}

		@Override
		public Component getTableCellEditorComponent(JTable table1, Object value, boolean isSelected, int row,
				int column) {
			// TODO Auto-generated method stub
			return button;
		}

	}

}
