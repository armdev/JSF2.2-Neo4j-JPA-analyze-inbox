/*
SQLyog Ultimate v9.50 
MySQL - 5.6.17 : Database - maildb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `inbox` */
create database maildb;

use maildb;

DROP TABLE IF EXISTS `inbox`;

CREATE TABLE `inbox` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email_from` varchar(255) DEFAULT NULL,
  `email_to` varchar(255) DEFAULT NULL,
  `email_cc` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17516 DEFAULT CHARSET=utf8;

/*Data for the table `inbox` */

insert  into `inbox`(`id`,`email_from`,`email_to`,`email_cc`,`title`,`created_date`,`content`) values (1,'user1@gmail.am','user2@gmail.it','user3@gmail.ze','Neo4j and JSF2.2','2013-06-12','fwgfwerger'),(2,'user1@gmail.am','user2@gmail.it','user3@gmail.ze,user4@gmail.ze ','Warby Parker: \"Uh-oh, your prescription is expiring\"','2014-06-12','This may not be news for a lot of people, but I just discovered that you can send text messages via email to most major cellular providers. This tip is stellar in that it is part stupid frugal trick (save money on texting from your cell phone by emailing for free), part productivity (you can probably type faster from a normal QWERTY keyboard than a phone), and part tech geek. You get the three-fer on this one! Here’s how to do it.'),(3,'user1@gmail.am','user2@gmail.it','user3@gmail.ze,user4@gmail.ze ','Best of Groupon: The Deals That Make Us Proud ','2014-09-12','This may not be news for a lot of people, but I just discovered that you can send text messages via email to most major cellular providers. This tip is stellar in that it is part stupid frugal trick (save money on texting from your cell phone by emailing for free), part productivity (you can probably type faster from a normal QWERTY keyboard than a phone), and part tech geek. You get the three-fer on this one! Here’s how to do it.'),(4,'user1@gmail.am','user2@gmail.it','user3@gmail.ze,user4@gmail.ze ','Barack Obama: \"Hey\"','2015-01-15','In the following example, I’m going to add my mom’s cell to my gmail contacts. Let’s say that her phone provider is Verizon and her number is (555)123-4567. A should be able to set up contacts through most email programs (if you can’t, switch to gmail).'),(5,'user1@gmail.am','user2@gmail.it,user7@gmail.am','user5@gmail.ze','Rent the Runway: \"Happy Birthday Lindsay - Surprise Inside!\"','2015-01-21','This is your first success in your fight against your disease and you will beat it for sure!!!\r\n\r\nThanks for the update.\r\n\r\nTake care\r\nUlli\r\n\r\n-----Original Message-----\r\nFrom: Trevor Mercieca \r\nSent: Dienstag, 21. Juni 2011 15:57\r\nTo: Ulrike Berthold\r\nSubject: Update\r\n\r\nHi ulli finally some partial good news. After 2 mri the docs excluded the possibility of a tumor :-) now the attention is focused on blood cloths or tight veins and i just did an MRV scan hopefully everything will be over soon\r\n\r\nTgabks a lot for everything ulli\r\n\r\nRegards\r\nTrevor\r\n\r\nSent via Droid\r\n'),(6,'user1@gmail.am','user2@gmail.it,user6@gmail.ze','user5@gmail.ze','RE: Maximizer CRM Software','2014-06-22','In the following example, I’m going to add my mom’s cell to my gmail contacts. Let’s say that her phone provider is Verizon and her number is (555)123-4567. A should be able to set up contacts through most email programs (if you can’t, switch to gmail).'),(7,'user1@gmail.am','user2@gmail.it,user6@gmail.ze','user6@gmail.ze','DocuSign: \"What are our customers saying?\"','2015-02-24','In the following example, I’m going to add my mom’s cell to my gmail contacts. Let’s say that her phone provider is Verizon and her number is (555)123-4567. A should be able to set up contacts through most email programs (if you can’t, switch to gmail).'),(8,'user1@gmail.am','user2@gmail.it,user7@gmail.am','user7@gmail.am','UncommonGoods: \"As You Wish\"','2014-02-24','In the following example, I’m going to add my mom’s cell to my gmail contacts. Let’s say that her phone provider is Verizon and her number is (555)123-4567. A should be able to set up contacts through most email programs (if you can’t, switch to gmail).'),(9,'user2@gmail.it','user1@gmail.am,user7@gmail.am','user7@gmail.am','Manicube: \"*Don\'t Open This Email*\"','2014-06-27','In the following example, I’m going to add my mom’s cell to my gmail contacts. Let’s say that her phone provider is Verizon and her number is (555)123-4567. A should be able to set up contacts through most email programs (if you can’t, switch to gmail).'),(10,'user2@gmail.it','user1@gmail.am,user7@gmail.am','user7@gmail.am','Zillow: \"What Can You Afford?\"','2014-06-22','Did you know that every AT&T mobile number has a corresponding email address? If you send an email to that address, your email is delivered to the device as a text, picture, or video message. You can also send a text, picture, or video message to any email address from your wireless device.'),(11,'user2@gmail.it','user1@gmail.am,user7@gmail.am','user7@gmail.am','Manicube: \"*Don\'t Open This Email*\"','2014-06-22','Did you know that every AT&T mobile number has a corresponding email address? If you send an email to that address, your email is delivered to the device as a text, picture, or video message. You can also send a text, picture, or video message to any email address from your wireless device.'),(12,'user2@gmail.it','user1@gmail.am,user7@gmail.am','user7@gmail.am','Refinery29: \"The broke girl\'s guide to a luxury vacation\"','2015-01-21','Did you know that every AT&T mobile number has a corresponding email address? If you send an email to that address, your email is delivered to the device as a text, picture, or video message. You can also send a text, picture, or video message to any email address from your wireless device.'),(13,'user2@gmail.it','user1@gmail.am,user7@gmail.am','user7@gmail.am','Manicube: \"*Don\'t Open This Email*\"','2014-06-22','By simply sending an email to 1112223333@txt.att.net for example, you can send text messages to cell phones via email. This simple technology can also be'),(14,'user2@gmail.it','user1@gmail.am,user7@gmail.am','user7@gmail.am','UncommonGoods: \"As You Wish\"','2014-09-12','By simply sending an email to 1112223333@txt.att.net for example, you can send text messages to cell phones via email. This simple technology can also be'),(15,'user2@gmail.it','user1@gmail.am','user7@gmail.am','Zillow: \"What Can You Afford?\"','2015-01-21','By simply sending an email to 1112223333@txt.att.net for example, you can send text messages to cell phones via email. This simple technology can also be'),(16,'user2@gmail.it','user1@gmail.am','user7@gmail.am','Eater Boston: \"Where to Drink Beer Right Now\"','2014-06-22','This site aims to be the most complete and up to date list of email addresses that can be used to send text messages to phones. Hence email ..'),(17,'user2@gmail.it','user1@gmail.am','user7@gmail.am','Refinery29: \"The broke girl\'s guide to a luxury vacation\"','2015-01-21','This site aims to be the most complete and up to date list of email addresses that can be used to send text messages to phones. Hence email ..'),(18,'user2@gmail.it','user1@gmail.am','user7@gmail.am','DocuSign: \"What are our customers saying?\"','2014-09-12','This site aims to be the most complete and up to date list of email addresses that can be used to send text messages to phones. Hence email ..'),(19,'user7@gmail.am','user1@gmail.am','user6@gmail.ze','Hello, How are you????','2015-02-24','Be happy today'),(20,'user1@gmail.am','user9@gmail.it','user5@gmail.ze','UncommonGoods: \"As You Wish\"','2014-06-22','Did you know that every AT&T mobile number has a corresponding email address? If you send an email to that address, your email is delivered to the device as a text, picture, or video message. You can also send a text, picture, or video message to any email address from your wireless device.');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `dateRegistered` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`firstname`,`lastname`,`email`,`password`,`status`,`dateRegistered`) values (2,'Joe','Savona','user7@gmail.am','12345',1,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
