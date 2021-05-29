package twins.dao;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import twins.data.OperationEntity;


public interface OperationDao extends MongoRepository<OperationEntity, String> {

    // public List<OperationEntity> findByUserId(
    //     @Param("userId") String userSpace,
    //     Pageable pageable
    // );

}
