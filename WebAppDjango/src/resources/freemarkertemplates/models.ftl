from django.db import models

# Generated by KROKI
# http://www.kroki-mde.net/

from django.contrib.auth.models import User

<#list enumerations as enum>
${enum.name} = (
	<#list enum.labels as label>
	('${label_index}', '${label}'),
	</#list>
)

</#list>
    
class Login(models.Model):
	username = models.CharField(max_length = 60, blank=True, null=False)
	password = models.CharField(max_length = 60, blank=True, null=False)
	
	def __str__(self):
		return self.username
        		
<#list models as model>
class ${model.name}(models.Model):
<#list model.fieldsList as field>
<#if field.key?? && field.key == true>
	${field.fieldName} = models.AutoField(primary_key = True)
<#elseif field.enumerationName??>
	${field.fieldName} = models.CharField(choices=${field.enumerationName}, max_length = 50, null = True, blank = True)
<#elseif field.entryTypesEnum == 'CharField'>
	${field.fieldName} = models.${field.entryTypesEnum}(max_length = <#if field.length == 0>255<#else>${field.length}</#if>, null = True, blank = True)
<#elseif field.entryTypesEnum == 'IntegerField'>
	${field.fieldName} = models.${field.entryTypesEnum}(default = 0, null = True, blank = True)
<#elseif field.entryTypesEnum == 'Textarea'>
	${field.fieldName} = models.CharField(max_length= 255, default ="", null = True, blank = True)
<#elseif field.entryTypesEnum == 'FloatField'>
<#elseif field.entryTypesEnum == 'BooleanField'>
	${field.fieldName} = models.${field.entryTypesEnum}(default = False, blank = True)
<#elseif field.entryTypesEnum == 'ForeignKey'>
	${field.fieldName} = models.${field.entryTypesEnum}(${classnameModelMap[field.className]} , null = True, blank = True)
</#if>	
</#list>	

	def __str__(self):
		return '${model.name}: '<#list model.fieldsList as field> + '${field.fieldName} = ' + self.${field.fieldName}.__str__()</#list>
		
</#list>

<#--
Old way of generating primary keys
#list models as model>
class ${model.name}(models.Model):
<#list model.fieldsList as field>
<#elseif field.entryTypesEnum == 'CharField'>
	${field.fieldName} = models.${field.entryTypesEnum}(max_length = <#if field.length == 0>255<#else>${field.length}</#if><#if field.key == true>, primary_key = True</#if>)
<#elseif field.entryTypesEnum == 'IntegerField'>
	${field.fieldName} = models.${field.entryTypesEnum}(default = 0<#if field.key == true>, primary_key = True</#if>)
<#elseif field.entryTypesEnum == 'TextField'>
	${field.fieldName} = models.${field.entryTypesEnum}(<#if field.key == true>, primary_key = True</#if>)
<#elseif field.entryTypesEnum == 'FloatField'>
<#elseif field.entryTypesEnum == 'BooleanField'>
	${field.fieldName} = models.${field.entryTypesEnum}(default = False<#if field.key == true>, primary_key = True</#if>)
<#elseif field.entryTypesEnum == 'ForeignKey'>
	${field.fieldName} = models.${field.entryTypesEnum}(${classnameModelMap[field.className]} , null = True)
</#if>	
</#list>	

	def __str__(self):
		return '${model.name}: '<#list model.fieldsList as field> + '${field.fieldName} = ' + self.${field.fieldName}.__str__()</#list>
		
</#list>
-->
