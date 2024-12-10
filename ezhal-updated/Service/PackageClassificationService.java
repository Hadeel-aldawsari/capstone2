package com.example.ezhal.Service;

import com.example.ezhal.ApiResponse.ApiException;
import com.example.ezhal.Model.Category;
import com.example.ezhal.Model.PackageClassification;
import com.example.ezhal.Repository.CategoryRepository;
import com.example.ezhal.Repository.PackageClassificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PackageClassificationService {

    private final PackageClassificationRepository packageClassificationRepository;

    public List<PackageClassification> getAll(){
        if(packageClassificationRepository.findAll().isEmpty())throw new ApiException("no Package Classification found");
        return packageClassificationRepository.findAll();
    }

    public void add(PackageClassification packageClassification){
        packageClassificationRepository.save(packageClassification);
    }

    public void update(Integer id,PackageClassification packageClassification){
        PackageClassification p=packageClassificationRepository.findPackageClassificationById(id);
        if(p==null)throw new ApiException("no package classification found with this id");
        p.setName(packageClassification.getName());
        packageClassificationRepository.save(p);
    }


    public void delete(Integer id){
        PackageClassification p=packageClassificationRepository.findPackageClassificationById(id);
        if(p==null)throw new ApiException("no package classification found with this id");
        packageClassificationRepository.delete(p);
    }
}
