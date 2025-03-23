package state.concrete;

import commands.implementation.MoveCommand;
import gui.swing.view.DiagramView;
import painters.ElementPainter;
import repository.implementation.diagramElements.elements.Interclass;
import state.State;

import java.awt.*;
import java.util.ArrayList;

public class MoveState implements State {

    //private Interclass ic = null;
    private Point startP = new Point(-1, -1);
    //private ElementPainter currEP = null;
    private ArrayList<ElementPainter> eps;
    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<Point> startPForCom;
    @Override
    public void misKliknut(Point p, DiagramView dv) {
        Point padjustedP = dv.adjustPointForZoom(p);
        //adjustedP

        if(dv.containsElement(padjustedP) && (dv.elementAt(padjustedP).getModel() instanceof Interclass) && !dv.getSelectedPainters().isEmpty() &&
        dv.getSelectedPainters().contains(dv.elementAt(padjustedP))){ //da se osiguram
            startP = ((Interclass) dv.elementAt(padjustedP).getModel()).getPosition();
            //currEP = dv.elementAt(p);
            eps = dv.getSelectedPainters();
            while(!points.isEmpty())points.removeFirst();
            startPForCom = new ArrayList<>();
            for(int i = 0; i < eps.size();i++){
                if(eps.get(i).getModel() instanceof Interclass) {
                    points.add(((Interclass) eps.get(i).getModel()).getPosition());
                    //startPForCom.add(((Interclass) eps.get(i).getModel()).getPosition());
                    startPForCom.add(new Point(((Interclass) eps.get(i).getModel()).getPosition().x,
                            ((Interclass) eps.get(i).getModel()).getPosition().y));
                }
            }

        }
    }

    @Override
    public void misSeDrzi(Point p, DiagramView dv) {
        if(startP.x != -1 && startP.y != -1 && !eps.isEmpty()){ //aka ako se kliknulo na nesto legit
            Point padjustedP = dv.adjustPointForZoom(p);
            //adjustedP
            for(int i = 0; i < eps.size();i++){
                if(eps.get(i).getModel() instanceof Interclass){
                    ((Interclass) eps.get(i).getModel()).setPosition(new
                            Point(points.get(i).x + padjustedP.x - startP.x, points.get(i).y + padjustedP.y - startP.y));
                }
            }
        }
    }

    @Override
    public void misPusten(Point p, DiagramView dv) {
        //za komandu:
        ArrayList<Point> endPForCom;
        if(startP.x != -1 && startP.y != -1 && !eps.isEmpty()){ //aka ako se kliknulo na nesto legit
            Point padjustedP = dv.adjustPointForZoom(p);
            //adjustedP
            //todo moguce da ovde to radim?
            endPForCom = new ArrayList<>();
            for(int i = 0; i < eps.size();i++){
                if(eps.get(i).getModel() instanceof Interclass){
                    //((Interclass) eps.get(i).getModel()).setPosition(new Point(points.get(i).x + padjustedP.x - startP.x, points.get(i).y + padjustedP.y - startP.y));
                    endPForCom.add(new Point(points.get(i).x + padjustedP.x - startP.x, points.get(i).y + padjustedP.y - startP.y));
                }
            }

            MoveCommand mc = new MoveCommand(eps, startPForCom, endPForCom);
            dv.getCommandManager().addCommand(mc);
            //            for(int i = 0; i < eps.size();i++){
//                if(eps.get(i).getModel() instanceof Interclass){
//                    ((Interclass) eps.get(i).getModel()).setPosition(new
//                            Point(points.get(i).x + padjustedP.x - startP.x, points.get(i).y + padjustedP.y - startP.y));
//                }
//            }

        }
        startPForCom = null;
        startP.x = -1;
        startP.y = -1;
    }
}
