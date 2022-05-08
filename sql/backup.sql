/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.40 : Database - mydb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mydb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mydb`;

/*Table structure for table `red_detail` */

DROP TABLE IF EXISTS `red_detail`;

CREATE TABLE `red_detail` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `record_id` INT(11) NOT NULL COMMENT '红包记录id',
  `amount` DECIMAL(8,2) DEFAULT NULL COMMENT '金额（单位为分）',
  `is_active` TINYINT(4) DEFAULT '1',
  `create_time` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=243 DEFAULT CHARSET=utf8 COMMENT='红包明细金额';

/*Data for the table `red_detail` */

INSERT  INTO `red_detail`(`id`,`record_id`,`amount`,`is_active`,`create_time`) VALUES (233,28,'697.00',NULL,'2022-05-08 09:02:51'),(234,28,'95.00',NULL,'2022-05-08 09:02:51'),(235,28,'463.00',NULL,'2022-05-08 09:02:51'),(236,28,'871.00',NULL,'2022-05-08 09:02:51'),(237,28,'2565.00',NULL,'2022-05-08 09:02:51'),(238,28,'588.00',NULL,'2022-05-08 09:02:51'),(239,28,'971.00',NULL,'2022-05-08 09:02:51'),(240,28,'418.00',NULL,'2022-05-08 09:02:51'),(241,28,'155.00',NULL,'2022-05-08 09:02:51'),(242,28,'3177.00',NULL,'2022-05-08 09:02:51');

/*Table structure for table `red_record` */

DROP TABLE IF EXISTS `red_record`;

CREATE TABLE `red_record` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL COMMENT '用户id',
  `red_packet` VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '红包全局唯一标识串',
  `total` INT(11) NOT NULL COMMENT '人数',
  `amount` DECIMAL(10,2) DEFAULT NULL COMMENT '总金额（单位为分）',
  `is_active` TINYINT(4) DEFAULT '1',
  `create_time` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='发红包记录';

/*Data for the table `red_record` */

INSERT  INTO `red_record`(`id`,`user_id`,`red_packet`,`total`,`amount`,`is_active`,`create_time`) VALUES (28,10086,'redis:red:packet:10086:113633813696300',10,'10000.00',NULL,'2022-05-08 09:02:51');

/*Table structure for table `red_rob_record` */

DROP TABLE IF EXISTS `red_rob_record`;

CREATE TABLE `red_rob_record` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) DEFAULT NULL COMMENT '用户账号',
  `red_packet` VARCHAR(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '红包标识串',
  `amount` DECIMAL(8,2) DEFAULT NULL COMMENT '红包金额（单位为分）',
  `rob_time` DATETIME DEFAULT NULL COMMENT '时间',
  `is_active` TINYINT(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=185 DEFAULT CHARSET=utf8 COMMENT='抢红包记录';

/*Data for the table `red_rob_record` */

INSERT  INTO `red_rob_record`(`id`,`user_id`,`red_packet`,`amount`,`rob_time`,`is_active`) VALUES (175,10032,'redis:red:packet:10086:113633813696300','697.00','2022-05-08 09:03:09',NULL),(176,10031,'redis:red:packet:10086:113633813696300','871.00','2022-05-08 09:03:09',NULL),(177,10030,'redis:red:packet:10086:113633813696300','2565.00','2022-05-08 09:03:09',NULL),(178,10037,'redis:red:packet:10086:113633813696300','418.00','2022-05-08 09:03:09',NULL),(179,10033,'redis:red:packet:10086:113633813696300','971.00','2022-05-08 09:03:09',NULL),(180,10035,'redis:red:packet:10086:113633813696300','95.00','2022-05-08 09:03:09',NULL),(181,10034,'redis:red:packet:10086:113633813696300','463.00','2022-05-08 09:03:09',NULL),(182,10036,'redis:red:packet:10086:113633813696300','588.00','2022-05-08 09:03:09',NULL),(183,10038,'redis:red:packet:10086:113633813696300','155.00','2022-05-08 09:03:09',NULL),(184,10052,'redis:red:packet:10086:113633813696300','3177.00','2022-05-08 09:03:09',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
