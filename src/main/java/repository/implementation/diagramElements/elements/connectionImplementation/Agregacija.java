package repository.implementation.diagramElements.elements.connectionImplementation;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import repository.composite.ClassyNode;
import repository.implementation.diagramElements.elements.Connection;

import java.awt.*;
@Getter
@NoArgsConstructor
@JsonTypeName("Agregacija")
public class Agregacija extends Connection {

    public Agregacija(String ime, ClassyNode parent) {
        super(ime, parent);
        setStartValues();
    }
    public Agregacija(String ime, ClassyNode parent, Paint paint, Stroke stroke) {
        super(ime, parent, paint, stroke);
    }

    private void setStartValues(){
        this.setFrom(null);
        this.setTo(null);
        this.stroke = new BasicStroke(3f);
        this.paint = new Color(0,0,0);
    }
}
