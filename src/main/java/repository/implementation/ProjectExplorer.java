package repository.implementation;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.NoArgsConstructor;
import repository.composite.ClassyNode;
import repository.composite.ClassyNodeComposite;
import repository.implementation.Project;
@NoArgsConstructor
@JsonTypeName("ProjectExplorer")
public class ProjectExplorer extends ClassyNodeComposite {



    public ProjectExplorer(String ime, ClassyNode parent) {
        super(ime, parent);
    }

    @Override
    public void addChild(ClassyNode node) {
        if(node != null && node instanceof Project){
            Project p = (Project) node;
            if(!this.getChildren().contains(p))
                this.getChildren().add(p);
        }
    }

    @Override
    public void removeChild(ClassyNode node) {
        if(node instanceof Project){
            Project p = (Project) node;
            if(this.getChildren().contains(p)){
                p.updateDelete();
                this.getChildren().remove(p);
            }
        }
    }
}
