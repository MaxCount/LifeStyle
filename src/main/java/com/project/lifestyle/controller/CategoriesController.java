package com.project.lifestyle.controller;

import com.project.lifestyle.dto.CategoriesRequest;
import com.project.lifestyle.dto.CategoriesResponce;
import com.project.lifestyle.service.CategoriesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoriesController {

    private final CategoriesService categoriesService;

    @PostMapping("/setCategories")
    private ResponseEntity<String> setCategories(@RequestBody CategoriesRequest categoriesRequest){
        categoriesService.save(categoriesRequest);
        return new ResponseEntity<>("Categories set", HttpStatus.OK);
    }

    @GetMapping("/getCategoriesByUserId")
    private List<CategoriesResponce> getCategoriesByUserId(){
        return categoriesService.getCategoriesByUserId();
    }
}
