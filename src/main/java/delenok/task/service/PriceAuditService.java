package delenok.task.service;

import delenok.task.domain.PriceAudit;
import delenok.task.domain.Product;
import delenok.task.repository.PriceAuditRepository;
import delenok.task.utils.Utils;
import delenok.task.utils.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PriceAuditService {

    @Autowired
    private PriceAuditRepository priceAuditRepository;


    public List<PriceAudit> getPricesAudit(String productId) {
        return priceAuditRepository.findByProductIdLike(productId);
    }

    public void addPriceAudit(Product product) {
        Validator.checkNotNull(product, "Product cannot be empty");
        PriceAudit audit = PriceAudit.builder()
                .productId(product.getId())
                .productName(product.getName())
                .price(product.getCurrentPrice())
                .timestamp(Utils.getCurrentTimeInMs())
                .build();
        priceAuditRepository.save(audit);
    }


}
