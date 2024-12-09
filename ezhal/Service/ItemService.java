package com.example.ezhal.Service;

import com.example.ezhal.ApiResponse.ApiException;
import com.example.ezhal.Model.Category;
import com.example.ezhal.Model.Item;
import com.example.ezhal.Model.OfficialProvider;
import com.example.ezhal.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;

    public List<Item> getAll(){
        if(itemRepository.findAll().isEmpty())throw new ApiException("no items found");
        return itemRepository.findAll();
    }

    public void add(Item item){
            if (userRepository.findUserById(item.getProviderId()) == null)
                throw new ApiException("user provider id not found");

        if(categoryRepository.findCategoryById(item.getCategoryId())==null)
        throw new ApiException("category id not found");

        itemRepository.save(item);
    }

    public void update(Integer id,Item item){
        Item i=itemRepository.findItemById(id);
        if(i==null)throw new ApiException("no item found with this id");

        if( userRepository.findUserById(item.getProviderId())==null)
            throw new ApiException("user provider id not found");

        if(categoryRepository.findCategoryById(item.getCategoryId())==null)
            throw new ApiException("category id not found");

        i.setName(item.getName());
        i.setDescription(item.getDescription());
        i.setImageUrl(item.getImageUrl());
        i.setCategoryId(item.getCategoryId());
        itemRepository.save(i);
    }


    public void delete(Integer id){
        Item i=itemRepository.findItemById(id);
        if(i==null)throw new ApiException("no item found with this id");
        itemRepository.delete(i);
    }


   //top rated item
    public List<Item> getTopRatedPackages() {
        List<Item> topRated = reviewRepository.findTopRatedItem();
        if (topRated.isEmpty()) {
            throw new ApiException("No top-rated packages found");
        }
        return topRated;
    }
}
