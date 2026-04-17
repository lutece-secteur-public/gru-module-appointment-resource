-- liquibase formatted sql
-- changeset appointment-resource:init_core_appointment-resource.sql
-- preconditions onFail:MARK_RAN onError:WARN

INSERT INTO core_dashboard(dashboard_name, dashboard_column, dashboard_order) VALUES('APPOINTMENT_USER_CALENDAR', 3, 3);
