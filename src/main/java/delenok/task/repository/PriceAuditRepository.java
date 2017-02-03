package delenok.task.repository;

import delenok.task.domain.PriceAudit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceAuditRepository extends AbstractRepository<PriceAudit> {
    List<PriceAudit> findByProductIdLike(String id);
}
