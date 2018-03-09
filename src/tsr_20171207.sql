set names utf8;

DROP TABLE IF EXISTS tsr_feedback;

CREATE TABLE tsr_feedback (
	fid INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
	comid INT COMMENT '公司id',
	custid INT  COMMENT '客服id',
	content VARCHAR(500) COMMENT '内容',
	icons VARCHAR(500) COMMENT  '图片',
	create_time DATETIME COMMENT  '创建时间',
	remark VARCHAR(20) COMMENT '备注'
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='客服意见反馈表';
