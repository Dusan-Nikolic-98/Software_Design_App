package state.concrete;

import gui.swing.controller.rightActions.popupsForStates.ElementPopupBoth;
import gui.swing.controller.rightActions.popupsForStates.ElementPopupForEnum;
import gui.swing.controller.rightActions.popupsForStates.ElementPopupForInterface;
import gui.swing.view.DiagramView;
import painters.ElementPainter;
import repository.implementation.Diagram;
import repository.implementation.diagramElements.elements.Interclass;
import repository.implementation.diagramElements.elements.interImplementation.MyClass;
import repository.implementation.diagramElements.elements.interImplementation.MyEnum;
import repository.implementation.diagramElements.elements.interImplementation.MyInterface;
import state.State;

import java.awt.*;

public class AddAttributeState implements State {
    @Override
    public void misKliknut(Point p, DiagramView dv) {
        //dodat deo za adj:
        Point padjustedP = dv.adjustPointForZoom(p);
        //adjustedP
        if(dv.containsElement(padjustedP)){
            Diagram d = dv.getModel();
            ElementPainter ep = dv.elementAt(padjustedP);
            if(ep.getModel() instanceof Interclass){
                //da sacuvam sve metode i sve atribute u listu stringova:
                if(ep.getModel() instanceof MyClass)
                    new ElementPopupBoth((Interclass) ep.getModel(), ((Interclass) ep.getModel()).getContents(), dv);
                else if(ep.getModel() instanceof MyInterface)
                    new ElementPopupForInterface((Interclass) ep.getModel(), ((Interclass) ep.getModel()).getContents(),dv);
                else if(ep.getModel() instanceof MyEnum)
                    new ElementPopupForEnum((Interclass) ep.getModel(), ((Interclass) ep.getModel()).getContents(),dv);

            }
        }
    }

    @Override
    public void misSeDrzi(Point p, DiagramView dv) {

    }

    @Override
    public void misPusten(Point p, DiagramView dv) {

    }
}
