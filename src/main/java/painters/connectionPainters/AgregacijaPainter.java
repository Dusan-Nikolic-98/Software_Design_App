package painters.connectionPainters;

import lombok.Getter;
import painters.ElementPainter;
import repository.implementation.diagramElements.elements.Connection;
import repository.implementation.diagramElements.elements.DiagramElement;
import repository.implementation.diagramElements.elements.connectionImplementation.Agregacija;

import java.awt.*;

public class AgregacijaPainter extends ElementPainter {

    private int[] currPos;
    public AgregacijaPainter(DiagramElement model) { //model aka linija koja ima from i to
        super(model);
        this.currPos = new int[]{1, 1, 1, 1};
    }

    @Override
    public void draw(Graphics2D g) {
        if(model instanceof Connection){
            if(((Connection) model).getFrom() != null && ((Connection) model).getTo() != null) {
                g.setPaint(model.getPaint());
                g.setStroke(model.getStroke());
                int x1 = ((Connection) model).getFrom().getPosition().x;
                int y1 = ((Connection) model).getFrom().getPosition().y;
                int x2 = ((Connection) model).getTo().getPosition().x;
                int y2 = ((Connection) model).getTo().getPosition().y;

                int x1kraj = x1 + (int)((Connection) model).getFrom().getSize().getWidth();
                int y1kraj = y1 + (int)((Connection) model).getFrom().getSize().getHeight();

                //---

                int x2kraj = x2 + (int)((Connection) model).getTo().getSize().getWidth();
                int y2kraj = y2 + (int)((Connection) model).getTo().getSize().getHeight();

                //---

                int x1cent = x1 + (int)((Connection) model).getFrom().getSize().getWidth()/2;
                int y1cent = y1 + (int)((Connection) model).getFrom().getSize().getHeight()/2;

                //---

                int x2cent = x2 + (int)((Connection) model).getTo().getSize().getWidth()/2;
                int y2cent = y2 + (int)((Connection) model).getTo().getSize().getHeight()/2;


                //---
                //i sad kombinacije sve:
                Point p1 = new Point();
                Point p2 = new Point();

                int[] tempNiz = findClosestPoints(x1, x2, y1, y2, x1cent,x2cent,y1cent,y2cent,x1kraj,x2kraj,y1kraj,y2kraj);
                p1.x = tempNiz[0]; p1.y = tempNiz[1]; p2.x = tempNiz[2]; p2.y = tempNiz[3];
                currPos = tempNiz;
                g.drawLine(p1.x, p1.y, p2.x,p2.y);
                //g.drawLine(x1, y1, x2, y2);
                //x - w
                //TODO CRTANJE STRELICA DELA STRELICE
                g.setPaint(Color.LIGHT_GRAY);
                //drawArrowHead(g, x1, y1, x2, y2);
                drawArrowHead(g, p1.x, p1.y, p2.x, p2.y);
            }
        }
    }
    private void drawArrowHead(Graphics2D g, int x1, int y1, int x2, int y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        double arrowLength = 12.0;

        int x3 = x2 - (int) (arrowLength * Math.cos(angle - Math.toRadians(30)));
        int y3 = y2 - (int) (arrowLength * Math.sin(angle - Math.toRadians(30)));

        int x4 = x2 - (int) (arrowLength * Math.cos(angle + Math.toRadians(30)));
        int y4 = y2 - (int) (arrowLength * Math.sin(angle + Math.toRadians(30)));

        int x5 = x2 - (int) (arrowLength * 2 * Math.cos(angle));
        int y5 = y2 - (int) (arrowLength * 2 * Math.sin(angle));

        g.fillPolygon(new int[]{x2,x3,x5,x4}, new int[]{y2,y3,y5,y4}, 4);
        g.setPaint(Color.BLACK);
        g.drawPolygon(new int[]{x2,x3,x5,x4}, new int[]{y2,y3,y5,y4}, 4);
    }

private static int[] findClosestPoints(int x1, int x2, int y1, int y2, int x1cent, int x2cent,
                                       int y1cent, int y2cent, int x1kraj, int x2kraj, int y1kraj, int y2kraj) {

    double[] distances = new double[12];

    distances[0] = calculateDistance(x1cent, y1, x2cent, y2kraj);
    distances[1] = calculateDistance(x1cent, y1, x2, y2cent);
    distances[2] = calculateDistance(x1cent, y1, x2kraj, y2cent);

    distances[3] = calculateDistance(x1cent, y1kraj, x2cent, y2);
    distances[4] = calculateDistance(x1cent, y1kraj, x2, y2cent);
    distances[5] = calculateDistance(x1cent, y1kraj, x2kraj, y2cent);

    distances[6] = calculateDistance(x1, y1cent, x2cent, y2);
    distances[7] = calculateDistance(x1, y1cent, x2cent, y2kraj);
    distances[8] = calculateDistance(x1, y1cent, x2kraj, y2cent);

    distances[9] = calculateDistance(x1kraj, y1cent, x2cent, y2);
    distances[10] = calculateDistance(x1kraj, y1cent, x2cent, y2kraj);
    distances[11] = calculateDistance(x1kraj, y1cent, x2, y2cent);

    int minIndex = 0;
    for (int i = 1; i < distances.length; i++) {
        if (distances[i] < distances[minIndex]) {
            minIndex = i;
        }
    }
    int[] result = new int[4];
    switch (minIndex) {
        case 0:  // dist1
            result = new int[]{x1cent, y1, x2cent, y2kraj};
            break;
        case 1:  // dist2
            result = new int[]{x1cent, y1, x2, y2cent};
            break;
        case 2:  // dist3
            result = new int[]{x1cent, y1, x2kraj, y2cent};
            break;
        case 3:  // dist4
            result = new int[]{x1cent, y1kraj, x2cent, y2};
            break;
        case 4:  // dist5
            result = new int[]{x1cent, y1kraj, x2, y2cent};
            break;
        case 5:  // dist6
            result = new int[]{x1cent, y1kraj, x2kraj, y2cent};
            break;
        case 6:  // dist7
            result = new int[]{x1, y1cent, x2cent, y2};
            break;
        case 7:  // dist8
            result = new int[]{x1, y1cent, x2cent, y2kraj};
            break;
        case 8:  // dist9
            result = new int[]{x1, y1cent, x2kraj, y2cent};
            break;
        case 9:  // dist10
            result = new int[]{x1kraj, y1cent, x2cent, y2};
            break;
        case 10:  // dist11
            result = new int[]{x1kraj, y1cent, x2cent, y2kraj};
            break;
        case 11:  // dist12
            result = new int[]{x1kraj, y1cent, x2, y2cent};
            break;
    }
    return result;
}

    private static double calculateDistance(int x1, int y1, int x2, int y2) {return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));}


    @Override
    public boolean elementAt(Point pos) {
        int x1 = currPos[0], y1 = currPos[1], x2 = currPos[2], y2 = currPos[3];
        return pos.x >= Math.min(x1, x2) && pos.x <= Math.max(x1, x2) && pos.y >= Math.min(y1, y2) && pos.y <= Math.max(y1, y2);
    }

    public int[] getCurrPos() {
        return currPos;
    }
}
