package com.example.ezhal.Controller;

import com.example.ezhal.ApiResponse.ApiResponse;
import com.example.ezhal.Model.OfficialProvider;
import com.example.ezhal.Service.OfficialProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/official-provider")
public class OfficialProviderController {

    private final OfficialProviderService officialProviderService;

    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(officialProviderService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid OfficialProvider officialProvider, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        officialProviderService.add(officialProvider);
        return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody @Valid OfficialProvider officialProvider, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        officialProviderService.update(id,officialProvider);
        return ResponseEntity.status(200).body(new ApiResponse("updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        officialProviderService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("deleted successfully"));
    }



}
