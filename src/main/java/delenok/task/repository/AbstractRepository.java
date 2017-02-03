package delenok.task.repository;

import delenok.task.domain.BasicDbEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbstractRepository<T extends BasicDbEntity> extends MongoRepository<T, String> {
}
