package repository.composite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import repository.implementation.Diagram;
import repository.implementation.Package;
import repository.implementation.Project;
import repository.implementation.diagramElements.elements.DiagramElement;

@Getter
@Setter
@NoArgsConstructor

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "ClassyNode")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClassyNodeComposite.class, name = "ClassyNodeComposite"),
        @JsonSubTypes.Type(value = DiagramElement.class, name = "DiagramElement")

})
public abstract class ClassyNode {
    private String ime;
    @ToString.Exclude
    @JsonIgnore
    private transient ClassyNode parent;


    public ClassyNode(String ime, ClassyNode parent){
        this.ime = ime;
        this.parent = parent;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ClassyNode) {
            ClassyNode otherObj = (ClassyNode) obj;
            return this.getIme().equals(otherObj.getIme());
        }
        return false;
    }
}
