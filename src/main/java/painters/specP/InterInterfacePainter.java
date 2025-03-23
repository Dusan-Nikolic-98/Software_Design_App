package painters.specP;

import lombok.Getter;
import painters.ElementPainter;
import repository.implementation.diagramElements.elements.DiagramElement;
import repository.implementation.diagramElements.elements.Interclass;
import repository.implementation.diagramElements.elements.classContent.ClassAttribute;
import repository.implementation.diagramElements.elements.classContent.ClassContent;
import repository.implementation.diagramElements.elements.classContent.ClassMethod;

import java.awt.*;
@Getter
public class InterInterfacePainter extends ElementPainter {
    protected Shape shape;
    public InterInterfacePainter(DiagramElement model) {
        super(model);
    }

    @Override
    public void draw(Graphics2D g) {
        if (model instanceof Interclass) {
            FontMetrics fontMetrics = g.getFontMetrics();

            int strH = fontMetrics.getHeight();

            shape = new Rectangle(((Interclass) model).getPosition(), ((Interclass) model).getSize()); //diagelement mu je model
            //shape = new Rectangle(((Interclass) model).getPosition(), dim);
            g.setPaint(this.model.getPaint());
            g.setStroke(this.model.getStroke());
            g.draw(getShape());
            g.setPaint(Color.WHITE);
            g.fill(getShape()); //moze i .fill ako hocu da ga popunim nekom bojom ili draw ali onda ne bi trebalo 2x draw da bude
            g.setPaint(Color.BLACK);
            g.drawString(model.getName(), ((Interclass) model).getPosition().x + shape.getBounds().width/2 -
                    fontMetrics.stringWidth(model.getName())/2, ((Interclass) model).getPosition().y + 10); //da bude centriran
            g.drawString("i", ((Interclass) model).getPosition().x + 2, ((Interclass) model).getPosition().y + 8);

            //linija
            g.drawLine(((Interclass) model).getPosition().x, ((Interclass) model).getPosition().y + 20,
                    ((Interclass) model).getPosition().x + shape.getBounds().width,((Interclass) model).getPosition().y + 20);
            //i sad ovde crtam sve cc koji postoje:
            int cy = ((Interclass) model).getPosition().y + 15;
            for(ClassContent cc: ((Interclass) model).getContents()){
                if(cc instanceof ClassMethod){
                    //printaj sve metode
                    cy += strH;
                    g.drawString(cc.getContent(), ((Interclass) model).getPosition().x, cy);

                }
            }

        }
    }

    @Override
    public boolean elementAt(Point pos) {
        return this.getShape().contains(pos);
    }
}
