package gui.swing.tree;

import core.ApplicationFramework;
import errorPart.enums.EventType;
import gui.swing.tree.model.ClassyTreeItem;
import gui.swing.tree.view.ClassyTreeView;
import repository.composite.ClassyNode;
import repository.composite.ClassyNodeComposite;
import repository.implementation.Diagram;
import repository.implementation.Package;
import repository.implementation.Project;
import repository.implementation.ProjectExplorer;
import repository.implementation.diagramElements.elements.Connection;
import repository.implementation.diagramElements.elements.interImplementation.MyClass;
import utils.Utils;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;

public class ClassyTreeImplementation implements ClassyTree{

    private ClassyTreeView treeView;
    private DefaultTreeModel treeModel;
    private int selectedVal = -1;
    private int projectIdx = 1;
    private int packageIdx = 1;
    private int diagramIdx = 1;
    private boolean deleteQ = false;

    @Override
    public ClassyTreeView generateTree(ProjectExplorer projectExplorer) {
        ClassyTreeItem root = new ClassyTreeItem(projectExplorer);
        treeModel = new DefaultTreeModel(root);
        treeView = new ClassyTreeView(treeModel);
        //treeModel.getRoot()
        return treeView;
    }

    @Override
    public void addChild(ClassyTreeItem parent, int flag) {

        if (!(parent.getClassyNode() instanceof ClassyNodeComposite)) {
            //takodje bag ako hocu plusic na diagram
            return;
        }

        ClassyNode child = createChild(parent.getClassyNode(), flag);
        parent.add(new ClassyTreeItem(child));

        ((ClassyNodeComposite) parent.getClassyNode()).addChild(child);
        treeView.expandPath(treeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public void addChildToDiag(ClassyNodeComposite diag, ClassyNode child) {
        ClassyTreeItem curr = giveMeThe((ClassyTreeItem) treeModel.getRoot(), diag);
        if(curr != null){
            curr.add(new ClassyTreeItem(child));
            treeView.expandPath(treeView.getSelectionPath());
            SwingUtilities.updateComponentTreeUI(treeView);
        }
    }

    @Override
    public void removeChildFromDiag(ClassyNodeComposite diag, ClassyNode child) {
        ClassyTreeItem curr = giveMeThe((ClassyTreeItem) treeModel.getRoot(), diag);
        ClassyTreeItem currchild = giveMeThe((ClassyTreeItem) treeModel.getRoot(), child);
        treeModel.removeNodeFromParent(currchild);
        SwingUtilities.updateComponentTreeUI(treeView);

    }

    @Override
    public void changeElementNames() {
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    private ClassyTreeItem giveMeThe(ClassyTreeItem curr, ClassyNode thisOne){
        if(curr.getClassyNode().equals(thisOne))return curr;
        if(!(curr.getClassyNode() instanceof ClassyNodeComposite))return curr.getClassyNode().equals(thisOne)? curr:null;
        int childI = curr.getChildCount();

        for (int i = 0; i < childI; i++) {

            ClassyTreeItem childItem = (ClassyTreeItem) curr.getChildAt(i);
            ClassyTreeItem result = giveMeThe(childItem, thisOne);
            if (result != null) {
                return result;
            }
        }

        return null;
    }


    @Override
    public void removeChild(ClassyTreeItem child) {
        if(child.getClassyNode().getParent() == null){
            //neki bag info
            ApplicationFramework.getInstance().getMessageGenerator().generateMessage(EventType.CANNOT_REMOVE_ROOT);
            System.out.println("ne mozes da obrises ovo");
        }else{
            ClassyNodeComposite parent = (ClassyNodeComposite) child.getClassyNode().getParent();

            parent.removeChild(child.getClassyNode());

            treeModel.removeNodeFromParent(child);
            SwingUtilities.updateComponentTreeUI(treeView); //da se apdejtuje na kraju view

        }
    }

    @Override
    public ClassyTreeItem getSelectedNode() {
        return (ClassyTreeItem) treeView.getLastSelectedPathComponent();
    }

    private ClassyNode createChild(ClassyNode parent, int flag) {
        if (parent instanceof ProjectExplorer) {
            projectIdx = 1;
            boolean joj;
            while(true){
                joj = true;
                for(ClassyNode cn: ((ProjectExplorer) parent).getChildren()){
                    if (cn.getIme().equalsIgnoreCase("Project " + projectIdx)) {
                        joj = false;
                        break;
                    }
                }
                if(joj)break;
                else projectIdx++;
            }
            return Utils.getNodeFactory("project").createNode("Project " + projectIdx++, parent);
        }else if(parent instanceof Project){
            packageIdx = 1;
            while(true){

                String currName = "Package" + packageIdx;
                if(moguceIme((ClassyNodeComposite) parent, currName))break;
                else packageIdx++;

            }
            return Utils.getNodeFactory("package").createNode("Package" + packageIdx++, parent);
        }else if(parent instanceof Package){
            if(flag == 1){
                diagramIdx = 1;
                while(true){

                    String currName = "Diagram" + diagramIdx;
                    if(moguceIme(giveMeProjectParent(parent), currName))break;
                    else diagramIdx++;
                }
                return Utils.getNodeFactory("diagram").createNode("Diagram" + diagramIdx++, parent);
            }else{
                packageIdx = 1;
                while(true){

                    String currName = "Package" + packageIdx;
                    if(moguceIme(giveMeProjectParent(parent), currName))break;
                    else packageIdx++;

                }
                return Utils.getNodeFactory("package").createNode("Package" + packageIdx++,parent);
            }

        }else if(parent instanceof Diagram){
            //pa sad ako je diagram napravi mu novo dete jbg
            switch (flag){
                case 1:{ //
                    return new MyClass("mcyclass", parent);
                    //System.out.println(1);
                }
                case 2:{
                    System.out.println(2);
                }
                case 3:{

                    System.out.println(3);
                }
                case 4:{

                    System.out.println(4);
                }
                case 5:{

                    System.out.println(5);
                }
            }

        }
        return null;
    }
    //za imenovanje:
    private boolean moguceIme(ClassyNodeComposite parent, String name){
        for(ClassyNode child: parent.getChildren()){
            if(child.getIme().equals(name))return false;
            if(child instanceof Package){
                if(!moguceIme((ClassyNodeComposite) child, name))return false;
            }
        }
        return true;
    }
    private Project giveMeProjectParent(ClassyNode child){
        while(child != null && !(child instanceof Project))child = child.getParent();
        return child != null? (Project) child: null;
    }

    //TODO deo za serijalizaciju
    @Override
    public void loadProject(Project p) {
        //ovde treba da ga metnem na drvo :D
        if(giveMeThe((ClassyTreeItem) treeModel.getRoot(), p) == null){
            //u prevodu ako nije vec ucitan
            ((ClassyTreeItem) treeModel.getRoot()).add(new ClassyTreeItem(p));
            if(((ClassyTreeItem) treeModel.getRoot()).getClassyNode() instanceof ClassyNodeComposite){
                ((ClassyNodeComposite) ((ClassyTreeItem) treeModel.getRoot()).getClassyNode()).addChild(p);
                treeView.expandPath(treeView.getSelectionPath());
                SwingUtilities.updateComponentTreeUI(treeView);

                ArrayList<ClassyNode> children = (ArrayList<ClassyNode>) p.getChildren();
                //i sad i sva deca da se dodaju na drvo:
                ubaciSvuDecu(p, children);
            }
        }
    }

    private void ubaciSvuDecu(ClassyNodeComposite parent, ArrayList<ClassyNode> children){
        for(ClassyNode cn: children){
            if(!(cn instanceof Connection)){
                addChildToDiag(parent, cn);

                if(cn instanceof ClassyNodeComposite){
                    ArrayList<ClassyNode> chil = (ArrayList<ClassyNode>) ((ClassyNodeComposite) cn).getChildren();
                    ubaciSvuDecu((ClassyNodeComposite) cn, chil);
                }
            }
        }
    }

    @Override
    public void loadDiagram(Diagram diag) {

        ArrayList<ClassyNode> children = (ArrayList<ClassyNode>) diag.getChildren();
        ubaciSvuDecu(diag, children);
    }



}
