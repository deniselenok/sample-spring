package delenok.task.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public abstract class BasicDbEntity {

    @Id
    @Indexed
    @JsonProperty
    protected String id;
}
