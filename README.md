CREATE TABLE `user` (
  `user_id` INTEGER AUTO_INCREMENT,
  `login` VARCHAR(60) UNIQUE NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `last_name` VARCHAR(60) NOT NULL,
  `first_name` VARCHAR(60) NOT NULL,
  `patronymic_name` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`user_id`)
);

CREATE TABLE `role` (
  `role_id` INTEGER AUTO_INCREMENT,
  `role_name` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`role_id`)
);

CREATE TABLE `user_role` (
  `user_id` INTEGER NOT NULL,
  `role_id` INTEGER NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`)
);

CREATE TABLE `contact` (
  `contact_id` INTEGER AUTO_INCREMENT,
  `last_name` VARCHAR(60) NOT NULL,
  `first_name` VARCHAR(60) NOT NULL,
  `patronymic_name` VARCHAR(60) NOT NULL,
  `mobile_phone` VARCHAR(30) NOT NULL,
  `home_phone` VARCHAR(30),
  `google_place_id` VARCHAR(60),
  `email` VARCHAR(120),
  `user_id` INTEGER NOT NULL,
  PRIMARY KEY (`contact_id`)
);

ALTER TABLE `user_role` 
	ADD CONSTRAINT `fk_user_role__role_id` 
    FOREIGN KEY (`role_id`) 
    REFERENCES `role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION;

ALTER TABLE `user_role` 
	ADD CONSTRAINT `fk_user_role__user_id` 
    FOREIGN KEY (`user_id`) 
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION;

ALTER TABLE `contact` 
	ADD CONSTRAINT `fk_contact__user_id` 
    FOREIGN KEY (`user_id`) 
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION;
    
INSERT INTO `role` (`role_name`) VALUE ('ROLE_CLIENT');