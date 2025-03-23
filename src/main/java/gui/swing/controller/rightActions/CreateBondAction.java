package gui.swing.controller.rightActions;

import gui.swing.controller.AbstractClassyAction;
import gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class CreateBondAction extends AbstractClassyAction {

    public CreateBondAction(){
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/connection.png"));
        putValue(NAME, "CreateBond");
        putValue(SHORT_DESCRIPTION, "napravi vezu");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getPv().startCreateBondState();
        System.out.println("pravljenje veze");
    }
}
