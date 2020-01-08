package systemFunction;

import java.sql.Connection;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import systemFrame.DB_Apply;
import systemFrame.DB_Judge;
import systemFrame.Frame_Main;

public class SQLSearch {

	public static void search(JLabel jl_Title, JTextField input, Connection con, DB_Judge judge, Frame_Main main) {

		/****************************************************************************/

		if (jl_Title.getText().equals("考勤信息")) {
			SearchMode1(input.getText(), main, judge.sql_Check, judge.V_SalCheck);
		}

		/****************************************************************************/

		if (jl_Title.getText().equals("工资报表")) {

			if (main.comboBox.getSelectedItem().equals("工资总表")) {
				SearchMode1(input.getText(), main, judge.sql_SalTotal, judge.V_SalTotal);
			}

			if (main.comboBox.getSelectedItem().equals("月工资")) {
				SearchMode2(input.getText(), main, judge.sql_SalMonth, judge.V_SalMonth);
			}
			if (main.comboBox.getSelectedItem().equals("部门工资")) {
				SearchMode4(input.getText(), main, judge.sql_SalDepart, judge.V_SalDepart);
			}
			if (main.comboBox.getSelectedItem().equals("加班工资")) {
				SearchMode1(input.getText(), main, judge.sql_SalOveTime, judge.V_SalOveTime);
			}
			if (main.comboBox.getSelectedItem().equals("年终奖")) {
				SearchMode3(input.getText(), main, judge.sql_SalAnnual, judge.V_SalAnnual);
			}
		}

		if (jl_Title.getText().equals("员工信息") || jl_Title.getText().equals("欢迎使用工资管理系统")) {
			main.setReport(DB_Apply.getDVector(DB_Apply.getResult(con, judge.sql_Worker + " where WNo = '"
					+ input.getText() + "' or WName LIKE '%" + input.getText() + "%'")), judge.V_Worker);
		}

		/****************************************************************************/

		if (jl_Title.getText().equals("部门信息")) {
			main.setReport(DB_Apply.getDVector(DB_Apply.getResult(con, judge.sql_Department + " where DNo = '"
					+ input.getText() + "%' or DName LIKE '%" + input.getText() + "%'")), judge.V_Department);
		}

	}

	public static void SearchMode4(String str, Frame_Main main, String sql, Vector<String> view) {
		String regex1 = "[0-9]+";
		String regex2 = "\\d{4}-\\d{1,2}";

		Pattern pattern1 = Pattern.compile(regex1);
		Matcher isNum = pattern1.matcher(str);

		Pattern pattern2 = Pattern.compile(regex2);
		Matcher isSymbol = pattern2.matcher(str);

		if (isNum.matches() || isSymbol.matches()) {

			if (isNum.matches()) {
				if (str.length() <= 2) {
					int temp = Integer.valueOf(str);
					if (temp >= 1 && temp <= 12) {
						main.setReport(DB_Apply.getDVector(DB_Apply.getResult(main.con, sql + " where SMonth = " + str)),
								view);
					} else {
						JOptionPane.showMessageDialog(null, "月份输入错误！有效值为1到12", "错误", JOptionPane.ERROR_MESSAGE);
					}

				} else if (str.length() == 4) {
					int temp = Integer.valueOf(str);
					if (temp >= 1900 && temp <= 2900) {
						main.setReport(DB_Apply.getDVector(DB_Apply.getResult(main.con, sql + " where SYear = " + str)),
								view);
					} else {
						JOptionPane.showMessageDialog(null, "年份输入错误！有效值为1900到2900", "错误", JOptionPane.ERROR_MESSAGE);
					}

				} else if (str.length() == 3) {
					main.setReport(DB_Apply.getDVector(DB_Apply.getResult(main.con, sql + " where DNo = " + str)), view);
				} else {
					JOptionPane.showMessageDialog(null, "请输入有效数值！", "错误", JOptionPane.ERROR_MESSAGE);
				}
			} else if (isSymbol.matches()) {
				int year = Integer.valueOf(str.substring(0, 4));
				int month = Integer.valueOf(str.substring(5, str.length()));
				Boolean b1, b2;
				if (year >= 1900 && year <= 2900) {
					b1 = false;
				} else {
					b1 = true;
					JOptionPane.showMessageDialog(null, "年份输入错误！有效值为1900到2900。", "错误", JOptionPane.ERROR_MESSAGE);
				}
				if (month >= 1 && month <= 12) {
					b2 = false;
				} else {
					b2 = true;
					JOptionPane.showMessageDialog(null, "月份输入错误！有效值为1到12。", "错误", JOptionPane.ERROR_MESSAGE);
				}

				if (b1 || b2) {
					JOptionPane.showMessageDialog(null, "请输入有效的年份！格式为\"yyyy-mm\"。", "错误", JOptionPane.ERROR_MESSAGE);
				} else {
					main.setReport(DB_Apply.getDVector(
							DB_Apply.getResult(main.con, sql + " where SYear = " + year + "and SMonth =" + month)),
							view);
				}
			} else {
				JOptionPane.showMessageDialog(null, "输入错误！", "错误", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			main.setReport(DB_Apply.getDVector(DB_Apply.getResult(main.con, sql + " where DName LIKE '%" + str + "%'")),
					view);
		}

	}

	public static void SearchMode3(String str, Frame_Main main, String sql, Vector<String> view) {
		String regex1 = "[0-9]+";

		Pattern pattern1 = Pattern.compile(regex1);
		Matcher isNum = pattern1.matcher(str);

		if (isNum.matches()) {
			if (str.length() == 4) {
				int temp = Integer.valueOf(str);
				if (temp >= 1900 && temp <= 2900) {
					main.setReport(DB_Apply.getDVector(DB_Apply.getResult(main.con, sql + " where SYear = " + str)), view);
				}

				else {
					JOptionPane.showMessageDialog(null, "年份输入错误！有效值为1900到2900", "错误", JOptionPane.ERROR_MESSAGE);
				}
			} else if (str.length() == 6) {
				main.setReport(DB_Apply.getDVector(DB_Apply.getResult(main.con, sql + " where WNo = " + str)), view);
			} else {
				JOptionPane.showMessageDialog(null, "请输入有效数值！", "错误", JOptionPane.ERROR_MESSAGE);
			}
		}

		else {
			main.setReport(DB_Apply.getDVector(DB_Apply.getResult(main.con, sql + " where WName LIKE '%" + str + "%'")),
					view);
		}

	}

	public static void SearchMode2(String str, Frame_Main main, String sql, Vector<String> view) {
		String regex1 = "[0-9]+";
		String regex2 = "\\d{4}-\\w{1,2}";

		Pattern pattern1 = Pattern.compile(regex1);
		Matcher isNum = pattern1.matcher(str);

		Pattern pattern2 = Pattern.compile(regex2);
		Matcher isSymbol = pattern2.matcher(str);

		if (isNum.matches()) {
			if (str.length() <= 2) {
				int temp = Integer.valueOf(str);
				if (temp >= 1 && temp <= 12) {
					main.setReport(DB_Apply.getDVector(DB_Apply.getResult(main.con, sql + " where SMonth = " + str)),
							view);
				} else {
					JOptionPane.showMessageDialog(null, "月份输入错误！有效值为1到12", "错误", JOptionPane.ERROR_MESSAGE);
				}

			} else if (str.length() == 4) {
				int temp = Integer.valueOf(str);
				if (temp >= 1900 && temp <= 2900) {
					main.setReport(DB_Apply.getDVector(DB_Apply.getResult(main.con, sql + " where SYear = " + str)), view);
				} else {
					JOptionPane.showMessageDialog(null, "年份输入错误！有效值为1900到2900", "错误", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "请输入有效数值！", "错误", JOptionPane.ERROR_MESSAGE);
			}
		} else if (isSymbol.matches()) {
			int year = Integer.valueOf(str.substring(0, 4));
			int month = Integer.valueOf(str.substring(5, str.length()));
			Boolean b1, b2;
			if (year >= 1900 && year <= 2900) {
				b1 = false;
			} else {
				b1 = true;
				JOptionPane.showMessageDialog(null, "年份输入错误！有效值为1900到2900。", "错误", JOptionPane.ERROR_MESSAGE);
			}
			if (month >= 1 && month <= 12) {
				b2 = false;
			} else {
				b2 = true;
				JOptionPane.showMessageDialog(null, "月份输入错误！有效值为1到12。", "错误", JOptionPane.ERROR_MESSAGE);
			}

			if (b1 || b2) {
				JOptionPane.showMessageDialog(null, "请输入有效的年份！格式为\"yyyy-mm\"。", "错误", JOptionPane.ERROR_MESSAGE);
			} else {
				main.setReport(
						DB_Apply.getDVector(
								DB_Apply.getResult(main.con, sql + " where SYear = " + year + "and SMonth =" + month)),
						view);
			}
		} else {
			JOptionPane.showMessageDialog(null, "输入错误！", "错误", JOptionPane.ERROR_MESSAGE);
		}

	}

	public static void SearchMode1(String str, Frame_Main main, String sql, Vector<String> view) {
		String regex1 = "[0-9]+";
		String regex2 = "\\d{4}-\\w{1,2}";

		Pattern pattern1 = Pattern.compile(regex1);
		Matcher isNum = pattern1.matcher(str);

		Pattern pattern2 = Pattern.compile(regex2);
		Matcher isSymbol = pattern2.matcher(str);

		if (isNum.matches() || isSymbol.matches()) {

			if (isNum.matches()) {
				if (str.length() <= 2) {
					int temp = Integer.valueOf(str);
					if (temp >= 1 && temp <= 12) {
						main.setReport(DB_Apply.getDVector(DB_Apply.getResult(main.con, sql + " where SMonth = " + str)),
								view);
					} else {
						JOptionPane.showMessageDialog(null, "月份输入错误！有效值为1到12", "错误", JOptionPane.ERROR_MESSAGE);
					}

				} else if (str.length() == 4) {
					int temp = Integer.valueOf(str);
					if (temp >= 1900 && temp <= 2900) {
						main.setReport(DB_Apply.getDVector(DB_Apply.getResult(main.con, sql + " where SYear = " + str)),
								view);
					} else {
						JOptionPane.showMessageDialog(null, "年份输入错误！有效值为1900到2900", "错误", JOptionPane.ERROR_MESSAGE);
					}

				} else if (str.length() == 6) {
					main.setReport(DB_Apply.getDVector(DB_Apply.getResult(main.con, sql + " where WNo = " + str)), view);
				} else {
					JOptionPane.showMessageDialog(null, "请输入有效数值！", "错误", JOptionPane.ERROR_MESSAGE);
				}
			} else if (isSymbol.matches()) {
				int year = Integer.valueOf(str.substring(0, 4));
				int month = Integer.valueOf(str.substring(5, str.length()));
				Boolean b1, b2;
				if (year >= 1900 && year <= 2900) {
					b1 = false;
				} else {
					b1 = true;
					JOptionPane.showMessageDialog(null, "年份输入错误！有效值为1900到2900。", "错误", JOptionPane.ERROR_MESSAGE);
				}
				if (month >= 1 && month <= 12) {
					b2 = false;
				} else {
					b2 = true;
					JOptionPane.showMessageDialog(null, "月份输入错误！有效值为1到12。", "错误", JOptionPane.ERROR_MESSAGE);
				}

				if (b1 || b2) {
					JOptionPane.showMessageDialog(null, "请输入有效的年份！格式为\"yyyy-mm\"。", "错误", JOptionPane.ERROR_MESSAGE);
				} else {
					main.setReport(DB_Apply.getDVector(
							DB_Apply.getResult(main.con, sql + " where SYear = " + year + "and SMonth =" + month)),
							view);
				}
			} else {
				JOptionPane.showMessageDialog(null, "输入错误！", "错误", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			main.setReport(DB_Apply.getDVector(DB_Apply.getResult(main.con, sql + " where WName LIKE '%" + str + "%'")),
					view);
		}

	}

}
