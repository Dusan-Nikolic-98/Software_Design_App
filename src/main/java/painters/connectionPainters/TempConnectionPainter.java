package painters.connectionPainters;

import lombok.Setter;
import painters.ElementPainter;
import repository.implementation.diagramElements.elements.Connection;
import repository.implementation.diagramElements.elements.DiagramElement;

import java.awt.*;
@Setter
public class TempConnectionPainter extends ElementPainter {

    public Point tempEnd;
    public TempConnectionPainter(DiagramElement model) {
        super(model);
        tempEnd = null;
    }

    @Override
    public void draw(Graphics2D g) {

        if(model instanceof Connection){
            if(((Connection) model).getFrom() != null) {
                g.setPaint(Color.BLUE);
                g.setStroke(model.getStroke());
                int x1 = ((Connection) model).getFrom().getPosition().x;
                int y1 = ((Connection) model).getFrom().getPosition().y;

                x1 = x1 +((int)((Connection) model).getFrom().getSize().getWidth())/2;
                y1 = y1 +((int)((Connection) model).getFrom().getSize().getHeight())/2;

                int x2 = tempEnd.x;
                int y2 = tempEnd.y;
                g.drawLine(x1, y1, x2, y2);
                g.setPaint(Color.GREEN);
                drawArrowHead(g, x1, y1, x2, y2);
            }
        }
    }
    private void drawArrowHead(Graphics2D g, int x1, int y1, int x2, int y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        double arrowLength = 15.0; // You can adjust the arrow length as needed

        // Coordinates of arrowhead points
        int x3 = x2 - (int) (arrowLength * Math.cos(angle - Math.toRadians(30)));
        int y3 = y2 - (int) (arrowLength * Math.sin(angle - Math.toRadians(30)));

        int x4 = x2 - (int) (arrowLength * Math.cos(angle + Math.toRadians(30)));
        int y4 = y2 - (int) (arrowLength * Math.sin(angle + Math.toRadians(30)));

        // Draw the arrowhead
        g.fillPolygon(new int[]{x2, x3, x4}, new int[]{y2, y3, y4}, 3);
    }

    @Override
    public boolean elementAt(Point pos) {
//        int x1 = ((Connection) model).getFrom().getPosition().x;
//        int y1 = ((Connection) model).getFrom().getPosition().y;
//        int x2 = ((Connection) model).getTo().getPosition().x;
//        int y2 = ((Connection) model).getTo().getPosition().y;
//        return pos.x >= Math.min(x1, x2) && pos.x <= Math.max(x1, x2) && pos.y >= Math.min(y1, y2) && pos.y <= Math.max(y1, y2);
        return false;
    }
}
