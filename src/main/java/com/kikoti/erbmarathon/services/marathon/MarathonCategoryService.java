package com.kikoti.erbmarathon.services.marathon;

import com.kikoti.erbmarathon.dtos.MarathonCategoryDto;
import com.kikoti.erbmarathon.entity.MarathonCategory;
import com.kikoti.erbmarathon.utils.ResponseWrapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface MarathonCategoryService {
    ResponseEntity<ResponseWrapper<MarathonCategory>> saveMarathonCategory(MarathonCategoryDto request);
    ResponseEntity<ResponseWrapper<Page<MarathonCategory>>> getMarathonCategoriesWithPagination(int page, int size);
    ResponseEntity<ResponseWrapper<MarathonCategory>> getMarathonCategoryById(Long id);
    ResponseEntity<ResponseWrapper<Void>> deleteMarathonCategory(Long id);
}
