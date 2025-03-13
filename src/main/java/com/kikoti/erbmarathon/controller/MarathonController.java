package com.kikoti.erbmarathon.controller;

import com.kikoti.erbmarathon.dtos.MarathonDto;
import com.kikoti.erbmarathon.dtos.MarathonManageCategoryDto;
import com.kikoti.erbmarathon.entity.Marathon;
import com.kikoti.erbmarathon.services.marathon.MarathonService;
import com.kikoti.erbmarathon.utils.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/marathons")
public class MarathonController {

    private final MarathonService marathonService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<Marathon>> createOrUpdateEvent(@Valid @RequestBody MarathonDto marathon) {
        return marathonService.saveMarathon(marathon);
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<Marathon>>> getAllMarathons(
            @RequestParam(defaultValue = "0") int page,    // Default page number is 0
            @RequestParam(defaultValue = "10") int size) {  // Default page size is 10
        return marathonService.getAllMarathons(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Marathon>> getEventById(@PathVariable Long id) {
        return marathonService.getMarathonById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteEvent(@PathVariable Long id) {
        return marathonService.deleteMarathon(id);
    }

    @PutMapping("/category/{type}")
    public ResponseEntity<ResponseWrapper<Marathon>> addCategoryToEvent(
            @Valid @RequestBody MarathonManageCategoryDto request, @PathVariable String type
    ) {
        return marathonService.manageMarathonCategories(request, type);
    }

}

