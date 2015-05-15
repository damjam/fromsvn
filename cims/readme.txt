新增parking_info表
CREATE TABLE `parking_bill` (
	`ID` VARCHAR(20) NOT NULL,
	`OWNER_ID` VARCHAR(12) NULL DEFAULT NULL,
	`OWNER_NAME` VARCHAR(20) NULL DEFAULT NULL,
	`CREATE_DATE` DATETIME NULL DEFAULT NULL,
	`CREATE_USER` VARCHAR(50) NULL DEFAULT NULL,
	`AMOUNT` DOUBLE NULL DEFAULT NULL,
	`MOBILE` VARCHAR(20) NULL DEFAULT NULL,
	`BEGIN_DATE` VARCHAR(8) NULL DEFAULT NULL,
	`END_DATE` VARCHAR(8) NULL DEFAULT NULL,
	`PARKING_SN` VARCHAR(8) NULL DEFAULT NULL,
	`CAR_SN` VARCHAR(10) NULL DEFAULT NULL,
	`HOUSE_SN` VARCHAR(10) NULL DEFAULT NULL,
	`IS_INTERNAL` VARCHAR(1) NULL DEFAULT NULL,
	`STATE` VARCHAR(2) NULL DEFAULT NULL,
	`CHARGE_DATE` DATETIME NULL DEFAULT NULL,
	`CHARGE_USER` VARCHAR(20) NULL DEFAULT NULL,
	`REMARK` VARCHAR(50) NULL DEFAULT NULL,
	PRIMARY KEY (`ID`)
)
COMMENT='停车费账单'
COLLATE='gbk_chinese_ci'
ENGINE=InnoDB
;

house_info新增字段order_sn并赋值
新增bill_track表
CREATE TABLE `bill_track` (
	`id` VARCHAR(20) NOT NULL,
	`bill_type` VARCHAR(2) NULL DEFAULT NULL,
	`bill_amount` DOUBLE NULL DEFAULT NULL,
	`house_sn` VARCHAR(10) NULL DEFAULT NULL,
	`bill_id` VARCHAR(20) NULL DEFAULT NULL,
	`branch_no` VARCHAR(20) NULL DEFAULT NULL,
	`expire_date` VARCHAR(10) NULL DEFAULT NULL,
	`left_days` INT(11) NULL DEFAULT NULL,
	`over_days` INT(11) NULL DEFAULT NULL,
	`owner_name` VARCHAR(20) NULL DEFAULT NULL,
	`owner_cel` VARCHAR(20) NULL DEFAULT NULL,
	`notice_times` INT(11) NULL DEFAULT NULL,
	`create_date` DATE NULL DEFAULT NULL,
	`end_user` VARCHAR(20) NULL DEFAULT NULL,
	`state` VARCHAR(2) NULL DEFAULT NULL,
	`end_user_cel` VARCHAR(20) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COMMENT='账单跟踪信息表'
COLLATE='gbk_chinese_ci'
ENGINE=InnoDB
;


INSERT INTO `privilege` (`LIMIT_ID`, `LIMIT_NAME`, `PARENT`, `IS_MENU`, `MENU_ORDER`, `IF_AUDIT`) VALUES ('bill_track', '账单到期提醒', 'charge_mng', 'Y', '9', 'N');

INSERT INTO `privilege_resource` (`LIMIT_ID`, `ID`, `URL`, `PARAM`, `IS_ENTRY`) VALUES ('bill_track', '201503311051', '/billTrack.do', 'list', 'Y');
INSERT INTO `privilege_resource` (`LIMIT_ID`, `ID`, `URL`, `PARAM`, `IS_ENTRY`) VALUES ('bill_track', '201503311052', '/billTrack.do', 'delete', 'N');
INSERT INTO `privilege_resource` (`LIMIT_ID`, `ID`, `URL`, `PARAM`, `IS_ENTRY`) VALUES ('bill_track', '201503311053', '/billTrack.do', 'sendNotice', 'N');
INSERT INTO `privilege_resource` (`LIMIT_ID`, `ID`, `URL`, `PARAM`, `IS_ENTRY`) VALUES ('bill_track', '201503311054', '/billTrack.do', 'toAdd', 'N');
INSERT INTO `privilege_resource` (`LIMIT_ID`, `ID`, `URL`, `PARAM`, `IS_ENTRY`) VALUES ('bill_track', '201503311055', '/billTrack.do', 'doAdd', 'N');
INSERT INTO `privilege_resource` (`LIMIT_ID`, `ID`, `URL`, `PARAM`, `IS_ENTRY`) VALUES ('bill_track', '201503312143', '/billTrack.do', 'discard', 'N');
INSERT INTO `privilege_resource` (`LIMIT_ID`, `ID`, `URL`, `PARAM`, `IS_ENTRY`) VALUES ('bill_track', '201505090001', '/billTrack.do', 'generateAll', 'N');

CREATE TABLE IF NOT EXISTS `bill_track` (
  `id` varchar(20) NOT NULL,
  `bill_type` varchar(2) DEFAULT NULL,
  `house_sn` varchar(10) DEFAULT NULL,
  `bill_id` varchar(20) DEFAULT NULL,
  `branch_no` varchar(20) DEFAULT NULL,
  `expire_date` varchar(10) DEFAULT NULL,
  `left_days` int(11) DEFAULT NULL,
  `over_days` int(11) DEFAULT NULL,
  `owner_name` varchar(20) DEFAULT NULL,
  `owner_cel` varchar(20) DEFAULT NULL,
  `notice_times` int(11) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `end_user` varchar(20) DEFAULT NULL,
  `state` varchar(2) DEFAULT NULL,
  `end_user_cel` varchar(20) DEFAULT NULL,
  `bill_amount` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='账单跟踪信息表';


timer_do id类型改为字符串,para2改为字符串

INSERT INTO `privilege` (`LIMIT_ID`, `LIMIT_NAME`, `PARENT`, `IS_MENU`, `MENU_ORDER`, `IF_AUDIT`) VALUES ('timer_task', '定时任务管理', 'system_mng', 'Y', '8', 'N');

INSERT INTO `privilege_resource` (`LIMIT_ID`, `ID`, `URL`, `PARAM`, `IS_ENTRY`) VALUES ('timer_task', '201503281043', '/timer.do', 'toTimerMng', 'Y');
INSERT INTO `privilege_resource` (`LIMIT_ID`, `ID`, `URL`, `PARAM`, `IS_ENTRY`) VALUES ('timer_task', '201503281044', '/timer.do', 'delete', 'N');
INSERT INTO `privilege_resource` (`LIMIT_ID`, `ID`, `URL`, `PARAM`, `IS_ENTRY`) VALUES ('timer_task', '201503281045', '/timer.do', 'query', 'N');
INSERT INTO `privilege_resource` (`LIMIT_ID`, `ID`, `URL`, `PARAM`, `IS_ENTRY`) VALUES ('timer_task', '201503281048', '/timer.do', 'doEdit', 'N');
INSERT INTO `privilege_resource` (`LIMIT_ID`, `ID`, `URL`, `PARAM`, `IS_ENTRY`) VALUES ('timer_task', '201503281049', '/timer.do', 'toEdit', 'N');
INSERT INTO `privilege_resource` (`LIMIT_ID`, `ID`, `URL`, `PARAM`, `IS_ENTRY`) VALUES ('timer_task', '201503281050', '/timerDo.do', 'list', 'N');

处理owner_info表里的一个房屋信息对应多个业主的数据，并对house_sn建唯一性索引
--delete owner_info t where t.house_sn in (select t1.house_sn from owner_info t1, owner_info t2 where t1.id <t2.id and t1.HOUSE_SN = t2.HOUSE_SN);

