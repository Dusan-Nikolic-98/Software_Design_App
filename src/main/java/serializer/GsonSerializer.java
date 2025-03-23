package serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ApplicationFramework;
import core.Serializer;
import gui.swing.controller.rightActions.DiagMouseListener;
import gui.swing.view.DiagramView;
import gui.swing.view.MainFrame;
import painters.connectionPainters.AgregacijaPainter;
import painters.connectionPainters.GeneralizacijaPainter;
import painters.connectionPainters.KompozicijaPainter;
import painters.connectionPainters.ZavisnostPainter;
import painters.specP.InterCPainter;
import painters.specP.InterEnumPainter;
import painters.specP.InterInterfacePainter;
import repository.composite.ClassyNode;
import repository.composite.ClassyNodeComposite;
import repository.implementation.Diagram;
import repository.implementation.Package;
import repository.implementation.Project;
import repository.implementation.diagramElements.elements.Connection;
import repository.implementation.diagramElements.elements.Interclass;
import repository.implementation.diagramElements.elements.connectionImplementation.Agregacija;
import repository.implementation.diagramElements.elements.connectionImplementation.Generalizacija;
import repository.implementation.diagramElements.elements.connectionImplementation.Kompozicija;
import repository.implementation.diagramElements.elements.connectionImplementation.Zavisnost;
import repository.implementation.diagramElements.elements.interImplementation.MyClass;
import repository.implementation.diagramElements.elements.interImplementation.MyEnum;
import repository.implementation.diagramElements.elements.interImplementation.MyInterface;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class GsonSerializer implements Serializer {

    @Override
    public Project loadProject(File file) {

        try{
            ObjectMapper om = new ObjectMapper();
            Project p = om.readValue(file, Project.class);
            //da se projektu doda roditelj
            p.setParent(ApplicationFramework.getInstance().getClassyRepository().getProjectExplorer());

            //odavde vracanje svih potrebnih stvari elementima projekta
            ArrayList<ClassyNode> children = (ArrayList<ClassyNode>) p.getChildren();
            rekV(p, children);

            HashMap<Diagram, DiagramView> listOfDiagrams = MainFrame.getInstance().getPv().getListOfDiagrams();
            rekurzijaDiagElem(p, listOfDiagrams);
            MainFrame.getInstance().getPv().setListOfDiagrams(listOfDiagrams);
            //za konekcije
            dodVezeRek(p, listOfDiagrams);
            MainFrame.getInstance().getPv().setListOfDiagrams(listOfDiagrams);
            return p;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void rekurzijaDiagElem(ClassyNode cn,HashMap<Diagram, DiagramView> listOfDiagrams){
        //da pravim dv i da dodajem subove svuda gde treba
        if(cn instanceof Diagram){
            Diagram diag = (Diagram) cn;
            if(!listOfDiagrams.containsKey(diag)){ //prvo ako pv i dalje nema dv za d
                DiagramView dv = new DiagramView();
                diag.addSubscriber(dv);
                DiagMouseListener dm = new DiagMouseListener();
                dv.addMouseListener(dm);
                dv.addMouseMotionListener(dm);
                dv.setModel(diag);
                dm.setMouseDV(dv);
                listOfDiagrams.put(diag, dv);
            }
        }else if(cn instanceof Interclass){
            //i sad ovde ako je diagElem, to znaci da vec postoji dv u listi svih tako da mogu da se pozivam na njega!
            Diagram par = (Diagram)cn.getParent();
            DiagramView dv = listOfDiagrams.get(par);
            Interclass de = (Interclass) cn;
            dodavanjeDiagElem(de,dv);
        }
        //i bilo bi ono.... lepo da ima i rekurzije u fji koja se zove rekurzija :   )
        if(cn instanceof ClassyNodeComposite){
            for(ClassyNode child: ((ClassyNodeComposite) cn).getChildren()){
                rekurzijaDiagElem(child, listOfDiagrams);
            }
        }

    }

    private void dodavanjeDiagElem(Interclass de,DiagramView dv){
        if(de instanceof MyClass){
            MyClass mc = (MyClass) de;
            InterCPainter icpainter = new InterCPainter(mc);
            dv.getPainters().add(icpainter);
            mc.addSubscriber(dv);
            dv.getModel().addChild(mc);
        }else if(de instanceof MyEnum){
            MyEnum mc = (MyEnum) de;
            InterEnumPainter icpainter = new InterEnumPainter(mc);
            dv.getPainters().add(icpainter);
            mc.addSubscriber(dv);
            dv.getModel().addChild(mc);
        }else if(de instanceof MyInterface){
            MyInterface mc = (MyInterface) de;
            InterInterfacePainter icpainter = new InterInterfacePainter(mc);
            dv.getPainters().add(icpainter);
            mc.addSubscriber(dv);
            dv.getModel().addChild(mc);
        }
    }

    private void dodVezeRek(ClassyNode cn,HashMap<Diagram, DiagramView> listOfDiagrams){
        if(cn instanceof Connection){ //da dodam sve veze
            Diagram par = (Diagram) cn.getParent();
            DiagramView dv = listOfDiagrams.get(par);
            Connection con = (Connection) cn;
            dodavanjeVeza(con, dv);
        }
        //rekurzija deo rekurzije
        if(cn instanceof ClassyNodeComposite){
            for(ClassyNode child: ((ClassyNodeComposite) cn).getChildren()){
                dodVezeRek(child, listOfDiagrams);
            }
        }

    }
    private void dodavanjeVeza(Connection c, DiagramView dv){
        if(c instanceof Agregacija){
            Agregacija ag = (Agregacija) c;
            //da pokazuje na dobre klase:
            for(ClassyNode chi: ((ClassyNodeComposite)ag.getParent()).getChildren()){
                if(chi instanceof Interclass){
                    if(ag.getFrom().getIme().equalsIgnoreCase(chi.getIme())  && ag.getFrom().getPosition().equals(((Interclass) chi).getPosition())){
                        Interclass ic1 = (Interclass) chi;
                        ag.setFrom(ic1);
                    }
                    if(ag.getTo().getIme().equalsIgnoreCase(chi.getIme()) && ag.getTo().getPosition().equals(((Interclass) chi).getPosition())){
                        Interclass ic2 = (Interclass) chi;
                        ag.setTo(ic2);
                    }
                }
            }
            AgregacijaPainter ap = new AgregacijaPainter(ag);
            dv.getPainters().add(ap);
            dv.getModel().addChild(ag);
        }else if(c instanceof Generalizacija){
            Generalizacija gen = (Generalizacija) c;

            for(ClassyNode chi: ((ClassyNodeComposite)gen.getParent()).getChildren()){
                if(chi instanceof Interclass){
                    if(gen.getFrom().getIme().equalsIgnoreCase(chi.getIme())  && gen.getFrom().getPosition().equals(((Interclass) chi).getPosition())){
                        Interclass ic1 = (Interclass) chi;
                        gen.setFrom(ic1);
                    }
                    if(gen.getTo().getIme().equalsIgnoreCase(chi.getIme()) && gen.getTo().getPosition().equals(((Interclass) chi).getPosition())){
                        Interclass ic2 = (Interclass) chi;
                        gen.setTo(ic2);
                    }
                }
            }


            GeneralizacijaPainter ap = new GeneralizacijaPainter(gen);
            dv.getPainters().add(ap);
            dv.getModel().addChild(gen);

        }else if(c instanceof Kompozicija){
            Kompozicija komp = (Kompozicija) c;
            for(ClassyNode chi: ((ClassyNodeComposite)komp.getParent()).getChildren()){
                if(chi instanceof Interclass){
                    if(komp.getFrom().getIme().equalsIgnoreCase(chi.getIme())  && komp.getFrom().getPosition().equals(((Interclass) chi).getPosition())){
                        Interclass ic1 = (Interclass) chi;
                        komp.setFrom(ic1);
                    }
                    if(komp.getTo().getIme().equalsIgnoreCase(chi.getIme()) && komp.getTo().getPosition().equals(((Interclass) chi).getPosition())){
                        Interclass ic2 = (Interclass) chi;
                        komp.setTo(ic2);
                    }
                }
            }
            KompozicijaPainter ap = new KompozicijaPainter(komp);
            dv.getPainters().add(ap);
            dv.getModel().addChild(komp);

        }else if(c instanceof Zavisnost){
            Zavisnost zav = (Zavisnost) c;
            float[] dashPattern = {5.0f, 5.0f};
            zav.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f));
            for(ClassyNode chi: ((ClassyNodeComposite)zav.getParent()).getChildren()){
                if(chi instanceof Interclass){
                    if(zav.getFrom().getIme().equalsIgnoreCase(chi.getIme())  && zav.getFrom().getPosition().equals(((Interclass) chi).getPosition())){
                        Interclass ic1 = (Interclass) chi;
                        zav.setFrom(ic1);
                    }
                    if(zav.getTo().getIme().equalsIgnoreCase(chi.getIme()) && zav.getTo().getPosition().equals(((Interclass) chi).getPosition())){
                        Interclass ic2 = (Interclass) chi;
                        zav.setTo(ic2);
                    }
                }
            }

            ZavisnostPainter ap = new ZavisnostPainter(zav);
            dv.getPainters().add(ap);
            dv.getModel().addChild(zav);

        }

    }

    private void rekV(ClassyNodeComposite parent, ArrayList<ClassyNode> children){
        for(ClassyNode cn: children){
            //logika za dodavanje parenta za sve:
            cn.setParent(parent);
            if((cn instanceof Diagram)) ((Diagram)cn).setSubs(new ArrayList<>());
            if((cn instanceof Package)) ((Package)cn).setSubs(new ArrayList<>());
            if((cn instanceof Interclass)) {((Interclass) cn).setSubs(new ArrayList<>());}
            if(cn instanceof ClassyNodeComposite){
                ArrayList<ClassyNode> chil = (ArrayList<ClassyNode>) ((ClassyNodeComposite) cn).getChildren();

                rekV((ClassyNodeComposite) cn, chil); //rekurzija
            }
        }

    }

    @Override
    public void saveProject(Project proj) {
        try{
            ObjectMapper om = new ObjectMapper();
            //om.enableDefaultTyping(); //zbog polimorfizma :D //uguseno :(
            om.writeValue(new File(proj.getPutanja()), proj);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public Diagram loadDiagram(File file, Diagram diag) {
        try{
            ObjectMapper om = new ObjectMapper();
            Diagram d = om.readValue(file, Diagram.class);
            ArrayList<ClassyNode> children = (ArrayList<ClassyNode>) d.getChildren();
            for(ClassyNode cn: children)diag.addChild(cn); //prvo da njemu da dodam decu
            rekV(diag, children); //pa da dodam deci njega

            HashMap<Diagram, DiagramView> listOfDiagrams = MainFrame.getInstance().getPv().getListOfDiagrams();
            rekurzijaDiagElem(diag, listOfDiagrams);
            MainFrame.getInstance().getPv().setListOfDiagrams(listOfDiagrams);
            dodVezeRek(diag, listOfDiagrams);
            MainFrame.getInstance().getPv().setListOfDiagrams(listOfDiagrams);
            return diag;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveDiagram(Diagram d) {
        try{
            ObjectMapper om = new ObjectMapper();
            //ne radi kad se poziva u resources, ne zeli da ga ucitava tu
            //koliko sam ja razumeo, resources moze samo da se cita dok program radi :(
            String path = "diagTemplates" + File.separator + d.getIme() + ".json";
            om.writeValue(new File(path), d);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
