package repository.factory;

import repository.composite.ClassyNode;
import repository.implementation.ProjectExplorer;

public class ProjectExpFactory implements NodeFactory {
    public ClassyNode createNode(String name, ClassyNode parent) {
        return new ProjectExplorer(name, null);
    }
}
