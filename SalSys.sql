/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : SQL Server
 Source Server Version : 11003000
 Source Host           : localhost:1433
 Source Catalog        : SalSys
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 11003000
 File Encoding         : 65001

 Date: 08/01/2020 21:43:57
*/


-- ----------------------------
-- Table structure for SalSys_Allowance
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[SalSys_Allowance]') AND type IN ('U'))
	DROP TABLE [dbo].[SalSys_Allowance]
GO

CREATE TABLE [dbo].[SalSys_Allowance] (
  [ATransport] decimal(8,2) DEFAULT ((0)) NULL,
  [APresent] decimal(8,2) DEFAULT ((0)) NULL,
  [AHouse] decimal(8,2) DEFAULT ((0)) NULL,
  [ADiet] decimal(8,2) DEFAULT ((0)) NULL
)
GO

ALTER TABLE [dbo].[SalSys_Allowance] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of SalSys_Allowance
-- ----------------------------
INSERT INTO [dbo].[SalSys_Allowance] VALUES (N'50.00', N'100.00', N'100.00', N'300.00')
GO


-- ----------------------------
-- Table structure for SalSys_CheckStat
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[SalSys_CheckStat]') AND type IN ('U'))
	DROP TABLE [dbo].[SalSys_CheckStat]
GO

CREATE TABLE [dbo].[SalSys_CheckStat] (
  [WNo] char(6) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [CheckYear] int  NOT NULL,
  [CheckMonth] int  NOT NULL,
  [EvectionDay] smallint  NULL,
  [AbsentDay] smallint  NULL,
  [LeaveDay] smallint  NULL,
  [LateDay] smallint  NULL,
  [THoliday] smallint  NULL,
  [TWeekday] smallint  NULL
)
GO

ALTER TABLE [dbo].[SalSys_CheckStat] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of SalSys_CheckStat
-- ----------------------------
INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200000', N'2016', N'8', N'0', N'1', N'0', N'0', N'4', N'0')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200000', N'2019', N'12', N'0', N'0', N'1', N'0', N'4', N'0')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200001', N'2018', N'3', N'0', N'3', N'0', N'0', N'2', N'0')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200001', N'2019', N'2', N'1', N'0', N'0', N'0', N'1', N'1')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200002', N'2018', N'2', N'2', N'0', N'0', N'0', N'5', N'0')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200003', N'2019', N'4', N'0', N'0', N'0', N'2', N'8', N'0')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200003', N'2019', N'5', N'0', N'0', N'0', N'1', N'0', N'0')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200004', N'2019', N'2', N'0', N'0', N'4', N'0', N'0', N'0')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200005', N'2016', N'11', N'0', N'0', N'0', N'1', N'5', N'5')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200005', N'2018', N'2', N'1', N'0', N'4', N'0', N'4', N'0')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200006', N'2015', N'8', N'0', N'1', N'0', N'0', N'0', N'0')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200006', N'2019', N'7', N'0', N'0', N'5', N'0', N'0', N'0')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200007', N'2014', N'2', N'0', N'1', N'0', N'1', N'4', N'4')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200007', N'2018', N'7', N'1', N'0', N'1', N'0', N'8', N'1')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200009', N'2015', N'5', N'0', N'0', N'2', N'0', N'4', N'0')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200010', N'2017', N'2', N'0', N'1', N'0', N'3', N'0', N'0')
GO

INSERT INTO [dbo].[SalSys_CheckStat] VALUES (N'200010', N'2019', N'1', N'0', N'3', N'2', N'0', N'1', N'0')
GO


-- ----------------------------
-- Table structure for SalSys_Department
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[SalSys_Department]') AND type IN ('U'))
	DROP TABLE [dbo].[SalSys_Department]
GO

CREATE TABLE [dbo].[SalSys_Department] (
  [DNo] char(3) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [DName] varchar(12) COLLATE Chinese_PRC_CI_AS  NOT NULL
)
GO

ALTER TABLE [dbo].[SalSys_Department] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of SalSys_Department
-- ----------------------------
INSERT INTO [dbo].[SalSys_Department] VALUES (N'001', N'人力资源部')
GO

INSERT INTO [dbo].[SalSys_Department] VALUES (N'002', N'财务部')
GO

INSERT INTO [dbo].[SalSys_Department] VALUES (N'003', N'产品开发部')
GO

INSERT INTO [dbo].[SalSys_Department] VALUES (N'004', N'后勤部')
GO


-- ----------------------------
-- Table structure for SalSys_Education
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[SalSys_Education]') AND type IN ('U'))
	DROP TABLE [dbo].[SalSys_Education]
GO

CREATE TABLE [dbo].[SalSys_Education] (
  [EEdu] varchar(12) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [EMoney] decimal(8,2) DEFAULT ((0)) NOT NULL
)
GO

ALTER TABLE [dbo].[SalSys_Education] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of SalSys_Education
-- ----------------------------
INSERT INTO [dbo].[SalSys_Education] VALUES (N'本科', N'500.00')
GO

INSERT INTO [dbo].[SalSys_Education] VALUES (N'博士', N'650.00')
GO

INSERT INTO [dbo].[SalSys_Education] VALUES (N'大专', N'400.00')
GO

INSERT INTO [dbo].[SalSys_Education] VALUES (N'高中', N'400.00')
GO

INSERT INTO [dbo].[SalSys_Education] VALUES (N'硕士', N'600.00')
GO

INSERT INTO [dbo].[SalSys_Education] VALUES (N'中专', N'300.00')
GO


-- ----------------------------
-- Table structure for SalSys_Feedback
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[SalSys_Feedback]') AND type IN ('U'))
	DROP TABLE [dbo].[SalSys_Feedback]
GO

CREATE TABLE [dbo].[SalSys_Feedback] (
  [FNo] char(8) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [WNo] char(6) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [CheckYear] int  NOT NULL,
  [CheckMonth] int  NOT NULL,
  [FMessage] text COLLATE Chinese_PRC_CI_AS  NULL,
  [Symbol] varchar(6) COLLATE Chinese_PRC_CI_AS  NOT NULL
)
GO

ALTER TABLE [dbo].[SalSys_Feedback] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of SalSys_Feedback
-- ----------------------------
INSERT INTO [dbo].[SalSys_Feedback] VALUES (N'20190909', N'200000', N'2016', N'8', N'您好', N'未处理')
GO

INSERT INTO [dbo].[SalSys_Feedback] VALUES (N'20190909', N'200003', N'2019', N'5', N'错了！！！！！！', N'未处理')
GO

INSERT INTO [dbo].[SalSys_Feedback] VALUES (N'20190912', N'200004', N'2019', N'2', N'你好', N'未处理')
GO

INSERT INTO [dbo].[SalSys_Feedback] VALUES (N'20190912', N'200007', N'2014', N'2', N'你好啊你好啊你好啊啊你好啊你好啊你好啊啊你好啊你好啊你好啊啊', N'未处理')
GO


-- ----------------------------
-- Table structure for SalSys_Job
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[SalSys_Job]') AND type IN ('U'))
	DROP TABLE [dbo].[SalSys_Job]
GO

CREATE TABLE [dbo].[SalSys_Job] (
  [JName] varchar(12) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [JMoney] decimal(8,2) DEFAULT ((0)) NOT NULL
)
GO

ALTER TABLE [dbo].[SalSys_Job] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of SalSys_Job
-- ----------------------------
INSERT INTO [dbo].[SalSys_Job] VALUES (N'部门主管', N'2000.00')
GO

INSERT INTO [dbo].[SalSys_Job] VALUES (N'副经理', N'3000.00')
GO

INSERT INTO [dbo].[SalSys_Job] VALUES (N'经理', N'4000.00')
GO

INSERT INTO [dbo].[SalSys_Job] VALUES (N'员工', N'1000.00')
GO

INSERT INTO [dbo].[SalSys_Job] VALUES (N'组长', N'1500.00')
GO


-- ----------------------------
-- Table structure for SalSys_Position
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[SalSys_Position]') AND type IN ('U'))
	DROP TABLE [dbo].[SalSys_Position]
GO

CREATE TABLE [dbo].[SalSys_Position] (
  [PName] varchar(12) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [PMoney] decimal(8,2) DEFAULT ((0)) NOT NULL
)
GO

ALTER TABLE [dbo].[SalSys_Position] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of SalSys_Position
-- ----------------------------
INSERT INTO [dbo].[SalSys_Position] VALUES (N'初级工程师', N'2000.00')
GO

INSERT INTO [dbo].[SalSys_Position] VALUES (N'初级技师', N'1000.00')
GO

INSERT INTO [dbo].[SalSys_Position] VALUES (N'高级工程师', N'6000.00')
GO

INSERT INTO [dbo].[SalSys_Position] VALUES (N'高级技师', N'4000.00')
GO

INSERT INTO [dbo].[SalSys_Position] VALUES (N'中级工程师', N'4000.00')
GO

INSERT INTO [dbo].[SalSys_Position] VALUES (N'中级技师', N'3000.00')
GO


-- ----------------------------
-- Table structure for SalSys_SetWage
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[SalSys_SetWage]') AND type IN ('U'))
	DROP TABLE [dbo].[SalSys_SetWage]
GO

CREATE TABLE [dbo].[SalSys_SetWage] (
  [SItem] nvarchar(10) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [SMoney] decimal(8,2)  NULL
)
GO

ALTER TABLE [dbo].[SalSys_SetWage] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of SalSys_SetWage
-- ----------------------------
INSERT INTO [dbo].[SalSys_SetWage] VALUES (N'迟到一次', N'10.00')
GO

INSERT INTO [dbo].[SalSys_SetWage] VALUES (N'出差一天', N'50.00')
GO

INSERT INTO [dbo].[SalSys_SetWage] VALUES (N'工龄补贴', N'100.00')
GO

INSERT INTO [dbo].[SalSys_SetWage] VALUES (N'工作日加班', N'15.00')
GO

INSERT INTO [dbo].[SalSys_SetWage] VALUES (N'节假日加班', N'240.00')
GO

INSERT INTO [dbo].[SalSys_SetWage] VALUES (N'旷工一天', N'50.00')
GO

INSERT INTO [dbo].[SalSys_SetWage] VALUES (N'请假一天', N'20.00')
GO


-- ----------------------------
-- Table structure for SalSys_Tax
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[SalSys_Tax]') AND type IN ('U'))
	DROP TABLE [dbo].[SalSys_Tax]
GO

CREATE TABLE [dbo].[SalSys_Tax] (
  [Std] decimal(8,2) DEFAULT ((0)) NOT NULL,
  [Rate] float(53) DEFAULT ((0)) NULL
)
GO

ALTER TABLE [dbo].[SalSys_Tax] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of SalSys_Tax
-- ----------------------------
INSERT INTO [dbo].[SalSys_Tax] VALUES (N'5000.00', N'0.15')
GO

INSERT INTO [dbo].[SalSys_Tax] VALUES (N'10000.00', N'0.17')
GO

INSERT INTO [dbo].[SalSys_Tax] VALUES (N'13000.00', N'0.2')
GO


-- ----------------------------
-- Table structure for SalSys_Users
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[SalSys_Users]') AND type IN ('U'))
	DROP TABLE [dbo].[SalSys_Users]
GO

CREATE TABLE [dbo].[SalSys_Users] (
  [WNo] char(6) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [WPassword] varchar(16) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [WRight] varchar(8) COLLATE Chinese_PRC_CI_AS  NOT NULL
)
GO

ALTER TABLE [dbo].[SalSys_Users] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of SalSys_Users
-- ----------------------------
INSERT INTO [dbo].[SalSys_Users] VALUES (N'200000', N'1234', N'普通用户')
GO

INSERT INTO [dbo].[SalSys_Users] VALUES (N'200001', N'1234', N'管理员')
GO

INSERT INTO [dbo].[SalSys_Users] VALUES (N'200002', N'123456789', N'普通用户')
GO

INSERT INTO [dbo].[SalSys_Users] VALUES (N'200003', N'123456789', N'普通用户')
GO

INSERT INTO [dbo].[SalSys_Users] VALUES (N'200004', N'123456789', N'普通用户')
GO

INSERT INTO [dbo].[SalSys_Users] VALUES (N'200005', N'123456789', N'普通用户')
GO

INSERT INTO [dbo].[SalSys_Users] VALUES (N'200006', N'123456789', N'普通用户')
GO

INSERT INTO [dbo].[SalSys_Users] VALUES (N'200007', N'123456789', N'普通用户')
GO

INSERT INTO [dbo].[SalSys_Users] VALUES (N'200008', N'123456789', N'普通用户')
GO

INSERT INTO [dbo].[SalSys_Users] VALUES (N'200009', N'123456789', N'普通用户')
GO

INSERT INTO [dbo].[SalSys_Users] VALUES (N'200010', N'123456789', N'普通用户')
GO


-- ----------------------------
-- Table structure for SalSys_Worker
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[SalSys_Worker]') AND type IN ('U'))
	DROP TABLE [dbo].[SalSys_Worker]
GO

CREATE TABLE [dbo].[SalSys_Worker] (
  [WNo] char(6) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [WName] varchar(6) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [WSex] char(2) COLLATE Chinese_PRC_CI_AS  NULL,
  [WBirth] datetime  NULL,
  [WNative] varchar(12) COLLATE Chinese_PRC_CI_AS  NULL,
  [EEdu] varchar(12) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [WTel] char(11) COLLATE Chinese_PRC_CI_AS  NULL,
  [WEnter] int  NULL,
  [WIdentity] char(18) COLLATE Chinese_PRC_CI_AS  NULL,
  [DNo] char(3) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [PName] varchar(12) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [JName] varchar(12) COLLATE Chinese_PRC_CI_AS  NOT NULL
)
GO

ALTER TABLE [dbo].[SalSys_Worker] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Records of SalSys_Worker
-- ----------------------------
INSERT INTO [dbo].[SalSys_Worker] VALUES (N'200000', N'陈涛', N'男', N'1997-07-08 00:00:00.000', N'山东济南', N'中专', N'13662758884', N'2016', N'370100199707084519', N'004', N'中级技师', N'员工')
GO

INSERT INTO [dbo].[SalSys_Worker] VALUES (N'200001', N'苏大强', N'男', N'1997-08-08 00:00:00.000', N'山东济南', N'本科', N'13744755252', N'2017', N'370100199708082419', N'001', N'中级工程师', N'部门主管')
GO

INSERT INTO [dbo].[SalSys_Worker] VALUES (N'200002', N'倪悦铭', N'男', N'1998-04-08 00:00:00.000', N'广东潮汕', N'本科', N'13744755552', N'2018', N'445100199804082419', N'002', N'中级工程师', N'经理')
GO

INSERT INTO [dbo].[SalSys_Worker] VALUES (N'200003', N'苏二强', N'男', N'1997-08-08 00:00:00.000', N'山东济南', N'本科', N'13744752452', N'2016', N'370100199708085519', N'001', N'中级工程师', N'组长')
GO

INSERT INTO [dbo].[SalSys_Worker] VALUES (N'200004', N'苏三强', N'男', N'1997-08-08 00:00:00.000', N'山东济南', N'大专', N'13744757848', N'2018', N'370100199708088719', N'001', N'初级工程师', N'副经理')
GO

INSERT INTO [dbo].[SalSys_Worker] VALUES (N'200005', N'张丽丽', N'女', N'1997-12-23 00:00:00.000', N'广东东莞', N'硕士', N'13662114585', N'2019', N'441900199712232449', N'003', N'高级工程师', N'部门主管')
GO

INSERT INTO [dbo].[SalSys_Worker] VALUES (N'200006', N'张有财', N'男', N'1995-02-28 00:00:00.000', N'山东济南', N'大专', N'13554888579', N'2015', N'370100199502282419', N'003', N'中级工程师', N'组长')
GO

INSERT INTO [dbo].[SalSys_Worker] VALUES (N'200007', N'黄庄', N'男', N'1997-06-24 00:00:00.000', N'广东东莞', N'本科', N'13742455152', N'2019', N'441900199706241557', N'004', N'高级工程师', N'员工')
GO

INSERT INTO [dbo].[SalSys_Worker] VALUES (N'200008', N'王琦', N'女', N'1997-04-22 00:00:00.000', N'山东济南', N'大专', N'13788485516', N'2019', N'370100199704221587', N'003', N'初级技师', N'员工')
GO

INSERT INTO [dbo].[SalSys_Worker] VALUES (N'200009', N'苏小强', N'男', N'1997-02-02 00:00:00.000', N'山东济南', N'大专', N'13881545252', N'2018', N'370100199802022419', N'002', N'中级技师', N'员工')
GO

INSERT INTO [dbo].[SalSys_Worker] VALUES (N'200010', N'林大斌', N'男', N'1999-07-08 00:00:00.000', N'广东湛江', N'硕士', N'13662758884', N'2016', N'440506874568885452', N'004', N'中级技师', N'部门主管')
GO


-- ----------------------------
-- View structure for V_Department
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[V_Department]') AND type IN ('V'))
	DROP VIEW [dbo].[V_Department]
GO

CREATE VIEW [dbo].[V_Department] AS SELECT D.DNo, D.DName, A.DManager, B.DCount
FROM (SELECT DNo, COUNT(WNo) AS DCount FROM SalSys_Worker GROUP BY DNo) AS B  
LEFT JOIN (SELECT DNo, WName DManager FROM SalSys_Worker WHERE (JName = '部门主管')) AS A 
ON A.DNo = B.DNo 
INNER JOIN SalSys_Department D
ON B.DNo = D.DNo

---考勤工资视图
GO


-- ----------------------------
-- View structure for V_Feedback
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[V_Feedback]') AND type IN ('V'))
	DROP VIEW [dbo].[V_Feedback]
GO

CREATE VIEW [dbo].[V_Feedback] AS SELECT FNo, WNo,CheckDate=RTRIM(CAST(CheckYear AS char))+'-'+RIGHT(RTRIM(CAST((CheckMonth+100) AS char)),2),FMessage,Symbol
FROM SalSys_Feedback
GO


-- ----------------------------
-- View structure for V_SalAnnual
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[V_SalAnnual]') AND type IN ('V'))
	DROP VIEW [dbo].[V_SalAnnual]
GO

CREATE VIEW [dbo].[V_SalAnnual] AS SELECT DISTINCT TOP 100 PERCENT W.WNo,  W.WName, SYear,
BonusSal = ROUND(SUM(ATransport+APresent+AHouse+ADiet), 2), 
TotalSal = ROUND(SUM(S.RealSal), 2), 
AnnualSal = ROUND((SUM(ATransport+APresent+AHouse+ADiet)+SUM(RealSal))/12 , 2)
FROM V_SalTotal S, V_Worker W
WHERE W.WNo = S.WNo
GROUP BY SYear,W.WNo,W.WName
ORDER BY WNo ASC
GO


-- ----------------------------
-- View structure for V_SalBase
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[V_SalBase]') AND type IN ('V'))
	DROP VIEW [dbo].[V_SalBase]
GO

CREATE VIEW [dbo].[V_SalBase] AS SELECT DISTINCT WNo, Wname, JMoney, PMoney, EMoney, 
TMoney = ROUND(WTime*(SELECT SMoney FROM SalSys_SetWage WHERE SItem = '工龄补贴'), 2), 
BaseSal = ROUND(JMoney+PMoney+EMoney+WTime*(SELECT SMoney FROM SalSys_SetWage WHERE SItem = '工龄补贴'), 2)
FROM SalSys_Job, SalSys_Position, SalSys_Education, V_Worker
WHERE V_Worker.PName = SalSys_Position.PName AND 
	  V_Worker.JName = SalSys_Job.JName AND 
	  V_Worker.EEdu = SalSys_Education.EEdu
GO


-- ----------------------------
-- View structure for V_SalCheck
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[V_SalCheck]') AND type IN ('V'))
	DROP VIEW [dbo].[V_SalCheck]
GO

CREATE VIEW [dbo].[V_SalCheck] AS SELECT DISTINCT TOP 100 PERCENT SalSys_CheckStat.WNo, WName,CheckYear as SYear, CheckMonth as SMonth, EvectionDay, AbsentDay, LeaveDay, LateDay, 
CheckSal = -ROUND((EvectionDay*(SELECT SMoney FROM SalSys_SetWage WHERE SItem='出差一天')+
			   AbsentDay*(SELECT SMoney FROM SalSys_SetWage WHERE SItem='旷工一天')+
			    LeaveDay*(SELECT SMoney FROM SalSys_SetWage WHERE SItem='请假一天')+
			    LateDay*(SELECT SMoney FROM SalSys_SetWage WHERE SItem='迟到一次')),2)
FROM SalSys_CheckStat, SalSys_Worker
WHERE SalSys_CheckStat.WNo = SalSys_Worker.WNo
ORDER BY WNo ASC
GO


-- ----------------------------
-- View structure for V_SalDepart
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[V_SalDepart]') AND type IN ('V'))
	DROP VIEW [dbo].[V_SalDepart]
GO

CREATE VIEW [dbo].[V_SalDepart] AS SELECT DISTINCT D.DNo, D.DName, SYear as SYear, SMonth as SMonth, Average = ROUND(AVG(RealSal), 2), Total = ROUND(SUM(S.RealSal), 2)
FROM V_SalTotal S, SalSys_Worker W, SalSys_Department D
WHERE W.WNo = S.WNo AND W.DNo = D.DNo
GROUP BY D.DNo, D.DName, SYear, SMonth

---年终奖视图
GO


-- ----------------------------
-- View structure for V_SalMonth
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[V_SalMonth]') AND type IN ('V'))
	DROP VIEW [dbo].[V_SalMonth]
GO

CREATE VIEW [dbo].[V_SalMonth] AS SELECT DISTINCT  SYear as SYear, SMonth as SMonth, Average = ROUND(AVG(RealSal), 2), Total = ROUND(SUM(RealSal), 2)
FROM V_SalTotal
GROUP BY SYear,SMonth

---部门工资视图
GO


-- ----------------------------
-- View structure for V_SalOveTime
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[V_SalOveTime]') AND type IN ('V'))
	DROP VIEW [dbo].[V_SalOveTime]
GO

CREATE VIEW [dbo].[V_SalOveTime] AS SELECT DISTINCT TOP 100 PERCENT SalSys_CheckStat.WNo, WName, CheckYear as SYear, CheckMonth as SMonth, THoliday, TWeekday, 
OveTimeSal = ROUND(THoliday*(SELECT SMoney FROM SalSys_SetWage WHERE SItem='节假日加班')+
			 TWeekday*(SELECT SMoney FROM SalSys_SetWage WHERE SItem='工作日加班'),2)
FROM SalSys_CheckStat, SalSys_Worker
WHERE SalSys_CheckStat.WNo = SalSys_Worker.WNo
ORDER BY SalSys_CheckStat.WNo ASC
 
---基本工资视图
GO


-- ----------------------------
-- View structure for V_SalTotal
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[V_SalTotal]') AND type IN ('V'))
	DROP VIEW [dbo].[V_SalTotal]
GO

CREATE VIEW [dbo].[V_SalTotal] AS SELECT DISTINCT TOP 100 PERCENT W.WNo, W.WName, C.SYear as SYear, C.SMonth as SMonth, A.ATransport, A.APresent, A.AHouse, A.ADiet, C.CheckSal, B.BaseSal, O.OveTimeSal,  
TotalSal = ROUND(B.BaseSal + C.CheckSal + O.OveTimeSal + A.ADiet + A.AHouse+ A.APresent + A.ATransport, 2), 
RealSal = CASE
WHEN(B.BaseSal + C.CheckSal + O.OveTimeSal + A.ADiet + A.AHouse+ A.APresent + A.ATransport)<5000 THEN ROUND((B.BaseSal + C.CheckSal + O.OveTimeSal + A.ADiet + A.AHouse+ A.APresent + A.ATransport)*(SELECT 1-Rate FROM SalSys_Tax WHERE Std=5000), 2)
WHEN(B.BaseSal + C.CheckSal + O.OveTimeSal + A.ADiet + A.AHouse+ A.APresent + A.ATransport)<10000 THEN ROUND((B.BaseSal + C.CheckSal + O.OveTimeSal + A.ADiet + A.AHouse+ A.APresent + A.ATransport)*(SELECT 1-Rate FROM SalSys_Tax WHERE Std=10000), 2)
WHEN(B.BaseSal + C.CheckSal + O.OveTimeSal + A.ADiet + A.AHouse+ A.APresent + A.ATransport)<13000 THEN ROUND((B.BaseSal + C.CheckSal + O.OveTimeSal + A.ADiet + A.AHouse+ A.APresent + A.ATransport)*(SELECT 1-Rate FROM SalSys_Tax WHERE Std=13000), 2)
END
FROM
V_SalBase B INNER JOIN
SalSys_Worker W ON W.WNo = B.WNo INNER JOIN
   V_SalCheck C ON W.WNo = C.WNo INNER JOIN
 V_SalOveTime O ON W.WNo = O.WNo AND 
C.SYear = O.SYear AND
C.SMonth = O.SMonth CROSS JOIN
SalSys_Allowance A
ORDER BY WNo ASC

---月份工资视图
GO


-- ----------------------------
-- View structure for V_Worker
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[V_Worker]') AND type IN ('V'))
	DROP VIEW [dbo].[V_Worker]
GO

CREATE VIEW [dbo].[V_Worker] AS SELECT WNo, WName, WSex, CONVERT(varchar(10),WBirth,23) WBirth, WNative, EEdu, WTel, WEnter, WIdentity, DName, PName, JName, WTime=YEAR(GETDATE())-WEnter
FROM SalSys_Worker, SalSys_Department
WHERE SalSys_Worker.DNo = SalSys_Department.DNo

---部门视图
GO


-- ----------------------------
-- Checks structure for table SalSys_CheckStat
-- ----------------------------
ALTER TABLE [dbo].[SalSys_CheckStat] ADD CONSTRAINT [SalSys_Check8] CHECK ([CheckYear]>=(1900) AND [CheckYear]<=(2900))
GO

ALTER TABLE [dbo].[SalSys_CheckStat] ADD CONSTRAINT [SalSys_Check1] CHECK ([CheckMonth]>=(1) AND [CheckMonth]<=(12))
GO

ALTER TABLE [dbo].[SalSys_CheckStat] ADD CONSTRAINT [SalSys_Check2] CHECK ([EvectionDay]>=(0) AND [EvectionDay]<=(31))
GO

ALTER TABLE [dbo].[SalSys_CheckStat] ADD CONSTRAINT [SalSys_Check3] CHECK ([AbsentDay]>=(0) AND [AbsentDay]<=(31))
GO

ALTER TABLE [dbo].[SalSys_CheckStat] ADD CONSTRAINT [SalSys_Check4] CHECK ([LeaveDay]>=(0) AND [LeaveDay]<=(31))
GO

ALTER TABLE [dbo].[SalSys_CheckStat] ADD CONSTRAINT [SalSys_Check5] CHECK ([LateDay]>=(0) AND [LateDay]<=(31))
GO

ALTER TABLE [dbo].[SalSys_CheckStat] ADD CONSTRAINT [SalSys_Check6] CHECK ([THoliday]>=(0))
GO

ALTER TABLE [dbo].[SalSys_CheckStat] ADD CONSTRAINT [SalSys_Check7] CHECK ([TWeekday]>=(0) AND [TWeekday]<=(31))
GO


-- ----------------------------
-- Primary Key structure for table SalSys_CheckStat
-- ----------------------------
ALTER TABLE [dbo].[SalSys_CheckStat] ADD CONSTRAINT [CheckStat_Prim] PRIMARY KEY CLUSTERED ([WNo], [CheckYear], [CheckMonth])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table SalSys_Department
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Department] ADD CONSTRAINT [Department_Prim] PRIMARY KEY CLUSTERED ([DNo])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Checks structure for table SalSys_Education
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Education] ADD CONSTRAINT [Education_Check] CHECK ([EMoney]>=(0))
GO


-- ----------------------------
-- Primary Key structure for table SalSys_Education
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Education] ADD CONSTRAINT [Education_Prim] PRIMARY KEY CLUSTERED ([EEdu])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Checks structure for table SalSys_Feedback
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Feedback] ADD CONSTRAINT [Feedback_Check2] CHECK ([CheckYear]>=(1900) AND [CheckYear]<=(2900))
GO

ALTER TABLE [dbo].[SalSys_Feedback] ADD CONSTRAINT [Feedback_Check1] CHECK ([CheckMonth]>=(1) AND [CheckMonth]<=(12))
GO

ALTER TABLE [dbo].[SalSys_Feedback] ADD CONSTRAINT [Feedback_Check3] CHECK ([Symbol]='已处理' OR [Symbol]='未处理')
GO


-- ----------------------------
-- Primary Key structure for table SalSys_Feedback
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Feedback] ADD CONSTRAINT [Feedback_Prim] PRIMARY KEY CLUSTERED ([FNo], [WNo])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Checks structure for table SalSys_Job
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Job] ADD CONSTRAINT [Job_Check] CHECK ([JMoney]>=(0))
GO


-- ----------------------------
-- Primary Key structure for table SalSys_Job
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Job] ADD CONSTRAINT [Job_Prim] PRIMARY KEY CLUSTERED ([JName])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Checks structure for table SalSys_Position
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Position] ADD CONSTRAINT [Position_Check] CHECK ([PMoney]>=(0))
GO


-- ----------------------------
-- Primary Key structure for table SalSys_Position
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Position] ADD CONSTRAINT [Position_Prim] PRIMARY KEY CLUSTERED ([PName])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Checks structure for table SalSys_SetWage
-- ----------------------------
ALTER TABLE [dbo].[SalSys_SetWage] ADD CONSTRAINT [SetWage_Check] CHECK ([SMoney]>=(0))
GO


-- ----------------------------
-- Primary Key structure for table SalSys_SetWage
-- ----------------------------
ALTER TABLE [dbo].[SalSys_SetWage] ADD CONSTRAINT [SetWage_Prim] PRIMARY KEY CLUSTERED ([SItem])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Checks structure for table SalSys_Tax
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Tax] ADD CONSTRAINT [Tax_Check1] CHECK ([Std]>=(0))
GO


-- ----------------------------
-- Primary Key structure for table SalSys_Tax
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Tax] ADD CONSTRAINT [Tax_Prim] PRIMARY KEY CLUSTERED ([Std])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Primary Key structure for table SalSys_Users
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Users] ADD CONSTRAINT [Users_Prim] PRIMARY KEY CLUSTERED ([WNo])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Checks structure for table SalSys_Worker
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Worker] ADD CONSTRAINT [Sex_Che] CHECK ([Wsex]='女' OR [Wsex]='男')
GO

ALTER TABLE [dbo].[SalSys_Worker] ADD CONSTRAINT [CK__SalSys_Wo__WEnte__1FCDBCEB] CHECK ([WEnter]>=(1900) AND [WEnter]<=(2900))
GO


-- ----------------------------
-- Primary Key structure for table SalSys_Worker
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Worker] ADD CONSTRAINT [Worker_Prim] PRIMARY KEY CLUSTERED ([WNo])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Foreign Keys structure for table SalSys_CheckStat
-- ----------------------------
ALTER TABLE [dbo].[SalSys_CheckStat] ADD CONSTRAINT [CheckStat_Fore] FOREIGN KEY ([WNo]) REFERENCES [dbo].[SalSys_Worker] ([WNo]) ON DELETE CASCADE ON UPDATE CASCADE
GO


-- ----------------------------
-- Foreign Keys structure for table SalSys_Feedback
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Feedback] ADD CONSTRAINT [Feedback_Fore] FOREIGN KEY ([WNo]) REFERENCES [dbo].[SalSys_Worker] ([WNo]) ON DELETE CASCADE ON UPDATE CASCADE
GO


-- ----------------------------
-- Foreign Keys structure for table SalSys_Users
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Users] ADD CONSTRAINT [Users_Fore] FOREIGN KEY ([WNo]) REFERENCES [dbo].[SalSys_Worker] ([WNo]) ON DELETE CASCADE ON UPDATE CASCADE
GO


-- ----------------------------
-- Foreign Keys structure for table SalSys_Worker
-- ----------------------------
ALTER TABLE [dbo].[SalSys_Worker] ADD CONSTRAINT [Worker_Fore1] FOREIGN KEY ([EEdu]) REFERENCES [dbo].[SalSys_Education] ([EEdu]) ON DELETE CASCADE ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[SalSys_Worker] ADD CONSTRAINT [Worker_Fore2] FOREIGN KEY ([DNo]) REFERENCES [dbo].[SalSys_Department] ([DNo]) ON DELETE CASCADE ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[SalSys_Worker] ADD CONSTRAINT [Worker_Fore3] FOREIGN KEY ([PName]) REFERENCES [dbo].[SalSys_Position] ([PName]) ON DELETE CASCADE ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[SalSys_Worker] ADD CONSTRAINT [Worker_Fore4] FOREIGN KEY ([JName]) REFERENCES [dbo].[SalSys_Job] ([JName]) ON DELETE CASCADE ON UPDATE CASCADE
GO

