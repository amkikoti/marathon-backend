package com.kikoti.erbmarathon.services.marathon;

import com.kikoti.erbmarathon.dtos.MarathonDto;
import com.kikoti.erbmarathon.dtos.MarathonManageCategoryDto;
import com.kikoti.erbmarathon.entity.Marathon;
import com.kikoti.erbmarathon.utils.ResponseWrapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;


public interface MarathonService {
    ResponseEntity<ResponseWrapper<Marathon>> saveMarathon(MarathonDto request);
    ResponseEntity<ResponseWrapper<Page<Marathon>>> getAllMarathons(int page, int size);
    ResponseEntity<ResponseWrapper<Marathon>> getMarathonById(Long id);
    ResponseEntity<ResponseWrapper<Void>> deleteMarathon(Long id);
    ResponseEntity<ResponseWrapper<Marathon>> manageMarathonCategories(MarathonManageCategoryDto request, String type);
}
