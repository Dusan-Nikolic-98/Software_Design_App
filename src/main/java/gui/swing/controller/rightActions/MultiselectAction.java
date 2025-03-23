package gui.swing.controller.rightActions;

import gui.swing.controller.AbstractClassyAction;
import gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class MultiselectAction extends AbstractClassyAction {

    public MultiselectAction(){
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/multiselect.png"));
        putValue(NAME, "Multiselect");
        putValue(SHORT_DESCRIPTION, "selektuj jedan ili vise objekata");

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getPv().startMultiselectState();
    }
}
