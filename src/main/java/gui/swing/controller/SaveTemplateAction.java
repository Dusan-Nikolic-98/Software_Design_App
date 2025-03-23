package gui.swing.controller;

import core.ApplicationFramework;
import errorPart.enums.EventType;
import gui.swing.view.MainFrame;
import repository.composite.ClassyNode;
import repository.implementation.Diagram;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SaveTemplateAction extends AbstractClassyAction{
    public SaveTemplateAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/save_template.png"));
        putValue(NAME, "Save template");
        putValue(SHORT_DESCRIPTION, "Save diagram template");

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getInstance().getClassyTree().getSelectedNode() != null) {
            ClassyNode cn = MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode();
            if (cn instanceof Diagram) {
                //mozda samo joptionpane da li zelim da ga sacuvam ili jok
                if (JOptionPane.showConfirmDialog(null, "Save: " + cn.getIme() + " as a template?") == JOptionPane.YES_OPTION) {
                    Diagram diag = (Diagram) cn;
                    ApplicationFramework.getInstance().getSerializer().saveDiagram(diag);
                }
            } else {
                //neki nijediag bag
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.DIAGRAM_NOT_SELECTED);
            }
        }else{
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.DIAGRAM_NOT_SELECTED);
        }
    }
}
