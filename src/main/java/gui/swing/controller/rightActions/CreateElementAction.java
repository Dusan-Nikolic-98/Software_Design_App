package gui.swing.controller.rightActions;

import gui.swing.controller.AbstractClassyAction;
import gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class CreateElementAction extends AbstractClassyAction {
    public CreateElementAction(){
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/create.png"));
        putValue(NAME, "Create");
        putValue(SHORT_DESCRIPTION, "napravi novi c");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getPv().startCreateElementState();
        System.out.println("novi c");

    }
}
