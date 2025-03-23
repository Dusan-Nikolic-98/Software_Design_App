package gui.swing;

import core.Gui;
import gui.swing.view.MainFrame;

public class SwingGui implements Gui {
    private MainFrame mainFrame;
    @Override
    public void start() {
        mainFrame = MainFrame.getInstance();
        mainFrame.setVisible(true);
    }
}
