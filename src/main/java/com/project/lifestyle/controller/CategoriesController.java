package com.project.lifestyle.controller;

import com.project.lifestyle.dto.CategoriesRequest;
import com.project.lifestyle.service.CategoriesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
