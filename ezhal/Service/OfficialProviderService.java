package com.example.ezhal.Service;


import com.example.ezhal.ApiResponse.ApiException;
import com.example.ezhal.Model.Item;
import com.example.ezhal.Model.OfficialProvider;
import com.example.ezhal.Repository.OfficialProviderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OfficialProviderService {
    private final OfficialProviderRepository officialProviderRepository;

    public List<OfficialProvider> getAll(){
        if(officialProviderRepository.findAll().isEmpty())throw new ApiException("no official Provider found");
        return officialProviderRepository.findAll();
    }

    public void add(OfficialProvider officialProvider){
        officialProviderRepository.save(officialProvider);
    }

    public void update(Integer id,OfficialProvider officialProvider){
        OfficialProvider o=officialProviderRepository.findOfficialProviderById(id);
        if(o==null)throw new ApiException("no official Provider found with this id");
        o.setProviderName(officialProvider.getProviderName());
        officialProviderRepository.save(o);
    }


    public void delete(Integer id){
        OfficialProvider i=officialProviderRepository.findOfficialProviderById(id);
        if(i==null)throw new ApiException("no official Provider found with this id");
        officialProviderRepository.delete(i);
    }

}
