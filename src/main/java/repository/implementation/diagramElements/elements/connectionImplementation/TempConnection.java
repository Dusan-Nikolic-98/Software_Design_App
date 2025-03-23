package repository.implementation.diagramElements.elements.connectionImplementation;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.NoArgsConstructor;
import repository.composite.ClassyNode;
import repository.implementation.diagramElements.elements.Connection;

import java.awt.*;
@NoArgsConstructor
@JsonTypeName("TempConnection")
public class TempConnection extends Connection {
    public TempConnection(String ime, ClassyNode parent) {
        super(ime, parent);
        setStartValues();
    }
    private void setStartValues(){
        this.setFrom(null);
        this.setTo(null);
        this.stroke = new BasicStroke(3f);
        this.paint = new Color(255,255,255);
    }
}
