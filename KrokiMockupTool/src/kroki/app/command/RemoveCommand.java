/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kroki.app.command;

import java.util.ArrayList;
import java.util.List;

import kroki.profil.VisibleElement;
import kroki.profil.association.Hierarchy;
import kroki.profil.association.Next;
import kroki.profil.association.Zoom;
import kroki.profil.group.ElementsGroup;
import kroki.profil.panel.VisibleClass;
import kroki.profil.panel.container.ParentChild;
import kroki.uml_core_basic.UmlOperation;
import kroki.uml_core_basic.UmlProperty;

/**
 * Komanda brisanja elementa
 * @author Vladan Marsenić (vladan.marsenic@gmail.com)
 * @author Renata
 */
public class RemoveCommand implements Command {

	private List<VisibleElement> visibleElementList;
	private List<Integer> groupIndexes;
	private List<Hierarchy> hierarchies = new ArrayList<Hierarchy>();

	public RemoveCommand(List<VisibleElement> visibleElementList) {
		this.visibleElementList = new ArrayList<VisibleElement>();
		this.visibleElementList.addAll(visibleElementList);

		groupIndexes = new ArrayList<Integer>();
		VisibleClass visibleClass;
		ElementsGroup elementsGroup;
		for (VisibleElement visibleElement : visibleElementList){
			visibleClass = getVisibleClass(visibleElement);
			if (visibleClass == null)
				continue;
			if (visibleElement instanceof Hierarchy){
				hierarchies.add((Hierarchy)visibleElement);
				continue;
			}
			elementsGroup = visibleElement.getParentGroup();
			groupIndexes.add(elementsGroup.getVisibleElementList().indexOf(visibleElement));
		}

		ParentChild panel = null;
		List<Hierarchy> currentSuccessors = new ArrayList<Hierarchy>();
		this.visibleElementList.removeAll(hierarchies);

		for (Hierarchy hierarchy : hierarchies){
			//delete hierarchy and all successors

			if (panel == null)
				panel = (ParentChild)hierarchy.umlClass();
			currentSuccessors.clear();
			panel.allSuccessors(currentSuccessors, hierarchy);
			currentSuccessors.add(0,hierarchy);

			for (Hierarchy h : currentSuccessors)
				if (!this.visibleElementList.contains(h)){
					this.visibleElementList.add(h);
					elementsGroup = hierarchy.getParentGroup();
					groupIndexes.add(elementsGroup.getVisibleElementList().indexOf(hierarchy));
				}
		}

	}

	public void doCommand() {
		VisibleClass visibleClass;
		ElementsGroup elementsGroup;
		for (VisibleElement visibleElement : visibleElementList){
			visibleClass = getVisibleClass(visibleElement);
			if (visibleClass == null)
				continue;

			if (visibleElement instanceof Zoom){
				Zoom zoom = (Zoom)visibleElement;
				if (zoom.opposite() != null){
					//changed opposite
					if (zoom.opposite() instanceof Next){
						Next next = (Next) zoom.opposite();
						next.setOpposite(null);
						next.setTargetPanel(null);
					}
			}
			}
			elementsGroup = visibleElement.getParentGroup();
			if (elementsGroup != null) {
				elementsGroup.removeVisibleElement(visibleElement);
				elementsGroup.update();
			}
			if (visibleClass != null) {
				visibleClass.removeVisibleElement(visibleElement);
				visibleClass.update();
			}

		}
	}

	public void undoCommand() {


		VisibleClass visibleClass;
		ElementsGroup elementsGroup;
		int index = 0;

		//Should add elements at their original positions
		//add all elements first, to avoid exceptions

		for (VisibleElement  visibleElement : visibleElementList){
			
			if (visibleElement instanceof Zoom){
				Zoom zoom = (Zoom)visibleElement;
				if (zoom.opposite() != null){
					//changed opposite
					if (zoom.opposite() instanceof Next){
						Next next = (Next) zoom.opposite();
						next.setOpposite(zoom);
						next.setTargetPanel(zoom.getActivationPanel());
					}
			}
			}
			elementsGroup = visibleElement.getParentGroup();
			visibleClass = getVisibleClass(visibleElement);
			if (visibleClass == null)
				continue;
			if (elementsGroup != null) 
				elementsGroup.addVisibleElement(visibleElement);

			if (visibleClass != null) 
				visibleClass.addVisibleElement(visibleElement);
		}


		for (VisibleElement visibleElement : visibleElementList){
			visibleClass = getVisibleClass(visibleElement);
			if (visibleClass == null)
				continue;

			elementsGroup = visibleElement.getParentGroup();
			int groupIndex = groupIndexes.get(index ++);

			if (elementsGroup != null) {
				elementsGroup.removeVisibleElement(visibleElement);
				elementsGroup.addVisibleElement(groupIndex, visibleElement);
				elementsGroup.update();
			}
			
		}

	}

	private VisibleClass getVisibleClass(VisibleElement visibleElement){
		if (visibleElement instanceof UmlProperty) 
			return (VisibleClass) ((UmlProperty) visibleElement).umlClass();
		else if (visibleElement instanceof UmlOperation) 
			return (VisibleClass) ((UmlOperation) visibleElement).umlClass();
		else
			return null;
	}
}
