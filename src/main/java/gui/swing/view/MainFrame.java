package gui.swing.view;

import core.ApplicationFramework;
import errorPart.msgGen.Message;
import gui.swing.controller.ActionManager;
import gui.swing.tree.ClassyTree;
import gui.swing.tree.ClassyTreeImplementation;
import lombok.Getter;
import lombok.Setter;
import observer.ISubscriber;
import observer.Notification;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class MainFrame extends JFrame implements ISubscriber{
    private MainFrame(){

    }
    private static MainFrame instance = null;
    private ActionManager actionManager;
    private MyMenuBar myMenuBar;
    private MyToolBar myToolBar;
    private ClassyTree classyTree;
    private PackageView2 pv;

    private void initialize(){

        actionManager = new ActionManager();
        classyTree =new ClassyTreeImplementation();
        ApplicationFramework.getInstance().getMessageGenerator().addSubscriber(this); //TODO ako sme
        initGUI();
    }
    private void initGUI(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenH = screenSize.height;
        int screenW = screenSize.width;
        setSize(screenW*2/3,screenH*2/3);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ClassyCrafT");

        myMenuBar = new MyMenuBar();
        setJMenuBar(myMenuBar);


        myToolBar = new MyToolBar();
        add(myToolBar, BorderLayout.NORTH);

        //add jtree
        JTree projectExplorer = classyTree.generateTree(ApplicationFramework.getInstance().getClassyRepository().getProjectExplorer());

        //JPanel deskt = new JPanel();
        //deskt.setBackground(Color.WHITE);
        //TODO PACKAGE VIEW DEO
        pv = new PackageView2();
        pv.setPreferredSize(new Dimension(500,500));

        JScrollPane left = new JScrollPane(projectExplorer);
        left.setBackground(Color.LIGHT_GRAY);
        left.setMinimumSize(new Dimension(200, 200));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, pv);
        //dodaju se stvari sa get contentpane.ad
        getContentPane().add(split, BorderLayout.CENTER);
        split.setDividerLocation(200);
        split.setOneTouchExpandable(true);

    }

    public static MainFrame getInstance(){
        if(instance == null){
            instance = new MainFrame();
            instance.initialize();
        }
        return instance;
    }


    @Override
    public void update(Notification notification) {
        if(notification.toString().equals("INFO") ||
                notification.toString().equals("CANNOT_ADD_CHILD_TO_LEAF")||
                notification.toString().equals("CANNOT_REMOVE_ROOT")||
                notification.toString().equals("NAME_EMPTY") ||
                notification.toString().equals("PROJECT_NOT_SELECTED") ||
                notification.toString().equals("DIAGRAM_NOT_SELECTED")){
            //ako je neki od tih bagova da se pojavi popap
            Message msg = (Message)notification.getObjOfNotification();
            JOptionPane.showMessageDialog(this,msg.toString());
        }
    }
}
