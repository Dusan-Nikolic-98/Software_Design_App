package gui.swing.tree;

import gui.swing.tree.model.ClassyTreeItem;
import gui.swing.tree.view.ClassyTreeView;
import repository.composite.ClassyNode;
import repository.composite.ClassyNodeComposite;
import repository.implementation.Diagram;
import repository.implementation.Project;
import repository.implementation.ProjectExplorer;

public interface ClassyTree {
    ClassyTreeView generateTree(ProjectExplorer projectExplorer);
    void addChild(ClassyTreeItem parent, int flag);
    void removeChild(ClassyTreeItem child);
    ClassyTreeItem getSelectedNode();
    void addChildToDiag(ClassyNodeComposite diag, ClassyNode child);
    void removeChildFromDiag(ClassyNodeComposite diag, ClassyNode child);
    void changeElementNames();
    void loadProject(Project proj);
    void loadDiagram(Diagram diag);
}
