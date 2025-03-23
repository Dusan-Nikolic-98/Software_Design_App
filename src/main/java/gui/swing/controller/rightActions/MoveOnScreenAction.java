package gui.swing.controller.rightActions;

import gui.swing.controller.AbstractClassyAction;
import gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class MoveOnScreenAction extends AbstractClassyAction {
    public MoveOnScreenAction(){
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/movescreen.png"));
        putValue(NAME, "Move on screen");
        putValue(SHORT_DESCRIPTION, "pomeraj se po ekranu");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getPv().startMoveOnScreenState();
    }
}
