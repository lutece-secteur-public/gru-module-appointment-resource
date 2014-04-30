<jsp:useBean id="manageappointmentResources" scope="session" class="fr.paris.lutece.plugins.appointment.modules.resource.web.AppointmentResourceJspBean" />
<% String strContent = manageappointmentResources.processController ( request , response ); %>

<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../../../AdminFooter.jsp" %>
