DROP DATABASE spike;

CREATE DATABASE IF NOT EXISTS spike;

use spike;

CREATE TABLE IF NOT EXISTS spike_item(
  `spike_id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(120) NOT NULL,
  `quantity` int NOT NULL COMMENT 'Quantity in stock',
  `start_time` TIMESTAMP  NOT NULL COMMENT 'Spike start time',
  `end_time`   TIMESTAMP   NOT NULL COMMENT 'Spike end time',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (spike_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)
)ENGINE=INNODB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

INSERT into spike_item(name,quantity,start_time,end_time)
VALUES
  ('$100 to get iphone8',100,'2016-01-01 00:00:00','2016-01-02 00:00:00'),
  ('$150 to get ipad',200,'2016-01-01 00:00:00','2016-01-02 00:00:00'),
  ('$1000 to get mac book pro',300,'2016-01-01 00:00:00','2016-01-02 00:00:00'),
  ('$1200 to get iMac',400,'2016-01-01 00:00:00','2016-01-02 00:00:00');

CREATE TABLE IF NOT EXISTS successful_spike(
  `spike_id` BIGINT NOT NULL,
  `user_phone` BIGINT NOT NULL,
  `status` TINYINT NOT NULL DEFAULT -1 COMMENT 'status:-1:invalid 0:success 1:paid 2:shipped',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(spike_id,user_phone),
  KEY idx_create_time(create_time)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

