<%
	/**
	* Template Editor overview template
	*
	* @author Jeroen Wesbeek
	* @since 20100422
	* @package wizard
	*
	* Revision information:
	* $Rev: 1430 $
	* $Author: work@osx.eu $
	* $Date: 2011-01-21 21:05:36 +0100 (Fri, 21 Jan 2011) $
	*/
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<meta name="layout" content="${layout}"/>
		<title>template editor</title>
		<g:if env="development">
			<script src="${resource(dir: 'js', file: 'templateEditor.js')}" type="text/javascript"></script>
			<link rel="stylesheet" href="${resource(dir: 'css', file: 'templateEditor.css')}" />
		</g:if>
		<g:else>
			<script src="${resource(dir: 'js', file: 'templateEditor.min.js')}" type="text/javascript"></script>
			<link rel="stylesheet" href="${resource(dir: 'css', file: 'templateEditor.min.css')}" />
		</g:else>
		<style type="text/css">
		  #content .templateEditorStep { font-size: 0.8em; }
		</style>
		<script type="text/javascript" language="javascript">
		  var standalone = ${extraparams?.standalone ? 'true' : 'false'};
		</script>
	</head>
	<body>

		<div class="templateEditorStep" id="step1_template">
			<h3>Select template</h3>
			<p>Showing templates for <b>${humanReadableEntity}</b><g:if test="${templates.size() > 0 }"> (<g:link controller="templateEditor" action="compare" params="${[entity: encryptedEntity] + extraparams}"><b>compare</b></g:link>)</g:if>.</p>
            <sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_TEMPLATEADMIN">
                <p>Please select a template to edit or create a new template.</p>
            </sec:ifAnyGranted>
            <sec:ifNotGranted roles="ROLE_ADMIN, ROLE_TEMPLATEADMIN">
                <p>Please select a template to view the templatefields within the template or request a new/modification to a template.</p>
            </sec:ifNotGranted>

			<ul id="templates">
                <li class="empty ui-state-default" <g:if test="${templates.size() > 0 }">style='display: none;'</g:if>>
                <sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_TEMPLATEADMIN">
                    There are no templates for ${humanReadableEntity}. Use the 'Add template' button to add fields.</li>
                </sec:ifAnyGranted>
                <sec:ifNotGranted roles="ROLE_ADMIN, ROLE_TEMPLATEADMIN">
                    There are no templates for ${humanReadableEntity}. Use the 'Add template' button to add fields.</li>
                </sec:ifNotGranted>
                </li>
                <g:each in="${templates}" var="currentTemplate">
				  <g:render template="elements/liTemplate" model="['template': currentTemplate, 'extraparams': extraparams]"/>
				</g:each>
			</ul>

            <sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_TEMPLATEADMIN">
                <div id="addNew">
                    <a href="#" onClick="editTemplate( 'new' ); this.blur(); window.scrollBy(0,1000); $('input#name').focus(); return false;">
                        <b>Create new template</b>
                    </a>

                    <form class="templateField_form" id="template_new_form" action="createTemplate">
                        <g:hiddenField name="entity" value="${encryptedEntity}" />
                        <g:hiddenField name="ontologies" value="${ontologies}" />
                        <g:hiddenField name="standalone" value="${extraparams?.standalone}" />
                        <g:render template="elements/templateForm" model="['template': null]"/>
                        <div class="templateFieldButtons">
                            <input type="button" value="Save" onClick="createTemplate( 'new' );">
                            <input type="button" value="Cancel" onClick="hideTemplateForm( 'new' );">
                        </div>
                    </form>
                </div>
            </sec:ifAnyGranted>

            <sec:ifNotGranted roles="ROLE_ADMIN, ROLE_TEMPLATEADMIN">
                <div id="addNew">
                    <a href="#" onClick="editTemplate( 'requestTemplate' ); this.blur(); window.scrollBy(0,1000); $('input#name').focus(); return false;">
                          <b>Request new/modification to template</b>
                    </a>

                    <form class="templateField_form" id="template_requestTemplate_form" action="sendRequest">
                        <g:hiddenField name="entity" value="${encryptedEntity}" />
                        <g:hiddenField name="ontologies" value="${ontologies}" />
                        <g:hiddenField name="standalone" value="${extraparams?.standalone}" />
                        <g:render template="elements/requestTemplateForm" model="['templateType': humanReadableEntity]"/>
                        <g:set var='email' value="ferryjagers@hotmail.com"/>
                        <div class="templateFieldButtons">
                            <input type="button" value="Send" onClick="requestTemplate ( 'requestTemplate' );">
                            <input type="button" value="Cancel" onClick="hideTemplateForm( 'requestTemplate' );">
                        </div>
                    </form>
                </div>
            </sec:ifNotGranted>

			<g:form action="template" name="templateChoice" method="GET">
				<g:hiddenField name="entity" value="${encryptedEntity}" />
				<g:hiddenField name="ontologies" value="${ontologies}" />
				<g:hiddenField name="standalone" value="${extraparams?.standalone}" />
				<input type="hidden" name="template" id="templateSelect" value="${template?.id}">
			</g:form>
		</div>
		<br clear="all" />
		<div id="wait" class="wait">
		  &nbsp;
		</div>
		<div class="wait_text wait">
		  <img src="<g:resource dir="images" file="spinner.gif" />"> Please wait
		</div>


	</body>
</html>