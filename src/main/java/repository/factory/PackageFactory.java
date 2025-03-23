package repository.factory;

import repository.composite.ClassyNode;
import repository.implementation.Package;

public class PackageFactory implements NodeFactory {

    @Override
    public ClassyNode createNode(String name, ClassyNode parent) {
        return new Package(name, parent);
    }
}
