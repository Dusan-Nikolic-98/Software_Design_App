package gui.swing.tree.view;

import gui.swing.tree.controller.ClassyTreeCellEditor;
import gui.swing.tree.controller.ClassyTreeSelectionListener;
import gui.swing.tree.controller.MyMouseListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

public class ClassyTreeView extends JTree {

    public ClassyTreeView(DefaultTreeModel defaultTreeModel) {
        setModel(defaultTreeModel);
        ClassyTreeCellRenderer classyTreeCellRenderer = new ClassyTreeCellRenderer();
        addTreeSelectionListener(new ClassyTreeSelectionListener());
        setCellEditor(new ClassyTreeCellEditor(this, classyTreeCellRenderer));
        setCellRenderer(classyTreeCellRenderer);
        addMouseListener(new MyMouseListener());
        setEditable(true);
    }
}
