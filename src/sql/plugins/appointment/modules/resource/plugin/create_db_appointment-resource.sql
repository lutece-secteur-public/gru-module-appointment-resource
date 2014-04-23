DROP TABLE IF EXISTS appointment_resource_form_rt;
CREATE TABLE appointment_resource_form_rt (
	id int NOT NULL default '0',
	id_appointment_form int NOT NULL default '0',
	resource_type_name varchar(255) NOT NULL default '',
	description varchar(255) NOT NULL default '',
	is_app_admin_user smallint NOT NULL default 0,
	PRIMARY KEY (id)
);

CREATE INDEX idx_appointment_resource_form_rt_form ON appointment_resource_form_rt (id_appointment_form);


DROP TABLE IF EXISTS workflow_task_set_app_resource_cf;
CREATE TABLE workflow_task_set_app_resource_cf (
	id_task int NOT NULL default '0',
	id_form_resource_type int NOT NULL default '0',
	PRIMARY KEY (id_task)
);


DROP TABLE IF EXISTS appointment_resource_app_res;
CREATE TABLE appointment_resource_app_res (
	id_appointment int NOT NULL default '0',
	id_app_form_res_type int NOT NULL default '0',
	id_resource varchar(255) NOT NULL default '',
	PRIMARY KEY (id_appointment,id_app_form_res_type)
);


DROP TABLE IF EXISTS workflow_task_set_appointment_resource_history;
CREATE TABLE workflow_task_set_appointment_resource_history (
	id int NOT NULL default '0',
	id_history int NOT NULL default '0',
	id_appointment int NOT NULL default '0',
	id_resource varchar(255) NOT NULL default '',
	id_form_resource_type int NOT NULL default '0',
	PRIMARY KEY (id)
);

CREATE INDEX idx_wf_set_appointment_resource_hist_id_hist ON workflow_task_set_appointment_resource_history (id_history);
