package twins.dao;

import twins.data.OperationEntity;
import org.springframework.data.repository.CrudRepository;

public interface OperationDao extends CrudRepository<OperationEntity, String> {

}
