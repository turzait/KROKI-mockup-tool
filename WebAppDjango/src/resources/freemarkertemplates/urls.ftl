# Generated by KROKI
# http://www.kroki-mde.net/

from django.conf.urls import patterns, include, url
from django.conf.urls.static import static

urlpatterns = patterns('',
    
    # Set Django login and logout links
    url(r'^logout/$', '${modulename}.views.logout_user', name='logout'),
    url(r'^login/$', '${modulename}.views.login_user', name='login'),  
    
    # home page
    url(r'^$', '${modulename}.views.index', name='index'),
      
<#list urls as url>
    #Links for ${url.pattern}
    url(r'^${url.pattern}/list', '${modulename}.views.${url.view}_list', name = '${url.view}_list'),   
    url(r'^${url.pattern}/search', '${modulename}.views.${url.view}_search', name = '${url.view}_search'),
    url(r'^${url.pattern}/new', '${modulename}.views.${url.view}_new', name = '${url.view}_new'), 
    url(r'^${url.pattern}/(?P<${url.view}_id>\d+)', '${modulename}.views.${url.view}', name = '${url.view}'), 
    url(r'^${url.pattern}/edit/(?P<${url.view}_id>\d+)', '${modulename}.views.${url.view}_edit', name = '${url.view}_edit'),   
    url(r'^${url.pattern}/delete/(?P<${url.view}_id>\d+)', '${modulename}.views.${url.view}_delete', name = '${url.view}_delete'),     

 </#list>

<#list panels as panel>
<#if panel.standardOperations.operations?has_content >   
<#list panel.standardOperations.operations as operation>
    url(r'^${panel.name}/${operation.name}', '${modulename}.views.${panel.name}_${operation.name}', name = '${panel.name}_${operation.name}'),
</#list>   
</#if>
</#list>

) 