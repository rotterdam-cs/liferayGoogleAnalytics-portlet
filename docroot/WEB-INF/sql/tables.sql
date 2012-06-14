create table GoogleAnalytics_Configuration (
	configurationId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	propertyname VARCHAR(75) null,
	propertyvalue VARCHAR(150) null
);