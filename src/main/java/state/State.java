package state;

import gui.swing.view.DiagramView;

import java.awt.*;

public interface State {
    //TODO ovde ce biti sve fje koje sva stanja imaju
    void misKliknut(Point p, DiagramView dv);
    void misSeDrzi(Point p, DiagramView dv);
    void misPusten(Point p, DiagramView dv);
}
