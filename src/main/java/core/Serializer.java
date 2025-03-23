package core;

import repository.implementation.Diagram;
import repository.implementation.Project;

import java.io.File;

public interface Serializer {

    Project loadProject(File file);
    void saveProject(Project proj);
    Diagram loadDiagram(File file, Diagram diag);
    void saveDiagram(Diagram diag);

}
