-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: mitienda0221
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `item_grupo`
--

DROP TABLE IF EXISTS `item_grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_grupo` (
  `iditem_grupo` int unsigned NOT NULL AUTO_INCREMENT,
  `id_paciente` int unsigned NOT NULL,
  `idgrupo` int unsigned NOT NULL,
  `num_desc_disp` int unsigned NOT NULL,
  `precio` float unsigned NOT NULL,
  PRIMARY KEY (`iditem_grupo`),
  KEY `fk_item_grupo_idx` (`idgrupo`),
  KEY `fk_itemgrupo_pac_idx` (`id_paciente`),
  CONSTRAINT `fk_item_grupo` FOREIGN KEY (`idgrupo`) REFERENCES `grupo` (`idgrupo`),
  CONSTRAINT `fk_itemgrupo_paciente` FOREIGN KEY (`id_paciente`) REFERENCES `paciente` (`id_paciente`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_grupo`
--

LOCK TABLES `item_grupo` WRITE;
/*!40000 ALTER TABLE `item_grupo` DISABLE KEYS */;
INSERT INTO `item_grupo` VALUES (100,1,12,3,10.5),(101,1,13,3,10.5),(102,61,14,3,20),(103,61,15,3,20),(104,61,16,3,20),(105,61,17,3,20),(106,63,18,3,30),(107,1,19,3,10.5),(108,1,20,3,10.5),(109,1,21,3,10.5),(110,1,22,3,10.5),(111,1,23,3,10.5),(112,1,24,3,10.5),(113,61,24,3,20),(114,63,24,3,30),(115,61,25,3,20),(116,63,25,3,30),(117,1,26,3,10.5),(118,63,26,3,30),(119,61,27,3,20),(120,63,27,3,30),(121,63,28,3,30),(122,1,28,3,10.5),(123,1,29,3,10.5),(124,61,30,3,20),(125,63,31,3,30),(126,1,31,3,10.5),(127,61,32,3,20),(128,1,33,3,10.5),(129,63,34,3,30),(130,61,35,3,20),(131,63,36,3,30),(132,1,37,3,10.5),(133,61,38,3,20),(134,63,39,3,30);
/*!40000 ALTER TABLE `item_grupo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-09 16:31:52
