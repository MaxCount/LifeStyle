package com.project.lifestyle.service;

import com.project.lifestyle.dto.CategoriesRequest;
import com.project.lifestyle.dto.CategoriesResponce;
import com.project.lifestyle.mapper.CategoriesMapper;
import com.project.lifestyle.model.Categories;
import com.project.lifestyle.repository.CategoriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final AuthService authService;
    private final CategoriesMapper categoriesMapper;

    public void save(CategoriesRequest categoriesRequest) {
        categoriesRepository.save(categoriesMapper.map(categoriesRequest, authService.getUser()));
    }

    public List<CategoriesResponce> getCategoriesByUserId(){
        List<Categories> cat = categoriesRepository.findAllByUser(authService.getUser());
        List<CategoriesResponce> set = new ArrayList<>();
        for (Categories categories :cat) {

        set.add( CategoriesResponce.builder()
                .cloth(categories.getCloth())
                .leisure(categories.getLeisure())
                .technique(categories.getTechnique())
                .transport(categories.getTransport())
                .medicine(categories.getMedicine())
                .food(categories.getFood())
                .real_estate(categories.getReal_estate())
                .education(categories.getEducation())
                .cryptocurrency(categories.getCryptocurrency())
                .other(categories.getOther())
                .date(categories.getDate())
                .build());
        }
        return set;
    }
}
