package graphedit.model.components;

import graphedit.model.elements.GraphEditElement;
import graphedit.model.properties.Properties;
import graphedit.model.properties.PropertyEnums.GraphElementProperties;

import java.io.Serializable;
import java.util.Set;

public abstract class GraphElement implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Properties<GraphElementProperties> properties;
	protected GraphEditElement representedElement;

	public GraphElement() {
		properties = new Properties<GraphElementProperties>();
	}

	public Object getProperty(GraphElementProperties key) {
		return properties.get(key);
	}
	
	
	public Set<?> getEntrySet() { return null; }
	
	public Object setProperty(GraphElementProperties key, Object value) {
		Object result = properties.set(key, value);
		// uradi nesto
		return result;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public GraphEditElement getRepresentedElement() {
		return representedElement;
	}

	public void setRepresentedElement(GraphEditElement representedElement) {
		this.representedElement = representedElement;
	}

	@Override
	public String toString() {
		return (String) properties.get(GraphElementProperties.NAME);
	}

}