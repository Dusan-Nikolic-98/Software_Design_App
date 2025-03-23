package gui.swing.controller;

import core.ApplicationFramework;
import gui.swing.view.MainFrame;
import repository.implementation.Project;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

public class LoadAction extends AbstractClassyAction{

    public LoadAction(){
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/load.png"));
        putValue(NAME, "Load File");
        putValue(SHORT_DESCRIPTION, "Load a file");

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text and JSON Files", "txt", "json");
        jfc.setFileFilter(filter);

        if(jfc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
            try{
                File file = jfc.getSelectedFile();
                //dakle prvo pravim taj projekat
                Project p = ApplicationFramework.getInstance().getSerializer().loadProject(file);
                //pa ga doradjujem u drvetu
                MainFrame.getInstance().getClassyTree().loadProject(p);
            }catch (Exception e2){
                e2.printStackTrace();
            }

        }
    }
}
