package state.concrete;

import commands.implementation.CreateElementCommand;
import gui.swing.view.DiagramView;
import gui.swing.view.MainFrame;
import painters.ElementPainter;
import painters.specP.InterCPainter;
import painters.specP.InterEnumPainter;
import painters.specP.InterInterfacePainter;
import repository.composite.ClassyNode;
import repository.implementation.Diagram;
import repository.implementation.diagramElements.elements.Interclass;
import repository.implementation.diagramElements.elements.classContent.ClassAttribute;
import repository.implementation.diagramElements.elements.classContent.ClassContent;
import repository.implementation.diagramElements.elements.classContent.ClassMethod;
import repository.implementation.diagramElements.elements.classContent.EnumClassContent;
import repository.implementation.diagramElements.elements.interImplementation.MyClass;
import repository.implementation.diagramElements.elements.interImplementation.MyEnum;
import repository.implementation.diagramElements.elements.interImplementation.MyInterface;
import state.State;

import javax.swing.*;
import java.awt.*;

public class DuplicateState implements State {
    @Override
    public void misKliknut(Point p, DiagramView dv) {
        Point padjustedP = dv.adjustPointForZoom(p);
        //adjustedP

        if(dv.containsElement(padjustedP) && (dv.elementAt(padjustedP).getModel() instanceof Interclass)){
            ElementPainter ep = dv.elementAt(padjustedP);
            String name = ep.getModel().getName() != null? ep.getModel().getName(): "veza";
            int ans = JOptionPane.showConfirmDialog(null, "do you want to duplicate element: " + name);
            if(ans == JOptionPane.YES_OPTION){
                Interclass el = (Interclass) ep.getModel();
                if(el instanceof MyClass){
                    Diagram d = dv.getModel();
                    //logika za dodavanje vise istih kopija zbog imena
                    int i = 0;
                    while(true){
                        String ch = String.valueOf(i);
                        boolean flag = true;
                        String nameTemp = name+"(cp)" + ch;
                        System.out.println("name temp za koje se proverava: "+nameTemp);
                        for(ClassyNode cn: d.getChildren()){
                            System.out.println("provereno dete: " + cn.getIme());
                            if(cn.getIme().equalsIgnoreCase(nameTemp)) {
                                flag = false;
                                System.out.println("dosao nekad do toga da postoji takvo dete");
                                break;
                            }
                        }
                        if(flag)break;
                        i++;
                    }

                    MyClass mc = new MyClass(el.getIme() + "(cp)" + i, d);
                    //mogu ovde da dodam da se pravi dijagonalno dolije cim moze npr
                    mc.setPosition(new Point(el.getPosition().x+15, el.getPosition().y+15));
                    mc.setSize(new Dimension(el.getSize().width, el.getSize().height));
                    //da se dodaju kontenti:
                    //mc.setContents(el.getContents()); ovo je plitko :(

                    for(ClassContent cc: el.getContents()){
                        if(cc instanceof ClassAttribute){
                            mc.getContents().add(new ClassAttribute(cc.getContent()));
                        }else{ //ako je ClassMethod
                            mc.getContents().add(new ClassMethod(cc.getContent()));
                        }
                    }
                    //todo ovde
//                    InterCPainter icpainter = new InterCPainter(mc);
//                    dv.getPainters().add(icpainter);
//                    mc.addSubscriber(dv);
//
//                    MainFrame.getInstance().getClassyTree().addChildToDiag(d, mc);
//                    d.addChild(mc);
                    CreateElementCommand cec = new CreateElementCommand(d, mc, dv, 0);
                    dv.getCommandManager().addCommand(cec);


                }else if(el instanceof MyEnum){
                    Diagram d = dv.getModel();
                    //logika za dodavanje vise istih kopija zbog imena
                    int i = 0;
                    while(true){
                        String ch = String.valueOf(i);
                        boolean flag = true;
                        String nameTemp = name+"(cp)" + ch;
                        //System.out.println("name temp za koje se proverava: "+nameTemp);
                        for(ClassyNode cn: d.getChildren()){
                            //System.out.println("provereno dete: " + cn.getIme());
                            if(cn.getIme().equalsIgnoreCase(nameTemp)) {
                                flag = false;
                                //System.out.println("dosao nekad do toga da postoji takvo dete");
                                break;
                            }
                        }
                        if(flag)break;
                        i++;
                    }
                    //odavde se menja
                    MyEnum mc = new MyEnum(el.getIme() + "(cp)" + i, d);
                    //mogu ovde da dodam da se pravi dijagonalno dolije cim moze npr
                    mc.setPosition(new Point(el.getPosition().x+15, el.getPosition().y+15));
                    mc.setSize(new Dimension(el.getSize().width, el.getSize().height));
                    //da se dodaju kontenti:
                    //mc.setContents(el.getContents()); ovo je plitko :(

                    for(ClassContent cc: el.getContents()){
                        if(cc instanceof ClassAttribute){
                            mc.getContents().add(new ClassAttribute(cc.getContent()));
                        }else if(cc instanceof ClassMethod){ //ako je ClassMethod
                            mc.getContents().add(new ClassMethod(cc.getContent()));
                        }else{
                            mc.getContents().add(new EnumClassContent(cc.getContent()));
                        }
                    }
                    //todo ovde

//                    InterEnumPainter icpainter = new InterEnumPainter(mc);
//                    dv.getPainters().add(icpainter);
//                    mc.addSubscriber(dv);
//
//                    MainFrame.getInstance().getClassyTree().addChildToDiag(d, mc);
//                    d.addChild(mc);
                    CreateElementCommand cec = new CreateElementCommand(d,mc,dv,1);
                    dv.getCommandManager().addCommand(cec);


                }else if(el instanceof MyInterface){
                    Diagram d = dv.getModel();
                    //logika za dodavanje vise istih kopija zbog imena
                    int i = 0;
                    while(true){
                        String ch = String.valueOf(i);
                        boolean flag = true;
                        String nameTemp = name+"(cp)" + ch;
                        //System.out.println("name temp za koje se proverava: "+nameTemp);
                        for(ClassyNode cn: d.getChildren()){
                            //System.out.println("provereno dete: " + cn.getIme());
                            if(cn.getIme().equalsIgnoreCase(nameTemp)) {
                                flag = false;
                                //System.out.println("dosao nekad do toga da postoji takvo dete");
                                break;
                            }
                        }
                        if(flag)break;
                        i++;
                    }
                    //odavde se menja
                    MyInterface mc = new MyInterface(el.getIme() + "(cp)" + i, d);
                    //mogu ovde da dodam da se pravi dijagonalno dolije cim moze npr
                    mc.setPosition(new Point(el.getPosition().x+15, el.getPosition().y+15));
                    mc.setSize(new Dimension(el.getSize().width, el.getSize().height));
                    //da se dodaju kontenti:
                    //mc.setContents(el.getContents()); ovo je plitko :(

                    for(ClassContent cc: el.getContents()){
                        if(cc instanceof ClassAttribute){
                            mc.getContents().add(new ClassAttribute(cc.getContent()));
                        }else if(cc instanceof ClassMethod){ //ako je ClassMethod
                            mc.getContents().add(new ClassMethod(cc.getContent()));
                        }else{
                            mc.getContents().add(new EnumClassContent(cc.getContent()));
                        }
                    }
                    //todo ovde
//                    InterInterfacePainter icpainter = new InterInterfacePainter(mc);
//                    dv.getPainters().add(icpainter);
//                    mc.addSubscriber(dv);
//
//                    MainFrame.getInstance().getClassyTree().addChildToDiag(d, mc);
//                    d.addChild(mc);
                    CreateElementCommand cec = new CreateElementCommand(d,mc,dv,2);
                    dv.getCommandManager().addCommand(cec);
                }
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
