package repository.implementation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.NoArgsConstructor;
import lombok.Setter;
import observer.IPublisher;
import observer.ISubscriber;
import observer.Notification;
import observer.NotificationType;
import repository.composite.ClassyNode;
import repository.composite.ClassyNodeComposite;

import java.util.ArrayList;
@Setter
@NoArgsConstructor
@JsonTypeName("Package")
public class Package extends ClassyNodeComposite implements IPublisher {
    @JsonIgnore
    private transient ArrayList<ISubscriber> subs;
    public Package(String ime, ClassyNode parent) {
        super(ime, parent);
        this.subs = new ArrayList<>();
    }

    @Override
    public void addChild(ClassyNode node) {
        if(node != null && (node instanceof Package || node instanceof Diagram)){
            if(node instanceof Package){
                Package p = (Package) node;
                if(!this.getChildren().contains(p))
                    this.getChildren().add(p);
            }else{
                Diagram p = (Diagram) node;
                if(!this.getChildren().contains(p)) {
                    this.getChildren().add(p);
                    notifySubscribers(new Notification(p, NotificationType.ADDED_CHILD));
                }
            }
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
        }else if(node instanceof Diagram){
            Diagram d = (Diagram) node;
            if(this.getChildren().contains(d)){
                //notifySubscribers(new Notification(d, NotificationType.REMOVED_CHILD)); //pre
                this.getChildren().remove(d);
                notifySubscribers(new Notification(d, NotificationType.REMOVED_CHILD));
            }
        }
        else{
            //neki error pp
        }
    }

    @Override
    public void addSubscriber(ISubscriber iSubscriber) {
        if(!this.subs.contains(iSubscriber))this.subs.add(iSubscriber);
        //notifySubscribers(new Notification(this, NotificationType.CHANGE_PARENT));
    }

    @Override
    public void removeSubscriber(ISubscriber iSubscriber) {
        if(this.subs.contains(iSubscriber))this.subs.remove(iSubscriber);
    }

    @Override
    public void notifySubscribers(Notification notification) {
        for(ISubscriber sub: this.subs){
            sub.update(notification);
        }
    }
}
