package com.kikoti.erbmarathon.services.marathon;

import com.kikoti.erbmarathon.dtos.MarathonDto;
import com.kikoti.erbmarathon.dtos.MarathonManageCategoryDto;
import com.kikoti.erbmarathon.entity.Marathon;
import com.kikoti.erbmarathon.entity.MarathonCategory;
import com.kikoti.erbmarathon.repository.MarathonCategoryRepository;
import com.kikoti.erbmarathon.repository.MarathonRepository;
import com.kikoti.erbmarathon.utils.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class MarathonServiceImpl implements MarathonService {

    private final MarathonRepository marathonRepository;
    private final MarathonCategoryRepository marathonCategoryRepository;

    @Override
    public ResponseEntity<ResponseWrapper<Marathon>> saveMarathon(MarathonDto request) {
        Marathon marathon = new Marathon();
        marathon.setMarathonTitle(request.getMarathonTitle());
        marathon.setMarathonDescription(request.getMarathonDescription());
        marathon.setMarathonStartDate(request.getMarathonStartDate());
        marathon.setMarathonStartTime(request.getMarathonStartTime());
        marathon.setMarathonCountry(request.getMarathonCountry());
        marathon.setMarathonRegion(request.getMarathonRegion());
        marathon.setMarathonYear(request.getMarathonYear());
        List<Long> categoryIds = request.getCategories();
        if (!categoryIds.isEmpty()) {
            List<MarathonCategory> categories = marathonCategoryRepository.findAllById(categoryIds);
            marathon.setCategories(categories);
        }
        if (request.getId() != null) {
            Optional<Marathon> data = marathonRepository.findById(request.getId());
            if (data.isPresent()) {
                marathon.setId(request.getId());
            } else {
                ResponseWrapper<Marathon> response = new ResponseWrapper<>(null, "Marathon not found", false);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }
        Marathon savedMarathon = marathonRepository.save(marathon);
        ResponseWrapper<Marathon> response = new ResponseWrapper<>(savedMarathon, "Marathon saved successfully", true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseWrapper<Page<Marathon>>> getAllMarathons(int page, int size) {
        Page<Marathon> marathons = marathonRepository.findAll(PageRequest.of(page, size));
        ResponseWrapper<Page<Marathon>> response = new ResponseWrapper<>(marathons, "Marathons fetched successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseWrapper<Marathon>> getMarathonById(Long id) {
        Optional<Marathon> marathon = marathonRepository.findById(id);
        if (marathon.isPresent()) {
            ResponseWrapper<Marathon> response = new ResponseWrapper<>(marathon.get(), "Marathon found", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<Marathon> response = new ResponseWrapper<>(null, "Marathon not found", false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ResponseWrapper<Void>> deleteMarathon(Long id) {
        if (marathonRepository.existsById(id)) {
            marathonRepository.deleteById(id);
            ResponseWrapper<Void> response = new ResponseWrapper<>(null, "Marathon deleted successfully", true);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            ResponseWrapper<Void> response = new ResponseWrapper<>(null, "Marathon not found", false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ResponseWrapper<Marathon>> manageMarathonCategories(@Valid MarathonManageCategoryDto request, String type) {
        if (type.isEmpty() || (!type.equals("add") && !type.equals("remove"))) {
            ResponseWrapper<Marathon> response = new ResponseWrapper<>(null, "Marathon action type is not valid", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Long marathonId = request.getMarathon();
        Optional<Marathon> marathon = marathonRepository.findById(marathonId);
        if (marathon.isPresent()) {
            List<Long> categoryIds = request.getCategories();
            if (!categoryIds.isEmpty()) {
                Marathon currentMarathon = marathon.get();
                List<MarathonCategory> categories = marathonCategoryRepository.findByIdIn(categoryIds);
                List<MarathonCategory> existingCategories = currentMarathon.getCategories();
                for (MarathonCategory newCategory : categories) {
                    if (!existingCategories.contains(newCategory) && type.equals("add")) {
                        existingCategories.add(newCategory);
                    } else if (existingCategories.contains(newCategory) && type.equals("remove")) {
                        existingCategories.remove(newCategory);
                    }
                }
                currentMarathon.setCategories(existingCategories);
                marathonRepository.save(currentMarathon);
                ResponseWrapper<Marathon> response = new ResponseWrapper<>(currentMarathon, "Marathon updated successfully", true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            ResponseWrapper<Marathon> response = new ResponseWrapper<>(null, "Categories not found", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            ResponseWrapper<Marathon> response = new ResponseWrapper<>(null, "Marathon not found", false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
