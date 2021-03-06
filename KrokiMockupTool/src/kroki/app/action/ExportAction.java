package kroki.app.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import kroki.app.KrokiMockupToolApp;
import kroki.app.view.Canvas;
import kroki.commons.camelcase.NamingUtil;
import kroki.profil.VisibleElement;
import kroki.profil.association.Zoom;
import kroki.profil.operation.BussinessOperation;
import kroki.profil.operation.Report;
import kroki.profil.operation.Transaction;
import kroki.profil.operation.VisibleOperation;
import kroki.profil.panel.StandardPanel;
import kroki.profil.panel.VisibleClass;
import kroki.profil.panel.mode.ViewMode;
import kroki.profil.panel.std.StdPanelSettings;
import kroki.profil.property.VisibleProperty;
import kroki.profil.subsystem.BussinesSubsystem;
import kroki.profil.utils.VisibleClassUtil;


/**
 * Export the project to an XML file
 * @author Kroki Team
 */
public class ExportAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public ExportAction() {
		putValue(NAME, "Export...");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		NamingUtil cc = new NamingUtil();
		Canvas c = KrokiMockupToolApp.getInstance().getTabbedPaneController().getCurrentTabContent();
        VisibleClass visibleClass = c.getVisibleClass();
        //NEW:
        BussinesSubsystem project = (BussinesSubsystem) visibleClass.umlPackage();
        while (true) {
            if (project.nestingPackage() != null) {
                project = (BussinesSubsystem) project.nestingPackage();
            } else {
                break;
            }
        }

        for(int i=0; i<project.ownedElementCount(); i++) {
        	
        	VisibleElement el = project.getOwnedElementAt(i);
        	if(el instanceof BussinesSubsystem) {
        		System.out.println("Eelement " + i + ": " + el.getLabel() + " is a subsystem");
        	}else if(el instanceof VisibleClass) {
        		if(el instanceof StandardPanel) {
        			StandardPanel sp = (StandardPanel)el;
        			StdPanelSettings sps = sp.getStdPanelSettings();
        			
        			
        			VisibleClass vc = (VisibleClass)el;
        			
        			String settingsString = "Settings:\n";
        			String ejbString = "Atributi: \n";
        			String opsString = "Operacije: \n";
        			
        			//-----------------PANEL SETTINGS------------------------------
        			if(sp.isAdd()) {
        				settingsString += "	ADD : TRUE\n";
        			}else {
        				settingsString += "	ADD : FALSE\n";
        			}
        			if(sp.isDelete()) {
        				settingsString += "	DELETE : TRUE\n";
        			}else {
        				settingsString += "	DELETE : FALSE\n";
        			}
        			if(sps.getDefaultViewMode() == ViewMode.INPUT_PANEL_MODE) {
        				settingsString += "	VIEW MODE : PANEL\n";
        			}else if(sps.getDefaultViewMode() == ViewMode.TABLE_VIEW) {
        				settingsString += "	VIEW MODE : TABLE\n";
        			}
        			if(sp.isChangeMode()) {
        				settingsString += "	CHANGE MODE : TRUE\n";
        			}else {
        				settingsString += "	CHANGE MODE : FALSE\n";
        			}
        			if(sp.isDataNavigation()) {
        				settingsString += "	DATA NAVIGATION : TRUE\n";
        			}else {
        				settingsString += "	DATA NAVIGATION : FALSE\n";
        			}
        			
        			
        			//-------------------------CLASS ATTRIBUTES-----------------------------------------------------------------------------
        			for(int j=0; j < VisibleClassUtil.containedProperties(vc).size(); j++) {
        				VisibleProperty vp = VisibleClassUtil.containedProperties(vc).get(j);
        			
        				ejbString += "	" + j + " " + cc.toCamelCase(vp.getLabel(), true) + "(" + vp.getColumnLabel()  +") : " + vp.getComponentType() + "\n";
        			}
        			
        			for(int l=0; l < VisibleClassUtil.containedZooms(vc).size(); l++) {
        				Zoom z = VisibleClassUtil.containedZooms(vc).get(l);
        				ejbString += "	" + l + " " + cc.toCamelCase(z.getLabel(), true) + " : " + z.getTargetPanel().getLabel() + "\n";
        			}
        			
        			//--------------------------OPERATIONS------------------------------------------------
        			for(int k=0; k < VisibleClassUtil.containedOperations(vc).size(); k++) {
        				VisibleOperation vo = VisibleClassUtil.containedOperations(vc).get(k);
        				if(vo instanceof BussinessOperation) {
        					if(vo instanceof Report) {
        						//Report r = (Report)vo;
        						
        						opsString += "	" + k + " " + cc.toCamelCase(vo.getLabel(), true) + " : REPORT\n";
        					}else if (vo instanceof Transaction) {
        						opsString += "	" + k + " " + cc.toCamelCase(vo.getLabel(), true) + " : TRANSACTION\n";
        					}
        				}
        			}
        			
        			System.out.println("Panel " + sp.getLabel().toUpperCase() + "\nKlasa: " + sp.getPersistentClass().name() + "\n" + ejbString + settingsString + opsString);
        		}
        		
        	}
        }
        
	}

}
