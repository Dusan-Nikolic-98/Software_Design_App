package repository.implementation.diagramElements.elements.classContent;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonTypeName("EnumClassContent")
public class EnumClassContent extends ClassContent{
    public EnumClassContent(String content) {
        super(content);
    }
}
