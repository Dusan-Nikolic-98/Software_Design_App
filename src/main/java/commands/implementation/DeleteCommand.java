package commands.implementation;

import commands.AbstractCommand;
import gui.swing.view.DiagramView;
import gui.swing.view.MainFrame;
import painters.ElementPainter;
import painters.connectionPainters.AgregacijaPainter;
import painters.connectionPainters.GeneralizacijaPainter;
import painters.connectionPainters.KompozicijaPainter;
import painters.connectionPainters.ZavisnostPainter;
import painters.specP.InterCPainter;
import painters.specP.InterEnumPainter;
import painters.specP.InterInterfacePainter;
import repository.composite.ClassyNode;
import repository.implementation.Diagram;
import repository.implementation.diagramElements.elements.Connection;
import repository.implementation.diagramElements.elements.DiagramElement;
import repository.implementation.diagramElements.elements.Interclass;
import repository.implementation.diagramElements.elements.connectionImplementation.Agregacija;
import repository.implementation.diagramElements.elements.connectionImplementation.Generalizacija;
import repository.implementation.diagramElements.elements.connectionImplementation.Kompozicija;
import repository.implementation.diagramElements.elements.connectionImplementation.Zavisnost;
import repository.implementation.diagramElements.elements.interImplementation.MyClass;
import repository.implementation.diagramElements.elements.interImplementation.MyEnum;
import repository.implementation.diagramElements.elements.interImplementation.MyInterface;

import java.util.ArrayList;

public class DeleteCommand extends AbstractCommand {

    private ArrayList<Interclass> elementi = new ArrayList<>();
    private ArrayList<Connection> konekcije = new ArrayList<>();
    private DiagramView dv;
    private DiagramElement soloEl;
    private ArrayList<Connection> soloKon = new ArrayList<>();
    private boolean flag;

    public DeleteCommand(DiagramView dv,ArrayList<ElementPainter> selectElem, ArrayList<ElementPainter> selectConn){
        this.flag = true;
        this.dv = dv;
        for(ElementPainter ep: selectElem){
            if(ep.getModel() instanceof Interclass){
                elementi.add((Interclass) ep.getModel());
            }else {
                konekcije.add((Connection) ep.getModel());
            }
        }
        for(ElementPainter ep: selectConn){
            if(ep.getModel() instanceof Connection)
                konekcije.add((Connection) ep.getModel());
            else
                elementi.add((Interclass) ep.getModel());
        }
    }

    public DeleteCommand(DiagramView dv, DiagramElement soloEl){
        this.flag = false;
        this.dv = dv;
        this.soloEl = soloEl;
        if(soloEl instanceof Interclass) {
            for (ClassyNode de : dv.getModel().getChildren()) {
                if (de instanceof Connection) {
                    if(((Connection) de).getTo().equals(soloEl) || ((Connection) de).getFrom().equals(soloEl))
                        soloKon.add((Connection) de);

                }
            }
        }

    }
    @Override
    public void doCommand() {
        if(flag){ //ako se brise vise
            Diagram d = dv.getModel();
            for(int i = 0; i < elementi.size();i++){
                MainFrame.getInstance().getClassyTree().removeChildFromDiag(d, elementi.get(i));
                dv.getModel().removeChild(elementi.get(i));
            }
            for(int i = 0; i < konekcije.size();i++){
                d.removeChild(konekcije.get(i));
            }
        }else{ //ako se brise solo
            if (!(soloEl instanceof Connection))
                MainFrame.getInstance().getClassyTree().removeChildFromDiag(dv.getModel(), soloEl);
            dv.getModel().removeChild(soloEl);

        }
    }
    @Override
    public void undoCommand() {
        if(flag){ //ako se brisalo vise
            //prvo sve klase
            for(int i = 0; i < elementi.size();i++){
                Interclass ic = null;
                ElementPainter ep = null;
                if(elementi.get(i) instanceof MyClass){
                    ic = elementi.get(i);
                    ep = new InterCPainter(ic);
                }else if(elementi.get(i) instanceof MyEnum){
                    ic = elementi.get(i);
                    ep = new InterEnumPainter(ic);
                }else if(elementi.get(i) instanceof MyInterface){
                    ic = elementi.get(i);
                    ep = new InterInterfacePainter(ic);
                }
                if(ic != null && !dv.getModel().getChildren().contains(ic)){ //da se osiguram
                    dv.getPainters().add(ep);
                    ic.addSubscriber(dv);
                    MainFrame.getInstance().getClassyTree().addChildToDiag(dv.getModel(), ic);
                    dv.getModel().addChild(ic);
                }
            }
            //pa sve veze
            for(int i = 0; i < konekcije.size();i++){
                Connection cc = null;
                ElementPainter ep = null;
                //agregacija, generalizacija, kompozicija, zavisnost
                if(konekcije.get(i) instanceof Agregacija){
                    cc = konekcije.get(i);
                    ep = new AgregacijaPainter(cc);
                }else if(konekcije.get(i) instanceof Generalizacija){
                    cc = konekcije.get(i);
                    ep = new GeneralizacijaPainter(cc);
                }else if(konekcije.get(i) instanceof Kompozicija){
                    cc = konekcije.get(i);
                    ep = new KompozicijaPainter(cc);
                } else if(konekcije.get(i) instanceof Zavisnost){
                    cc = konekcije.get(i);
                    ep = new ZavisnostPainter(cc);
                }
                if(cc != null && !dv.getModel().getChildren().contains(cc)){
                    dv.getPainters().add(ep);
                    dv.getModel().addChild(cc);
                }
            }
        }else{ //ako se brisao jedan

            if(soloEl instanceof Interclass){
                //sva logika ako je interc
                Interclass ic = null;
                ElementPainter ep = null;
                if(soloEl instanceof MyClass){
                    ic = (MyClass)soloEl;
                    ep = new InterCPainter(ic);
                }else if(soloEl instanceof MyEnum){
                    ic = (MyEnum)soloEl;
                    ep = new InterEnumPainter(ic);
                }else if(soloEl instanceof MyInterface){
                    ic = (MyInterface)soloEl;
                    ep = new InterInterfacePainter(ic);
                }
                if(ic != null && !dv.getModel().getChildren().contains(ic)){
                    dv.getPainters().add(ep);
                    ic.addSubscriber(dv);
                    MainFrame.getInstance().getClassyTree().addChildToDiag(dv.getModel(), ic);
                    dv.getModel().addChild(ic);
                    //todo i za sve konekcije koje su mozda bile tu izbrisane
                    for(int i = 0; i < soloKon.size();i++){
                        Connection cc = null;
                        ElementPainter ep2 = null;
                        //agregacija, generalizacija, kompozicija, zavisnost
                        if(soloKon.get(i) instanceof Agregacija){
                            cc = soloKon.get(i);
                            ep2 = new AgregacijaPainter(cc);
                        }else if(soloKon.get(i) instanceof Generalizacija){
                            cc = soloKon.get(i);
                            ep2 = new GeneralizacijaPainter(cc);
                        }else if(soloKon.get(i) instanceof Kompozicija){
                            cc = soloKon.get(i);
                            ep2 = new KompozicijaPainter(cc);
                        } else if(soloKon.get(i) instanceof Zavisnost){
                            cc = soloKon.get(i);
                            ep2 = new ZavisnostPainter(cc);
                        }
                        if(cc != null && !dv.getModel().getChildren().contains(cc)){
                            dv.getPainters().add(ep2);
                            dv.getModel().addChild(cc);
                        }
                    }
                }
            }else if(soloEl instanceof Connection){
                //sva logika ako je conn
                Connection cc = null;
                ElementPainter ep = null;
                if(soloEl instanceof Agregacija){
                    cc = (Agregacija) soloEl;
                    ep = new AgregacijaPainter(cc);
                }else if(soloEl instanceof Generalizacija){
                    cc = (Generalizacija)soloEl;
                    ep = new GeneralizacijaPainter(cc);
                }else if(soloEl instanceof Kompozicija){
                    cc = (Kompozicija)soloEl;
                    ep = new KompozicijaPainter(cc);
                } else if(soloEl instanceof Zavisnost){
                    cc = (Zavisnost)soloEl;
                    ep = new ZavisnostPainter(cc);
                }
                if(cc != null && !dv.getModel().getChildren().contains(cc)){
                    dv.getPainters().add(ep);
                    dv.getModel().addChild(cc);
                }
            }

        }
    }
}
