package com.example.ezhal.Controller;


import com.example.ezhal.ApiResponse.ApiResponse;
import com.example.ezhal.Model.Category;
import com.example.ezhal.Model.PackageClassification;
import com.example.ezhal.Service.PackageClassificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/classification")
public class PackageClassificationController {
    private final PackageClassificationService packageClassificationService;

    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(packageClassificationService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid PackageClassification packageClassification, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        packageClassificationService.add(packageClassification);
        return ResponseEntity.status(200).body(new ApiResponse("classification added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Integer id,@RequestBody @Valid PackageClassification packageClassification,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        packageClassificationService.update(id,packageClassification);
        return ResponseEntity.status(200).body(new ApiResponse("classification updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        packageClassificationService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("classification deleted successfully"));
    }

}
