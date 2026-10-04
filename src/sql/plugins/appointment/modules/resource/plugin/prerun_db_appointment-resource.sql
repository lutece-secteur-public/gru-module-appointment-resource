-- liquibase formatted sql
-- changeset appointment-resource:prerun_db_appointment-resource.sql
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:1 SELECT COUNT(*) FROM core_datastore WHERE entity_key = 'core.plugins.status.appointment-resource.version'
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_schema = database() AND constraint_name = 'fk_appointment_res_form_rt_id_form'
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM appointment_resource_form_rt r LEFT JOIN appointment_form f ON f.id_form = r.id_appointment_form WHERE f.id_form IS NULL
-- comment Adds the form foreign key on a site where the module is installed without it
ALTER TABLE appointment_resource_form_rt ADD CONSTRAINT fk_appointment_res_form_rt_id_form FOREIGN KEY ( id_appointment_form )
				REFERENCES appointment_form ( id_form );

-- changeset appointment-resource:prerun_db_appointment-resource.sql-rev1.sql
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:1 SELECT COUNT(*) FROM core_datastore WHERE entity_key = 'core.plugins.status.appointment-resource.version'
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.table_constraints WHERE constraint_schema = database() AND constraint_name = 'fk_wf_set_app_res_hist_id_hist'
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM workflow_task_set_appointment_resource_history h LEFT JOIN workflow_resource_history r ON r.id_history = h.id_history WHERE r.id_history IS NULL
-- comment Adds the workflow history foreign key on a site where the module is installed without it
ALTER TABLE workflow_task_set_appointment_resource_history ADD CONSTRAINT fk_wf_set_app_res_hist_id_hist FOREIGN KEY ( id_history )
				REFERENCES workflow_resource_history ( id_history ) ON DELETE CASCADE ON UPDATE RESTRICT;
