package state.concrete;

import commands.implementation.CreateElementCommand;
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
import state.State;

import javax.swing.*;
import java.awt.*;

public class CreateElementState implements State {
    private int myClassIdx = 0;
    private int myEnumIdx = 0;
    private int myInterfaceIdx = 0;
    @Override
    public void misKliknut(Point p, DiagramView dv) {
            //mozda da iskoci popap pre nego sto napravim da me pita sta zelim da pravim
            Point padjustedP = dv.adjustPointForZoom(p);
            //adjustedP

            String[] options = {"Class", "Enum", "Interface", "Cancel"};
            int ans = JOptionPane.showOptionDialog(null, "choose an option", "What would you like to create?",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[3]);
            if(ans == 0) {  //AKO JE NOVA KLASA
                Diagram d = dv.getModel();
                //deo za unikatnost imena:
                boolean joj;
                myClassIdx = 1;
                while(true){
                    joj = true;
                    for(ClassyNode diagel : d.getChildren()){
                        if(diagel.getIme().equalsIgnoreCase("Class " + myClassIdx)){
                            joj = false;
                            break;
                        }
                    }
                    if(joj)break;
                    else myClassIdx++;
                }
                //dovde
                MyClass mc = new MyClass("Class " + myClassIdx, d); //prvo napravim novi myclass
                Point adjDimP = new Point(mc.getSize().width, mc.getSize().height);
                if (!dv.wouldOverlap(p, adjDimP.x, adjDimP.y)) {
                    mc.setPosition(padjustedP);
                    CreateElementCommand cec = new CreateElementCommand(d, mc, dv, 0);
                    dv.getCommandManager().addCommand(cec);

                }
            }else if(ans == 1){ //ako je enum
                Diagram d = dv.getModel();
                //odavde za ime
                myEnumIdx = 1;
                boolean joj;
                while(true){
                    joj = true;
                    for(ClassyNode cn : d.getChildren()){
                        if(cn.getIme().equalsIgnoreCase("Enum " + myEnumIdx)){
                            joj = false;
                            break;
                        }
                    }
                    if(joj)break;
                    else myEnumIdx++;
                }
                //dovde
                MyEnum mc = new MyEnum("Enum " + myEnumIdx, d); //prvo napravim novi myenum
                Point adjDimP = new Point(mc.getSize().width, mc.getSize().height);
                if (!dv.wouldOverlap(p, adjDimP.x, adjDimP.y)) {
                    mc.setPosition(padjustedP);

                    CreateElementCommand cec = new CreateElementCommand(d,mc,dv,1);
                    dv.getCommandManager().addCommand(cec);
                }
            }else if(ans == 2){ //ako je interfejs
                Diagram d = dv.getModel();
                //odavde za ime
                myInterfaceIdx = 1;
                boolean joj;
                while(true){
                    joj = true;
                    for(ClassyNode cn: d.getChildren()){
                        if(cn.getIme().equalsIgnoreCase("Interface " + myInterfaceIdx)){
                            joj = false;
                            break;
                        }
                    }
                    if(joj)break;
                    else myInterfaceIdx++;
                }
                //dovde
                MyInterface mc = new MyInterface("Interface " + myInterfaceIdx, d); //prvo napravim novi myenum
                Point adjDimP = new Point(mc.getSize().width, mc.getSize().height);
                if (!dv.wouldOverlap(p, adjDimP.x, adjDimP.y)) {
                    mc.setPosition(padjustedP);

                    CreateElementCommand cec = new CreateElementCommand(d,mc,dv,2);
                    dv.getCommandManager().addCommand(cec);

                }
            }
    }

    @Override
    public void misSeDrzi(Point p, DiagramView dv) {

    }

    @Override
    public void misPusten(Point p, DiagramView dv) {

    }
}
