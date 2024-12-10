package com.example.ezhal.Service;

import com.example.ezhal.ApiResponse.ApiException;
import com.example.ezhal.Model.OfficialProvider;
import com.example.ezhal.Model.Package;
import com.example.ezhal.Model.User;
import com.example.ezhal.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PackageService {

    private final PackageRepository packageRepository;
    private final OfficialProviderRepository officialProviderRepository;
    private final PackageClassificationRepository packageClassificationRepository;
    private final ReviewRepository reviewRepository;


    public List<Package> getAll() {
        List<Package> packages = packageRepository.findAll();
        if (packages.isEmpty()) {
            throw new ApiException("No packages found");
        }
        return packages;
    }


    public void add(Package packageItem) {

        OfficialProvider provider = officialProviderRepository.findOfficialProviderById(packageItem.getProviderId());
        if (provider == null) {
            throw new ApiException("Provider not found");
        }

        if (packageClassificationRepository.findPackageClassificationById(packageItem.getPackageClassificationId())==null) {
            throw new ApiException("Package Classification not found");
        }
        packageRepository.save(packageItem);
    }


    public void update(Integer id, Package packageItem) {
        Package existingPackage = packageRepository.findPackageById(id);
        if (existingPackage == null) {
            throw new ApiException("Package not found with this id");
        }

        OfficialProvider provider = officialProviderRepository.findOfficialProviderById(packageItem.getProviderId());
        if (provider == null) {
            throw new ApiException("Provider not found");
        }


        if (packageClassificationRepository.findPackageClassificationById(packageItem.getPackageClassificationId())==null) {
            throw new ApiException("Package Classification not found");
        }

        existingPackage.setPackageName(packageItem.getPackageName());
        existingPackage.setPackageClassificationId(packageItem.getPackageClassificationId());
        existingPackage.setDescription(packageItem.getDescription());
        existingPackage.setImageUrl(packageItem.getImageUrl());
        existingPackage.setNumberOfItem(packageItem.getNumberOfItem());
        packageRepository.save(existingPackage);
    }


    public void delete(Integer id) {
        Package packageItem = packageRepository.findById(id).orElse(null);
        if (packageItem == null) {
            throw new ApiException("Package not found with this id");
        }
        packageRepository.delete(packageItem);
    }



}
