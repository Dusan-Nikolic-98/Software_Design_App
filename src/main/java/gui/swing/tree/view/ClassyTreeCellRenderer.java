package gui.swing.tree.view;

import gui.swing.tree.model.ClassyTreeItem;
import lombok.NoArgsConstructor;
import repository.implementation.Diagram;
import repository.implementation.Package;
import repository.implementation.Project;
import repository.implementation.ProjectExplorer;
import repository.implementation.diagramElements.elements.connectionImplementation.Agregacija;
import repository.implementation.diagramElements.elements.interImplementation.MyClass;
import repository.implementation.diagramElements.elements.interImplementation.MyEnum;
import repository.implementation.diagramElements.elements.interImplementation.MyInterface;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;
@NoArgsConstructor
public class ClassyTreeCellRenderer extends DefaultTreeCellRenderer {
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row, hasFocus);
        URL imageURL = null;

        if (((ClassyTreeItem)value).getClassyNode() instanceof ProjectExplorer) {
            imageURL = getClass().getResource("/images/projExp.png");
        } else if (((ClassyTreeItem)value).getClassyNode() instanceof Project) {
            imageURL = getClass().getResource("/images/proj.PNG");
        } else if (((ClassyTreeItem)value).getClassyNode() instanceof Package) {
            imageURL = getClass().getResource("/images/pack.png");
        } else if (((ClassyTreeItem)value).getClassyNode() instanceof Diagram) {
            imageURL = getClass().getResource("/images/diagram.png");
        } //ovde mi sad treba jos sedam else ifova al aj za pocetak 3
        else if (((ClassyTreeItem)value).getClassyNode() instanceof MyClass) {
            imageURL = getClass().getResource("/images4diags/klasa.png");
        } else if (((ClassyTreeItem)value).getClassyNode() instanceof MyEnum) {
            imageURL = getClass().getResource("/images4diags/enum.png");
        } else if (((ClassyTreeItem)value).getClassyNode() instanceof MyInterface) {
            imageURL = getClass().getResource("/images4diags/interface.png");
        }

        Icon icon = null;
        if (imageURL != null)
            icon = new ImageIcon(imageURL);
        setIcon(icon);

        return this;
    }
}
