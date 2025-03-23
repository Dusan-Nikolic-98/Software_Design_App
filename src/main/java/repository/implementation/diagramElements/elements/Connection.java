package repository.implementation.diagramElements.elements;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import repository.composite.ClassyNode;
import repository.implementation.diagramElements.elements.connectionImplementation.*;

import java.awt.*;
@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "Connection")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Agregacija.class, name = "Agregacija"),
        @JsonSubTypes.Type(value = Generalizacija.class, name = "Generalizacija"),
        @JsonSubTypes.Type(value = Kompozicija.class, name = "Kompozicija"),
        @JsonSubTypes.Type(value = TempConnection.class, name = "TempConnection"),
        @JsonSubTypes.Type(value = Zavisnost.class, name = "Zavisnost")
})
public abstract class Connection extends DiagramElement{
    private Interclass from;
    private Interclass to;

    public Connection(String ime, ClassyNode parent) {
        super(ime, parent);
    }

    public Connection(String ime, ClassyNode parent, Paint paint, Stroke stroke) {
        super(ime, parent, paint, stroke);
    }

}
