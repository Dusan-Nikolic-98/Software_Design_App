package gui.swing.controller.rightActions;

import gui.swing.controller.AbstractClassyAction;
import gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class AddAttributeAction extends AbstractClassyAction {

    public AddAttributeAction(){
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/addStuff.png"));
        putValue(NAME, "add attributes");
        putValue(SHORT_DESCRIPTION, "dodaj atribute klasi");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getPv().startAddAttributeState();
        System.out.println("dodaj atribute");
    }
}
