package systemFrame;

import java.util.Vector;

public class DB_Judge {

	public String sql_Department = "select * from V_Department ";
	public String sql_SalAnnual = "select * from V_SalAnnual ";
	public String sql_Check = "select * from V_SalCheck ";
	public String sql_SalDepart = "select * from V_SalDepart";
	public String sql_SalMonth = "select * from V_SalMonth ";
	public String sql_SalOveTime = "select * from V_SalOveTime";
	public String sql_SalTotal = "select * from V_SalTotal";
	public String sql_Worker = "select * from V_Worker";
	public String sql_SetWage = "select * from SalSys_SetWage";
	public String sql_Education = "select * from SalSys_Education";
	public String sql_Job = "select * from SalSys_Job";
	public String sql_Postion = "select * from SalSys_Position";
	public String sql_Tax = "select * from SalSys_Tax";
	public String sql_Allowance = "select * from SalSys_Allowance";
	public String sql_Depart = "select * from SalSys_Department";
	public String sql_Feedback = "select * from V_Feedback";

	public 	Vector<String> V_Worker, V_Department, V_SalCheck, V_SalOveTime, V_SalAnnual, V_SalTotal, V_SalDepart, V_SalMonth,
			SalSys_Allowance, V_Feedback;

	public DB_Judge(String userID, String userRight) {
		if (userRight.equals("普通用户")) {
			sql_Department = sql_Department + " where WNo=" + userID;
			sql_SalAnnual += " where WNo=" + userID;
			sql_Worker += " where WNo=" + userID;
			sql_Check += " where WNo=" + userID;
			sql_SalTotal += " where WNo=" + userID;
			sql_SalOveTime += " where WNo=" + userID;
			Init();
		}
		if (userRight.equals("管理员")) {
			Init();
		}
	}

	public void Init() {
		V_Worker = new Vector<String>();
		V_Worker.add("员工号 ");
		V_Worker.add("姓名");
		V_Worker.add("性别");
		V_Worker.add("出生日期");
		V_Worker.add("籍贯");
		V_Worker.add("学历");
		V_Worker.add("联系号码 ");
		V_Worker.add("入职年份");
		V_Worker.add("身份证");
		V_Worker.add("部门");
		V_Worker.add("职称");
		V_Worker.add("职务");
		V_Worker.add("工龄");

		V_SalCheck = new Vector<String>();
		V_SalCheck.add("员工号");
		V_SalCheck.add("姓名");
		V_SalCheck.add("年份");
		V_SalCheck.add("月份");
		V_SalCheck.add("出差天数");
		V_SalCheck.add("旷工次数");
		V_SalCheck.add("请假天数");
		V_SalCheck.add("迟到次数");
		V_SalCheck.add("考勤扣款");

		V_SalOveTime = new Vector<String>();
		V_SalOveTime.add("员工号");
		V_SalOveTime.add("姓名");
		V_SalOveTime.add("年份");
		V_SalOveTime.add("月份");
		V_SalOveTime.add("节假日（小时）");
		V_SalOveTime.add("工作日（小时）");
		V_SalOveTime.add("加班津贴");

		V_SalAnnual = new Vector<String>();
		V_SalAnnual.add("员工号");
		V_SalAnnual.add("姓名");
		V_SalAnnual.add("年份");
		V_SalAnnual.add("津贴总和");
		V_SalAnnual.add("工资总和");
		V_SalAnnual.add("年终奖金");

		V_SalTotal = new Vector<String>();
		V_SalTotal.add("员工号");
		V_SalTotal.add("姓名");
		V_SalTotal.add("年份");
		V_SalTotal.add("月份");
		V_SalTotal.add("交通补贴");
		V_SalTotal.add("全勤补贴");
		V_SalTotal.add("住房补贴");
		V_SalTotal.add("伙食补贴");
		V_SalTotal.add("考勤扣款");
		V_SalTotal.add("基本工资");
		V_SalTotal.add("加班津贴");
		V_SalTotal.add("税前工资");
		V_SalTotal.add("税后工资");

		V_SalDepart = new Vector<String>();
		V_SalDepart.add("部门号");
		V_SalDepart.add("部门名");
		V_SalDepart.add("年份");
		V_SalDepart.add("月份");
		V_SalDepart.add("人均");
		V_SalDepart.add("总额");

		V_SalMonth = new Vector<String>();
		V_SalMonth.add("年份");
		V_SalMonth.add("月份");
		V_SalMonth.add("人均");
		V_SalMonth.add("总额");

		V_Department = new Vector<String>();
		V_Department.add("部门编号");
		V_Department.add("部门名称");
		V_Department.add("主管姓名");
		V_Department.add("人数");

		SalSys_Allowance = new Vector<String>();
		SalSys_Allowance.add("交通补贴");
		SalSys_Allowance.add("全勤补贴");
		SalSys_Allowance.add("住房补贴");
		SalSys_Allowance.add("伙食补贴");

		V_Feedback = new Vector<String>();
		V_Feedback.add("反馈号");
		V_Feedback.add("员工号");
		V_Feedback.add("考勤年月");
		V_Feedback.add("反馈信息");
		V_Feedback.add("状态");
		V_Feedback.add("操作");
		

	}

}
