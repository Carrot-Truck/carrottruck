drop database if exists carrottruck;
create database carrottruck;
use carrottruck;


-- 회원 (members) 관련 TABLES
CREATE TABLE IF NOT EXISTS `members`
(
    `member_id`     BIGINT AUTO_INCREMENT PRIMARY KEY,
    `email`         VARCHAR(100) NOT NULL,
    `nickname`      VARCHAR(100) NOT NULL,
    `encrypted_pwd` VARCHAR(255),
    `name`          VARCHAR(20)  NOT NULL,
    `phone_number`  VARCHAR(13)  NOT NULL,
    `role`          VARCHAR(10)  NOT NULL,
    `created_date`  TIMESTAMP    NOT NULL DEFAULT now(),
    `modified_date` TIMESTAMP    NOT NULL DEFAULT now(),
    `active`        TINYINT      NOT NULL DEFAULT 1
    );

CREATE TABLE IF NOT EXISTS `vendor_info`
(
    `vendor_info_id`  BIGINT AUTO_INCREMENT PRIMARY KEY,
    `vendor_id`       BIGINT,
    `trade_name`      VARCHAR(100) NOT NULL,
    `vendor_name`     VARCHAR(20)  NOT NULL,
    `business_number` VARCHAR(255) NOT NULL,
    `phone_number`    VARCHAR(13)  NOT NULL,
    `created_date`    TIMESTAMP    NOT NULL DEFAULT now(),
    `modified_date`   TIMESTAMP    NOT NULL DEFAULT now(),
    `active`          TINYINT      NOT NULL DEFAULT 1,
    FOREIGN KEY (`vendor_id`) REFERENCES `members` (`member_id`)
    );

CREATE TABLE IF NOT EXISTS `member_address`
(
    `member_address_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `member_id`         BIGINT,
    `address`           VARCHAR(255) NOT NULL,
    `selected`          TINYINT      NOT NULL,
    `created_date`      TIMESTAMP    NOT NULL DEFAULT now(),
    `modified_date`     TIMESTAMP    NOT NULL DEFAULT now(),
    `active`            TINYINT      NOT NULL DEFAULT 1,
    FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`)
    );

CREATE TABLE IF NOT EXISTS `member_device_token`
(
    `member_device_token_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `member_id`              BIGINT,
    `device_token`           VARCHAR(255) NOT NULL,
    `created_date`           TIMESTAMP    NOT NULL DEFAULT now(),
    `modified_date`          TIMESTAMP    NOT NULL DEFAULT now(),
    `active`                 TINYINT      NOT NULL DEFAULT 1,
    FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`)
    );

-- 푸드트럭 (food_truck) 관련 TABLES
CREATE TABLE IF NOT EXISTS `category`
(
    `category_id`   BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name`          VARCHAR(30) NOT NULL,
    `created_date`  TIMESTAMP   NOT NULL DEFAULT now(),
    `modified_date` TIMESTAMP   NOT NULL DEFAULT now(),
    `active`        TINYINT     NOT NULL DEFAULT 1
    );

CREATE TABLE IF NOT EXISTS `category_code`
(
    `category_code_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `category_id`      BIGINT,
    `code`             VARCHAR(6) NOT NULL,
    `created_date`     TIMESTAMP  NOT NULL DEFAULT now(),
    `modified_date`    TIMESTAMP  NOT NULL DEFAULT now(),
    `active`           TINYINT    NOT NULL DEFAULT 1,
    FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
    );

CREATE TABLE IF NOT EXISTS `food_truck`
(
    `food_truck_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `vendor_id`     BIGINT,
    `category_id`   BIGINT,
    `name`          VARCHAR(100) NOT NULL,
    `phone_number`  VARCHAR(13)  NOT NULL,
    `content`       TEXT,
    `origin_info`   TEXT,
    `prepare_time`  INT          NOT NULL,
    `wait_limits`   INT          NOT NULL,
    `selected`      TINYINT      NOT NULL,
    `created_date`  TIMESTAMP    NOT NULL DEFAULT now(),
    `modified_date` TIMESTAMP    NOT NULL DEFAULT now(),
    `active`        TINYINT      NOT NULL DEFAULT 1,
    FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
    FOREIGN KEY (`vendor_id`) REFERENCES `members` (`member_id`)
    );

CREATE TABLE IF NOT EXISTS `food_truck_schedule`
(
    `food_truck_schedule_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `food_truck_id`          BIGINT,
    `address`                VARCHAR(255)    NOT NULL,
    `latitude`               DECIMAL(15, 13) NOT NULL,
    `longitude`              DECIMAL(15, 12) NOT NULL,
    `day_of_week`            VARCHAR(30)     NOT NULL,
    `start_time`             TIME            NOT NULL,
    `end_time`               TIME            NOT NULL,
    `created_date`           TIMESTAMP       NOT NULL DEFAULT now(),
    `modified_date`          TIMESTAMP       NOT NULL DEFAULT now(),
    `active`                 TINYINT         NOT NULL DEFAULT 1,
    FOREIGN KEY (`food_truck_id`) REFERENCES `food_truck` (`food_truck_id`)
    );

CREATE TABLE IF NOT EXISTS `food_truck_image`
(
    `food_truck_image_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `food_truck_id`       BIGINT,
    `upload_file_name`    VARCHAR(255) NOT NULL,
    `store_file_name`     VARCHAR(255) NOT NULL,
    `created_date`        TIMESTAMP    NOT NULL DEFAULT now(),
    `modified_date`       TIMESTAMP    NOT NULL DEFAULT now(),
    `active`              TINYINT      NOT NULL DEFAULT 1,
    FOREIGN KEY (`food_truck_id`) REFERENCES `food_truck` (`food_truck_id`)
    );

CREATE TABLE IF Not Exists `food_truck_like`
(
    `food_truck_like_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `member_id`          BIGINT,
    `food_truck_id`      BIGINT,
    `created_date`       TIMESTAMP NOT NULL DEFAULT now(),
    `modified_date`      TIMESTAMP NOT NULL DEFAULT now(),
    `active`             TINYINT   NOT NULL DEFAULT 1,
    FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`),
    FOREIGN KEY (`food_truck_id`) REFERENCES `food_truck` (`food_truck_id`)
    );

-- 메뉴 (menu) 관련 TABLES
CREATE TABLE IF NOT EXISTS `menu`
(
    `menu_id`       BIGINT AUTO_INCREMENT PRIMARY KEY,
    `food_truck_id` BIGINT,
    `name`          VARCHAR(100) NOT NULL,
    `price`         INT          NOT NULL,
    `description`   TEXT,
    `sold_out`      TINYINT      NOT NULL DEFAULT 0,
    `created_date`  TIMESTAMP    NOT NULL DEFAULT now(),
    `modified_date` TIMESTAMP    NOT NULL DEFAULT now(),
    `active`        TINYINT      NOT NULL DEFAULT 1,
    FOREIGN KEY (`food_truck_id`) REFERENCES `food_truck` (`food_truck_id`)
    );

CREATE TABLE IF NOT EXISTS `menu_option`
(
    `menu_option_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `menu_id`        BIGINT,
    `name`           VARCHAR(100) NOT NULL,
    `price`          INT          NOT NULL,
    `description`    TEXT,
    `sold_out`       TINYINT      NOT NULL DEFAULT 0,
    `created_date`   TIMESTAMP    NOT NULL DEFAULT now(),
    `modified_date`  TIMESTAMP    NOT NULL DEFAULT now(),
    `active`         TINYINT      NOT NULL DEFAULT 1,
    FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`)
    );

CREATE TABLE IF NOT EXISTS `menu_image`
(
    `menu_image_id`    BIGINT AUTO_INCREMENT PRIMARY KEY,
    `menu_id`          BIGINT,
    `upload_file_name` VARCHAR(255) NOT NULL,
    `store_file_name`  VARCHAR(255) NOT NULL,
    `created_date`     TIMESTAMP    NOT NULL DEFAULT now(),
    `modified_date`    TIMESTAMP    NOT NULL DEFAULT now(),
    `active`           TINYINT      NOT NULL DEFAULT 1,
    FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`)
    );

CREATE TABLE IF NOT EXISTS `sales`
(
    `sales_id`      bigint AUTO_INCREMENT PRIMARY KEY,
    `food_truck_id` bigint,
    `address`       VARCHAR(255)    NOT NULL,
    `latitude`      decimal(15, 13) NOT NULL,
    `longitude`     decimal(15, 12) NOT NULL,
    `order_number`  int             NOT NULL DEFAULT 0,
    `total_amount`  int             NOT NULL,
    `start_time`    timestamp       NOT NULL,
    `end_time`      timestamp       NULL,
    `orderable`     tinyint         NOT NULL DEFAULT 0,
    `created_date`  timestamp       NOT NULL DEFAULT NOW(),
    `modified_date` timestamp       NOT NULL DEFAULT NOW(),
    `active`        tinyint         NOT NULL DEFAULT 1,
    FOREIGN KEY (`food_truck_id`) REFERENCES `food_truck` (`food_truck_id`)
    );

CREATE TABLE IF NOT EXISTS `orders`
(
    `orders_id`     bigint AUTO_INCREMENT PRIMARY KEY,
    `member_id`     bigint,
    `sales_id`      bigint,
    `status`        varchar(10) NOT NULL,
    `expect_time`   timestamp   NULL,
    `total_price`   int         NOT NULL,
    `created_date`  timestamp   NOT NULL DEFAULT NOW(),
    `modified_date` timestamp   NOT NULL DEFAULT NOW(),
    `active`        tinyint     NOT NULL DEFAULT 1,
    FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`),
    FOREIGN KEY (`sales_id`) REFERENCES `sales` (`sales_id`)
    );

CREATE TABLE IF NOT EXISTS `orders_menu`
(
    `orders_menu_id` bigint AUTO_INCREMENT PRIMARY KEY,
    `orders_id`      bigint,
    `menu_id`        bigint,
    `quantity`       int       NOT NULL,
    `created_date`   timestamp NOT NULL DEFAULT NOW(),
    `modified_date`  timestamp NOT NULL DEFAULT NOW(),
    `active`         tinyint   NOT NULL DEFAULT 1,
    FOREIGN KEY (`orders_id`) REFERENCES `orders` (`orders_id`),
    FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`)
    );

CREATE TABLE IF NOT EXISTS `orders_menu_option`
(
    `orders_menu_option_id` bigint AUTO_INCREMENT PRIMARY KEY,
    `orders_menu_id`        bigint,
    `menu_option_id`        bigint,
    `quantity`              int       NOT NULL,
    `created_date`          timestamp NOT NULL DEFAULT NOW(),
    `modified_date`         timestamp NOT NULL DEFAULT NOW(),
    `active`                tinyint   NOT NULL DEFAULT 1,
    FOREIGN KEY (`orders_menu_id`) REFERENCES `orders_menu` (`orders_menu_id`),
    FOREIGN KEY (`menu_option_id`) REFERENCES `menu_option` (`menu_option_id`)
    );

CREATE TABLE IF NOT EXISTS `review`
(
    `review_id`     bigint    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `member_id`     bigint,
    `food_truck_id` bigint,
    `orders_id`     bigint,
    `content`       text      NOT NULL,
    `grade`         int       NOT NULL,
    `created_date`  timestamp NOT NULL DEFAULT NOW(),
    `modified_date` timestamp NOT NULL DEFAULT NOW(),
    `active`        tinyint   NOT NULL DEFAULT 1,
    FOREIGN KEY (`member_id`) REFERENCES members (`member_id`),
    FOREIGN KEY (`food_truck_id`) REFERENCES food_truck (`food_truck_id`),
    FOREIGN KEY (`orders_id`) REFERENCES orders (`orders_id`)
    );

CREATE TABLE IF NOT EXISTS `comment`
(
    `comment_id`    bigint    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `review_id`     bigint,
    `vendor_id`     bigint,
    `content`       text      NOT NULL,
    `created_date`  timestamp NOT NULL DEFAULT NOW(),
    `modified_date` timestamp NOT NULL DEFAULT NOW(),
    `active`        tinyint   NOT NULL DEFAULT 1,
    FOREIGN KEY (`review_id`) REFERENCES review (`review_id`),
    FOREIGN KEY (`vendor_id`) REFERENCES members (`member_id`)
    );

CREATE TABLE IF NOT EXISTS `review_image`
(
    `review_image_id`  bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `review_id`        bigint,
    `upload_file_name` varchar(255) NOT NULL,
    `store_file_name`  varchar(255) NOT NULL,
    `created_date`     timestamp    NOT NULL DEFAULT NOW(),
    `modified_date`    timestamp    NOT NULL DEFAULT NOW(),
    `active`           tinyint      NOT NULL DEFAULT 1,
    FOREIGN KEY (`review_id`) REFERENCES review (`review_id`)
    );

CREATE TABLE IF NOT EXISTS `review_report`
(
    `review_report_id` bigint      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `member_id`        bigint,
    `review_id`        bigint,
    `reason`           text        NOT NULL,
    `status`           varchar(10) NOT NULL COMMENT 'enum: 대기중, 처리중, 처리완료',
    `created_date`     timestamp   NOT NULL DEFAULT NOW(),
    `modified_date`    timestamp   NOT NULL DEFAULT NOW(),
    `active`           tinyint     NOT NULL DEFAULT 1,
    FOREIGN KEY (`member_id`) REFERENCES members (`member_id`),
    FOREIGN KEY (`review_id`) REFERENCES review (`review_id`)
    );

CREATE TABLE IF NOT EXISTS `survey`
(
    `survey_id`     bigint AUTO_INCREMENT PRIMARY KEY,
    `category_id`   bigint,
    `member_id`     bigint,
    `sido`          varchar(20)  NOT NULL,
    `sigungu`       varchar(20)  NOT NULL,
    `dong`          varchar(20)  NOT NULL,
    `content`       varchar(200) NULL,
    `created_date`  timestamp    NOT NULL default now(),
    `modified_date` timestamp    NOT NULL default now(),
    `active`        tinyint      NOT NULL default 1,
    FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
    FOREIGN KEY (`member_id`) REFERENCES `members` (`member_id`)
    );

CREATE TABLE IF NOT EXISTS `adong_code`
(
    `adong_code_id` bigint primary key auto_increment,
    `adong_code`    varchar(8)  NOT NULL UNIQUE,
    `sido`          varchar(10) NOT NULL,
    `sigungu`       varchar(10) NULL,
    `dong`          varchar(20) NULL,
    `created_date`  timestamp   NOT NULL
    );

CREATE TABLE IF NOT EXISTS `sido` (
	`sido_id` bigint primary key auto_increment,
	`name` varchar(10) NOT NULL,
    UNIQUE KEY `unique_sido` (`name`)
	);

CREATE TABLE IF NOT EXISTS `sigungu` (
	`sigungu_id` bigint primary key auto_increment,
	`sido_id` bigint,
	`name` varchar(10) NOT NULL,
	FOREIGN KEY (`sido_id`) REFERENCES `sido` (`sido_id`),
    UNIQUE KEY `unique_sigungu` (`sido_id`, `name`)
	);

CREATE TABLE IF NOT EXISTS `dong` (
	`dong_id` bigint primary key auto_increment,
	`sigungu_id` bigint,
	`name` varchar(10) NOT NULL,
	FOREIGN KEY (`sigungu_id`) REFERENCES `sigungu` (`sigungu_id`),
    UNIQUE KEY `unique_dong` (`sigungu_id`, `name`)
	);