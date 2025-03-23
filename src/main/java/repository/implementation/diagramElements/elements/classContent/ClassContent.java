package repository.implementation.diagramElements.elements.classContent;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import repository.implementation.diagramElements.elements.connectionImplementation.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "ClassContent")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClassAttribute.class, name = "ClassAttribute"),
        @JsonSubTypes.Type(value = ClassMethod.class, name = "ClassMethod"),
        @JsonSubTypes.Type(value = EnumClassContent.class, name = "EnumClassContent")
})
public abstract class ClassContent {
    private String content;
    public ClassContent(String content){
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassContent that)) return false;
        return Objects.equals(content, that.content);
    }

}
