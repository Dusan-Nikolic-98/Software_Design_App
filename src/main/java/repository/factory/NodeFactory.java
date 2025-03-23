package repository.factory;

import repository.composite.ClassyNode;

public interface NodeFactory {
    ClassyNode createNode(String name, ClassyNode parent);
}
