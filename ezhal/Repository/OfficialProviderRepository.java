package com.example.ezhal.Repository;

import com.example.ezhal.Model.OfficialProvider;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficialProviderRepository  extends JpaRepository<OfficialProvider,Integer> {

    OfficialProvider findOfficialProviderById(Integer id);
}
