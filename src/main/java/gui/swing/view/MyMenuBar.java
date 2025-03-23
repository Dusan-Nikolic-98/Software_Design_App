package gui.swing.view;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JMenuBar {

    public MyMenuBar(){
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        //a u fileM cu dodavati sve akcije kasnije
        fileMenu.add(MainFrame.getInstance().getActionManager().getNewAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getDeleteAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getExitAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getInfoAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getSaveTemplateAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getLoadTemplateAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getExportAction());
        add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        editMenu.add(MainFrame.getInstance().getActionManager().getSaveAsAction());
        editMenu.add(MainFrame.getInstance().getActionManager().getChangeAuthorAction());
        editMenu.add(MainFrame.getInstance().getActionManager().getUndoAction());
        editMenu.add(MainFrame.getInstance().getActionManager().getRedoAction());
        editMenu.add(MainFrame.getInstance().getActionManager().getSaveAction());
        editMenu.add(MainFrame.getInstance().getActionManager().getLoadAction());
        add(editMenu);
    }

}
