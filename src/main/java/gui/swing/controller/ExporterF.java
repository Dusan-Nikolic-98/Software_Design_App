package gui.swing.controller;

import repository.composite.ClassyNode;
import repository.composite.ClassyNodeComposite;
import repository.implementation.Diagram;
import repository.implementation.Package;
import repository.implementation.Project;
import repository.implementation.diagramElements.elements.Connection;
import repository.implementation.diagramElements.elements.Interclass;
import repository.implementation.diagramElements.elements.classContent.ClassAttribute;
import repository.implementation.diagramElements.elements.classContent.ClassContent;
import repository.implementation.diagramElements.elements.classContent.ClassMethod;
import repository.implementation.diagramElements.elements.connectionImplementation.Agregacija;
import repository.implementation.diagramElements.elements.connectionImplementation.Generalizacija;
import repository.implementation.diagramElements.elements.connectionImplementation.Kompozicija;
import repository.implementation.diagramElements.elements.connectionImplementation.Zavisnost;
import repository.implementation.diagramElements.elements.interImplementation.MyClass;
import repository.implementation.diagramElements.elements.interImplementation.MyEnum;
import repository.implementation.diagramElements.elements.interImplementation.MyInterface;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class ExporterF {

    private HashMap<ClassyNode, String> hm;
    private Project p;
    private File f;
    public ExporterF(Project p, File f){
        hm = new HashMap<>();
        this.p = p;
        this.f = f;
        //pa sad redom da pozivam fje koje ce da mi prave program
        String loc = f.getAbsolutePath();
        try{
            f.mkdir();
            rekZaPravljenje(p,loc);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void rekZaPravljenje(ClassyNode curr, String loc){
        //i sad ovde samo 3 ifa ako je projekat, package ili diagram
        System.out.println("rek za pravljenje");
        if(curr instanceof Project){
            for(ClassyNode child: ((Project) curr).getChildren()){
                rekZaPravljenje(child, loc);
            }
        } else if(curr instanceof Package){
            String locloc = loc + File.separator + curr.getIme();
            File file = new File(locloc);
            file.mkdir();
            for(ClassyNode child: ((ClassyNodeComposite) curr).getChildren()){
                rekZaPravljenje(child, locloc);
            }
        }else if(curr instanceof Diagram){
            //i sad deo sa kodom
            String locloc = loc + File.separator + curr.getIme();
            File file = new File(locloc);
            file.mkdir();
            for(ClassyNode cn: ((Diagram) curr).getChildren()){
                if(cn instanceof MyClass){
                    pisiMyClass((MyClass) cn, locloc);
                }else if(cn instanceof MyInterface){
                    pisiMyInterface((MyInterface) cn, locloc);
                }else if(cn instanceof MyEnum){
                    pisiMyEnum((MyEnum) cn, locloc);
                }
            }
        }
    }
    private void pisiMyClass(MyClass mc,String path){
        String loc = path + File.separator + mc.getIme().replace(" ", "") + ".java";
        ArrayList<ClassAttribute> ccAtributi = new ArrayList<>(); ArrayList<String> ccMetode = new ArrayList<>();
        for(int i = 0; i < mc.getContents().size();i++){
            if(mc.getContents().get(i) instanceof ClassAttribute)ccAtributi.add((ClassAttribute) mc.getContents().get(i));
            else if(mc.getContents().get(i) instanceof ClassMethod) ccMetode.add(mc.getContents().get(i).getContent());
        }
        //atributi
        ArrayList<String> attPrivatnost = new ArrayList<>();
        for(int i = 0; i < ccAtributi.size();i++)attPrivatnost.add(privPub(ccAtributi.get(i).getVisibility().charAt(0)));
        //metode
        ArrayList<String> privatnost = new ArrayList<>();ArrayList<String> imeMetode = new ArrayList<>();ArrayList<String[]> argumenti = new ArrayList<>();
        for(int i = 0; i < ccMetode.size();i++){
            privatnost.add(privPub(ccMetode.get(i).charAt(0)));
            int idxOd = ccMetode.get(i).indexOf('('), idxDo = ccMetode.get(i).indexOf(')');
            if(idxOd < idxDo -1){
                String args = ccMetode.get(i).substring(idxOd+1,idxDo);
                String[] ar = args.split(",");
                argumenti.add(ar);
            }else{
                argumenti.add(null);
            }
            String imeM = ccMetode.get(i).substring(1,idxOd);
            imeMetode.add(imeM);
        }
        //logika za dodavanje atributa sa strane:
        ArrayList<String> attSaStrDataType = new ArrayList<>();
        Interclass roditelj = null;
        for(ClassyNode cn: ((Diagram)mc.getParent()).getChildren()){
            //agregacija, kompozicija i zavisnost
            if((cn instanceof Agregacija) || (cn instanceof Kompozicija) || (cn instanceof Zavisnost)){
                //onajKojiSadrzi ----> sadrzani
                if(((Connection) cn).getFrom().equals(mc)){
                    attSaStrDataType.add(((Connection) cn).getTo().getIme().replace(" ", ""));
                }
            }
            if(cn instanceof Generalizacija){
                if(((Generalizacija) cn).getFrom().equals(mc)){
                    //generalizacija
                    roditelj = ((Generalizacija) cn).getTo();
                }
            }
        }
        //logika za dodavanje metoda sa strane:
        ArrayList<String> privRod = null; ArrayList<String> imeMetRod = null;
        ArrayList<String[]> argsRod = null; ArrayList<String> metodeRoditelja = null;
        if(roditelj != null){
            privRod = new ArrayList<>(); imeMetRod = new ArrayList<>(); argsRod = new ArrayList<>();
            metodeRoditelja = new ArrayList<>();
            for(ClassContent cc: roditelj.getContents()) if(cc instanceof ClassMethod)metodeRoditelja.add(cc.getContent());

            for(int i = 0; i < metodeRoditelja.size();i++){
                privRod.add(privPub(metodeRoditelja.get(i).charAt(0)));
                int idxOd = metodeRoditelja.get(i).indexOf('('), idxDo = metodeRoditelja.get(i).indexOf(')');
                if(idxOd < idxDo -1){
                    String args =  metodeRoditelja.get(i).substring(idxOd + 1, idxDo);
                    String[] ar = args.split(",");
                    argsRod.add(ar);
                }else{
                    argsRod.add(null);
                }
                String imeM = metodeRoditelja.get(i).substring(1, idxOd);
                imeMetRod.add(imeM);
            }
        }
        try(PrintWriter pw = new PrintWriter(loc)){
            //logika za klasu
            pw.write("pacakge " + loc + "\n\n");
            pw.write("public class " + mc.getIme().replace(" ", ""));
            //deo za extends
            if(roditelj != null)  pw.write(" extends " + roditelj.getIme().replace(" ", ""));

            pw.write(" {\n\n");
            //spoljni att
            int attIdx = 0;
            for(int i = 0; i < attSaStrDataType.size();i++){
                pw.write("\tprivate " + attSaStrDataType.get(i) + " att" + attIdx++ + ";\n");
            }
            //pa sad svi att
            for(int i = 0; i < ccAtributi.size();i++){
                pw.write("\t"+attPrivatnost.get(i)+" "+ccAtributi.get(i).getDataType() + " " +ccAtributi.get(i).getAttName()+";\n");
            }
            //od vani metode
            if(metodeRoditelja != null){
                pw.write("\n\t//metode nadklase: \n");
                for(int i = 0; i < metodeRoditelja.size();i++){ //sve nasledjene met
                    pw.write("\t@Override\n");
                    pw.write("\t" + privRod.get(i) + " void " + imeMetRod.get(i) + "(");
                    if(argsRod.get(i) != null){
                        int cx = 0;
                        for(int j = 0; j < argsRod.get(i).length;j++){
                            if(j != 0)pw.write(", ");
                            pw.write(argsRod.get(i)[j] + " arg" + cx++);
                        }
                    }
                    pw.write(") { }\n");
                }
            }
            //pa sve metode
            pw.write("\n\t//metode:\n");
            for(int i = 0; i < ccMetode.size();i++){ //sve metode
                pw.write("\t" + privatnost.get(i) +" void "+ imeMetode.get(i) + "(");
                if(argumenti.get(i) != null){ //da se ispisu svi argumenti
                    int cx = 0;
                    for(int j = 0;j < argumenti.get(i).length;j++){
                        if(j != 0)pw.write(", ");
                        pw.write(argumenti.get(i)[j] + " arg" + cx++);
                    }
                }
                pw.write(") { }\n");
            }

            pw.write("\n}");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void pisiMyInterface(MyInterface mi, String path){
        String loc = path + File.separator + mi.getIme().replace(" ", "") + ".java";
        ArrayList<String> cc = new ArrayList<>();
        for(int i = 0; i < mi.getContents().size();i++)cc.add(mi.getContents().get(i).getContent());
        ArrayList<String> privatnost = new ArrayList<>();ArrayList<String> imeMetode = new ArrayList<>();ArrayList<String[]> argumenti = new ArrayList<>();
        for(int i = 0; i < cc.size();i++){
            //privatnost
            privatnost.add(privPub(cc.get(i).charAt(0)));
            int idxOd = cc.get(i).indexOf('('), idxDo = cc.get(i).indexOf(')');

            if(idxOd < idxDo -1){ //aka samo ako postoje argumenti
                String args = cc.get(i).substring(idxOd+1,idxDo);
                String[] ar = args.split(",");
                argumenti.add(ar);
            }else{
                argumenti.add(null); //da ako nema argumenata samo vrati null
            }
            String imeM = cc.get(i).substring(1,idxOd);
            imeMetode.add(imeM);
        }
        //deo za dodavanje metoda sa strane:
        Interclass roditelj = null;
        for(ClassyNode cn: ((Diagram)mi.getParent()).getChildren()){
            if(cn instanceof Generalizacija){
                if(((Generalizacija) cn).getFrom().equals(mi)){
                    //generalizacija
                    roditelj = ((Generalizacija) cn).getTo();
                }
            }
        }
        ArrayList<String> privRod = null; ArrayList<String> imeMetRod = null;
        ArrayList<String[]> argsRod = null; ArrayList<String> metodeRoditelja = null;
        if(roditelj != null){
            privRod = new ArrayList<>(); imeMetRod = new ArrayList<>(); argsRod = new ArrayList<>();
            metodeRoditelja = new ArrayList<>();
            for(ClassContent cco: roditelj.getContents()) if(cco instanceof ClassMethod)metodeRoditelja.add(cco.getContent());

            for(int i = 0; i < metodeRoditelja.size();i++){
                privRod.add(privPub(metodeRoditelja.get(i).charAt(0)));
                int idxOd = metodeRoditelja.get(i).indexOf('('), idxDo = metodeRoditelja.get(i).indexOf(')');
                if(idxOd < idxDo -1){
                    String args =  metodeRoditelja.get(i).substring(idxOd + 1, idxDo);
                    String[] ar = args.split(",");
                    argsRod.add(ar);
                }else{
                    argsRod.add(null);
                }
                String imeM = metodeRoditelja.get(i).substring(1, idxOd);
                imeMetRod.add(imeM);
            }
        }

        try(PrintWriter pw = new PrintWriter(loc)){
            //logika za interfejs
            pw.write("package " + loc + "\n\n");
            pw.write("public interface " + mi.getIme().replace(" ", ""));
            if(roditelj != null)  pw.write(" extends " + roditelj.getIme().replace(" ", ""));
            pw.write(" {\n\n");
            //metode sa strane:
            if(metodeRoditelja != null){
                pw.write("\n\t//metode nadklase: \n");
                for(int i = 0; i < metodeRoditelja.size();i++){ //sve nasledjene met
                    pw.write("\t@Override\n");
                    pw.write("\t" + privRod.get(i) + " void " + imeMetRod.get(i) + "(");
                    if(argsRod.get(i) != null){
                        int cx = 0;
                        for(int j = 0; j < argsRod.get(i).length;j++){
                            if(j != 0)pw.write(", ");
                            pw.write(argsRod.get(i)[j] + " arg" + cx++);
                        }
                    }
                    pw.write(");\n");
                }
                pw.write("\n");
            }
            //sve metode
            pw.write("\t//metode:\n\n");
            for(int i = 0; i < cc.size();i++){
                pw.write("\t" + privatnost.get(i) +" void "+ imeMetode.get(i) + "(");
                if(argumenti.get(i) != null){ //da se ispisu svi argumenti
                    int cx = 0;
                    for(int j = 0;j < argumenti.get(i).length;j++){
                        if(j != 0)pw.write(", ");
                        pw.write(argumenti.get(i)[j] + " arg" + cx++);
                    }
                }
                pw.write(");\n");
            }
            pw.write("\n}");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void pisiMyEnum(MyEnum me, String path){
        String loc = path + File.separator + me.getIme().replace(" ", "") + ".java";
        ArrayList<String> enumi = new ArrayList<>();
        for(int i = 0; i < me.getContents().size();i++)enumi.add(me.getContents().get(i).getContent());

        try(PrintWriter pw = new PrintWriter(loc)){
            pw.write("package " + loc + "\n\n");
            pw.write("public enum " + me.getIme().replace(" ", "") + " {\n\t");
            boolean flag = false;
            for(int i = 0; i < enumi.size();i++){
                flag = true;
                if(i != 0)pw.write(", ");
                pw.write(enumi.get(i));
            }
            if(flag)pw.write(";");
            pw.write("\n}");


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //za metode:
    private String privPub(char c){
        return switch (c) {
            case '+' -> "public";
            case '-' -> "private";
            case '#' -> "protected";
            default -> "";
        };
    }

}
