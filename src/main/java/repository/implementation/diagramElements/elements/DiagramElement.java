package repository.implementation.diagramElements.elements;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import repository.composite.ClassyNode;
import serializer.PaintDeserializer;
import serializer.PaintSerializer;
import serializer.StrokeDeserializer;
import repository.implementation.diagramElements.elements.Interclass;
import repository.implementation.diagramElements.elements.Connection;

import java.awt.*;
@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "DiagramElement")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Connection.class, name = "Connection"),
        @JsonSubTypes.Type(value = Interclass.class, name = "Interclass")
})
public abstract class DiagramElement extends ClassyNode {
    @JsonSerialize(using = PaintSerializer.class)
    @JsonDeserialize(using = PaintDeserializer.class)
    protected Paint paint;
    @JsonDeserialize(using = StrokeDeserializer.class)
    protected Stroke stroke;
    protected String name;
    protected String description;


    public DiagramElement(String ime, ClassyNode parent) {
        super(ime, parent);
    }
    public DiagramElement(String ime, ClassyNode parent, Paint paint, Stroke stroke){
        super(ime, parent);
        this.paint = paint;
        this.stroke = stroke;
    }
}
