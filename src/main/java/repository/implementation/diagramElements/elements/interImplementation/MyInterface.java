package repository.implementation.diagramElements.elements.interImplementation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.NoArgsConstructor;
import observer.ISubscriber;
import observer.Notification;
import observer.NotificationType;
import repository.composite.ClassyNode;
import repository.implementation.diagramElements.elements.Interclass;
import repository.implementation.diagramElements.elements.classContent.ClassContent;
import repository.implementation.diagramElements.elements.classContent.ClassMethod;

import java.awt.*;
import java.util.ArrayList;
@JsonTypeName("MyInterface")
public class MyInterface extends Interclass {
    //private ClassMethod cm;
    public MyInterface(){
        setStartValues();
        this.setSubs(new ArrayList<>());
    }
    public MyInterface(String ime, ClassyNode parent) {
        super(ime, parent);
        setStartValues();
    }


    private void setStartValues(){
        setSize(new Dimension(90,40));
        setStroke(new BasicStroke(3f));
        setPaint(new Color(0,100,100));
        setName(this.getIme());
    }

    @Override
    public void setContents(ArrayList<ClassContent> contents) {
        super.setContents(contents);
        System.out.println("Element added or removed to/from interface contents list!");
        //todo ovde treba da se menja dim
        int strH = 16, maloSlovo = 6, velikoSlovo = 12, spejs = 4, currH = 41, currW =90;
        int vi = 0, li = 0, finalW = 0;
        for(int i = 0; i < contents.size(); i++){
            vi++;
            li = 8;
            String currS = contents.get(i).getContent();
            for(char ch: currS.toCharArray()){
                if (Character.isUpperCase(ch)) {
                    li += velikoSlovo;
                } else { //(Character.isWhitespace(ch))
                    li += maloSlovo;
                }
            }
            finalW = Math.max(finalW, li);
            //li = Math.max(currW, 5 + contents.get(i).getContent().length()); //li moze i malo preciznije
        }
        currH += vi*strH; //dakle rasce za svaki dodat string toliko koliko treba

        finalW = Math.max(90, Math.max(finalW, getNameWidth())); //cisto da min bude i dalje ovaj
        setSize(new Dimension(finalW, currH));
        notifySubscribers(new Notification(this, NotificationType.CHILD_CHANGED));
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        //todo i ovde potencijalno treba da se menja dim
        if(name != null) {
            int zac = 40, nameSize = name.length() * 6;
            int newW = Math.max(getContentWidth(), zac + nameSize);
            setSize(new Dimension(newW, getSize().height));
            notifySubscribers(new Notification(this, NotificationType.CHILD_CHANGED));
        }

    }
    private int getNameWidth(){return this.getName() != null? 35+this.getName().length()*6:0;}
    private int getContentWidth(){
        //ovde nek bude takodje 2 max gde je jedan 90 cisto da ne moze da ide ispod toga
        int maloSlovo = 6, velikoSlovo = 12, spejs = 4, currH = 41, currW =90;
        int li = 0, finalW = 0;
        for(int i = 0; i < getContents().size(); i++){
            li = 8;
            String currS = getContents().get(i).getContent();
            for(char ch: currS.toCharArray()){
                if (Character.isUpperCase(ch)) {
                    li += velikoSlovo;
                } else { //(Character.isWhitespace(ch))
                    li += maloSlovo;
                }
            }
            finalW = Math.max(finalW, li);
        }
        finalW = Math.max(finalW, 90);
        return finalW;
    }
    @Override
    public void setPosition(Point p){
        this.position = p;
        notifySubscribers(new Notification(this, NotificationType.CHILD_CHANGED));
    }


    @Override
    public void addSubscriber(ISubscriber iSubscriber) {
        if(!this.getSubs().contains(iSubscriber)) this.getSubs().add(iSubscriber);
    }

    @Override
    public void removeSubscriber(ISubscriber iSubscriber) {
        if(this.getSubs().contains(iSubscriber)) this.getSubs().remove(iSubscriber);
    }

    @Override
    public void notifySubscribers(Notification notification) {
        for(ISubscriber sub: this.getSubs()){
            sub.update(notification);
        }
    }
}
