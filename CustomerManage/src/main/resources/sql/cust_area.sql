DROP TABLE IF EXISTS `cust_area`;
CREATE TABLE `cust_area`(
    `id`                    INT UNSIGNED    PRIMARY KEY     AUTO_INCREMENT,
--
    `parent_id`             INT UNSIGNED                    COMMENT '父级区域id',
--   
    `area_code`             CHAR(10)        NOT NULL        COMMENT '区域编码',
    `area_name`             VARCHAR(50)     NOT NULL        COMMENT '区域名称',
    `area_long_name`        VARCHAR(50)     NOT NULL        COMMENT '区域全名',
--
    `area_level`            TINYINT         DEFAULT '3'     COMMENT '行政级别，0国家，1省, 2市，3区/县',
    `direct_level`          TINYINT                         COMMENT '直观级别，如直辖市、地级市、县级市同视为市级',
--
    `area_order`            TINYINT                         COMMENT '先后顺序'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区域表';