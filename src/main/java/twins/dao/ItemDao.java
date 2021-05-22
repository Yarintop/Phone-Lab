package twins.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import twins.data.ItemEntity;

public interface ItemDao extends PagingAndSortingRepository<ItemEntity, String> {
    
    public Page<ItemEntity> findAllByActiveTrue(Pageable pageable);
    
}
