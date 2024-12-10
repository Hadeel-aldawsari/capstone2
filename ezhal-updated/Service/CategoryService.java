package com.example.ezhal.Service;

import com.example.ezhal.ApiResponse.ApiException;
import com.example.ezhal.Model.Category;
import com.example.ezhal.Repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAll(){
        if(categoryRepository.findAll().isEmpty())throw new ApiException("no category found");
        return categoryRepository.findAll();
    }

    public void add(Category category){
        categoryRepository.save(category);
    }

    public void update(Integer id,Category category){
        Category c=categoryRepository.findCategoryById(id);
        if(c==null)throw new ApiException("no category found with this id");
        c.setName(category.getName());
        categoryRepository.save(c);
    }


    public void delete(Integer id){
        Category c=categoryRepository.findCategoryById(id);
        if(c==null)throw new ApiException("no category found with this id");
        categoryRepository.delete(c);
    }

}
