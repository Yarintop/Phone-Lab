package twins.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import twins.data.ItemEntity;

public interface ItemDao extends PagingAndSortingRepository<ItemEntity, String> {
    
    public Page<ItemEntity> findAllByActiveTrue(Pageable pageable);
    public Page<ItemEntity> findAllByActiveTrueAndParents_itemId(String parentId, Pageable pageable);
    public Page<ItemEntity> findAllByParents_itemId(String parentId, Pageable pageable);
    public Page<ItemEntity> findAllByActiveTrueAndChildren_itemId(String childId, Pageable pageable);
    public Page<ItemEntity> findAllByChildren_itemId(String childId, Pageable pageable);

}
