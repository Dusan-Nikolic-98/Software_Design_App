package gui.swing.tree.model;

import lombok.Getter;
import lombok.Setter;
import repository.composite.ClassyNode;

import javax.swing.tree.DefaultMutableTreeNode;
@Getter
@Setter
public class ClassyTreeItem extends DefaultMutableTreeNode {
    //ima instancu objekta koji predstavlja
    ClassyNode classyNode;
    public ClassyTreeItem(ClassyNode classyNode){this.classyNode = classyNode;}
    @Override
    public String toString(){return classyNode.getIme();}

    public void setIme(String ime){this.classyNode.setIme(ime);}

}
