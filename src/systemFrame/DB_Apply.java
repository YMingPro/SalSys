package systemFrame;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

import myModule.Frame_Edit;

public class DB_Apply {

	/* *************获取结果集************* */
	public static ResultSet getResult(Connection con, String sql) {
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			System.out.println("这里出错啦");
			JOptionPane.showMessageDialog(null, e.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
			e.getStackTrace();
		}
		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("出错啦");
			e.getStackTrace();
		}
		return rs;
	}

	/* ************* 获取双Vector ************* */
	public static Vector<Vector<String>> getDVector(ResultSet rs) {

		Vector<Vector<String>> vector = new Vector<Vector<String>>();

		ResultSetMetaData rsm;

		int column = 0;

		try {
			rsm = rs.getMetaData();
			column = rsm.getColumnCount();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
			e.getStackTrace();
		}

		Vector<String> v;

		try {
			while (rs.next()) {
				v = new Vector<String>();
				for (int i = 1; i <= column; i++)
					v.add(rs.getString(i));
//				Enumeration<String> elements = v.elements();
//				while (elements.hasMoreElements()) {
//					System.out.println(elements.nextElement());
//				}
				vector.add(v);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
			e.getStackTrace();
		}

		return vector;
	}

	/* ************* 获取单Vector ************* */
	public static Vector<String> getSVector(ResultSet rs) {

		Vector<String> vector = new Vector<String>();

		ResultSetMetaData rsm;

		int column = 0;

		try {
			rsm = rs.getMetaData();
			column = rsm.getColumnCount();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
			e.getStackTrace();
		}

		try {
			while (rs.next()) {
				for (int i = 1; i <= column; i++)
					vector.add(rs.getString(i));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return vector;
	}

	/* ************* 获取单String ************* */
	public static String[] getSString(Vector<String> v) {
		int count = v.size();
		String[] s = v.toArray(new String[count]);// vector -> array -> []
		return s;
	}

	/* ************* 更新 ************* */
	public static int UpdateData(Connection con, String sql, Frame_Edit frame) {
		Statement sm = null;
		int rs = 0;
		try {
			sm = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
			e.getStackTrace();
		}
		try {
			rs = sm.executeUpdate(sql);
			
				
			
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "更新失败，请检查输入项的格式是否正确!\n");
			JOptionPane.showConfirmDialog(null, e.getMessage());
			e.getStackTrace();
			
		}
		frame.setData();
		return rs;
	}
	
	public static int UpdateDialog(Connection con, String sql) {
		Statement sm = null;
		int rs = 0;
		try {
			sm = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
			e.getStackTrace();
		}
		try {
			rs = sm.executeUpdate(sql);
			
				
			
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "更新失败，请检查输入项的格式是否正确!\n");
			JOptionPane.showConfirmDialog(null, e.getMessage());
			e.getStackTrace();
			
		}
		return rs;
	}


	/* ************* 插入 ************* */
	public static int InsertData(Connection con, String sql, Frame_Edit frame) {
		Statement sm = null;
		int rs = 0;
		try {
			sm = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
			e.getStackTrace();
		}
		try {
			rs = sm.executeUpdate(sql);
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "插入失败，请检查输入项的格式是否正确!\n");
			JOptionPane.showConfirmDialog(null, e.getMessage());
			e.getStackTrace();
			frame.setData();
		}
		return rs;
	}

	/* ************* 删除 ************* */
	public static int DelData(Connection con, String sql, Frame_Edit frame) {
		Statement sm = null;
		int rs = 0;
		try {
			sm = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
		}
		try {
			rs = sm.executeUpdate(sql);
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "删除失败，请检查输入项的格式是否正确!\n");
			JOptionPane.showConfirmDialog(null, e.getMessage());
			e.getStackTrace();
			frame.setData();
		}
		return rs;
	}

	/* ************* Setup面板调用 ************* */
	public static String SearchMoney(ResultSet rs) {
		try {
			while (rs.next())
				return String.valueOf((rs.getString(1)));
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		return null;
	}

}
