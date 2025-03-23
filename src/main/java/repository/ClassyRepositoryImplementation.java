package repository;

import core.ClassyRepository;
import observer.ISubscriber;
import observer.Notification;
import observer.NotificationType;
import repository.composite.ClassyNode;
import repository.composite.ClassyNodeComposite;
import repository.implementation.Diagram;
import repository.implementation.Package;
import repository.implementation.ProjectExplorer;

import java.util.ArrayList;

public class ClassyRepositoryImplementation implements ClassyRepository {
    private ProjectExplorer projectExplorer;
    private ArrayList<ISubscriber> subscribers;
    public ClassyRepositoryImplementation(){
        projectExplorer = new ProjectExplorer("project exp", null);
        subscribers = new ArrayList<>();
    }
    @Override
    public ProjectExplorer getProjectExplorer() {return projectExplorer;}

    //treba da ima polje project explorera jer treba da ima metodu koja ga vraca

    @Override
    public boolean isChildOf(ClassyNode parent, ClassyNode child){
        if(child == null || parent == null)return false;
        if(child.equals(parent))return true;
        while(child.getParent() != null){
            child = child.getParent();
            if(child.equals(parent))return true;
        }
        return false;
    }
}
