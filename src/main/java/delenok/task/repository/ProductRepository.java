package delenok.task.repository;

import delenok.task.domain.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends AbstractRepository<Product> {
}
