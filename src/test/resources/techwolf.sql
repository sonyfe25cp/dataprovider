create table jobs(
	id int NOT NULL AUTO_INCREMENT,
	title varchar(200),
	company varchar(200),
	publishTime varchar(100),
	jobSite varchar(45),
	details varchar(500),
	PRIMARY KEY (`id`)
);