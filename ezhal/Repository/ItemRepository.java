package com.example.ezhal.Repository;

import com.example.ezhal.Model.Category;
import com.example.ezhal.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {

    Item findItemById(Integer id);
    Item findItemsByProviderId(Integer id);


    @Query("select i.providerId from Item i where i.id =?1")
    Integer getProviderIdByItemId(Integer id);



}
