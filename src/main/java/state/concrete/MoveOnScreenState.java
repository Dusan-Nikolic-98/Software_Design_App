package state.concrete;

import gui.swing.view.DiagramView;
import state.State;

import java.awt.*;

public class MoveOnScreenState implements State {
    private Point startP = new Point(-1,-1);

    @Override
    public void misKliknut(Point p, DiagramView dv) {
        startP = dv.adjustPointForZoom(p);

    }

    @Override
    public void misSeDrzi(Point p, DiagramView dv) {
        Point endP = dv.adjustPointForZoom(p);
        dv.setOffset(new Point(endP.x-startP.x, endP.y-startP.y));
        startP.x = endP.x; startP.y = endP.y; //da se i azuriraju te stvari
    }

    @Override
    public void misPusten(Point p, DiagramView dv) {

    }
}
