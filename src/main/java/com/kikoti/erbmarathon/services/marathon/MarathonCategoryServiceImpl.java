package com.kikoti.erbmarathon.services.marathon;

import com.kikoti.erbmarathon.dtos.MarathonCategoryDto;
import com.kikoti.erbmarathon.entity.MarathonCategory;
import com.kikoti.erbmarathon.repository.MarathonCategoryRepository;
import com.kikoti.erbmarathon.utils.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MarathonCategoryServiceImpl implements MarathonCategoryService {

    private final MarathonCategoryRepository marathonCategoryRepository;

    @Override
    public ResponseEntity<ResponseWrapper<MarathonCategory>> saveMarathonCategory(MarathonCategoryDto request) {
        MarathonCategory category = new MarathonCategory();
        category.setCategoryTitle(request.getCategoryTitle());
        category.setCategoryLength(request.getCategoryLength());
        category.setCategoryMeasure(request.getCategoryMeasure());
        category.setCategoryEntry(request.getCategoryEntry());
        category.setCategoryLimit(request.getCategoryLimit());
        if (request.getId() != null) {
            Optional<MarathonCategory> data = marathonCategoryRepository.findById(request.getId());
            if (data.isPresent()) {
                category.setId(request.getId());
            } else {
                ResponseWrapper<MarathonCategory> response = new ResponseWrapper<>(null, "Marathon Category not found", false);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }
        MarathonCategory savedMarathonCategory = marathonCategoryRepository.save(category);
        ResponseWrapper<MarathonCategory> response = new ResponseWrapper<>(savedMarathonCategory, "Marathon Category saved successfully", true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseWrapper<Page<MarathonCategory>>> getMarathonCategoriesWithPagination(int page, int size) {
        Page<MarathonCategory> marathonCategories = marathonCategoryRepository.findAll(PageRequest.of(page, size));
        ResponseWrapper<Page<MarathonCategory>> response = new ResponseWrapper<>(marathonCategories, "Marathon Categories fetched successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ResponseWrapper<MarathonCategory>> getMarathonCategoryById(Long id) {
        Optional<MarathonCategory> marathonCategory = marathonCategoryRepository.findById(id);
        if (marathonCategory.isPresent()) {
            ResponseWrapper<MarathonCategory> response = new ResponseWrapper<>(marathonCategory.get(), "Marathon Category found", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<MarathonCategory> response = new ResponseWrapper<>(null, "Marathon Category not found", false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ResponseWrapper<Void>> deleteMarathonCategory(Long id) {
        if (marathonCategoryRepository.existsById(id)) {
            marathonCategoryRepository.deleteById(id);
            ResponseWrapper<Void> response = new ResponseWrapper<>(null, "Marathon Category deleted successfully", true);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ResponseWrapper<Void> response = new ResponseWrapper<>(null, "Marathon Category not found", false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
