package commands.implementation;

import commands.AbstractCommand;
import gui.swing.view.DiagramView;
import gui.swing.view.MainFrame;
import painters.specP.InterCPainter;
import painters.specP.InterEnumPainter;
import painters.specP.InterInterfacePainter;
import repository.composite.ClassyNode;
import repository.implementation.Diagram;
import repository.implementation.diagramElements.elements.DiagramElement;
import repository.implementation.diagramElements.elements.interImplementation.MyClass;
import repository.implementation.diagramElements.elements.interImplementation.MyEnum;
import repository.implementation.diagramElements.elements.interImplementation.MyInterface;

import java.awt.*;

public class CreateElementCommand extends AbstractCommand {
    //prvo mi treba sve sto ova konkretna komanda mora da ima da bi radila kako treba
    private Diagram d;
    private DiagramElement child;
    private DiagramView dv;
    private int pok;

    //i onda mi treba i njen konstruktor
    public CreateElementCommand(Diagram d, DiagramElement child, DiagramView dv, int pok){
        this.d = d;
        this.child = child;
        this.dv = dv;
        this.pok = pok;
    }


    @Override
    public void doCommand() {

        switch (pok) {
            case 0: {
                //KLASA
                MyClass mc = (MyClass) child;
                InterCPainter icpainter = new InterCPainter(mc);
                dv.getPainters().add(icpainter);
                mc.addSubscriber(dv);
                MainFrame.getInstance().getClassyTree().addChildToDiag(d, mc);
                d.addChild(mc);
                break;
            }
            case 1: {
                //enum
                MyEnum mc = (MyEnum) child;
                InterEnumPainter icpainter = new InterEnumPainter(mc);
                dv.getPainters().add(icpainter);
                mc.addSubscriber(dv);
                MainFrame.getInstance().getClassyTree().addChildToDiag(d, mc);
                d.addChild(mc);
                break;
            }
            case 2: {
                //Interfejs
                MyInterface mc = (MyInterface) child;
                InterInterfacePainter icpainter = new InterInterfacePainter(mc);
                dv.getPainters().add(icpainter);
                mc.addSubscriber(dv);
                //i tek sad kad sam napravio sve treba diagramu da dodam ovo dete koje sam napravio
                MainFrame.getInstance().getClassyTree().addChildToDiag(d, mc);
                d.addChild(mc);
                break;
            }
            default:{

            }
        }

    }

    @Override
    public void undoCommand() {
        MainFrame.getInstance().getClassyTree().removeChildFromDiag(d, child);
        d.removeChild(child);
    }
}
