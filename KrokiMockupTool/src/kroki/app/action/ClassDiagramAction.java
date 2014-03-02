package kroki.app.action;

import graphedit.app.ApplicationMode;
import graphedit.app.MainFrame;
import graphedit.model.elements.GraphEditPackage;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import kroki.app.KrokiMockupToolApp;
import kroki.app.utils.ImageResource;
import kroki.profil.group.ElementsGroup;
import kroki.profil.panel.StandardPanel;
import kroki.profil.panel.VisibleClass;
import kroki.profil.subsystem.BussinesSubsystem;
import kroki.uml_core_basic.UmlPackage;
import kroki.uml_core_basic.UmlType;

public class ClassDiagramAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClassDiagramAction(){
		putValue(NAME, "Show class diagram");
		putValue(SHORT_DESCRIPTION, "Show class diagram");
		ImageIcon smallIcon = new ImageIcon(ImageResource.getImageResource("action.classDiagramAction.icon"));
		putValue(SMALL_ICON, smallIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		BussinesSubsystem proj = null;
		if (KrokiMockupToolApp.getInstance().getKrokiMockupToolFrame().getTree().getSelectionPath()!=null){
			String selectedNoded = KrokiMockupToolApp.getInstance().getKrokiMockupToolFrame().getTree().getSelectionPath().getLastPathComponent().toString();
			for(int j=0; j<KrokiMockupToolApp.getInstance().getWorkspace().getPackageCount(); j++) {
				BussinesSubsystem pack = (BussinesSubsystem)KrokiMockupToolApp.getInstance().getWorkspace().getPackageAt(j);
				if(pack.getLabel().equals(selectedNoded)) {
					proj = pack;
				}
			}
		}
		
		MainFrame.getInstance().setAppMode(ApplicationMode.USER_INTERFACE);

		List<UmlPackage> workspaceList = KrokiMockupToolApp.getInstance().getWorkspace().getPackageList();
		if (proj != null)
			graphedit.model.GraphEditWorkspace.getInstance().setProject(proj);
		else
			graphedit.model.GraphEditWorkspace.getInstance().setPackageList(workspaceList);

		MainFrame.getInstance().setVisible(true);

		//graphedit.model.GraphEditWorkspace.getInstance().getMainFrame().setVisible(true);
		List<GraphEditPackage> packageList = graphedit.model.GraphEditWorkspace.getInstance().getpackageList();
		UmlPackage umlPackage;
		for (GraphEditPackage pack: packageList){
			umlPackage = pack.getUmlPackage();
			if (!workspaceList.contains(umlPackage))
				workspaceList.add(umlPackage);
			updatePanels(umlPackage);
		}

		KrokiMockupToolApp.getInstance().getKrokiMockupToolFrame().getTree().updateUI();
		KrokiMockupToolApp.getInstance().getKrokiMockupToolFrame().getCanvasTabbedPane().updateUI();
	}

	private void updatePanels(UmlPackage pack){
		StandardPanel panel;
		for (UmlType ownedType : pack.ownedType()){
			if (ownedType instanceof StandardPanel){
				panel = (StandardPanel) ownedType;
				((ElementsGroup) panel.getVisibleElementList().get(1)).update();
				((ElementsGroup) panel.getVisibleElementList().get(2)).update();;
				/*NamingUtil cc = new NamingUtil();
				System.out.println(panel.getPersistentClass().name());
				panel.getPersistentClass().setName(cc.toCamelCase(panel.getLabel(), false));
				System.out.println(panel.getPersistentClass().name());
				panel.getComponent().setName(panel.getLabel());
				StyleToolbar st = (StyleToolbar) KrokiMockupToolApp.getInstance().getGuiManager().getStyleToolbar();
				st.updateAllToggles(((ElementsGroup) panel.getVisibleElementList().get(1)));
				st.updateAllToggles(((ElementsGroup) panel.getVisibleElementList().get(2)));*/
			}
			((VisibleClass)ownedType).update();
		}
		for (UmlPackage nestedPack : pack.nestedPackage())
			updatePanels(nestedPack);

	}

}
