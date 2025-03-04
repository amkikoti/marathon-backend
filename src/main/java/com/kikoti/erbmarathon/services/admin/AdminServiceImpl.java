package com.kikoti.erbmarathon.services.admin;

import com.kikoti.erbmarathon.dtos.MarathonDto;
import com.kikoti.erbmarathon.entity.Marathon;
import com.kikoti.erbmarathon.repository.MarathonRepository;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MarathonRepository marathonRepository;

    @Override
    public boolean createMarathon(MarathonDto marathonDto) {
        try {
            Marathon marathon = new Marathon();
            marathon.setName(marathonDto.getName());
            marathon.setLocation(marathonDto.getLocation());
            marathon.setDate(marathonDto.getDate());
            marathon.setTime(marathonDto.getTime());
            marathon.setDescription(marathonDto.getDescription());
            marathon.setMaxParticipants(marathonDto.getMaxParticipants());
            marathon.setActive(marathonDto.isActive());
            marathon.setRegistrationFee(marathonDto.getRegistrationFee());
            marathonRepository.save(marathon);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Override
    public List<MarathonDto> getAllMarathons() {
        return marathonRepository.findAll().stream().map(Marathon::getMarathonDto).collect(Collectors.toList());
    }

    public void deleteMarathon(Long marathonId) {
        // Check if the marathon exists
        if (marathonRepository.existsById(marathonId)) {
            marathonRepository.deleteById(marathonId);
        } else {
            throw new RuntimeException("Marathon not found with id: " + marathonId);
        }
    }


    @Override
    public MarathonDto getMarathonById(Long marathonId) {
        Optional<Marathon> optionalMarathon = marathonRepository.findById(marathonId);
        return optionalMarathon.map(Marathon::getMarathonDto).orElse(null);
    }

    @Override
    public boolean updateMarathon(Long marathonId, MarathonDto marathonDto) throws IOException {
        Optional<Marathon> optionalMarathon = marathonRepository.findById(marathonId);
        if(optionalMarathon.isPresent()) {
            Marathon existingMarathon = optionalMarathon.get();
            existingMarathon.setName(marathonDto.getName());
            existingMarathon.setLocation(marathonDto.getLocation());
            existingMarathon.setDate(marathonDto.getDate());
            existingMarathon.setTime(marathonDto.getTime());
            existingMarathon.setDescription(marathonDto.getDescription());
            existingMarathon.setMaxParticipants(marathonDto.getMaxParticipants());
            existingMarathon.setActive(marathonDto.isActive());
            existingMarathon.setRegistrationFee(marathonDto.getRegistrationFee());
            marathonRepository.save(existingMarathon);
            return true;

        }
        return false;
    }
}
