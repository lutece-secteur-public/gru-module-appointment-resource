INSERT INTO resource_resource_type VALUES ('salle','Salle');

INSERT INTO resource_resource VALUES (1,'salle','Salle 1');
INSERT INTO resource_resource VALUES (2,'salle','Salle 2');

INSERT INTO appointment_resource_form_rt VALUES (1,1,'salle','Lieu du rendez-vous',0,1);
INSERT INTO appointment_resource_form_rt VALUES (3,1,'ADMIN_USER','Administrateur en charge du dossier',1,0);


INSERT INTO workflow_workflow VALUES (105,'Workflow de gestion des rendez-vous avec ressources','Workflow de gestion des rendez-vous avec ressources','2014-02-11 07:36:34',1,'all');

INSERT INTO workflow_state VALUES (109,'Non validé','Non validé',105,1,0,0,1);
INSERT INTO workflow_state VALUES (110,'Validé','Validé',105,0,0,0,2);
INSERT INTO workflow_state VALUES (111,'Annulé','Annulé',105,0,0,0,3);


INSERT INTO workflow_action VALUES (118,'Valider','Valider',105,109,110,1,0,0,1,0);
INSERT INTO workflow_action VALUES (119,'Annuler par un administrateur','Annuler par un administrateur',105,110,111,2,0,0,2,0);
INSERT INTO workflow_action VALUES (120,'Annuler par l\'utilisateur','Annuler par l\'utilisateur',105,109,111,2,0,0,3,0);
INSERT INTO workflow_action VALUES (121,'Annuler par un administrateur','Annuler par un administrateur',105,109,111,2,0,0,4,0);
INSERT INTO workflow_action VALUES (122,'Annuler par l\'utilisateur','Annuler par l\'utilisateur',105,110,111,2,0,0,5,0);
INSERT INTO workflow_action VALUES (123,'Associer à un administrateur','Associer à un administrateur',105,110,110,3,0,0,6,0);

INSERT INTO workflow_task VALUES (147,'taskChangeAppointmentStatus',118,3);
INSERT INTO workflow_task VALUES (148,'taskNotifyAppointment',118,4);
INSERT INTO workflow_task VALUES (149,'taskNotifyAdminAppointment',118,5);
INSERT INTO workflow_task VALUES (150,'taskUpdateAppointmentCancelAction',118,6);
INSERT INTO workflow_task VALUES (151,'taskTypeComment',119,1);
INSERT INTO workflow_task VALUES (152,'taskChangeAppointmentStatus',119,2);
INSERT INTO workflow_task VALUES (153,'taskNotifyAdminAppointment',119,3);
INSERT INTO workflow_task VALUES (154,'taskManualAppointmentNotification',119,4);
INSERT INTO workflow_task VALUES (156,'taskChangeAppointmentStatus',120,1);
INSERT INTO workflow_task VALUES (157,'taskNotifyAppointment',120,2);
INSERT INTO workflow_task VALUES (158,'taskTypeComment',121,1);
INSERT INTO workflow_task VALUES (159,'taskChangeAppointmentStatus',121,2);
INSERT INTO workflow_task VALUES (160,'taskNotifyAppointment',121,3);
INSERT INTO workflow_task VALUES (161,'taskNotifyAdminAppointment',121,4);
INSERT INTO workflow_task VALUES (162,'taskChangeAppointmentStatus',122,1);
INSERT INTO workflow_task VALUES (163,'taskNotifyAdminAppointment',122,2);
INSERT INTO workflow_task VALUES (164,'taskNotifyAppointment',122,3);
INSERT INTO workflow_task VALUES (165,'taskNotifyAdminAppointment',123,2);
INSERT INTO workflow_task VALUES (167,'taskNotifyAdminAppointment',123,3);
INSERT INTO workflow_task VALUES (168,'taskSetAppointmentResource',123,1);
INSERT INTO workflow_task VALUES (169,'taskSetAppointmentResource',118,1);
INSERT INTO workflow_task VALUES (170,'taskSetAppointmentResource',118,2);

INSERT INTO workflow_task_change_appointment_status_cf VALUES (147,10);
INSERT INTO workflow_task_change_appointment_status_cf VALUES (152,-10);
INSERT INTO workflow_task_change_appointment_status_cf VALUES (156,-10);
INSERT INTO workflow_task_change_appointment_status_cf VALUES (159,-10);
INSERT INTO workflow_task_change_appointment_status_cf VALUES (162,-10);

INSERT INTO workflow_task_comment_config VALUES (151,'Veuillez saisir le motif de l\'annulation',1);
INSERT INTO workflow_task_comment_config VALUES (158,'Veuillez saisir le motif de l\'annulation',1);

INSERT INTO workflow_task_notify_admin_appointment_cf VALUES (149,0,'L\'équipe Lutèce','no-reply@mydomain.com','Une demande de rendez-vous vous a été attribuée pour le ${date_appointment} à ${time_appointment}','<p>Bonjour,</p>\r\n<p>&nbsp;</p>\r\n<p>Le rendez-vous du ${date_appointment} &agrave; ${time_appointment} vous a &eacute;t&eacute; attribu&eacute;. Merci de vous rendez disponible pour cet horraire, ou d\'annuler le rendez-vous en cas d\'impossibilit&eacute;.</p>\r\n<p>&nbsp;</p>\r\n<p>Cordialement,</p>\r\n<p>&nbsp;</p>\r\n<p>L\'&eacute;quipe Lut&egrave;ce</p>','','',102,0,1,1,'');
INSERT INTO workflow_task_notify_admin_appointment_cf VALUES (153,0,'L\'équipe Lutèce','no-reply@mydomain.com','Annulation du rendez-vous du ${date_appointment}','<p>Bonjour,</p>\r\n<p>&nbsp;</p>\r\n<p>je rendez-vous du ${date_appointment} &agrave; ${time_appointment} a &eacute;t&eacute; annul&eacute;.</p>\r\n<p>&nbsp;</p>\r\n<p>Cordialement,</p>\r\n<p>&nbsp;</p>\r\n<p>L\'&eacute;quipe Lut&egrave;ce.</p>','','',0,0,1,0,'');
INSERT INTO workflow_task_notify_admin_appointment_cf VALUES (161,0,'L\'équipe Lutèce','no-reply@mydomain.com','Annulation de votre rendez-vous du ${date_appointment}','<p>Bonjour,</p>\r\n<p>&nbsp;</p>\r\n<p>le rendez-vous du&nbsp;${date_appointment} &agrave; ${time_appointment} a &eacute;t&eacute; annul&eacute; par le gestionnaire.</p>\r\n<p>&nbsp;</p>\r\n<p>Cordialement,</p>\r\n<p>&nbsp;</p>\r\n<p>L\'&eacute;quipe Lut&egrave;ce.</p>','','',0,0,1,0,'');
INSERT INTO workflow_task_notify_admin_appointment_cf VALUES (163,1,'L\'équipe Lutèce','no-reply@mydomain.com','Annulation d\'un rendez-vous par l\'utilisateur','<p>Bonjour,</p>\r\n<p>&nbsp;</p>\r\n<p>${firstName} ${lastName} a annul&eacute; le rendez-vous du ${date_appointment} &agrave; ${time_appointment}.</p>\r\n<p>&nbsp;</p>\r\n<p>Cordialement,</p>\r\n<p>&nbsp;</p>\r\n<p>l\'&eacute;quipe Lut&egrave;ce.</p>','','',0,0,1,0,'');
INSERT INTO workflow_task_notify_admin_appointment_cf VALUES (165,0,'L\'équipe Lutèce','no-reply@mydomain.com','Le rendez-vous du ${date_appointment} à ${time_appointment} a été réattribué','<p>Bonjour,</p>\r\n<p>&nbsp;</p>\r\n<p>le rendez-vous du ${date_appointment} &agrave; ${time_appointment} a &eacute;t&eacute; r&eacute;attribu&eacute; &agrave; un autre agent.</p>\r\n<p>&nbsp;</p>\r\n<p>Cordialement,</p>\r\n<p>&nbsp;</p>\r\n<p>L\'&eacute;quipe Lut&egrave;ce</p>','','',0,0,1,0,'');
INSERT INTO workflow_task_notify_admin_appointment_cf VALUES (167,0,'L\'équipe Lutèce','no-reply@mydomain.com','Le rendez-vous du ${date_appointment} à ${time_appointment} vous a été attribué','<p>Bonjour,</p>\r\n<p>&nbsp;</p>\r\n<p>le rendez-vous du ${date_appointment} &agrave; ${time_appointment} vous a &eacute;t&eacute; attribu&eacute;. Merci de vous rendre disponible pour cet horraire.</p>\r\n<p>&nbsp;</p>\r\n<p>Cordialement,</p>\r\n<p>&nbsp;</p>\r\n<p>l\'&eacute;quipe Lut&egrave;ce.</p>','','',102,0,1,1,'');

INSERT INTO workflow_task_notify_appointment_cf VALUES (148,'L\'équipe Lutèce','no-reply@mydomain.com','Votre rendez-vous du ${date_appointment} a été validé','<p>Bonjour ${firstName} ${lastName},</p>\r\n<p>&nbsp;</p>\r\n<p>votre demande de rendez-vous du ${date_appointment} &agrave; ${time_appointment} avec la reference <strong>${reference}</strong> a &eacute;t&eacute; valid&eacute;.</p>\r\n<p>En cas d\'indisponibilit&eacute;, vous pouvez annuler le rendez-vous en suivante le lien suivant :</p>\r\n<p><a title=\"Annuler le rendez-vous\" href=\"${url_cancel}\">Annuler le rendez-vous</a></p>\r\n<p>&nbsp;</p>\r\n<p>Cordialement,</p>\r\n<p>l\'&eacute;quipe Lut&egrave;ce.</p>','','',106,1,1,'');
INSERT INTO workflow_task_notify_appointment_cf VALUES (157,'L\'équipe Lutèce','no-reply@mydomain.com','Annulation de votre rendez-vous du ${date_appointment}','<p>Bonjour,</p>\r\n<p>&nbsp;</p>\r\n<p>A votre demande, votre rendez-vous a bien &eacute;t&eacute; annul&eacute;.</p>\r\n<p>&nbsp;</p>\r\n<p>Cordialement,</p>\r\n<p>&nbsp;</p>\r\n<p>L\'&eacute;quipe Lut&egrave;ce</p>','','',0,1,0,'');
INSERT INTO workflow_task_notify_appointment_cf VALUES (160,'L\'équipe Lutèce','no-reply@mydomain.com','Annulation de votre rendez-vous du ${date_appointment}','<p>Bonjour,</p>\r\n<p>&nbsp;</p>\r\n<p>En raison de l\'indisponibilit&eacute; de nos agents, votre rendez-vous du ${date_appointment} &agrave; ${time_appointment} a &eacute;t&eacute; annul&eacute;.</p>\r\n<p>&nbsp;</p>\r\n<p>Veuillez nous excuser pour la g&egrave;ne ocasion&eacute;e.</p>\r\n<p>&nbsp;</p>\r\n<p>Cordialement,</p>\r\n<p>&nbsp;</p>\r\n<p>L\'&eacute;quipe Lut&egrave;ce</p>','','',0,1,0,'');
INSERT INTO workflow_task_notify_appointment_cf VALUES (164,'L\'équipe Lutèce','no-reply@mydomain.com','Votre rendez-vous du ${date_appointment} a bien été annulé','<p>Bonjour,</p>\r\n<p>&nbsp;</p>\r\n<p>A votre demande, votre rendez-vous du ${date_appointment} &agrave; ${time_appointment} avec nos services a bien &eacute;t&eacute; annul&eacute;.</p>\r\n<p>&nbsp;</p>\r\n<p>Cordialement,</p>\r\n<p>&nbsp;</p>\r\n<p>L\'&eacute;quipe Lut&egrave;ce.</p>','','',0,1,0,'');

INSERT INTO workflow_task_set_app_resource_cf VALUES (168,3,1);
INSERT INTO workflow_task_set_app_resource_cf VALUES (169,3,1);
INSERT INTO workflow_task_set_app_resource_cf VALUES (170,1,1);

INSERT INTO workflow_task_update_appointment_cancel_cf VALUES (150,122);

UPDATE appointment_form SET id_workflow = 105 WHERE id_form = 1;

INSERT INTO workflow_action VALUES(125,'Action d''arriver sur l''etat Non validé', '',105,109,109,1,0,0,0,1);
INSERT INTO workflow_task VALUES(172,'taskUpdateAppointmentCancelAction',125,1);
INSERT INTO workflow_task_update_appointment_cancel_cf VALUES (172,120);
