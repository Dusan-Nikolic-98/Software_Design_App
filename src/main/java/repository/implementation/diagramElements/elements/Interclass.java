package repository.implementation.diagramElements.elements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import observer.IPublisher;
import observer.ISubscriber;
import repository.composite.ClassyNode;
import repository.implementation.diagramElements.elements.classContent.ClassContent;
import repository.implementation.diagramElements.elements.interImplementation.MyClass;
import repository.implementation.diagramElements.elements.interImplementation.MyEnum;
import repository.implementation.diagramElements.elements.interImplementation.MyInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "Interclass")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MyClass.class, name = "MyClass"),
        @JsonSubTypes.Type(value = MyInterface.class, name = "MyInterface"),
        @JsonSubTypes.Type(value = MyEnum.class, name = "MyEnum")
})
public abstract class Interclass extends DiagramElement implements IPublisher { //kockice
    private String name;
    private boolean isVisible;
    protected Point position;
    protected Dimension size;
    ArrayList<ClassContent> contents = new ArrayList<>();
    @JsonIgnore
    private transient ArrayList<ISubscriber> subs;

    public Interclass(String ime, ClassyNode parent) {
        super(ime, parent);
        //this.contents = new ArrayList<>();
        this.position = null;
        this.subs = new ArrayList<>();
    }
        public Interclass(String ime, ClassyNode parent, Paint paint, Stroke stroke, Point position, Dimension size) {
        super(ime, parent, paint, stroke);
        this.size = size;
        this.position = position;
    }

    //novo za dod
    public void addContent(ClassContent content) {
        getContents().add(content);
        setContents(getContents());
    }

    public void removeContent(ClassContent content){
        getContents().remove(content);
        setContents(getContents());
    }
    public void removeCCByName(String s) {
        Iterator<ClassContent> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            ClassContent cc = iterator.next();
            if (cc.getContent().equalsIgnoreCase(s)) {
                iterator.remove();
            }
        }
    }

}
