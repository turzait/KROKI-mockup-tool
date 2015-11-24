   {% extends "base.html" %}
  {% load crispy_forms_tags %}
  
  {% block head_title%} {{block.super}} {% endblock %}
  
  {% block jumbotron_content %}
		<form class="form-horizontal" method="post" action="">	        			
	 	{% csrf_token%}
	 		<fieldset>
				<legend>${panel.entityBean.label}</legend>
				<div class="form-group">
					{% for field in ${panel.entityBean.name}Form %}
					{{ field.errors }}           		
           			<div class="col-sm-3"> {{ field.label_tag }}</div> <div class="col-sm-9">{{ field }} </div>
        			{% endfor %}
        		</div>
        		<div class="form-group">
					<div class="col-sm-9 col-sm-offset-3">
					{% if ${panel.name}.id == None %}
						<a class="btn btn-default" href="{% url '${panel.name}_list' %}">Back</a>
					{% else %}
					<a class="btn btn-default" href="{% url '${panel.name}' ${panel.name}.id %}">Back</a>
					{% endif %}
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</div>
			</fieldset>
		</form>  
      
  {% endblock %}
  
  