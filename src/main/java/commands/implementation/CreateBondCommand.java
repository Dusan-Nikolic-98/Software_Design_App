package commands.implementation;

import commands.AbstractCommand;
import gui.swing.view.DiagramView;
import gui.swing.view.MainFrame;
import painters.connectionPainters.AgregacijaPainter;
import painters.connectionPainters.GeneralizacijaPainter;
import painters.connectionPainters.KompozicijaPainter;
import painters.connectionPainters.ZavisnostPainter;
import painters.specP.InterCPainter;
import painters.specP.InterEnumPainter;
import painters.specP.InterInterfacePainter;
import repository.implementation.Diagram;
import repository.implementation.diagramElements.elements.DiagramElement;
import repository.implementation.diagramElements.elements.connectionImplementation.Agregacija;
import repository.implementation.diagramElements.elements.connectionImplementation.Generalizacija;
import repository.implementation.diagramElements.elements.connectionImplementation.Kompozicija;
import repository.implementation.diagramElements.elements.connectionImplementation.Zavisnost;
import repository.implementation.diagramElements.elements.interImplementation.MyClass;
import repository.implementation.diagramElements.elements.interImplementation.MyEnum;
import repository.implementation.diagramElements.elements.interImplementation.MyInterface;

public class CreateBondCommand extends AbstractCommand {

    private Diagram d;
    private DiagramElement child;
    private DiagramView dv;
    private int pok;
    public CreateBondCommand(Diagram d, DiagramElement child, DiagramView dv, int pok){
        this.d = d;
        this.child = child;
        this.dv = dv;
        this.pok = pok;
    }


    @Override
    public void doCommand() {
        switch (pok) {
            case 0: {
                //agregacija
                Agregacija ag = (Agregacija) child;
                AgregacijaPainter ap = new AgregacijaPainter(ag);
                dv.getPainters().add(ap);
                //i sad kad je napravljena i linija do kraja i painter za nju, moze add dete pa ce se iscrtati samo
                dv.getModel().addChild(ag);
                break;
            }
            case 1: {
                //Generalizacija
                Generalizacija gen = (Generalizacija) child;
                GeneralizacijaPainter ap = new GeneralizacijaPainter(gen);
                dv.getPainters().add(ap);
                //i sad kad je napravljena i linija do kraja i painter za nju, moze add dete pa ce se iscrtati samo
                dv.getModel().addChild(gen);
                break;
            }
            case 2: {
                //Kompozicija
                Kompozicija komp = (Kompozicija) child;
                KompozicijaPainter ap = new KompozicijaPainter(komp);
                dv.getPainters().add(ap);
                //i sad kad je napravljena i linija do kraja i painter za nju, moze add dete pa ce se iscrtati samo
                dv.getModel().addChild(komp);
                break;
            }
            case 3:{ //Zavisnost
                //
                Zavisnost zav = (Zavisnost) child;
                ZavisnostPainter ap = new ZavisnostPainter(zav);
                dv.getPainters().add(ap);
                //i sad kad je napravljena i linija do kraja i painter za nju, moze add dete pa ce se iscrtati samo
                dv.getModel().addChild(zav);

            }
            default:{

            }
        }

    }

    @Override
    public void undoCommand() {
        dv.getModel().removeChild(child);
    }
}
