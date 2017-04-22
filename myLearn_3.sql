
CREATE DATABASE MyLearn_3;


CREATE TABLE `shop_seckill` (
  `id` bigint(24) unsigned NOT NULL,
  `name` varchar(120) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '商品名称',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `number` int(11) NOT NULL DEFAULT '0' COMMENT '商品库存',
  PRIMARY KEY (`id`),
  KEY `start_time_index` (`start_time`),
  KEY `end_time_index` (`end_time`),
  KEY `create_time_index` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀库存';


CREATE TABLE `shop_success_killed` (
  `user_phone` char(11) NOT NULL DEFAULT '' COMMENT '用户手机号',
  `seckill_id` bigint(20) NOT NULL COMMENT '秒杀商品id',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：-1无效，0成功，1已付款，2已发货',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_phone` (`user_phone`,`seckill_id`),
  KEY `create_time_index` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功记录';