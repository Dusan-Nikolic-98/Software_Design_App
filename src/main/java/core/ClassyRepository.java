package core;

import observer.IPublisher;
import repository.composite.ClassyNode;
import repository.composite.ClassyNodeComposite;
import repository.implementation.ProjectExplorer;

public interface ClassyRepository {

    ProjectExplorer getProjectExplorer();
    boolean isChildOf(ClassyNode parent, ClassyNode child);
    //treba da ima samo metodu getRoot
}
