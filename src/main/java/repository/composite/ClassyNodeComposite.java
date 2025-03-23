package repository.composite;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import repository.implementation.Diagram;
import repository.implementation.Package;
import repository.implementation.Project;
import repository.implementation.ProjectExplorer;
import repository.implementation.diagramElements.elements.Connection;
import repository.implementation.diagramElements.elements.DiagramElement;
import repository.implementation.diagramElements.elements.Interclass;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "ClassyNodeComposite")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ProjectExplorer.class, name = "ProjectExplorer"),
        @JsonSubTypes.Type(value = Project.class, name = "Project"),
        @JsonSubTypes.Type(value = Package.class, name = "Package"),
        @JsonSubTypes.Type(value = Diagram.class, name = "Diagram"),
        @JsonSubTypes.Type(value = Connection.class, name = "Connection"),
        @JsonSubTypes.Type(value = Interclass.class, name = "Interclass")
})
public abstract class ClassyNodeComposite extends ClassyNode {

    List<ClassyNode> children;
    public ClassyNodeComposite(String ime, ClassyNode parent) {
        super(ime, parent);
        this.children = new ArrayList<>();
    }
    public abstract void addChild(ClassyNode node);
    public abstract void removeChild(ClassyNode node);

    public ClassyNode getChildByName(String name) {
        for (ClassyNode child: this.getChildren()) {
            if (name.equals(child.getIme())) {
                return child;
            }
        }
        return null;
    }
}
