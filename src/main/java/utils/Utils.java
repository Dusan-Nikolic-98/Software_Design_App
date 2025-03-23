package utils;

import repository.factory.DiagramFactory;
import repository.factory.NodeFactory;
import repository.factory.PackageFactory;
import repository.factory.ProjectFactory;

public class Utils {
    public static NodeFactory getNodeFactory(String type) {
        if (type.equalsIgnoreCase("project")) {
            return new ProjectFactory();
        } else if (type.equalsIgnoreCase("package")) {
            return new PackageFactory();
        } else if (type.equalsIgnoreCase("diagram")) {
            return new DiagramFactory();
        }
        return null;
    }
}