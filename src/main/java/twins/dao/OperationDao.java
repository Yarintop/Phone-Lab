package twins.dao;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import twins.data.OperationEntity;


public interface OperationDao extends PagingAndSortingRepository<OperationEntity, String> {

    // public List<OperationEntity> findByUserId(
    //     @Param("userId") String userSpace,
    //     Pageable pageable
    // );

}
