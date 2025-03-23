package repository.implementation.diagramElements.elements.connectionImplementation;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import repository.composite.ClassyNode;
import repository.implementation.diagramElements.elements.Connection;

import java.awt.*;
@Getter
@NoArgsConstructor
@JsonTypeName("Generalizacija")
public class Generalizacija extends Connection {
    public Generalizacija(String ime, ClassyNode parent) {
        super(ime, parent);
        setStartValues();
    }
    private void setStartValues(){
        this.setFrom(null);
        this.setTo(null);
        this.stroke = new BasicStroke(3f);
        this.paint = new Color(0,0,0);
    }
}
