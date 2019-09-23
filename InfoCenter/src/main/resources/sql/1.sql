create table `info_center_message` (
  `id` bigint(20) primary key  not null auto_increment,
	`user_no` bigint(20) not null comment '用户编号',
	`template_name` char(3) not null comment '模板名称唯一标识(模块编号)',
  `template_type` varchar(20) not null comment '推送类型，短信shortmessage/极光pushmessage/微信wechatmessage',
  `title` varchar(200) not null comment '推送标题',
  `context` text not null comment '内容',
  `created_date` timestamp not null default current_timestamp comment '创建时间',
  `updated_date` timestamp not null default current_timestamp on update current_timestamp comment '最后修改时间',
  `created_by` varchar(50) default null comment '创建人id',
  `updated_by` varchar(50) default null comment '最后修改人id',
  `enable` int(2) not null default '1' comment '是否可用（可用=1，禁用=0）'
)comment='消息表'