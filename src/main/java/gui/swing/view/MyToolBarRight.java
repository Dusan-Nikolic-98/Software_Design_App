package gui.swing.view;

import javax.swing.*;

public class MyToolBarRight extends JToolBar {
    public MyToolBarRight(){
        super(VERTICAL);
        setFloatable(true);
        add(MainFrame.getInstance().getActionManager().getCreateElementAction());
        add(MainFrame.getInstance().getActionManager().getDeleteElementAction());
        add(MainFrame.getInstance().getActionManager().getCreateBondAction());
        add(MainFrame.getInstance().getActionManager().getMoveElementAction());
        add(MainFrame.getInstance().getActionManager().getAddAttributeAction());
        add(MainFrame.getInstance().getActionManager().getMultiselectAction());
        add(MainFrame.getInstance().getActionManager().getDuplicateAction());
        add(MainFrame.getInstance().getActionManager().getZoomInAction());
        add(MainFrame.getInstance().getActionManager().getZoomOutAction());
        add(MainFrame.getInstance().getActionManager().getMoveOnScreenAction());
    }
}
