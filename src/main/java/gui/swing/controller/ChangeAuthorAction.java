package gui.swing.controller;

import core.ApplicationFramework;
import errorPart.enums.EventType;
import gui.swing.tree.model.ClassyTreeItem;
import gui.swing.view.MainFrame;
import observer.Notification;
import observer.NotificationType;
import repository.implementation.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ChangeAuthorAction extends AbstractClassyAction{

    public ChangeAuthorAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/author.png"));
        putValue(NAME, "Author");
        putValue(SHORT_DESCRIPTION, "Change or delete author");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selected = (ClassyTreeItem) MainFrame.getInstance().getClassyTree().getSelectedNode();
        if(selected != null && selected.getClassyNode() instanceof Project){
            Project proj = (Project) selected.getClassyNode();
            String s = JOptionPane.showInputDialog(null, "Enter new name of author:");
            proj.setImeAutora(s);
            if(ApplicationFramework.getInstance().getClassyRepository().
                    isChildOf(selected.getClassyNode(), MainFrame.getInstance().getPv().getModel())){
                MainFrame.getInstance().getPv().getModel().notifySubscribers(new
                        Notification(selected.getClassyNode(), NotificationType.CHANGE_AUTHOR_NAME));
            }
            System.out.println(s);


        }else{
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.PROJECT_NOT_SELECTED);
        }
    }
}
