ALTER TABLE appointment_resource_form_rt ADD CONSTRAINT fk_appointment_res_form_rt_id_form FOREIGN KEY ( id_appointment_form )
				REFERENCES appointment_form ( id_form );
