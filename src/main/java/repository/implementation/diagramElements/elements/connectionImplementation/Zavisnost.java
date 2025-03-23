package repository.implementation.diagramElements.elements.connectionImplementation;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import repository.composite.ClassyNode;
import repository.implementation.diagramElements.elements.Connection;

import java.awt.*;
@Getter
@NoArgsConstructor
@JsonTypeName("Zavisnost")
public class Zavisnost extends Connection {
    public Zavisnost(String ime, ClassyNode parent) {
        super(ime, parent);
        setStartValues();
    }
    private void setStartValues(){
        this.setFrom(null);
        this.setTo(null);
        float[] dashPattern = {5.0f, 5.0f};
        this.stroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f);
        this.paint = new Color(0,0,0);
    }
}
