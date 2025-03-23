package commands.implementation;

import commands.AbstractCommand;
import painters.ElementPainter;
import repository.implementation.diagramElements.elements.Interclass;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoveCommand extends AbstractCommand {

    private ArrayList<Point> startingP = new ArrayList<>();
    private ArrayList<Point> endP = new ArrayList<>();
    private ArrayList<ElementPainter> eps = new ArrayList<>();
    private ArrayList<Point> secondEnd = new ArrayList<>();
    private boolean flag = false;

    public MoveCommand(ArrayList<ElementPainter> epp,ArrayList<Point> startPo, ArrayList<Point> endPo){
        for(int i = 0; i < startPo.size();i++)
            this.startingP.add(new Point(startPo.get(i).x, startPo.get(i).y));

        for(int i = 0; i < endPo.size();i++) {
            this.endP.add(new Point(endPo.get(i).x, endPo.get(i).y));
        }

        for(int i = 0; i < epp.size();i++)
            this.eps.add(epp.get(i));

    }


    @Override
    public void doCommand() {
        if(!flag) {
            for (int i = 0; i < eps.size(); i++) {
                if (eps.get(i).getModel() instanceof Interclass) {
                    ((Interclass) eps.get(i).getModel()).setPosition(endP.get(i));
                }
            }


            for (Point point : endP) {
                this.secondEnd.add(new Point(point.x, point.y));
            }
            flag = true;
        }else{
            for (int i = 0; i < eps.size(); i++) {
                if (eps.get(i).getModel() instanceof Interclass) {
                    ((Interclass) eps.get(i).getModel()).setPosition(secondEnd.get(i));
                }
            }
        }

    }

    @Override
    public void undoCommand() {

        for(int i = 0; i < eps.size();i++){
            if(eps.get(i).getModel() instanceof Interclass){
                ((Interclass) eps.get(i).getModel()).setPosition(startingP.get(i));

            }
        }
    }

}
