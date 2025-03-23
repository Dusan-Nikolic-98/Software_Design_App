package repository.factory;

import repository.composite.ClassyNode;
import repository.implementation.Diagram;

public class DiagramFactory implements NodeFactory {
    @Override
    public ClassyNode createNode(String name, ClassyNode parent) {
        return new Diagram(name, parent);
    }
}