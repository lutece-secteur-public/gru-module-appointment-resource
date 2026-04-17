-- liquibase formatted sql
-- changeset appointment-resource:init_appointment_resource_foreign_keys.sql
-- preconditions onFail:MARK_RAN onError:WARN

ALTER TABLE appointment_resource_form_rt ADD CONSTRAINT fk_appointment_res_form_rt_id_form FOREIGN KEY ( id_appointment_form )
				REFERENCES appointment_form ( id_form );
