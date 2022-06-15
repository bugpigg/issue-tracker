-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema issue_tracker
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `issue_tracker` ;

-- -----------------------------------------------------
-- Schema issue_tracker
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `issue_tracker` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `issue_tracker` ;

-- -----------------------------------------------------
-- Table `issue_tracker`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issue_tracker`.`member` (
  `member_id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(20) NULL DEFAULT NULL,
  `name` VARCHAR(20) NULL DEFAULT NULL,
  `type` VARCHAR(10) NULL DEFAULT NULL,  
  `img_url` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`member_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `issue_tracker`.`milestone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issue_tracker`.`milestone` (
  `milestone_id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NULL DEFAULT NULL,  
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `completion_date` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`milestone_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `issue_tracker`.`issue`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issue_tracker`.`issue` (  
  `issue_id` BIGINT NOT NULL AUTO_INCREMENT,  
  `title` VARCHAR(50) NULL DEFAULT NULL,
  `content` VARCHAR(255) NULL DEFAULT NULL,
  `is_opened` BIT(1) NOT NULL,  
  `milestone_id` BIGINT NULL DEFAULT NULL,
  `member_id` BIGINT NULL DEFAULT NULL,
  `created_date` DATETIME(6) NULL DEFAULT NULL,
  `modified_date` DATETIME(6) NULL DEFAULT NULL,
  PRIMARY KEY (`issue_id`),
  INDEX `fk_issue_comment_id` (`comment_id` ASC) VISIBLE,
  INDEX `fk_issue_milestone_id` (`milestone_id` ASC) VISIBLE,
  INDEX `fk_issue_member_id` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_issue_milestone`
    FOREIGN KEY (`milestone_id`)
    REFERENCES `issue_tracker`.`milestone` (`milestone_id`),
  CONSTRAINT `fk_issue_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `issue_tracker`.`member` (`member_id`),
  CONSTRAINT `fk_issue_comment`
    FOREIGN KEY (`comment_id`)
    REFERENCES `issue_tracker`.`comment` (`comment_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `issue_tracker`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issue_tracker`.`comment` (
  `comment_id` BIGINT NOT NULL AUTO_INCREMENT,  
  `content` VARCHAR(255) NULL DEFAULT NULL,
  `issue_id` BIGINT NULL DEFAULT NULL,
  `member_id` BIGINT NULL DEFAULT NULL,
  `created_date` DATETIME(6) NULL DEFAULT NULL,
  `modified_date` DATETIME(6) NULL DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  INDEX `fk_comment_issue_id` (`issue_id` ASC) VISIBLE,
  INDEX `fk_comment_member_id` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `issue_tracker`.`member` (`member_id`),
  CONSTRAINT `fk_comment_issue`
    FOREIGN KEY (`issue_id`)
    REFERENCES `issue_tracker`.`issue` (`issue_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `issue_tracker`.`emogi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issue_tracker`.`emogi` (
  `emogi_id` BIGINT NOT NULL AUTO_INCREMENT,
  `unicode_value` VARCHAR(50) NULL DEFAULT NULL,
  `comment_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`emogi_id`),
  INDEX `fk_emogi_comment_id` (`comment_id` ASC) VISIBLE,
  CONSTRAINT `fk_emogi_comment`
    FOREIGN KEY (`comment_id`)
    REFERENCES `issue_tracker`.`comment` (`comment_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `issue_tracker`.`label`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issue_tracker`.`label` (
  `label_id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `background_color` VARCHAR(20) NULL DEFAULT NULL,  
  `id_dark_mode` BIT(1) NOT NULL, 
  PRIMARY KEY (`label_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `issue_tracker`.`issue_label`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issue_tracker`.`issue_label` (
  `issue_label_id` BIGINT NOT NULL AUTO_INCREMENT,
  `issue_id` BIGINT NULL DEFAULT NULL,
  `label_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`issue_label_id`),
  INDEX `fk_issue_label_issue_id` (`issue_id` ASC) VISIBLE,
  INDEX `fk_issue_label_label_id` (`label_id` ASC) VISIBLE,
  CONSTRAINT `fk_issue_label_issue`
    FOREIGN KEY (`issue_id`)
    REFERENCES `issue_tracker`.`issue` (`issue_id`),
  CONSTRAINT `fk_issue_label_label`
    FOREIGN KEY (`label_id`)
    REFERENCES `issue_tracker`.`label` (`label_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `issue_tracker`.`issue_member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `issue_tracker`.`issue_member` (
  `issue_member_id` BIGINT NOT NULL AUTO_INCREMENT,
  `issue_id` BIGINT NULL DEFAULT NULL,
  `member_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`issue_member_id`),
  INDEX `fk_issue_member_issue_id` (`issue_id` ASC) VISIBLE,
  INDEX `fk_issue_member_member_id` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_issue_member_issue`
    FOREIGN KEY (`issue_id`)
    REFERENCES `issue_tracker`.`issue` (`issue_id`),
  CONSTRAINT `fk_issue_member_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `issue_tracker`.`member` (`member_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
