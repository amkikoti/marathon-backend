package com.kikoti.erbmarathon.controller;

import com.kikoti.erbmarathon.dtos.MarathonCategoryDto;
import com.kikoti.erbmarathon.entity.MarathonCategory;
import com.kikoti.erbmarathon.services.marathon.MarathonCategoryService;
import com.kikoti.erbmarathon.utils.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/marathon-category")
@RequiredArgsConstructor
public class MarathonCategoryController {

    private final MarathonCategoryService marathonCategoryService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<MarathonCategory>> createOrUpdateMarathonCategory(@Valid @RequestBody MarathonCategoryDto request) {
        return marathonCategoryService.saveMarathonCategory(request);
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<MarathonCategory>>> getMarathonCategories(
            @RequestParam(defaultValue = "0") int page,    // Default page number is 0
            @RequestParam(defaultValue = "10") int size) {  // Default page size is 10
        return marathonCategoryService.getMarathonCategoriesWithPagination(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<MarathonCategory>> getMarathonCategoryById(@PathVariable Long id) {
        return marathonCategoryService.getMarathonCategoryById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteMarathonCategory(@PathVariable Long id) {
        return marathonCategoryService.deleteMarathonCategory(id);
    }
}
