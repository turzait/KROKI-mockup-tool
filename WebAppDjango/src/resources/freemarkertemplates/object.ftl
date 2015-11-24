  {% extends "base.html" %}
  {% load crispy_forms_tags %}
  
  {% block head_title%} {{block.super}} {% endblock %}
  
  {% block jumbotron_content %}
   	<div class="row">
		<form class="form-horizontal">	        			
	 		{% csrf_token%}
	 			<fieldset>
					<legend>${panel.entityBean.label}</legend>
					<div class="form-group">
						{% for field in ${panel.entityBean.name}Form %}
           				<div class="col-sm-3"> {{ field.label_tag }}</div> <div class="col-sm-9">{{ field }} </div>
        				{% endfor %}
        			</div>
        		</fieldset>
        		<div class="form-group">
        			<div class="col-sm-9 col-sm-offset-3">
        				<a class="btn btn-default" href="{% url '${panel.name}_list' %}"> Back</a>
        				{% if editable == "true" %}
         				<a class="btn btn-primary" href="{% url '${panel.name}_edit' ${panel.name}_id %}"><span class="glyphicon glyphicon-pencil" aria-hidden="true"> Edit</span></a>			
        		 		{% endif %}																							           		
           			</div>
           		</div> 
        </form>         
    </div>
      
  {% endblock %}