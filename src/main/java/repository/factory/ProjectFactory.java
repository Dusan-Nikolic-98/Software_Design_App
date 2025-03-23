package repository.factory;

import repository.composite.ClassyNode;
import repository.implementation.Project;

public class ProjectFactory implements NodeFactory {
    public ClassyNode createNode(String name, ClassyNode parent) {
        return new Project(name, parent);
    }
}
