package com.kikoti.erbmarathon.controller;

import com.kikoti.erbmarathon.dtos.MarathonDto;
import com.kikoti.erbmarathon.services.admin.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/marathon")
    public ResponseEntity<String> createMarathon(@Valid @RequestBody MarathonDto marathonDto) {
        boolean success = adminService.createMarathon(marathonDto);
        if (success)
            return ResponseEntity.status(HttpStatus.CREATED).body("Marathon successfully created!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create marathon");
    }

    @GetMapping("/marathons")
    public ResponseEntity<List<MarathonDto>> getAllMarathons() {
        List<MarathonDto> marathonDtoList = adminService.getAllMarathons();
        return ResponseEntity.ok(marathonDtoList);
    }

    @DeleteMapping("/marathon/{marathonId}")
    public ResponseEntity<Void> deleteMarathon(@PathVariable Long marathonId) {
        adminService.deleteMarathon(marathonId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/marathon/{marathonId}")
    public ResponseEntity<MarathonDto> getMarathonById(@PathVariable Long marathonId) {
        MarathonDto marathonDto = adminService.getMarathonById(marathonId);
        if (marathonDto != null) return ResponseEntity.ok(marathonDto);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/marathon/{marathonId}")
    public ResponseEntity<?> updateMarathon(@PathVariable Long marathonId, @ModelAttribute MarathonDto marathonDto) throws IOException {
        try {
            boolean success = adminService.updateMarathon(marathonId, marathonDto);
            if (success) return ResponseEntity.ok().build();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }


}

