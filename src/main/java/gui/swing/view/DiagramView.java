package gui.swing.view;

import commands.CommandManager;
import lombok.Getter;
import lombok.Setter;
import observer.ISubscriber;
import observer.Notification;
import observer.NotificationType;
import painters.ElementPainter;
import painters.connectionPainters.*;
import painters.specP.InterCPainter;
import painters.specP.InterEnumPainter;
import painters.specP.InterInterfacePainter;
import repository.implementation.Diagram;
import repository.implementation.diagramElements.elements.Connection;
import repository.implementation.diagramElements.elements.DiagramElement;
import repository.implementation.diagramElements.elements.Interclass;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;

@Getter
@Setter
public class DiagramView extends JPanel implements ISubscriber {
    public Rectangle selectRec;
    public Diagram diagram;
    private int no;
    public Diagram model;
    private ArrayList<ElementPainter> painters;
    private ArrayList<ElementPainter> selectedPainters;
    private ArrayList<ElementPainter> selectedConnections;
    private AffineTransform at;
    private static final double ZOOM_STEP = 0.1;
    private double currZoom;
    private boolean flagForTransform;
    private double startingT;
    private int scrollX; private int scrollY;
    //todo moj dv treba da ima svoj CommandManager
    public CommandManager commandManager;

    public DiagramView() {
        this.model = null;
        painters = new ArrayList<>();
        selectedPainters = new ArrayList<>();
        selectedConnections = new ArrayList<>();
        selectRec = new Rectangle();
        at = new AffineTransform();
        currZoom = 1.25;
        startingT = 1.0;
        at.scale(currZoom,currZoom);
        flagForTransform = false;

        scrollX = 0; scrollY = 0;
        setBackground(Color.LIGHT_GRAY);
        commandManager = new CommandManager();
    }

    @Override
    public String toString() {
        return diagram.getIme();
    }

    @Override
    public void update(Notification notification) {
        if (notification.getNotificationType().equals(NotificationType.REMOVED_CHILD)) {
            DiagramElement de = (DiagramElement) notification.getObjOfNotification();
            removeElement(de);
        }
        repaint(); //i ovo bi sad po defaultu trebalo da zove paintComponent metodus
    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(!flagForTransform){
            at = AffineTransform.getScaleInstance(g2.getTransform().getScaleX(),g2.getTransform().getScaleY());
            currZoom = g2.getTransform().getScaleX();
            startingT = g2.getTransform().getScaleX();
            flagForTransform = true;
        }

        g2.setTransform(at);
        if (selectRec.x != -1) {
            g2.draw(selectRec);
            changeSelectedPainters();
        }
        for (ElementPainter ep : painters) {
            if (ep.getModel() instanceof Connection){
                ep.draw(g2);
                //da ako ga sadrzi iscrta neki kvadratic oko
                if(selectedConnections.contains(ep)){
                    float[] dashPattern = {5, 5}; // Adjust the values to change the dash pattern
                    BasicStroke dashedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0);
                    g2.setStroke(dashedStroke);
                    g2.setPaint(Color.RED);
                    int[] lin;
                    if(ep instanceof AgregacijaPainter)
                        lin = ((AgregacijaPainter) ep).getCurrPos();
                    else if(ep instanceof GeneralizacijaPainter)
                        lin = ((GeneralizacijaPainter)ep).getCurrPos();
                    else if(ep instanceof KompozicijaPainter)
                        lin = ((KompozicijaPainter) ep).getCurrPos();
                    else
                        lin = ((ZavisnostPainter) ep).getCurrPos();

                    g2.drawLine(lin[0] + 3, lin[1] + 3, lin[2] + 3, lin[3] + 3);
                    g2.drawLine(lin[0] - 3, lin[1] - 3, lin[2] - 3, lin[3] - 3);

                }
            }
        }
        for (ElementPainter ep : painters) {
            if (ep.getModel() instanceof Interclass) {
                ep.draw(g2);
                if (selectedPainters.contains(ep)) {
                    //da ako ga sadrzi iscrta neki kvadratic oko
                    g2.setPaint(Color.BLUE);
                    float[] dashPattern = {5, 5}; // 5da 5ne
                    BasicStroke dashedStroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, dashPattern, 0);
                    g2.setStroke(dashedStroke);
                    Rectangle rec = new Rectangle(((Interclass) ep.getModel()).getPosition().x - 5,
                            ((Interclass) ep.getModel()).getPosition().y - 5, ((Interclass) ep.getModel()).getSize().width + 10,
                            ((Interclass) ep.getModel()).getSize().height + 10);
                    g2.draw(rec);
                }
            }
        }

    }

    public boolean wouldOverlap(Point p, int x, int y) { //trenutno original p i original sirina i visina

        //pomereno cisto da vizuelno ne izlgeda zgusnuto
        Point pocetna = adjustPointForZoom(p);

        Point gl = pocetna;
        Point gd = new Point(pocetna.x+x, pocetna.y);
        Point dl = new Point(pocetna.x, pocetna.y+y);
        Point dd = new Point(pocetna.x+x, pocetna.y+y);


        for (ElementPainter ep : painters) {
            for(int i = 0; i < 6;i++){
                if(ep.elementAt(new Point(gl.x-i, gl.y-i))|| ep.elementAt(new Point(gd.x+i, gd.y-i))||
                ep.elementAt(new Point(dl.x-i, dl.y+i))|| ep.elementAt(new Point(dd.x+i, dd.y+i)))return true;
            }
        }
        return false;
    }

    public boolean containsElement(Point p) {
        for (ElementPainter ep : painters) {
            if (ep.elementAt(p)) return true;
        }
        return false;
    }

    public ElementPainter elementAt(Point p) {
        if (containsElement(p)) {
            for (ElementPainter ep : painters) {
                if (ep.elementAt(p)) return ep;
            }
        }
        return null;
    }

    private void removePainter(ElementPainter ep) {
        if (painters.contains(ep)) { //nek ostane if za slucaj da mora jos nesto da mu se radi
            painters.remove(ep);
        }
    }

    private void removeElement(DiagramElement de){
        //todo sad ovaj removeelem treba da se promeni da samo brise taj jedan painter koji postoji!!
        Iterator<ElementPainter> iterator = painters.iterator();
        while (iterator.hasNext()) {
            ElementPainter ep = iterator.next();
            if ((ep instanceof InterCPainter) || (ep instanceof InterEnumPainter) || (ep instanceof InterInterfacePainter)) {
                if (ep.getModel().equals(de)) {
                    if(selectedPainters.contains(ep))selectedPainters.remove(ep);
                    iterator.remove();
                }
            } else if ((ep instanceof AgregacijaPainter) || (ep instanceof TempConnectionPainter) || (ep instanceof GeneralizacijaPainter) ||
                    (ep instanceof KompozicijaPainter) || (ep instanceof ZavisnostPainter)) {
                if (ep.getModel().equals(de)) {
                    if(selectedConnections.contains(ep))selectedConnections.remove(ep);
                    iterator.remove();
                }
            }
        }
    }

    public void emptySelectedPainters() {
        while (!this.selectedPainters.isEmpty()) {

            this.selectedPainters.removeFirst();
        }
    }

    public void addSelectedPainter(Point p) {
        for (ElementPainter ep : painters) {
            if (ep.elementAt(p)) {
                if (!selectedPainters.contains(ep)) selectedPainters.add(ep);
            }
        }
    }

    public void setSelectRectDim(Point p, Dimension d) {
        this.selectRec.setLocation(p);
        this.selectRec.setSize(d);
        repaint();
    }

    private void changeSelectedPainters() {
        Iterator<ElementPainter> iterator = selectedPainters.iterator();
        while (iterator.hasNext()) {    //prvo da izbacim sve koji ne treba da budu tu vise
            ElementPainter ep = iterator.next();
            if (ep.getModel() instanceof Interclass) {
                Interclass interclass = (Interclass) ep.getModel();
                if (!selectRec.intersects(interclass.getPosition().x, interclass.getPosition().y,
                        interclass.getSize().width, interclass.getSize().height)) {
                    iterator.remove();
                }
            }
        }
        //sad da brisem iz liste sve veze koje nisu selektovane
        Iterator<ElementPainter> iterc = selectedConnections.iterator();
        while(iterc.hasNext()){
            ElementPainter ep = iterc.next();
            if((ep instanceof AgregacijaPainter)&& ep.getModel() instanceof Connection){
                int[] cp = ((AgregacijaPainter) ep).getCurrPos();
                if(!selectRec.intersects(Math.min(cp[0], cp[2]), Math.min(cp[1], cp[3]), Math.abs(cp[0]-cp[2]), Math.abs(cp[1]-cp[3]))){
                    iterc.remove();
                }
            }
            else if((ep instanceof GeneralizacijaPainter)&& ep.getModel() instanceof Connection){
                int[] cp = ((GeneralizacijaPainter) ep).getCurrPos();
                if(!selectRec.intersects(Math.min(cp[0], cp[2]), Math.min(cp[1], cp[3]), Math.abs(cp[0]-cp[2]), Math.abs(cp[1]-cp[3]))){
                    iterc.remove();
                }
            }
            else if((ep instanceof KompozicijaPainter)&& ep.getModel() instanceof Connection){
                int[] cp = ((KompozicijaPainter) ep).getCurrPos();
                if(!selectRec.intersects(Math.min(cp[0], cp[2]), Math.min(cp[1], cp[3]), Math.abs(cp[0]-cp[2]), Math.abs(cp[1]-cp[3]))){
                    iterc.remove();
                }
            }
            else if((ep instanceof ZavisnostPainter)&& ep.getModel() instanceof Connection){
                int[] cp = ((ZavisnostPainter) ep).getCurrPos();
                if(!selectRec.intersects(Math.min(cp[0], cp[2]), Math.min(cp[1], cp[3]), Math.abs(cp[0]-cp[2]), Math.abs(cp[1]-cp[3]))){
                    iterc.remove();
                }
            }
        }
        //pa sad da dodam nove ako treba:
        for(ElementPainter ep: painters){
            if(ep instanceof InterCPainter && ep.getModel() instanceof Interclass){
                Interclass interclass = (Interclass) ep.getModel();
                if(!selectedPainters.contains(ep) && (selectRec.intersects(interclass.getPosition().x, interclass.getPosition().y,
                        interclass.getSize().width, interclass.getSize().height)||
                        (((InterCPainter) ep).getShape()!= null && ep.elementAt(new Point(selectRec.x, selectRec.y))))){
                    selectedPainters.add(ep);
                }
            }
            else if(ep instanceof InterEnumPainter && ep.getModel() instanceof Interclass){
                Interclass interclass = (Interclass) ep.getModel();
                if(!selectedPainters.contains(ep) && (selectRec.intersects(interclass.getPosition().x, interclass.getPosition().y,
                        interclass.getSize().width, interclass.getSize().height)||
                        (((InterEnumPainter) ep).getShape()!= null && ep.elementAt(new Point(selectRec.x, selectRec.y))))){
                    selectedPainters.add(ep);
                }
            }
            else if(ep instanceof InterInterfacePainter && ep.getModel() instanceof Interclass){
                Interclass interclass = (Interclass) ep.getModel();
                if(!selectedPainters.contains(ep) && (selectRec.intersects(interclass.getPosition().x, interclass.getPosition().y,
                        interclass.getSize().width, interclass.getSize().height)||
                        (((InterInterfacePainter) ep).getShape()!= null && ep.elementAt(new Point(selectRec.x, selectRec.y))))){
                    selectedPainters.add(ep);
                }
            }
            else if(ep instanceof AgregacijaPainter && ep.getModel() instanceof Connection){
                //jer treba i konekcije da se dodaju jelte
                int[] cp = ((AgregacijaPainter) ep).getCurrPos();
                if(!selectedConnections.contains(ep) && (selectRec.intersects(Math.min(cp[0], cp[2]), Math.min(cp[1], cp[3]),
                        Math.abs(cp[0]-cp[2]), Math.abs(cp[1]-cp[3])) || ep.elementAt(new Point(selectRec.x, selectRec.y)))){
                    selectedConnections.add(ep);
                }
            }
            else if(ep instanceof GeneralizacijaPainter && ep.getModel() instanceof Connection){
                //jer treba i konekcije da se dodaju jelte
                int[] cp = ((GeneralizacijaPainter) ep).getCurrPos();
                if(!selectedConnections.contains(ep) && (selectRec.intersects(Math.min(cp[0], cp[2]), Math.min(cp[1], cp[3]),
                        Math.abs(cp[0]-cp[2]), Math.abs(cp[1]-cp[3])) || ep.elementAt(new Point(selectRec.x, selectRec.y)))){
                    selectedConnections.add(ep);
                }
            }
            else if(ep instanceof KompozicijaPainter && ep.getModel() instanceof Connection){
                //jer treba i konekcije da se dodaju jelte
                int[] cp = ((KompozicijaPainter) ep).getCurrPos();
                if(!selectedConnections.contains(ep) && (selectRec.intersects(Math.min(cp[0], cp[2]), Math.min(cp[1], cp[3]),
                        Math.abs(cp[0]-cp[2]), Math.abs(cp[1]-cp[3])) || ep.elementAt(new Point(selectRec.x, selectRec.y)))){
                    selectedConnections.add(ep);
                }
            }
            else if(ep instanceof ZavisnostPainter && ep.getModel() instanceof Connection){
                //jer treba i konekcije da se dodaju jelte
                int[] cp = ((ZavisnostPainter) ep).getCurrPos();
                if(!selectedConnections.contains(ep) && (selectRec.intersects(Math.min(cp[0], cp[2]), Math.min(cp[1], cp[3]),
                        Math.abs(cp[0]-cp[2]), Math.abs(cp[1]-cp[3])) || ep.elementAt(new Point(selectRec.x, selectRec.y)))){
                    selectedConnections.add(ep);
                }
            }
            //petlja dovde ide
        }
    }


    public void scaleUpAT(){
        this.currZoom *= (1.0 + ZOOM_STEP);
        updateTransform();
    }
    public void scaleDownAT(){
        this.currZoom /= (1.0 + ZOOM_STEP);
        updateTransform();
    }
    private void updateTransform(){
        at.translate(-scrollX, -scrollY);
        scrollX = 0; scrollY = 0;
        this.at = AffineTransform.getScaleInstance(currZoom, currZoom);
        System.out.println("CURRZOOM: " + currZoom);
        repaint();

    }

    public double[] getScaleAT(){
        double[] ret = new double[2];
        ret[0] = this.at.getScaleX();
        ret[1] = this.at.getScaleY();
        return ret;
    }

    public Point adjustPointForZoom(Point originalPoint) {
        double[] scaleAT = getScaleAT();
        double inverseZoomX = startingT / scaleAT[0];
        double inverseZoomY = startingT / scaleAT[1];

        AffineTransform inverseTransform = new AffineTransform();
        inverseTransform.scale(inverseZoomX, inverseZoomY);

        Point adjustedPoint = new Point();
        inverseTransform.transform(originalPoint, adjustedPoint);
        adjustedPoint.translate(scrollX, scrollY);
        return adjustedPoint;
    }

    public void setOffset(Point p){ //ako ni jedno ni drugo nisu manji od nule da sme da ih pomera
        int provX = scrollX - p.x/2;
        int provY = scrollY - p.y/2;
        if(provX >= 0 && provY >= 0) {
            at.translate(p.x / 2, p.y / 2);
            scrollX -= p.x / 2;
            scrollY -= p.y / 2;
            System.out.println("TRENUTNI SCROLLX I Y: " + scrollX + ", " + scrollY);
            repaint();
        }
    }

}
