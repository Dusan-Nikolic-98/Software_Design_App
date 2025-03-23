package commands.implementation;

import commands.AbstractCommand;
import gui.swing.view.MainFrame;
import repository.implementation.diagramElements.elements.Interclass;
import repository.implementation.diagramElements.elements.classContent.ClassContent;

public class AddAttributeCommand extends AbstractCommand {
    private String nameNew;
    private String nameOld;
    private Interclass ic;
    private ClassContent cc; //ClassMethod/ClassAttribute/EnumClassContent
    private int no; //0-method, 1-attribute, 2-enum //mozda nepotrebno
    //private boolean flag; //true-dodaj, false-obrisi
    //potencijalno: 0-dodavanje, 1-brisanje, 2-menjanje imena
    public AddAttributeCommand(Interclass ic, ClassContent cc,int no){
        this.ic = ic;
        this.cc = cc;
        this.no = no;
    }
    public AddAttributeCommand(Interclass ic, String nameNew,String nameOld, int no){
        this.ic = ic;
        this.cc = null;
        this.nameNew = nameNew;
        this.nameOld = nameOld;
        this.no = no;
    }


    @Override
    public void doCommand() {
        if(no == 0){ //ako je add
            ic.addContent(cc);
        }else if(no == 1){ //ako je remove
            ic.removeContent(cc);
        }else{//ako jechange name
            ic.setName(nameNew);
            ic.setIme(nameNew);
            MainFrame.getInstance().getClassyTree().changeElementNames();
        }
    }

    @Override
    public void undoCommand() {
        if(no == 0){ // ako je bio add
            ic.removeContent(cc);
        }else if(no == 1){ //ako je bio remove
            ic.addContent(cc);
        }else{ //ako je ime
            ic.setName(nameOld);
            ic.setIme(nameOld);
            MainFrame.getInstance().getClassyTree().changeElementNames();
        }
    }
}
