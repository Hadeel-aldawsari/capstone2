package com.example.ezhal.Repository;

import com.example.ezhal.Model.Item;
import com.example.ezhal.Model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PackageRepository extends JpaRepository<Package,Integer> {
    Package findPackageById(Integer id);
    Package findPackageByProviderId(Integer id);


    @Query("select i.providerId from Item i where i.id =?1")
    Integer getProviderIdByPackageId(Integer id);

}
