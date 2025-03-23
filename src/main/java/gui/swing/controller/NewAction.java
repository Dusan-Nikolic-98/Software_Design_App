package gui.swing.controller;

import gui.swing.tree.model.ClassyTreeItem;
import gui.swing.view.MainFrame;
import observer.Notification;
import observer.NotificationType;
import repository.implementation.Diagram;
import repository.implementation.Package;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewAction extends AbstractClassyAction{
    public NewAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/plus.png"));
        putValue(NAME, "New");
        putValue(SHORT_DESCRIPTION, "Create a new object");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selected = (ClassyTreeItem) MainFrame.getInstance().getClassyTree().getSelectedNode();
        if(selected != null && !(selected.getClassyNode() instanceof Diagram)) {//dakle ovde da se pojavi popap da li package ili dijagram
            // && !(selected.getClassyNode() instanceof Diagram)
            if (selected.getClassyNode() instanceof Package) {

                String[] options = {"Package", "Diagram", "Cancel"};
                int ans = JOptionPane.showOptionDialog(null, "choose an option", "Do you want a p or an o?",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                if (ans == 0) { //izabran package
                    MainFrame.getInstance().getClassyTree().addChild(selected, 0);
                } else if (ans == 1) {
                    MainFrame.getInstance().getClassyTree().addChild(selected, 1);
                }
            } else
                MainFrame.getInstance().getClassyTree().addChild(selected, -1);
        }
    }
}
