package gui.swing.controller.rightActions;

import gui.swing.controller.AbstractClassyAction;
import gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class MoveElementAction extends AbstractClassyAction {

    public MoveElementAction(){
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/moveTo.png"));
        putValue(NAME, "Move");
        putValue(SHORT_DESCRIPTION, "pomeri obj ili ga samo selektuj");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getPv().startMoveState();
    }
}
