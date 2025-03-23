package repository.implementation.diagramElements.elements.classContent;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeName("ClassAttribute")
public class ClassAttribute extends ClassContent{
    public String visibility; private String attName; private String dataType;

    public ClassAttribute(String content) {
        super(content);
    }
}
