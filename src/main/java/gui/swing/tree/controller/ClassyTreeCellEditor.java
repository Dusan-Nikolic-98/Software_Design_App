package gui.swing.tree.controller;

import core.ApplicationFramework;
import errorPart.enums.EventType;
import gui.swing.tree.model.ClassyTreeItem;
import gui.swing.view.MainFrame;
import observer.Notification;
import observer.NotificationType;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class ClassyTreeCellEditor extends DefaultTreeCellEditor implements ActionListener {
    private Object clickedOn = null;
    private JTextField edit = null;

    public ClassyTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
        super(tree, renderer);
    }

    public Component getTreeCellEditorComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean arg4, int arg5) {
        //super.getTreeCellEditorComponent(arg0,arg1,arg2,arg3,arg4,arg5);
        clickedOn =arg1;
        edit=new JTextField(arg1.toString());
        edit.addActionListener(this);
        return edit;
    }

    public boolean isCellEditable(EventObject arg0) { //mozda ovde moze to
        boolean flag = false;
        if (arg0 instanceof MouseEvent) {
            if (((MouseEvent) arg0).getClickCount() == 3) { //TODO IZMENI I PREBACI LOGIKU U MOUSE LIST
                return true;
            }
        }
        return flag;
    }

    public void actionPerformed(ActionEvent e){

        if (!(clickedOn instanceof ClassyTreeItem))
            return;
        ClassyTreeItem clicked = (ClassyTreeItem) clickedOn;
        if(e.getActionCommand().isEmpty()){
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.NAME_EMPTY);
        }
        clicked.setIme(e.getActionCommand());
        if(MainFrame.getInstance().getPv().getModel() != null) {
            MainFrame.getInstance().getPv().getModel().notifySubscribers(new Notification(MainFrame.getInstance().getPv().getModel(),
                    NotificationType.CHANGE_DIAGRAM_NAME));
            MainFrame.getInstance().getPv().getModel().notifySubscribers(new Notification(MainFrame.getInstance().getPv().getModel(),
                    NotificationType.CHANGE_PROJECT_NAME));
        }

    }

}
