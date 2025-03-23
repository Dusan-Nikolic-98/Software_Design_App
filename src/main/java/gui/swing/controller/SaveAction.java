package gui.swing.controller;

import core.ApplicationFramework;
import errorPart.enums.EventType;
import gui.swing.view.MainFrame;
import repository.composite.ClassyNode;
import repository.implementation.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class SaveAction extends AbstractClassyAction{

    public SaveAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/save.png"));
        putValue(NAME, "Save");
        putValue(SHORT_DESCRIPTION, "Save Project");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getInstance().getClassyTree().getSelectedNode() != null) {
            ClassyNode cn = MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode();
            if (cn instanceof Project) {
                //dakle za sad samo da radi save ako je ceo projekat
                JFileChooser jfc = new JFileChooser();
                Project proj = (Project) cn;
                File projectFile;
                if (!proj.isChanged()) return;

                if (proj.getPutanja() == null || proj.getPutanja().isEmpty()) {
                    if (jfc.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                        projectFile = jfc.getSelectedFile();
                        proj.setPutanja(projectFile.getPath() + ".json");
                    } else
                        return;
                }
                ApplicationFramework.getInstance().getSerializer().saveProject(proj);
                //proj.setChanged(false);
            }else{
                ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.PROJECT_NOT_SELECTED);

            }
        }else ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.PROJECT_NOT_SELECTED);
    }
}
