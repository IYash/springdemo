
CREATE TABLE `activity_wish_source` (
                                        `id` bigint(20) unsigned NOT NULL COMMENT '唯一记录id',
                                        `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
                                        `content` varchar(50) NOT NULL DEFAULT '' COMMENT '愿望内容',
                                        `status`  tinyint NOT NULL DEFAULT 0 COMMENT '0:仅自己可见1:公开2:精选',
                                        `likes`  bigint(20) NOT NULL DEFAULT 0 COMMENT '助力数量',
                                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
                                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录最后更新时间',
                                        PRIMARY KEY (`id`),
                                        KEY `idx_user` (`user_id`),
                                        KEY `idx_update`(`update_time`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='愿望表';

CREATE TABLE `activity_wish_tmp` (
                                     `id` bigint(20) unsigned NOT NULL COMMENT '唯一记录id',
                                     `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
                                     `content` varchar(50) NOT NULL DEFAULT '' COMMENT '愿望内容',
                                     `status`  tinyint NOT NULL DEFAULT 0 COMMENT '0:仅自己可见1:公开2:精选',
                                     `likes`  bigint(20) NOT NULL DEFAULT 0 COMMENT '助力数量',
                                     `create_time` datetime  DEFAULT null COMMENT '记录创建时间',
                                     `update_time` datetime  DEFAULT null COMMENT '记录最后更新时间',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='愿望表快照';


CREATE TABLE `activity_wish_association` (
                                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一记录id',
                                             `wish_id` bigint(20) unsigned NOT NULL COMMENT '愿望id',
                                             `like_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '助力用户',
                                             `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
                                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
                                             `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录最后更新时间',
                                             PRIMARY KEY (`id`),
                                             KEY `idx_wish_user` (`wish_id`,`like_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='助力关系';

CREATE TABLE `activity_wish_spark` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一记录id',
                                       `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
                                       `spark_level` int NOT NULL DEFAULT '0' COMMENT '已播放的烟花等级',
                                       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
                                       `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录最后更新时间',
                                       PRIMARY KEY (`id`),
                                       KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='烟花记录表';

CREATE TABLE `activity_wish_user` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一记录id',
                                      `wish_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '愿望id',
                                      `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
                                      PRIMARY KEY (`id`),
                                      KEY `idx_wish` (`wish_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='愿望用户关联表';
