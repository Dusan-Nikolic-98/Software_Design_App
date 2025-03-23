package gui.swing.controller;

import core.ApplicationFramework;
import errorPart.enums.EventType;
import gui.swing.tree.model.ClassyTreeItem;
import gui.swing.view.MainFrame;
import repository.implementation.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class ExportAction extends AbstractClassyAction{

    public ExportAction(){
        putValue(SMALL_ICON, loadIcon("/export.png"));
        putValue(NAME, "Export");
        putValue(SHORT_DESCRIPTION, "Export project as java code");

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        ClassyTreeItem node = (ClassyTreeItem) MainFrame.getInstance().getClassyTree().getSelectedNode();
        if(node != null && (node.getClassyNode() instanceof Project p)) {
            if (JOptionPane.showConfirmDialog(null, "Export project: " +
                    node.getClassyNode().getIme() + "?") == JOptionPane.YES_OPTION) {

                JFileChooser jfc = new JFileChooser();
                if (jfc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                    File file = jfc.getSelectedFile();
                    ExporterF ef = new ExporterF(p, file);
                }
            }
        }else{
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.PROJECT_NOT_SELECTED);
        }
    }
}
