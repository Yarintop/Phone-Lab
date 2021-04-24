package twins.dao;

import org.springframework.data.repository.CrudRepository;
import twins.data.OperationEntity;

public interface OperationDao extends CrudRepository<OperationEntity, String> {

}
