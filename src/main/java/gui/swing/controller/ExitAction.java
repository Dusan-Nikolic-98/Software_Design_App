package gui.swing.controller;

import java.awt.event.ActionEvent;

public class ExitAction extends AbstractClassyAction{
    public ExitAction(){
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/poz.png"));
        putValue(NAME, "Exit");
        putValue(SHORT_DESCRIPTION, "Exit program");

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }

}
