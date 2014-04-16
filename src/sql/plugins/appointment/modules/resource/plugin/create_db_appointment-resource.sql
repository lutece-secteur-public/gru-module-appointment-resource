DROP TABLE IF EXISTS appointment_resource_form_rt;
CREATE TABLE appointment_resource_form_rt (
	id int NOT NULL default '0',
	id_appointment_form int NOT NULL default '0',
	resource_type_name varchar(255) NOT NULL default '',
	description varchar(255) NOT NULL default '',
	PRIMARY KEY (id)
);

CREATE INDEX idx_appointment_resource_form_rt_form ON appointment_resource_form_rt (id_appointment_form);
