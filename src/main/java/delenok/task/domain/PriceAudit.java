package delenok.task.domain;

import com.google.common.base.MoreObjects;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Builder
@Document
public class PriceAudit extends BasicDbEntity {

    private String productId;
    private String productName;
    private BigDecimal price;
    private Long timestamp;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("productId", productId)
                .add("name", productName)
                .add("price", price)
                .add("timestamp", timestamp)
                .toString();
    }

}
