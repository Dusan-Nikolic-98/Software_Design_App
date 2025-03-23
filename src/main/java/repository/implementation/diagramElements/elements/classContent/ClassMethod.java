package repository.implementation.diagramElements.elements.classContent;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeName("ClassMethod")
public class ClassMethod extends ClassContent{

    private String visibility; private String metName;
    public ClassMethod(String content) {
        super(content);
    }
}
