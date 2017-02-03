package delenok.task.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Document
@Setter
@Getter
@Builder
public class Product extends BasicDbEntity {

    @NotNull
    @JsonProperty(required = true)
    private String name;

    @NotNull
    @JsonProperty(required = true)
    private BigDecimal currentPrice;

    private Long timestamp;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("timestamp", timestamp)
                .add("currentPrice", currentPrice)
                .toString();
    }

}
