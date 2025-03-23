package gui.swing.controller.rightActions;

import gui.swing.controller.AbstractClassyAction;
import gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class DuplicateAction extends AbstractClassyAction {
    public DuplicateAction(){
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/duplicate.png"));
        putValue(NAME, "Duplicate");
        putValue(SHORT_DESCRIPTION, "dupliraj element");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getPv().startDuplicateState();
    }
}
