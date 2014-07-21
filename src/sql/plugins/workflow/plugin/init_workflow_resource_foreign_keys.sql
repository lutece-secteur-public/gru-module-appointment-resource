ALTER TABLE workflow_task_set_appointment_resource_history ADD CONSTRAINT fk_wf_set_app_res_hist_id_hist FOREIGN KEY ( id_history )
				REFERENCES workflow_resource_history ( id_history ) ON DELETE CASCADE ON UPDATE RESTRICT;
