package app.dao;

import org.springframework.data.repository.CrudRepository;

import app.twins.data.ItemEntity;

public interface ItemDao extends CrudRepository<ItemEntity, String>{

}
