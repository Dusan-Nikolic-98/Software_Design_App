package gui.swing.view;

import gui.swing.controller.MyTPChangeListener;
import gui.swing.controller.rightActions.DiagMouseListener;
import lombok.Getter;
import lombok.Setter;
import observer.ISubscriber;
import observer.Notification;
import observer.NotificationType;
import repository.composite.ClassyNode;
import repository.implementation.Diagram;
import repository.implementation.Package;
import repository.implementation.Project;
import state.State;
import state.StateManager;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
@Getter
@Setter
public class PackageView2 extends JPanel implements ISubscriber {
    private JTabbedPane pane;
    private JLabel nameOfAuthor;
    private JLabel nameOfProject;
    private Package model;
    private JPanel emptyPanel;
    private HashMap<Diagram, DiagramView> listOfDiagrams;
    private MyToolBarRight myToolBarRight;
    private StateManager stateManager;
    private int tren;

    public PackageView2(){
        init();
    }

    private void init(){
        stateManager = new StateManager();
        //crtanje
        this.pane = new JTabbedPane();
        this.model = null;
        this.listOfDiagrams = new HashMap<>();
        this.tren = 0;

        setLayout(new BorderLayout());
        nameOfAuthor = new JLabel("author: ");
        nameOfProject = new JLabel("project: ");
        emptyPanel = new JPanel();
        emptyPanel.setBackground(Color.GREEN);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(nameOfAuthor);
        p.add(nameOfProject);
        add(p, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);

        myToolBarRight = new MyToolBarRight();
        add(myToolBarRight, BorderLayout.EAST);
        MyTPChangeListener mtcl = new MyTPChangeListener();
        pane.addChangeListener(mtcl);
    }


    @Override
    public void update(Notification notification) {

        if(notification.getNotificationType().equals(NotificationType.CHANGE_PARENT_VIEW)){
            repaintTabs();
            changeProjectName();
            String aName = giveMeAuthorName();
            nameOfAuthor.setText("author: " + aName);
        } else if(notification.getNotificationType().equals(NotificationType.ADDED_CHILD)){
            Diagram diag = (Diagram) notification.getObjOfNotification();
            if(!listOfDiagrams.containsKey(diag)){
                DiagramView dv = new DiagramView();
                diag.addSubscriber(dv); //cisto da mu se odmah doda sub
                DiagMouseListener dm = new DiagMouseListener();
                dv.addMouseListener(dm); //TODO I OVDE MORAM DA MU DODAM MOUSE LISTENERA
                dv.addMouseMotionListener(dm);
                dv.setModel(diag); //TODO OVDE SAM MU DODAO MODEL AKO BUDEM MORAO DA GA BRISEM
                dm.setMouseDV(dv); //TODO ovde sam rekao da mouse listener ima instancu dv za koju se aktivira
                listOfDiagrams.put(diag, dv);
            }
            repaintTabs();

        }else if(notification.getNotificationType().equals(NotificationType.REMOVED_CHILD)){
            Diagram diag = (Diagram) notification.getObjOfNotification();
            listOfDiagrams.remove(diag);
            repaintTabs();

        } else if(notification.getNotificationType().equals(NotificationType.REMOVED)){
            Package p =(Package) notification.getObjOfNotification();
            System.out.println("velicina pre: " + listOfDiagrams.size());
            for(ClassyNode cn: p.getChildren()){
                if(cn instanceof Diagram diag){
                    listOfDiagrams.remove(diag);
                }
            }
            System.out.println("velicina posle: " + listOfDiagrams.size());
            removeTabbedPane();
            //i sad ovde da se sve zameni praznim ako se izbrisao i projekat

        }else if(notification.getNotificationType().equals(NotificationType.CHANGE_DIAGRAM_NAME)){
            repaintTabs();
        }else if(notification.getNotificationType().equals(NotificationType.CHANGE_AUTHOR_NAME)){
            //ako je promenjeno ime treba da ripejntuje
            Project p = (Project) notification.getObjOfNotification();
            String newName = p.getImeAutora();
            nameOfAuthor.setText("author: " + newName);
        }else if(notification.getNotificationType().equals(NotificationType.CHANGE_PROJECT_NAME)){
            changeProjectName();
        }else if(notification.getNotificationType().equals(NotificationType.DELETE_PARENT_PROJECT)){
            nameOfAuthor.setText("author: ");
            nameOfProject.setText("project: ");
        }
//        else if(notification.getNotificationType().equals(NotificationType.REMOVED_CHILD_PACKAGE)){
//
//
//        }
    }
    private void changeProjectName(){
        ClassyNode cn = model;
        while(cn instanceof Package)cn = cn.getParent();
        String s = cn.getIme();
        nameOfProject.setText("project: " + s);
    }
    private void repaintTabs(){
        if(model == null)return;
        tren = pane.getSelectedIndex() == -1? tren:pane.getSelectedIndex();
        while(pane.getTabCount() > 0){ //da ih obrise sve
            pane.removeTabAt(0);
        }
        for(ClassyNode cn: model.getChildren()){
            if(cn instanceof Diagram) {
                Diagram d = (Diagram) cn;
                if (!listOfDiagrams.containsKey(d)) {
                    DiagramView dv = new DiagramView();
                    d.addSubscriber(dv);
                    dv.setModel(d); //TODO OVDE SAM MU DODAO MODEL AKO BUDEM MORAO DA GA BRISEM
                    DiagMouseListener dm = new DiagMouseListener();
                    dv.addMouseListener(dm); //TODO MOZDA SAMO OVDE MOGU DA MU DODAM MOUSE LISTENERA
                    dv.addMouseMotionListener(dm);
                    dm.setMouseDV(dv); //TODO ovde sam rekao da mouse listener ima instancu dv za koju se aktivira
                    dv.setPreferredSize(new Dimension(3000,3000));

                    listOfDiagrams.put(d, dv);
                }
                pane.addTab(d.getIme(), listOfDiagrams.get(d));
            }
        }
        if(tren < pane.getTabCount())
            pane.setSelectedIndex(tren);
        else if(pane.getTabCount() != 0)pane.setSelectedIndex(0);
        tren = 0;
    }

    public void promeniRod(ClassyNode novi){
        System.out.println("menja roditelja");
        if(model != null){
            model.removeSubscriber(this);
        }
        Package newParent = (Package) novi;
        model = newParent;
        newParent.addSubscriber(this);
    }
    public void removeTabbedPane(){

        while(pane.getTabCount() > 0){ //da ih obrise sve
            pane.removeTabAt(0);
        }
        //TODO pa da se prebaci na obican JPane
    }
    public String giveMeAuthorName(){
        if(model != null){
            ClassyNode cn = model;
            while(cn instanceof Package){
                cn = cn.getParent();
            }
            Project pr = (Project) cn;
            return pr.getImeAutora();
        }
        return "";
    }

    public DiagramView getCurrentDiagramView() {
        int selectedIndex = pane.getSelectedIndex();
        if (selectedIndex != -1) {
            Component selectedComponent = pane.getComponentAt(selectedIndex);
            if (selectedComponent instanceof DiagramView) {
                return (DiagramView) selectedComponent;
            }
        }
        return null;
    }

    //DELOVI ZA STATE
    public void startCreateElementState(){this.stateManager.setCreateElementState();}
    public void startCreateBondState(){this.stateManager.setCreateBondState();}
    public void startDeleteState(){this.stateManager.setDeleteState();}
    public void startMoveState(){this.stateManager.setMoveState();}
    public void startAddAttributeState(){this.stateManager.setAddAttributeState();}
    public void startMultiselectState(){this.stateManager.setMultiselectState();}
    public void startDuplicateState(){this.stateManager.setDuplicateState();}
    public void startZoomInState(){this.stateManager.setZoomInState();}
    public void startZoomOutState(){this.stateManager.setZoomOutState();}
    public void startMoveOnScreenState(){this.stateManager.setMoveOnScreenState();}

    public void misKliknut(Point p, DiagramView dv){this.stateManager.getCurrState().misKliknut(p, dv);}
    public void misPusten(Point p, DiagramView dv){this.stateManager.getCurrState().misPusten(p, dv);}
    public void misSeDrzi(Point p, DiagramView dv){this.stateManager.getCurrState().misSeDrzi(p, dv);}




}
