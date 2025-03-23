package painters;

import lombok.Getter;
import repository.implementation.diagramElements.elements.DiagramElement;

import java.awt.*;
@Getter
public abstract class ElementPainter {
    protected DiagramElement model;

    public ElementPainter(DiagramElement model){
        this.model = model;
    }

    public abstract void draw(Graphics2D g);

    public abstract boolean elementAt(Point pos);

}
