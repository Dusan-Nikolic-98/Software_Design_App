package gui.swing.tree.controller;

import gui.swing.tree.model.ClassyTreeItem;
import gui.swing.view.MainFrame;
import observer.Notification;
import observer.NotificationType;
import repository.implementation.Diagram;
import repository.implementation.Package;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener implements MouseListener { // TODO samo ovo menjati da ne bi pokvarili program
    @Override
    public void mouseClicked(MouseEvent e) {

            if(e.getClickCount() == 2){
            ClassyTreeItem selected = (ClassyTreeItem) MainFrame.getInstance().getClassyTree().getSelectedNode();
            if(selected.getClassyNode() instanceof Package){
                MainFrame.getInstance().getPv().promeniRod(selected.getClassyNode());
                ((Package) selected.getClassyNode()).notifySubscribers(new Notification(selected.getClassyNode(),NotificationType.CHANGE_PARENT_VIEW));
                if(MainFrame.getInstance().getPv().getCurrentDiagramView() != null &&
                MainFrame.getInstance().getPv().getCurrentDiagramView().getCommandManager() != null)
                    MainFrame.getInstance().getPv().getCurrentDiagramView().getCommandManager().setFlags();
                else{ //da ako nema nista setuje oba na false
                    MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(false);
                    MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(false);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
