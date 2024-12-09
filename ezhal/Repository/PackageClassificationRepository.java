package com.example.ezhal.Repository;

import com.example.ezhal.Model.Category;
import com.example.ezhal.Model.PackageClassification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageClassificationRepository extends JpaRepository<PackageClassification,Integer> {

    PackageClassification findPackageClassificationById(Integer id);
}
