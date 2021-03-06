package twins.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import twins.data.UserEntity;

public interface UserDao extends MongoRepository<UserEntity, String> {
    
    // public List<UserEntity> findByUserId(
    //     @Param("userId") String userSpace,
    //     Pageable pageable
    // );

}
