DROP DATABASE IF EXISTS keygen;
CREATE DATABASE keygen;


USE keygen;

/*tables*/
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_email` varchar(128) COLLATE utf8_bin NOT NULL,
  `user_created_date_time` datetime NOT NULL,
  `user_enabled` tinyint(1) NOT NULL DEFAULT '1',
  `user_username` varchar(50) COLLATE utf8_bin NOT NULL,
  `user_password` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_email_user_username_UNIQUE` (`user_email`, `user_username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `statuses` (
  `status_id` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT '1',
  `status_value` varchar(45) COLLATE utf8_bin NOT NULL,
  `status_stage` int(11) NOT NULL,
  PRIMARY KEY (`status_id`),
  UNIQUE KEY `status_value_UNIQUE` (`status_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `user_requests` (
  `user_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `request_date_time` datetime NOT NULL,
  `request_is_active` tinyint(1) NOT NULL,
  `temp_url` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `temp_password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `request_status` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`user_request_id`),
  KEY `user_requests_user_id_fk_idx` (`user_id`),
  KEY `user_requests_request_status_fk_idx` (`temp_url`),
  CONSTRAINT `user_requests_request_status_fk` FOREIGN KEY (`request_status`) REFERENCES `statuses` (`status_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_requests_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `user_keys` (
  `user_request_id` int(11) NOT NULL,
  `generated_key` varchar(100) COLLATE utf8_bin NOT NULL,
  `generated_datetime` datetime NOT NULL,
  CONSTRAINT user_keys_unique UNIQUE (user_request_id, generated_key, generated_datetime)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


/*triggers*/
DELIMITER $$
CREATE DEFINER=`root`@`localhost` TRIGGER `keygen`.`statuses_BEFORE_INSERT` BEFORE INSERT ON `statuses` FOR EACH ROW
BEGIN
	/*IF ISNULL(NEW.status_id) THEN*/
	SET NEW.status_id = UUID(); 
	/*END IF;*/
END$$
DELIMITER ;

/*default data*/
insert into statuses (status_value, status_stage) values('CREATE_REQUEST', 1);
insert into statuses (status_value, status_stage) values('GENERATE_TEMP_URL_AND_PASSWORD_REQUEST', 2);
insert into statuses (status_value, status_stage) values('SEND_EMAIL_TO_USER_REQUEST', 3);
insert into statuses (status_value, status_stage) values('CONFIRM_USER_REQUEST', 4);
