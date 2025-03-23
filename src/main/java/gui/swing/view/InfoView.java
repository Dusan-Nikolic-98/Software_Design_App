package gui.swing.view;

import javax.swing.*;
import java.awt.*;

public class InfoView extends JDialog {

    public InfoView(JFrame parent, Icon i1, Icon i2){
        super(parent, "nas info", true);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenw = screenSize.width;
        int screenh = screenSize.height;
        setSize(screenw/3,screenh/3);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel pan1 = new JPanel();
        pan1.setPreferredSize(new Dimension(super.getWidth()/2,super.getHeight()/4));
        pan1.setLayout(new BoxLayout(pan1, BoxLayout.PAGE_AXIS));

        JLabel ime1 = new JLabel("Dusan");
        JLabel brind1 = new JLabel("RN 42/21");

        JLabel pic1 = new JLabel(i1);

        ime1.setAlignmentX(CENTER_ALIGNMENT);
        brind1.setAlignmentX(CENTER_ALIGNMENT);
        pic1.setAlignmentX(CENTER_ALIGNMENT);

        pan1.add(ime1);
        pan1.add(brind1);
        pan1.add(pic1);

        JPanel pan2 = new JPanel();

        pan2.setPreferredSize(new Dimension(super.getWidth()/2,super.getHeight()/4));
        pan2.setBackground(Color.lightGray);
        pan2.setLayout(new BoxLayout(pan2, BoxLayout.PAGE_AXIS));

        JLabel ime2 = new JLabel("Luka");
        JLabel brind2 = new JLabel("RN 34/23");

        JLabel pic2 = new JLabel(i2);

        ime2.setAlignmentX(CENTER_ALIGNMENT);
        brind2.setAlignmentX(CENTER_ALIGNMENT);
        pic2.setAlignmentX(CENTER_ALIGNMENT);

        pan2.add(ime2);
        pan2.add(brind2);
        pan2.add(pic2);

        this.add(pan1, BorderLayout.WEST);
        this.add(pan2, BorderLayout.CENTER);

        setVisible(true);

    }
}
