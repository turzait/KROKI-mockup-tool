package com.krogen.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.krogen.model.DataModel;
import com.krogen.model.ejb.EjbClass;
import com.krogen.model.enumeration.Enumeration;
import com.krogen.model.menu.DjangoMenu;
import com.krogen.model.menu.DjangoSubMenu;
import com.krogen.model.panel.DjangoPanel;
import com.krogen.model.panel.AdaptStandardPanel;

/**
 * Singleton class used to store objects from xml specification to application model
 * and as a communication bridge between some classes.
 * @author Milorad Filipovic
 */
public class DataContainer {

	private static Application application;
	private static DataContainer instance = null;
	protected DataModel model = new DataModel();
		
	/**
	 * Singleton constructor.
	 * @return existing instance or creates new if none exist.
	 */
	public static DataContainer getInstance() {
		if (instance != null) {
			return instance;
		} else {
			instance = new DataContainer();
			return instance;
		}
	}
	
//	//---------------------------------------------------------------------| ADD METHODS
	/**
	 * Adds XML-to-EJB mapping to model
	 * @param className
	 * @param xmlFile
	 */
	public void addEJBtoXMLMapping(String className, String xmlFile) {
		model.addEjbMapping(className, xmlFile);
	}
	
	/**
	 * Adds entity bean object to application model
	 * @param ejb
	 */
	public void addEntityBean(EjbClass ejb) {
			model.addEjbClass(ejb);
	}
	
	public void setDefaultMenu(DjangoSubMenu menu){
		model.setDefaultMenu(menu);
	}

	public void addMenus(ArrayList<DjangoSubMenu> allMenus) {
		model.addMenus(allMenus);
	}
	
	public void addMenu(DjangoMenu menu) {
		model.addMenu(menu);
	}
	
	public void addEnumeration(String name, Enumeration enumeration) {
		model.add(name, enumeration);
	}
	
	public void addPanelClassMapping(String className, String panelId) {
		model.addPanelClassMapping(className, panelId);
	}
	
	public void addTypeComponentMapping(String languageType, String componentTemplate) {
		model.addComponentTypeMapping(languageType, componentTemplate);
	}

	//---------------------------------------------------------------------| UTIL METHODS
	public EjbClass findEJBByClassName(String className) {
		for (Map.Entry<String, EjbClass> ejb: model.getEntityBeans().entrySet()){
			if (ejb.getValue().getEntityClass().equals(className)) {
				return ejb.getValue();
			}
		}		
		return null;
	}

	public Enumeration findEnumerationByName(String name) {
		Enumeration enumeration = null;
		for (String  enumName : model.getEnumerations().keySet()) {
			if(enumName.equals(name)) {
				return model.getEnumerations().get(enumName);
			}
		}
		return null;
	}
	
//	public AdaptStandardPanel getPanelByName(String name) {
//		AdaptPanel panel = model.getPanels().get(name);
//		if(panel instanceof AdaptStandardPanel) {
//			return (AdaptStandardPanel) panel;
//		}else {
//			return null;
//		}
//	}
//	
//	public String getPanelId(String className) {
//		return model.getPanelClassMap().get(className);
//	}
//	
//	public String getComponentForType(String typeName) {
//		return model.getComponentTypeMap().get(typeName);
//	}
//	
//	public Enumeration getEnumByName(String enumName) {
//		return model.getEnumerations().get(enumName);
//	}
//	
//	/**
//	 * Proxy method for {@code displayText} method in {@code AdaptMainFrame} class
//	 * @param type 0 - info, 1 - error, 2 - warning
//	 */
//	public static void displayTextOnMainFrame(String text, int type) {
//		Application.getMainFrame().displayText(text, type);
//	}
//	
//	public static void displayStackTraceOnMainFrame(Exception e) {
//		Application.getMainFrame().displayStackTrace(e);
//	}
//	
//	//---------------------------------------------------------------------| GETTERS
	public String getXMLFileName(String className) {
		return model.getXmlMappings().get(className);
	}
	
	public Map<String, String> getXMLMappings() {
		return model.getXmlMappings();
	}
	public static Application getApplication() {
		return application;
	}

	public static void setApplication(Application application) {
		DataContainer.application = application;
	}
	
	public ArrayList<DjangoMenu> getMenuList() {
		return (ArrayList<DjangoMenu>) model.getMenus();
	}
	
	public DjangoSubMenu getDefaultMenu() {
		return model.getDefaultMenu();
	}
	
	public List<DjangoPanel> getPanels(){
		return model.getPanels();
	}

	public Map<String, String> getPanelClassMap() {
		return model.getPanelClassMap();
	}

	public void addPanel(DjangoPanel panel) {
		model.add(panel);
		
	}
	public HashMap<String,EjbClass> getEjbClasses(){
		return model.getEntityBeans();
	}
	
	public Map<String, Enumeration> getEnumerations(){
		return model.getEnumerations();
	}
}
