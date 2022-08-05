package com.project.lifestyle.service;

import com.project.lifestyle.dto.CategoriesRequest;
import com.project.lifestyle.mapper.CategoriesMapper;
import com.project.lifestyle.repository.CategoriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final AuthService authService;
    private final CategoriesMapper categoriesMapper;

    public void save(CategoriesRequest categoriesRequest) {
        categoriesRepository.save(categoriesMapper.map(categoriesRequest, authService.getUser()));
    }
}
