<li id="template_${template.id}"class="ui-state-default">
  <g:if test='${templateadmin}'>
      <g:if test="${!template.inUse()}">
        <g:render template="elements/liTemplateEditable" model="['template': template, 'standalone': standalone]"/>
      </g:if>
      <g:else>
        <g:render template="elements/liTemplateNonDeletable" model="['template': template, 'standalone': standalone]"/>
      </g:else>
  </g:if>

  <g:else>
      <g:render template="elements/liTemplateNonEditable" model="['template': template, 'standalone': standalone]"/>
  </g:else>
</li>