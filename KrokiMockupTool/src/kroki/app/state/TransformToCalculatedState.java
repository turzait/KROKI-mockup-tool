package kroki.app.state;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.List;

import kroki.app.KrokiMockupToolApp;
import kroki.app.controller.TabbedPaneController;
import kroki.app.utils.CursorResource;
import kroki.app.view.Canvas;
import kroki.profil.ComponentType;
import kroki.profil.group.ElementsGroup;
import kroki.profil.panel.VisibleClass;
import kroki.profil.property.Calculated;
import kroki.profil.property.VisibleProperty;
import kroki.profil.utils.ElementsGroupUtil;
import kroki.profil.utils.UIPropertyUtil;
import kroki.profil.utils.VisibleClassUtil;

/**
 * Class represents a state which allows transformation
 * of fields into calculated fields
 * @author Vladan Marsenić (vladan.marsenic@gmail.com)
 */
public class TransformToCalculatedState extends State {

    private Image addEnabledIcon = CursorResource.getCursorResource("action.transformToCalculated.smallImage");
    private Image addDisabledIcon = CursorResource.getCursorResource("action.denied.smallImage");

    public TransformToCalculatedState(Context context) {
        super(context, "app.state.calculated");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        TabbedPaneController tabbedPaneController = KrokiMockupToolApp.getInstance().getTabbedPaneController();
        Canvas c = tabbedPaneController.getCurrentTabContent();
        VisibleClass visibleClass = c.getVisibleClass();

        List<VisibleProperty> visiblePropertyList = VisibleClassUtil.containedProperties(visibleClass);
        boolean flag = false;
        for (int i = 0; i < visiblePropertyList.size(); i++) {
            VisibleProperty visibleProperty = visiblePropertyList.get(i);
            if (!(visibleProperty instanceof Calculated)) {
                if (visibleProperty.getComponent().contains(e.getPoint())) {
                    if (visibleProperty.getComponentType() == ComponentType.TEXT_FIELD
                            || visibleProperty.getComponentType() == ComponentType.TEXT_AREA
                            || visibleProperty.getComponentType() == ComponentType.RADIO_BUTTON
                            || visibleProperty.getComponentType() == ComponentType.CHECK_BOX) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        if (flag) {
            tabbedPaneController.changeCursorImage(addEnabledIcon);
        } else {
            tabbedPaneController.changeCursorImage(addDisabledIcon);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        TabbedPaneController tabbedPaneController = KrokiMockupToolApp.getInstance().getTabbedPaneController();
        Canvas c = tabbedPaneController.getCurrentTabContent();
        VisibleClass visibleClass = c.getVisibleClass();

        if (e.getButton() == MouseEvent.BUTTON3) {
            tabbedPaneController.changeCursorImage(null);
            context.goNext(SELECT_STATE);
            return;
        }

        List<VisibleProperty> visiblePropertyList = VisibleClassUtil.containedProperties(visibleClass);
        int index = -1;
        VisibleProperty visibleProperty = null;
        for (int i = 0; i < visiblePropertyList.size(); i++) {
            visibleProperty = visiblePropertyList.get(i);
            if (!(visibleProperty instanceof Calculated)) {
                if (visibleProperty.getComponent().contains(e.getPoint())) {
                    if (visibleProperty.getComponentType() == ComponentType.TEXT_FIELD
                            || visibleProperty.getComponentType() == ComponentType.TEXT_AREA
                            || visibleProperty.getComponentType() == ComponentType.RADIO_BUTTON
                            || visibleProperty.getComponentType() == ComponentType.CHECK_BOX) {
                        index = i;
                        break;
                    }
                }
            }
        }

        //OVO MORA BITI UNDOABLE I REDO-ABLE
        if (index != -1) {
            ElementsGroup elg = VisibleClassUtil.getElementsGroupAtPoint(visibleClass, e.getPoint());
            if (elg != null) {
                int position = ElementsGroupUtil.indexOf(elg, visibleProperty);
                ElementsGroupUtil.removeVisibleElement(elg, visibleProperty);
                UIPropertyUtil.removeVisibleElement(visibleClass, visibleProperty);

                Calculated calculated = new Calculated(visibleProperty);
                ElementsGroupUtil.addVisibleElement(elg, position, calculated);
                UIPropertyUtil.addVisibleElement(visibleClass, calculated);

                elg.update();
                visibleClass.update();
                tabbedPaneController.changeCursorImage(null);

                c.getSelectionModel().clearSelection();
                c.getSelectionModel().addToSelection(calculated);
                c.repaint();
                context.goNext(SELECT_STATE);
            }
        }
    }
}
