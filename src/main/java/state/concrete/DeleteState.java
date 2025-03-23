package state.concrete;

import commands.implementation.DeleteCommand;
import gui.swing.view.DiagramView;
import gui.swing.view.MainFrame;
import painters.ElementPainter;
import repository.implementation.Diagram;
import repository.implementation.diagramElements.elements.Connection;
import repository.implementation.diagramElements.elements.Interclass;
import state.State;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DeleteState implements State {

    @Override
    public void misKliknut(Point p, DiagramView dv) {

    }

    @Override
    public void misSeDrzi(Point p, DiagramView dv) {

    }

    @Override
    public void misPusten(Point p, DiagramView dv) {
        Point padjustedP = dv.adjustPointForZoom(p);
        //adjustedP

        if(dv.containsElement(padjustedP)){
            //mejbi ovde da bude provera odmah da li je element jedan od selektovanih:
            if(((dv.elementAt(padjustedP).getModel() instanceof Interclass) && dv.getSelectedPainters().contains(dv.elementAt(padjustedP)))||
            (dv.elementAt(padjustedP).getModel() instanceof Connection) && dv.getSelectedConnections().contains(dv.elementAt(padjustedP))){
                ElementPainter ep2 = dv.elementAt(padjustedP);
                String name2 = ep2.getModel().getName() != null? ep2.getModel().getName(): "veza";
                int ans2 = JOptionPane.showConfirmDialog(null, "Do you want to delete all elements " +
                        "that are selected(with the rectangles around them)?");
                if(ans2 == JOptionPane.YES_OPTION){

                    DeleteCommand dc = new DeleteCommand(dv, dv.getSelectedPainters(), dv.getSelectedConnections());
                    dv.getCommandManager().addCommand(dc);

                }else{
                    //pitaj me da li zelim da obrisem samo taj jedan na koji sam kliknuo
                    int ans3 = JOptionPane.showConfirmDialog(null, "Then do you just want to " +
                            "delete the element you clicked on, " + name2 + "?");
                    if(ans3 == JOptionPane.YES_OPTION){
                        //i sad posle svih provera konacno da se pozz s njim
                        //prvo sve veze koje su potencijalno postojale
                        //todo ovde
                        DeleteCommand dc = new DeleteCommand(dv, ep2.getModel());
                        dv.getCommandManager().addCommand(dc);
                    }

                }
            }else {

                //Diagram d = dv.getModel();
                ElementPainter ep = dv.elementAt(padjustedP);
                String name = ep.getModel().getName() != null ? ep.getModel().getName() : "veza";
                int ans = JOptionPane.showConfirmDialog(null, "Do you want to delete element: "
                        + name + "?");
                if (ans == JOptionPane.YES_OPTION) {
                    //i sad posle svih provera konacno da se pozz s njim
                    //prvo sve veze koje su potencijalno postojale
                    //todo ovde
                    DeleteCommand dc = new DeleteCommand(dv, ep.getModel());
                    dv.getCommandManager().addCommand(dc);

                }
            }
            //dovde
        }

    }
}
