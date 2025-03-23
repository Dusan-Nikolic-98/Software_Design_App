package state.concrete;

import gui.swing.view.DiagramView;
import repository.implementation.diagramElements.elements.Interclass;
import state.State;

import java.awt.*;

public class MultiselectState implements State {
    private int x1 = -1;
    private int y1 = -1;
    private int x2 = -1;
    private int y2 = -1;
    @Override
    public void misKliknut(Point p, DiagramView dv) {
        Point adjustedP = dv.adjustPointForZoom(p);
        x1 = adjustedP.x; x2 = adjustedP.x;
        y1 = adjustedP.y; y2 = adjustedP.y;
//        x1 = p.x; x2 = p.x;
//        y1 = p.y; y2 = p.y;
        dv.setSelectRectDim(new Point(Math.min(x1,x2),Math.min(y1,y2)),new Dimension(Math.abs(x2-x1), Math.abs(y2-y1)));

    }

    @Override
    public void misSeDrzi(Point p, DiagramView dv) {
        Point adjustedP = dv.adjustPointForZoom(p);
        x2 = adjustedP.x; y2 = adjustedP.y;

        //x2 = p.x; y2 = p.y;
        dv.setSelectRectDim(new Point(Math.min(x1,x2),Math.min(y1,y2)),new Dimension(Math.abs(x2-x1), Math.abs(y2-y1)));

    }

    @Override
    public void misPusten(Point p, DiagramView dv) {
        dv.setSelectRectDim(new Point(-1,-1), new Dimension(0,0));
    }
}
