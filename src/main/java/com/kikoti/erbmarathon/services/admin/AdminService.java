package com.kikoti.erbmarathon.services.admin;

import com.kikoti.erbmarathon.dtos.MarathonDto;

import java.util.List;

public interface AdminService {
    boolean createMarathon(MarathonDto marathonDto);

    List<MarathonDto> getAllMarathons();
    void deleteMarathon(Long MarathonId);

    MarathonDto getMarathonById(Long marathonId);

    boolean updateMarathon(Long marathonId, MarathonDto marathonDto);
}
