<jsp:useBean id="manageappointmentformResources" scope="session" class="fr.paris.lutece.plugins.appointment.modules.resource.web.AppointmentFormResourcesJspBean" />
<% String strContent = manageappointmentformResources.processController ( request , response ); %>

<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../../../AdminFooter.jsp" %>
