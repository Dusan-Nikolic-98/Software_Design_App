package state.concrete;

import commands.implementation.CreateBondCommand;
import gui.swing.view.DiagramView;
import painters.ElementPainter;
import painters.connectionPainters.*;
import repository.implementation.diagramElements.elements.Connection;
import repository.implementation.diagramElements.elements.Interclass;
import repository.implementation.diagramElements.elements.connectionImplementation.*;
import state.State;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CreateBondState implements State {
    int prov = 0;
    static int agregacijaIdx = 0; static int generalizacijaIdx = 0;
    static int kompozicijaIdx = 0; static int zavisnostIdx = 0;
    TempConnection tc = null;
    TempConnectionPainter tcp = null;
    Agregacija ag = null;
    @Override
    public void misKliknut(Point p, DiagramView dv) {
        Point padjustedP = dv.adjustPointForZoom(p);
        //adjustedP

        if(dv.containsElement(padjustedP)){
            if(dv.elementAt(padjustedP).getModel() instanceof Interclass){ //cisto da se ne desi da sam kliknuo na liniju xD
                ag = new Agregacija("ag" + agregacijaIdx++, dv.getModel());
                Interclass ic = (Interclass) dv.elementAt(padjustedP).getModel();
                ag.setFrom(ic);
                prov++;
            }

        }
    }

    @Override
    public void misSeDrzi(Point p, DiagramView dv) {
        Point padjustedP = dv.adjustPointForZoom(p);
        //adjustedP

        if(prov > 0) {
            tc = new TempConnection("tc", dv.getModel());
            tc.setFrom(ag.getFrom());
            dv.getModel().removeChild(tc);
            dv.getPainters().remove(tcp);

            tcp = new TempConnectionPainter(tc);
            tcp.setTempEnd(padjustedP);
            dv.getPainters().add(tcp);
            dv.getModel().addChild(tc);

        }
    }

    @Override
    public void misPusten(Point p, DiagramView dv) {
        Point padjustedP = dv.adjustPointForZoom(p);
        //adjustedP

        dv.getModel().removeChild(tc);
        dv.getPainters().remove(tcp);
        if(prov > 0 && dv.containsElement(padjustedP)){
            if(ag != null && dv.elementAt(padjustedP).getModel() instanceof Interclass && !dv.elementAt(padjustedP).getModel().equals(ag.getFrom())){
                //dakle ako postoji i ako nije onaj od kog sam krenuo

                //jos jedan if za ako vec postoji veza izmedju ta dva:
                Interclass ic = (Interclass) dv.elementAt(padjustedP).getModel();
                ag.setTo(ic);
                boolean flag = true;
                for(ElementPainter e: dv.getPainters()){
                    if(e.getModel() instanceof Connection){
                        if((((Connection) e.getModel()).getFrom().equals(ag.getFrom()) && ((Connection) e.getModel()).getTo().equals(ag.getTo()))||
                        (((Connection) e.getModel()).getFrom().equals(ag.getTo()) && ((Connection) e.getModel()).getTo().equals(ag.getFrom())))
                            flag = false;
                            //DA samo moze jedna konekcija za 2 klase da se doda
                    }
                }


                if(flag) { //dakle samo ako mi je ostalo i dalje da nema takve veze, da se napravi
                    //todo ovde popap
                    String[] options = {"Agregacija", "Generalizacija", "Kompozicija","Zavisnost","Cancel"};
                    int ans = JOptionPane.showOptionDialog(null, "choose an option", "What bond would you like to create?",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[4]);
                    if(ans == 0){   //Agregacija
                        ag.setIme(ag.getFrom().getIme() + "-" + ag.getTo().getIme());
                        System.out.println(ag.getFrom().getIme() + "-" + ag.getTo().getIme());
                        ag.setName(ag.getIme());
                        //odavde
//                        AgregacijaPainter ap = new AgregacijaPainter(ag);
//                        dv.getPainters().add(ap);
//                        //i sad kad je napravljena i linija do kraja i painter za nju, moze add dete pa ce se iscrtati samo
//                        dv.getModel().addChild(ag);
                        CreateBondCommand cbc = new CreateBondCommand(dv.getModel(), ag, dv, 0);
                        dv.getCommandManager().addCommand(cbc);

                    }else if(ans == 1){ //Generalizacija
                        Generalizacija gen = new Generalizacija("gen" + generalizacijaIdx++, dv.getModel());
                        gen.setFrom(ag.getFrom());
                        gen.setTo(ag.getTo());
                        //menjati sve iz ag u gen
                        gen.setIme(gen.getFrom().getIme() + "-" + gen.getTo().getIme());
                        System.out.println(gen.getFrom().getIme() + "-" + gen.getTo().getIme());
                        gen.setName(gen.getIme());
                        //todo odavde

//                        GeneralizacijaPainter ap = new GeneralizacijaPainter(gen);
//                        dv.getPainters().add(ap);
//                        //i sad kad je napravljena i linija do kraja i painter za nju, moze add dete pa ce se iscrtati samo
//                        dv.getModel().addChild(gen);
                        CreateBondCommand cbc = new CreateBondCommand(dv.getModel(), gen, dv, 1);
                        dv.getCommandManager().addCommand(cbc);

                    }else if(ans == 2){ //Kompozicija
                        Kompozicija komp = new Kompozicija("komp" + kompozicijaIdx++, dv.getModel());
                        komp.setFrom(ag.getFrom());
                        komp.setTo(ag.getTo());
                        //menjati sve iz ag u gen
                        komp.setIme(komp.getFrom().getIme() + "-" + komp.getTo().getIme());
                        System.out.println(komp.getFrom().getIme() + "-" + komp.getTo().getIme());
                        komp.setName(komp.getIme());
                        //todo odavde
//                        KompozicijaPainter ap = new KompozicijaPainter(komp);
//                        dv.getPainters().add(ap);
//                        //i sad kad je napravljena i linija do kraja i painter za nju, moze add dete pa ce se iscrtati samo
//                        dv.getModel().addChild(komp);
                        CreateBondCommand cbc = new CreateBondCommand(dv.getModel(), komp, dv, 2);
                        dv.getCommandManager().addCommand(cbc);

                    }else if(ans == 3){ //Zavisnost
                        Zavisnost zav = new Zavisnost("zav" + zavisnostIdx++, dv.getModel());
                        zav.setFrom(ag.getFrom());
                        zav.setTo(ag.getTo());
                        //menjati sve iz ag u gen
                        zav.setIme(zav.getFrom().getIme() + "-" + zav.getTo().getIme());
                        System.out.println(zav.getFrom().getIme() + "-" + zav.getTo().getIme());
                        zav.setName(zav.getIme());
                        //todo odavde
//                        ZavisnostPainter ap = new ZavisnostPainter(zav);
//                        dv.getPainters().add(ap);
//                        //i sad kad je napravljena i linija do kraja i painter za nju, moze add dete pa ce se iscrtati samo
//                        dv.getModel().addChild(zav);
                        CreateBondCommand cbc = new CreateBondCommand(dv.getModel(), zav, dv, 3);
                        dv.getCommandManager().addCommand(cbc);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "there is already a connection here, please first delete " +
                            "the existing if you want to create a new one", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        ag = null; //da je svakako na kraju svega vratim na null da ne bi doslo do glupavih problema
        prov = 0;
    }
}
