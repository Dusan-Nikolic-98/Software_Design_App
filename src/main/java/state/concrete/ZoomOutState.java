package state.concrete;

import gui.swing.view.DiagramView;
import state.State;

import java.awt.*;

public class ZoomOutState implements State {
    @Override
    public void misKliknut(Point p, DiagramView dv) {
        dv.scaleDownAT();
    }

    @Override
    public void misSeDrzi(Point p, DiagramView dv) {

    }

    @Override
    public void misPusten(Point p, DiagramView dv) {

    }
}
