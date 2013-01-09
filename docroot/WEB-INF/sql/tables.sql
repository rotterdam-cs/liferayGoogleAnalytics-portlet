create table configuration_Configuration (
	configurationId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	propertyname VARCHAR(255) null,
	propertyvalue VARCHAR(255) null
);