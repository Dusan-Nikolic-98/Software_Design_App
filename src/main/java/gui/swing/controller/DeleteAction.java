package gui.swing.controller;

import core.ApplicationFramework;
import gui.swing.tree.model.ClassyTreeItem;
import gui.swing.view.MainFrame;
import observer.Notification;
import observer.NotificationType;
import repository.implementation.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class DeleteAction extends AbstractClassyAction{
    public DeleteAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/trash.png"));
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete an object");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem node = (ClassyTreeItem) MainFrame.getInstance().getClassyTree().getSelectedNode();
        //todo mozda moze i direkt ovde da se pita pa samo ako kaze da da se poziva
        if(node!= null) {
            int ans = JOptionPane.showConfirmDialog(null, "Do you want to delete node: " +
                    node.getClassyNode().getIme() + "?");
            if (ans == JOptionPane.YES_OPTION) {
                if (ApplicationFramework.getInstance().getClassyRepository().
                        isChildOf(node.getClassyNode(), MainFrame.getInstance().getPv().getModel())) {
                    if (node.getClassyNode() instanceof Project) {
                        MainFrame.getInstance().getPv().getModel().notifySubscribers(new
                                Notification(MainFrame.getInstance().getPv().getModel(), NotificationType.DELETE_PARENT_PROJECT));
                    }
                    MainFrame.getInstance().getPv().getModel().notifySubscribers(new
                            Notification(MainFrame.getInstance().getPv().getModel(), NotificationType.REMOVED));
                    //MainFrame.getInstance().getPv().removeTabbedPane();
                }
                MainFrame.getInstance().getClassyTree().removeChild(node);
            }
        }
    }
}
