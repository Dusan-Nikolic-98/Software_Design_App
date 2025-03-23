package gui.swing.controller;

import core.ApplicationFramework;
import errorPart.enums.EventType;
import gui.swing.tree.model.ClassyTreeItem;
import gui.swing.view.MainFrame;
import repository.implementation.Diagram;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoadTemplateAction extends AbstractClassyAction{

    public LoadTemplateAction(){
        putValue(SMALL_ICON, loadIcon("/diag_template.png"));
        putValue(NAME, "Load template");
        putValue(SHORT_DESCRIPTION, "Load diagram template");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selected = (ClassyTreeItem) MainFrame.getInstance().getClassyTree().getSelectedNode();
        if(selected != null && (selected.getClassyNode() instanceof Diagram)){
            Path dir = Paths.get("diagTemplates");
            JFileChooser jfc = new JFileChooser(dir.toString());
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text and JSON Files", "txt", "json");
            jfc.setFileFilter(filter);

            if(jfc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
                File file = jfc.getSelectedFile();
                Diagram diag = (Diagram) selected.getClassyNode();
                //napravim diag
                //mozda da mu se prosledi i selektovani pa da se on i vrati kad mu se dodaju sve stvari koje treba
                Diagram d = ApplicationFramework.getInstance().getSerializer().loadDiagram(file, diag);
                MainFrame.getInstance().getClassyTree().loadDiagram(d);

            }
        }else{
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.DIAGRAM_NOT_SELECTED);
        }

    }
}
