package gui.swing.controller.rightActions;

import gui.swing.view.DiagramView;
import gui.swing.view.MainFrame;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
@Getter
@Setter
public class DiagMouseListener implements MouseListener, MouseMotionListener {

    //pa ce odavde da se prosledjuje onim razlicitim stejtovima (tj samo ce pozivati curr state i mouse to sto treba)
    //i moze da im prosledjuje e.getPoint
    private DiagramView mouseDV;
    public DiagMouseListener(){
        //super();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //MainFrame.getInstance().getPv().getCurrState().misKliknut(e.getPoint());
        MainFrame.getInstance().getPv().misKliknut(e.getPoint(), this.mouseDV);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //MainFrame.getInstance().getPv().getCurrState().misPusten(e.getPoint());
        MainFrame.getInstance().getPv().misPusten(e.getPoint(), this.mouseDV);
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        //MainFrame.getInstance().getPv().getCurrState().misSeDrzi(e.getPoint());
        MainFrame.getInstance().getPv().misSeDrzi(e.getPoint(), this.mouseDV);
    }





    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
