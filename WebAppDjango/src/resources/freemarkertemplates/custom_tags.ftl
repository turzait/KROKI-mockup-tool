# Generated by KROKI
# http://www.kroki-mde.net/

from django import template

register = template.Library()

@register.filter()
def lookup(the_dict, key):
    return the_dict.get(key, '')