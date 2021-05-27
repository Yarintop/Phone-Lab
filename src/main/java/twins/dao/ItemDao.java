package twins.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import twins.data.ItemEntity;

public interface ItemDao extends MongoRepository<ItemEntity, String> {
    
    public Page<ItemEntity> findAllByActiveTrue(Pageable pageable);
    public Page<ItemEntity> findAllByActiveTrueAndParents_id(String parentId, Pageable pageable);
    public Page<ItemEntity> findAllByActiveTrueAndChildren_id(String childId, Pageable pageable);
    
    public Page<ItemEntity> findAllByChildren_id(String childId, Pageable pageable);
    public Page<ItemEntity> findAllByParents_id(String parentId, Pageable pageable);
    
    public List<ItemEntity> findAllByParents_id(String parentId);
    
    public int countByType(String type);
    public int countByTypeAndActiveTrue(String type);
}
