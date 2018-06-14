/*
 * Copyright (c) 2002-2018, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.appointment.modules.resource.web;

import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentFormResourceType;
import fr.paris.lutece.plugins.appointment.modules.resource.business.AppointmentFormResourceTypeHome;
import fr.paris.lutece.plugins.appointment.service.AppointmentResourceIdService;
import fr.paris.lutece.plugins.appointment.service.FormService;
import fr.paris.lutece.plugins.appointment.web.AppointmentFormJspBean;
import fr.paris.lutece.plugins.appointment.web.dto.AppointmentFormDTO;
import fr.paris.lutece.plugins.resource.business.IResourceType;
import fr.paris.lutece.plugins.resource.service.ResourceService;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.util.mvc.admin.MVCAdminJspBean;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.utils.MVCUtils;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Jsp Bean to manage appointment form resources
 */
@Controller( controllerJsp = AppointmentFormResourcesJspBean.JSP_MANAGE_APPOINTMENT_FORM_RESOURCE_TYPE, controllerPath = AppointmentFormResourcesJspBean.PATH_MANAGE_APPOINTMENT_FORM_RESOURCE_TYPE, right = AppointmentFormJspBean.RIGHT_MANAGEAPPOINTMENTFORM )
public class AppointmentFormResourcesJspBean extends MVCAdminJspBean
{
    /**
     * Path of this controller
     */
    public static final String PATH_MANAGE_APPOINTMENT_FORM_RESOURCE_TYPE = "jsp/admin/plugins/appointment/modules/resource/";

    /**
     * JSP name of this controller
     */
    public static final String JSP_MANAGE_APPOINTMENT_FORM_RESOURCE_TYPE = "ManageAppointmentFormResources.jsp";

    /**
     * Url of the JSP of this controller, with its full path (without the base URL)
     */
    public static final String JSP_URL_MANAGE_APPOINTMENT_FORM_RESOURCE_TYPE = PATH_MANAGE_APPOINTMENT_FORM_RESOURCE_TYPE
            + JSP_MANAGE_APPOINTMENT_FORM_RESOURCE_TYPE;
    private static final long serialVersionUID = -7228498724499752562L;

    // Views
    private static final String VIEW_MANAGE_FORM_RESOURCES = "manageFormResources";
    private static final String VIEW_CREATE_RESOURCE_TYPE = "createFormResourceType";
    private static final String VIEW_MODIFY_RESOURCE_TYPE = "modifyFormResourceType";
    private static final String VIEW_CONFIRM_REMOVE_RESOURCE_TYPE = "confirmRemoveFormResourceType";

    // Actions
    private static final String ACTION_DO_CREATE_FORM_RESOURCE_TYPE = "doCreateFormResourceType";
    private static final String ACTION_DO_MODIFY_FORM_RESOURCE_TYPE = "doModifyFormResourceType";
    private static final String ACTION_DO_REMOVE_FORM_RESOURCE_TYPE = "doRemoveFormResourceType";
    private static final String ACTION_DO_SET_ADMIN_DEFAULT_RESOURCE_TYPE = "doSetAdminDefaultResourceType";
    private static final String ACTION_DO_SET_LOCATION_DEFAULT_RESOURCE_TYPE = "doSetLocationDefaultResourceType";

    // Marks
    private static final String MARK_LIST_RESOURCE_TYPES = "listResourceTypes";
    private static final String MARK_LIST_FORM_RESOURCE_TYPES = "listFormResourceTypes";
    private static final String MARK_FORM_RESOURCE_TYPE = "formResourceType";
    private static final String MARK_ADMIN_USER_RESOURCE_TYPE = "adminUserResourceType";
    private static final String MARK_LOCALE = "language";

    // Parameters
    private static final String PARAMETER_ID_FORM = "id_form";
    private static final String PARAMETER_ID_FORM_RESOURCE_TYPE = "idFormResourceType";

    // Messages
    private static final String MESSAGE_MANAGE_FORM_RESOURCES_PAGE_TITLE = "module.appointment.resource.manageAppointmentFormResources.pageTitle";
    private static final String MESSAGE_CREATE_FORM_RESOURCES_PAGE_TITLE = "module.appointment.resource.createAppointmentFormResources.pageTitle";
    private static final String MESSAGE_MODIFY_FORM_RESOURCES_PAGE_TITLE = "module.appointment.resource.modifyAppointmentFormResources.pageTitle";
    private static final String MESSAGE_APPOINTMENT_FORM_RESOURCE_TYPE_CREATED = "module.appointment.resource.createAppointmentFormResources.appointmentFormResourceTypeCreated";
    private static final String MESSAGE_APPOINTMENT_FORM_RESOURCE_TYPE_MODIFIED = "module.appointment.resource.modifyAppointmentFormResources.appointmentFormResourceTypeModified";
    private static final String MESSAGE_CONFIRM_REMOVE_FORM_RESOURCE_TYPE = "module.appointment.resource.removeAppointmentFormResource.confirmRemoveResourceType";
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "module.appointment.resource.model.entity.appointmentFormResourceType.attribute.";

    // Templates
    private static final String TEMPLATE_MANAGE_FORM_RESOURCES = "admin/plugins/appointment/modules/resource/manage_form_resources.html";
    private static final String TEMPLATE_CREATE_FORM_RESOURCES = "admin/plugins/appointment/modules/resource/create_form_resources.html";
    private static final String TEMPLATE_MODIFY_FORM_RESOURCES = "admin/plugins/appointment/modules/resource/modify_form_resources.html";
    private AppointmentFormResourceType _formResourceType;

    // Session variable to store working values
    private static final String SESSION_ATTRIBUTE_APPOINTMENT_FORM = "appointment.session.appointmentForm";
    
    /**
     * Manage the resources of an appointment form
     * 
     * @param request
     *            the request
     * @return The HTML content to display
     * @throws AccessDeniedException
     *             If the user is not authorized to access this feature
     */
    @View( value = VIEW_MANAGE_FORM_RESOURCES )
    public String getManageFormResources( HttpServletRequest request ) throws AccessDeniedException
    {
        _formResourceType = null;

        String strIdForm = request.getParameter( PARAMETER_ID_FORM );

        if ( StringUtils.isEmpty( strIdForm ) || !StringUtils.isNumeric( strIdForm ) )
        {
            return redirect( request, AppointmentFormJspBean.getURLManageAppointmentForms( request ) );
        }

        int nIdForm = Integer.parseInt( strIdForm );

        if ( !RBACService.isAuthorized( AppointmentFormDTO.RESOURCE_TYPE, strIdForm, AppointmentResourceIdService.PERMISSION_MODIFY_FORM, getUser( ) ) )
        {
            throw new AccessDeniedException( AppointmentResourceIdService.PERMISSION_MODIFY_FORM );
        }

        AppointmentFormDTO appointmentForm = (AppointmentFormDTO) request.getSession( ).getAttribute( SESSION_ATTRIBUTE_APPOINTMENT_FORM );
        if ( ( appointmentForm == null ) || ( nIdForm != appointmentForm.getIdForm( ) ) )
        {
            appointmentForm = FormService.buildAppointmentForm( nIdForm, 0, 0 );
        }

        if ( appointmentForm == null )
        {
            return redirect( request, AppointmentFormJspBean.getURLManageAppointmentForms( request ) );
        }

        Map<String, Object> model = getModel( );

        List<IResourceType> listResourceTypes = ResourceService.getInstance( ).getResourceTypesList( );

        model.put( MARK_LIST_RESOURCE_TYPES, listResourceTypes );
        model.put( MARK_LIST_FORM_RESOURCE_TYPES, AppointmentFormResourceTypeHome.findResourceTypesListFromIdForm( nIdForm ) );
        model.put( MARK_ADMIN_USER_RESOURCE_TYPE, AdminUser.RESOURCE_TYPE );
        model.put( MARK_LOCALE, getLocale( ) );

        AppointmentFormJspBean.addElementsToModel( request, appointmentForm, getUser( ), getLocale( ), model );

        return getPage( MESSAGE_MANAGE_FORM_RESOURCES_PAGE_TITLE, TEMPLATE_MANAGE_FORM_RESOURCES, model );
    }

    /**
     * Get the page to create an appointment form resource type
     * 
     * @param request
     *            the request
     * @return The page to create an appointment form resource type
     * @throws AccessDeniedException
     *             If the user is not authorized to access this feature
     */
    @View( value = VIEW_CREATE_RESOURCE_TYPE )
    public String getCreateFormResourceType( HttpServletRequest request ) throws AccessDeniedException
    {
        AppointmentFormResourceType formResourceType;
        int nIdForm;

        if ( _formResourceType != null )
        {
            formResourceType = _formResourceType;
            _formResourceType = null;
            nIdForm = formResourceType.getIdAppointmentForm( );
        }
        else
        {
            String strIdForm = request.getParameter( PARAMETER_ID_FORM );

            if ( StringUtils.isEmpty( strIdForm ) || !StringUtils.isNumeric( strIdForm ) )
            {
                return redirect( request, AppointmentFormJspBean.getURLManageAppointmentForms( request ) );
            }

            formResourceType = null;
            nIdForm = Integer.parseInt( strIdForm );
        }

        if ( !RBACService.isAuthorized( AppointmentFormDTO.RESOURCE_TYPE, Integer.toString( nIdForm ), AppointmentResourceIdService.PERMISSION_MODIFY_FORM,
                getUser( ) ) )
        {
            throw new AccessDeniedException( AppointmentResourceIdService.PERMISSION_MODIFY_FORM );
        }

        ReferenceList refListResourceTypes = new ReferenceList( );

        for ( IResourceType resourceType : ResourceService.getInstance( ).getResourceTypesList( ) )
        {
            refListResourceTypes.addItem( resourceType.getResourceTypeName( ), resourceType.getResourceTypeDescription( ) );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_FORM_RESOURCE_TYPE, formResourceType );
        model.put( MARK_LIST_RESOURCE_TYPES, refListResourceTypes );
        model.put( PARAMETER_ID_FORM, nIdForm );
        model.put( MARK_LOCALE, getLocale( ) );

        return getPage( MESSAGE_CREATE_FORM_RESOURCES_PAGE_TITLE, TEMPLATE_CREATE_FORM_RESOURCES, model );
    }

    /**
     * Do create an appointment form resource type
     * 
     * @param request
     *            the request
     * @return The next URL to redirect to
     * @throws AccessDeniedException
     *             If the user is not authorized to access this feature
     */
    @Action( value = ACTION_DO_CREATE_FORM_RESOURCE_TYPE )
    public String doCreateFormResourceType( HttpServletRequest request ) throws AccessDeniedException
    {
        AppointmentFormResourceType formResourceType = new AppointmentFormResourceType( );

        populate( formResourceType, request );

        if ( !RBACService.isAuthorized( AppointmentFormDTO.RESOURCE_TYPE, Integer.toString( formResourceType.getIdAppointmentForm( ) ),
                AppointmentResourceIdService.PERMISSION_MODIFY_FORM, getUser( ) ) )
        {
            throw new AccessDeniedException( AppointmentResourceIdService.PERMISSION_MODIFY_FORM );
        }

        if ( !validateBean( formResourceType, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            _formResourceType = formResourceType;

            return redirectView( request, VIEW_CREATE_RESOURCE_TYPE );
        }

        AppointmentFormResourceTypeHome.insert( formResourceType );

        addInfo( MESSAGE_APPOINTMENT_FORM_RESOURCE_TYPE_CREATED, getLocale( ) );

        return redirect( request, getUrlManageAppointmentFormResourceType( formResourceType.getIdAppointmentForm( ), request ) );
    }

    /**
     * Get the page to modify an appointment form resource type
     * 
     * @param request
     *            The request
     * @return The HTML content to display
     * @throws AccessDeniedException
     *             If the user is not authorized to access this feature
     */
    @View( value = VIEW_MODIFY_RESOURCE_TYPE )
    public String getModifyFormResourceType( HttpServletRequest request ) throws AccessDeniedException
    {
        AppointmentFormResourceType formResourceType;

        String strId = request.getParameter( PARAMETER_ID_FORM_RESOURCE_TYPE );

        if ( StringUtils.isEmpty( strId ) || !StringUtils.isNumeric( strId ) )
        {
            formResourceType = _formResourceType;
            _formResourceType = null;
        }
        else
        {
            int nId = Integer.parseInt( strId );
            formResourceType = AppointmentFormResourceTypeHome.findByPrimaryKey( nId );
        }

        if ( formResourceType == null )
        {
            return getManageFormResources( request );
        }

        if ( !RBACService.isAuthorized( AppointmentFormDTO.RESOURCE_TYPE, Integer.toString( formResourceType.getIdAppointmentForm( ) ),
                AppointmentResourceIdService.PERMISSION_MODIFY_FORM, getUser( ) ) )
        {
            throw new AccessDeniedException( AppointmentResourceIdService.PERMISSION_MODIFY_FORM );
        }

        List<IResourceType> listResourceTypes = ResourceService.getInstance( ).getResourceTypesList( );

        Map<String, Object> model = getModel( );
        model.put( MARK_FORM_RESOURCE_TYPE, formResourceType );
        model.put( MARK_LIST_RESOURCE_TYPES, listResourceTypes );
        model.put( MARK_LOCALE, getLocale( ) );

        return getPage( MESSAGE_MODIFY_FORM_RESOURCES_PAGE_TITLE, TEMPLATE_MODIFY_FORM_RESOURCES, model );
    }

    /**
     * Do modify an appointment form resource type
     * 
     * @param request
     *            The request
     * @return The next URL to redirect to
     * @throws AccessDeniedException
     *             If the user is not authorized to access this feature
     */
    @Action( value = ACTION_DO_MODIFY_FORM_RESOURCE_TYPE )
    public String doModifyFormResourceType( HttpServletRequest request ) throws AccessDeniedException
    {
        String strIdFormResourceType = request.getParameter( PARAMETER_ID_FORM_RESOURCE_TYPE );

        if ( StringUtils.isEmpty( strIdFormResourceType ) || !StringUtils.isNumeric( strIdFormResourceType ) )
        {
            return redirect( request, AppointmentFormJspBean.getURLManageAppointmentForms( request ) );
        }

        int nIdFormResourceType = Integer.parseInt( strIdFormResourceType );

        AppointmentFormResourceType formResourceType = AppointmentFormResourceTypeHome.findByPrimaryKey( nIdFormResourceType );               

        if ( formResourceType == null )
        {
            return redirect( request, AppointmentFormJspBean.getURLManageAppointmentForms( request ) );
        }

        if ( !RBACService.isAuthorized( AppointmentFormDTO.RESOURCE_TYPE, Integer.toString( formResourceType.getIdAppointmentForm( ) ),
                AppointmentResourceIdService.PERMISSION_MODIFY_FORM, getUser( ) ) )
        {
            throw new AccessDeniedException( AppointmentResourceIdService.PERMISSION_MODIFY_FORM );
        }
        // Need to save the configuration for these two boolean because the populate method lost them
        boolean bIsAppointmentAdminUser = formResourceType.getIsAppointmentAdminUser();
        boolean bIsLocation = formResourceType.getIsLocation();
        populate( formResourceType, request );
        formResourceType.setIsAppointmentAdminUser(bIsAppointmentAdminUser);
        formResourceType.setIsLocation(bIsLocation);
        if ( !validateBean( formResourceType, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            _formResourceType = formResourceType;

            return redirectView( request, VIEW_MODIFY_RESOURCE_TYPE );
        }

        AppointmentFormResourceTypeHome.update( formResourceType );

        addInfo( MESSAGE_APPOINTMENT_FORM_RESOURCE_TYPE_MODIFIED, getLocale( ) );

        return redirect( request, getUrlManageAppointmentFormResourceType( formResourceType.getIdAppointmentForm( ), request ) );
    }

    /**
     * Do confirm the removal of an appointment form resource type
     * 
     * @param request
     *            the request
     * @return the next URL to redirect to
     * @throws AccessDeniedException
     *             If the user is not authorized to access this feature
     */
    @View( value = VIEW_CONFIRM_REMOVE_RESOURCE_TYPE )
    public String getConfirmRemoveFormResourceType( HttpServletRequest request ) throws AccessDeniedException
    {
        String strIdFormResourceType = request.getParameter( PARAMETER_ID_FORM_RESOURCE_TYPE );

        if ( StringUtils.isEmpty( strIdFormResourceType ) || !StringUtils.isNumeric( strIdFormResourceType ) )
        {
            return getManageFormResources( request );
        }

        int nIdFormResourceType = Integer.parseInt( strIdFormResourceType );
        AppointmentFormResourceType formResourceType = AppointmentFormResourceTypeHome.findByPrimaryKey( nIdFormResourceType );

        if ( formResourceType == null )
        {
            return getManageFormResources( request );
        }

        if ( !RBACService.isAuthorized( AppointmentFormDTO.RESOURCE_TYPE, Integer.toString( formResourceType.getIdAppointmentForm( ) ),
                AppointmentResourceIdService.PERMISSION_MODIFY_FORM, getUser( ) ) )
        {
            throw new AccessDeniedException( AppointmentResourceIdService.PERMISSION_MODIFY_FORM );
        }

        UrlItem urlItem = new UrlItem( JSP_URL_MANAGE_APPOINTMENT_FORM_RESOURCE_TYPE );
        urlItem.addParameter( MVCUtils.PARAMETER_ACTION, ACTION_DO_REMOVE_FORM_RESOURCE_TYPE );
        urlItem.addParameter( PARAMETER_ID_FORM_RESOURCE_TYPE, strIdFormResourceType );

        return redirect( request,
                AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_FORM_RESOURCE_TYPE, urlItem.getUrl( ), AdminMessage.TYPE_CONFIRMATION ) );
    }

    /**
     * Do remove an appointment form resource type
     * 
     * @param request
     *            The request
     * @return The newt URL to redirect to
     * @throws AccessDeniedException
     *             If the user is not authorized to access this feature
     */
    @Action( value = ACTION_DO_REMOVE_FORM_RESOURCE_TYPE )
    public String doRemoveFormResourceType( HttpServletRequest request ) throws AccessDeniedException
    {
        String strIdFormResourceType = request.getParameter( PARAMETER_ID_FORM_RESOURCE_TYPE );

        if ( StringUtils.isEmpty( strIdFormResourceType ) || !StringUtils.isNumeric( strIdFormResourceType ) )
        {
            return redirect( request, AppointmentFormJspBean.getURLManageAppointmentForms( request ) );
        }

        int nIdFormResourceType = Integer.parseInt( strIdFormResourceType );
        AppointmentFormResourceType formResourceType = AppointmentFormResourceTypeHome.findByPrimaryKey( nIdFormResourceType );

        if ( formResourceType == null )
        {
            return redirect( request, AppointmentFormJspBean.getURLManageAppointmentForms( request ) );
        }

        if ( !RBACService.isAuthorized( AppointmentFormDTO.RESOURCE_TYPE, Integer.toString( formResourceType.getIdAppointmentForm( ) ),
                AppointmentResourceIdService.PERMISSION_MODIFY_FORM, getUser( ) ) )
        {
            throw new AccessDeniedException( AppointmentResourceIdService.PERMISSION_MODIFY_FORM );
        }

        AppointmentFormResourceTypeHome.remove( nIdFormResourceType );

        return redirect( request, getUrlManageAppointmentFormResourceType( formResourceType.getIdAppointmentForm( ), request ) );
    }

    /**
     * Do set a form resource type as the attribute containing the admin user associated with an appointment, so that when the attribute is modified, the admin
     * user associated with the appointment is also modified
     * 
     * @param request
     *            The request
     * @return The next URL to redirect to
     */
    @Action( ACTION_DO_SET_ADMIN_DEFAULT_RESOURCE_TYPE )
    public String doSetAdminDefaultResourceType( HttpServletRequest request )
    {
        String strIdFormResourceType = request.getParameter( PARAMETER_ID_FORM_RESOURCE_TYPE );
        int nIdFormResourceType = Integer.parseInt( strIdFormResourceType );
        AppointmentFormResourceType formResourceType = AppointmentFormResourceTypeHome.findByPrimaryKey( nIdFormResourceType );

        AppointmentFormResourceTypeHome.resetAppAdminUser( formResourceType.getIdAppointmentForm( ) );
        formResourceType.setIsAppointmentAdminUser( true );
        AppointmentFormResourceTypeHome.update( formResourceType );

        return redirect( request, VIEW_MANAGE_FORM_RESOURCES, PARAMETER_ID_FORM, formResourceType.getIdAppointmentForm( ) );
    }

    /**
     * Do set a form resource type as the attribute containing the localization of appointments
     * 
     * @param request
     *            The request
     * @return The next URL to redirect to
     */
    @Action( ACTION_DO_SET_LOCATION_DEFAULT_RESOURCE_TYPE )
    public String doSetLocalizationDefaultResourceType( HttpServletRequest request )
    {
        String strIdFormResourceType = request.getParameter( PARAMETER_ID_FORM_RESOURCE_TYPE );
        int nIdFormResourceType = Integer.parseInt( strIdFormResourceType );
        AppointmentFormResourceType formResourceType = AppointmentFormResourceTypeHome.findByPrimaryKey( nIdFormResourceType );

        AppointmentFormResourceTypeHome.resetLocalization( formResourceType.getIdAppointmentForm( ) );
        formResourceType.setIsLocation( true );
        AppointmentFormResourceTypeHome.update( formResourceType );

        return redirect( request, VIEW_MANAGE_FORM_RESOURCES, PARAMETER_ID_FORM, formResourceType.getIdAppointmentForm( ) );
    }

    /**
     * Get the URL to manage appointment form resource types
     * 
     * @param nIdForm
     *            the id of the form
     * @param request
     *            The request
     * @return The requested URL
     */
    public static final String getUrlManageAppointmentFormResourceType( int nIdForm, HttpServletRequest request )
    {
        UrlItem urlItem = new UrlItem( AppPathService.getBaseUrl( request ) + JSP_URL_MANAGE_APPOINTMENT_FORM_RESOURCE_TYPE );
        urlItem.addParameter( MVCUtils.PARAMETER_VIEW, VIEW_MANAGE_FORM_RESOURCES );
        urlItem.addParameter( PARAMETER_ID_FORM, nIdForm );

        return urlItem.getUrl( );
    }
}
