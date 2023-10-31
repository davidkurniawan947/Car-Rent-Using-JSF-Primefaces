/*
SQLyog Ultimate v12.4.1 (64 bit)
MySQL - 10.4.24-MariaDB : Database - rental
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rental` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `rental`;

/*Table structure for table `employees` */

DROP TABLE IF EXISTS `employees`;

CREATE TABLE `employees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `job` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

/*Data for the table `employees` */

insert  into `employees`(`id`,`username`,`password`,`job`) values 
(1,'damar','6e380168c5fb756bbe4c41355a2c03155f9733cda4d3e0badaa3e40d2018c55c','Manager'),
(2,'rikky','b14d71b180c3dd3a16af88a81f3307511b10c172d84813a1433bdec284b87d29','Employee'),
(10,'sasha','297581d6cd198a6e6df740f13288cb13a1e76cebe3f0ebc3fe259977addfd646','Employee'),
(14,'hashadmin','e6e8cf357a02d8daf3f3b61a045616efa8dc96882d11cfd936bbc2bbf8daa855','Employee'),
(15,'hashadmin2','3a9796d2637fc4ecd24589a9f981cb9443e4aa9e718768bb6b1cbd3132b9b8a6','Manager');

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tanggal_mulai` date NOT NULL,
  `tanggal_selesai` date NOT NULL,
  `product_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` varchar(255) NOT NULL,
  `total_harga` double NOT NULL,
  `confimed_by` int(11) DEFAULT NULL,
  `completed_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id_fk` (`product_id`),
  KEY `user_id_fk` (`user_id`),
  KEY `employee_id_confirm_fk` (`confimed_by`),
  KEY `employee_id_complete_fk` (`completed_by`),
  CONSTRAINT `employee_id_complete_fk` FOREIGN KEY (`completed_by`) REFERENCES `employees` (`id`),
  CONSTRAINT `employee_id_confirm_fk` FOREIGN KEY (`confimed_by`) REFERENCES `employees` (`id`),
  CONSTRAINT `product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4;

/*Data for the table `orders` */

insert  into `orders`(`id`,`tanggal_mulai`,`tanggal_selesai`,`product_id`,`user_id`,`status`,`total_harga`,`confimed_by`,`completed_by`) values 
(38,'2023-01-17','2023-01-19',2,3,'COMPLETED',600000,NULL,NULL),
(39,'2023-01-22','2023-01-28',16,19,'COMPLETED',3150000,NULL,2),
(40,'2023-01-15','2023-01-18',5,18,'CANCELLED',500000,NULL,NULL),
(41,'2023-01-16','2023-01-18',4,18,'CANCELLED',675000,NULL,NULL),
(43,'2023-01-22','2023-01-24',5,18,'CANCELLED',375000,NULL,NULL),
(45,'2023-01-15','2023-01-17',28,18,'COMPLETED',540000,NULL,NULL),
(46,'2023-01-18','2023-01-19',16,18,'CANCELLED',900000,NULL,NULL),
(47,'2023-01-23','2023-01-25',4,21,'COMPLETED',675000,NULL,NULL),
(48,'2023-01-12','2023-01-31',5,21,'CANCELLED',9000000,NULL,NULL),
(49,'2023-01-15','2023-01-21',17,21,'CANCELLED',2450000,NULL,NULL),
(50,'2023-01-23','2023-01-25',16,23,'CANCELLED',1350000,NULL,NULL),
(51,'2023-01-13','2023-01-14',5,3,'COMPLETED',150000,NULL,NULL),
(52,'2023-01-15','2023-01-18',16,3,'COMPLETED',150000,10,2),
(53,'2023-01-15','2023-01-21',17,3,'COMPLETED',2450000,10,1),
(54,'2023-01-24','2023-01-26',4,24,'COMPLETED',675000,10,2),
(55,'2023-01-16','2023-01-18',4,18,'COMPLETED',675000,2,2),
(56,'2023-01-17','2023-01-19',17,3,'COMPLETED',1050000,2,2),
(57,'2023-01-22','2023-01-28',17,18,'COMPLETED',2450000,2,2),
(58,'2023-01-29','2023-01-30',17,3,'COMPLETED',700000,2,2),
(59,'2023-01-18','2023-01-21',17,18,'COMPLETED',1400000,2,2),
(60,'2023-01-23','2023-01-25',17,15,'CONFIRMED',1050000,2,NULL),
(61,'2023-01-22','2023-01-26',17,18,'CONFIRMED',1750000,10,NULL),
(62,'2023-01-22','2023-01-26',17,3,'CANCELLED',1750000,NULL,NULL),
(63,'2023-01-26','2023-01-28',17,3,'CANCELLED',1050000,NULL,NULL),
(64,'2023-01-19','2023-01-21',4,3,'CONFIRMED',675000,2,NULL),
(65,'2023-01-22','2023-01-25',4,18,'PENDING',900000,NULL,NULL),
(66,'2023-02-14','2023-02-17',5,3,'COMPLETED',2200000,2,2),
(67,'2023-01-17','2023-01-18',2,26,'COMPLETED',400000,2,2);

/*Table structure for table `products` */

DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `seat` int(11) NOT NULL,
  `transmisi` varchar(255) NOT NULL,
  `plat` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4;

/*Data for the table `products` */

insert  into `products`(`id`,`name`,`price`,`seat`,`transmisi`,`plat`,`image`) values 
(1,'Daihatsu Sigra',150000,6,'Automatic','D 5321 ARC','sigra.jpg'),
(2,'Toyota Calya',200000,6,'Automatic','D 4333 FTC','calya.png'),
(3,'Honda Mobilio',175000,4,'Automatic','D 3213 KJW','mobilio.jpg'),
(4,'Daihatsu Terios',225000,5,'Manual','D 9537 HLI','terios.jpg'),
(5,'Isuzu Elf',550000,12,'Manual','D 1425 KLA','elf.png'),
(16,'Honda City',450000,4,'Automatic','D 4903 ALD','city.jpg'),
(17,'Suzuki Ertiga',350000,5,'Manual','D 3350 EFG','ertiga.png'),
(28,'Toyota Avanza',180000,6,'Automatic','D 7531 BLC','avanza.png'),
(32,'Toyota New Innova Reborn',450000,6,'Automatic','D 6465 ASC','innova.png');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama_lengkap` varchar(255) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `no_hp` varchar(255) NOT NULL,
  `nik` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;

/*Data for the table `users` */

insert  into `users`(`id`,`username`,`password`,`nama_lengkap`,`alamat`,`email`,`no_hp`,`nik`) values 
(1,'abcd','88d4266fd4e6338d13b845fcf289579d209c897823b9217da3e161936f031589','Andi','Jl. A','gfhfj@gmail.com','08778221255','090125871982'),
(3,'rikky','b14d71b180c3dd3a16af88a81f3307511b10c172d84813a1433bdec284b87d29','Rikky','Jl. A','rikky@gmail.com','123456789012','1234567890123456'),
(4,'udin','b1624a48de24aa81052d82df5d65ad7706561f24d7b015540d1c933c495c945d','Udin','Jl. A','udin@yahoo.com','12513624','1263458562'),
(6,'viee','9a6b751d39ea1ffa533cf2149bfb191308e6039f638d0b778e96fb76a0f8abad','Vienna','Jl. A','viee@gmail.com','087124872512','128128712985712'),
(7,'tyouoy','36bbe50ed96841d10443bcb670d6554f0a34b761be67ec9c4a8ad2c0c44ca42c','Test Account','Jl. A','asokfj@gmail.com','647467976','24724583569'),
(15,'test2','60303ae22b998861bce3b28f33eec1be758a213c86c93c076dbe9f558c11c752','Test Account2','Jl. A','test21@gmail.com','2092390872','1234567890123456'),
(18,'sasha','297581d6cd198a6e6df740f13288cb13a1e76cebe3f0ebc3fe259977addfd646','Sashaaaaa','Jl. B','sasha@gmail.com','1353574689','12357579680'),
(19,'test3','fd61a03af4f77d870fc21e05e7e80678095c92d808cfb3b5c279ee04c74aca13','sahsas','Jl. A','saaha','123524678901','1234567890123456'),
(21,'sasha2','dfb4c333623fd2e45779ef12d674a39ce2bd353def2190ba42bfb42460cbeba3','sashaaaaaaaaa','Jl. B','sasha@gmail.com','123456789012','1234567890123456'),
(23,'hasher','9320ea11f6d427aec4949634dc8676136b2fa8cdad289d22659b44541abb8c51','HASHERRS','Jl. B','hasherr@gmail.com','123456789012','1234567890123456'),
(24,'test5','a140c0c1eda2def2b830363ba362aa4d7d255c262960544821f556e16661b6ff','Test5','Jl. B','test@gmail.com','123456789012','1234567890123456'),
(25,'testalamat','92776350295a9edc363d73976f9b8b9e15e71d7a99561292baf35e9e60804313','testalamat','testalamat','testalamat@gmail.com','123456789012','1234567890123456'),
(26,'test7','bd7c911264aae15b66d4291b6850829aa96986b1d3ead34d1fdbfef27056c112','test7','Jl. ABC','test7@gmail.com','123456789012','1234567890123456');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
