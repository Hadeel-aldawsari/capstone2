package com.example.ezhal.Controller;

import com.example.ezhal.ApiResponse.ApiResponse;
import com.example.ezhal.Model.Category;
import com.example.ezhal.Model.Item;
import com.example.ezhal.Service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/item")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(itemService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity addByUser(@RequestBody @Valid Item item, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        itemService.add(item);
        return ResponseEntity.status(200).body(new ApiResponse("item added successfully"));
    }

//    @PostMapping("/official-provider/add")
//    public ResponseEntity addByOfficial(@RequestBody @Valid Item item, Errors errors){
//        if(errors.hasErrors()){
//            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
//        }
//        item.setProviderType("officialProvider");
//        itemService.add(item);
//        return ResponseEntity.status(200).body(new ApiResponse("item added successfully"));
//    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody @Valid Item item, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        itemService.update(id,item);
        return ResponseEntity.status(200).body(new ApiResponse("item updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        itemService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("item deleted successfully"));
    }

}
