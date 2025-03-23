package repository.implementation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.NoArgsConstructor;
import lombok.Setter;
import observer.IPublisher;
import observer.ISubscriber;
import observer.Notification;
import observer.NotificationType;
import repository.composite.ClassyNode;
import repository.composite.ClassyNodeComposite;
import repository.implementation.diagramElements.elements.Connection;
import repository.implementation.diagramElements.elements.DiagramElement;
import repository.implementation.diagramElements.elements.Interclass;
import repository.implementation.diagramElements.elements.connectionImplementation.*;
import repository.implementation.diagramElements.elements.interImplementation.MyClass;
import repository.implementation.diagramElements.elements.interImplementation.MyEnum;
import repository.implementation.diagramElements.elements.interImplementation.MyInterface;

import java.util.ArrayList;
import java.util.Iterator;
@Setter
@NoArgsConstructor
@JsonTypeName("Diagram")
public class Diagram extends ClassyNodeComposite implements IPublisher {
    @JsonIgnore
    private transient ArrayList<ISubscriber> subs;
    public Diagram(String ime, ClassyNode parent) {
        super(ime, parent);
        this.subs = new ArrayList<>();
    }

    @Override
    public void addChild(ClassyNode node) {
        if(node != null && node instanceof DiagramElement){
            DiagramElement de = (DiagramElement) node;
            if(de instanceof MyClass){
                if(!this.getChildren().contains(de)) {
                    this.getChildren().add(de);
                    MyClass mc = (MyClass) de;
                    notifySubscribers(new Notification(mc, NotificationType.ADDED_CHILD));
                }
            }else if(de instanceof Agregacija){
                if(!this.getChildren().contains(de)) {
                    this.getChildren().add(de);
                    Agregacija mc = (Agregacija) de;
                    notifySubscribers(new Notification(mc, NotificationType.ADDED_CHILD));
                }
            }else if(de instanceof TempConnection){
                if(!this.getChildren().contains(de)) {
                    this.getChildren().add(de);
                    TempConnection mc = (TempConnection) de;
                    notifySubscribers(new Notification(mc, NotificationType.ADDED_CHILD));
                }
            }else if(de instanceof MyEnum){
                if(!this.getChildren().contains(de)) {
                    this.getChildren().add(de);
                    MyEnum mc = (MyEnum) de;
                    notifySubscribers(new Notification(mc, NotificationType.ADDED_CHILD));
                }
            }else if(de instanceof MyInterface){
                if(!this.getChildren().contains(de)) {
                    this.getChildren().add(de);
                    MyInterface mc = (MyInterface) de;
                    notifySubscribers(new Notification(mc, NotificationType.ADDED_CHILD));
                }
            }else if(de instanceof Generalizacija){
                if(!this.getChildren().contains(de)) {
                    this.getChildren().add(de);
                    Generalizacija mc = (Generalizacija) de;
                    notifySubscribers(new Notification(mc, NotificationType.ADDED_CHILD));
                }
            }else if(de instanceof Kompozicija){
                if(!this.getChildren().contains(de)) {
                    this.getChildren().add(de);
                    Kompozicija mc = (Kompozicija) de;
                    notifySubscribers(new Notification(mc, NotificationType.ADDED_CHILD));
                }
            }else if(de instanceof Zavisnost){
                if(!this.getChildren().contains(de)) {
                    this.getChildren().add(de);
                    Zavisnost mc = (Zavisnost) de;
                    notifySubscribers(new Notification(mc, NotificationType.ADDED_CHILD));
                }
            }
        }
    }


    @Override
    public void removeChild(ClassyNode node) {
        if(node instanceof DiagramElement){
            DiagramElement de = (DiagramElement) node;
            if(this.getChildren().contains(de)){
                //todo mozda ovde logika za brisanje veza ako je interclass
                if(de instanceof Interclass){
                    Iterator<ClassyNode> iterator = this.getChildren().iterator();
                    while (iterator.hasNext()) {
                        ClassyNode delem = iterator.next();
                        if (delem instanceof Connection &&
                                (((Connection) delem).getFrom().equals(de) || ((Connection) delem).getTo().equals(de))) {
                            notifySubscribers(new Notification(delem, NotificationType.REMOVED_CHILD));
                            iterator.remove();
                        }
                    }

                }
                notifySubscribers(new Notification(de, NotificationType.REMOVED_CHILD));
                this.getChildren().remove(de);

            }
        }
    }

    @Override
    public void addSubscriber(ISubscriber iSubscriber) {
        if(!this.subs.contains(iSubscriber))this.subs.add(iSubscriber);
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

    @Override
    public void setIme(String ime) {
        super.setIme(ime);
        if(this.subs != null)
            notifySubscribers(new Notification(this, NotificationType.CHANGE_DIAGRAM_NAME));
    }
}
