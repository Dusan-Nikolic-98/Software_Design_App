package repository.implementation;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import observer.Notification;
import observer.NotificationType;
import repository.composite.ClassyNode;
import repository.composite.ClassyNodeComposite;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeName("Project")
public class Project extends ClassyNodeComposite {
    private String imeAutora;
    private String putanja;
    private boolean isChanged;
    public Project(String ime, ClassyNode parent) {
        super(ime, parent);
        isChanged = true;
    }
    public Project(String ime, ClassyNode parent, String imeAutora, String putanja){
        super(ime, parent);
        this.imeAutora = imeAutora;
        this.putanja = putanja;
        isChanged = true;
    }

    @Override
    public void addChild(ClassyNode node) {
        if(node != null && node instanceof Package){
            Package p = (Package) node;
            if(!this.getChildren().contains(p))
                this.getChildren().add(p);
        }
    }

    @Override
    public void removeChild(ClassyNode node) {
        if(node instanceof Package){
            Package p = (Package) node;
            if(this.getChildren().contains(p)){
                p.notifySubscribers(new Notification(p, NotificationType.REMOVED_CHILD_PACKAGE));
                this.getChildren().remove(p);
            }
        }
    }
    public void updateDelete(){
        for(ClassyNode p: this.getChildren()){
            if(p instanceof Package){
                Package pack = (Package) p;
                pack.notifySubscribers(new Notification(p, NotificationType.REMOVED_CHILD_PACKAGE));
            }
        }
    }
}
