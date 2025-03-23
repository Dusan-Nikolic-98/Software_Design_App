package gui.swing.controller;

import gui.swing.view.MainFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MyTPChangeListener implements ChangeListener {
    @Override
    public void stateChanged(ChangeEvent e) {
        SwingUtilities.invokeLater(()-> {

            int si = ((JTabbedPane) e.getSource()).getSelectedIndex();
            if(si > -1){ //da ne bude kad je -1 xD
                MainFrame.getInstance().getPv().getCurrentDiagramView().getModel().setIme(MainFrame.getInstance().getPv().getCurrentDiagramView().getModel().getIme());
                MainFrame.getInstance().getPv().getCurrentDiagramView().getCommandManager().setFlags();
            }
        });
    }
}
