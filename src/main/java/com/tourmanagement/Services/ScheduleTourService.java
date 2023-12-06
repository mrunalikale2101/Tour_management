package com.tourmanagement.Services;

import com.tourmanagement.DTOs.Request.ScheduleTourReqDTO;
import com.tourmanagement.DTOs.Response.ScheduleTourRespDTO;
import com.tourmanagement.Models.ScheduleTour;
import com.tourmanagement.Repositorys.ScheduleTourRepository;
import com.tourmanagement.Shared.Utils.EntityDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleTourService {
    private final ScheduleTourRepository scheduleTourRepository;
    private final EntityDtoConverter entityConverter;

    @Autowired
    public ScheduleTourService(
            ScheduleTourRepository scheduleTourRepository,
            EntityDtoConverter entityConverter
    ) {
        this.scheduleTourRepository = scheduleTourRepository;
        this.entityConverter = entityConverter;
    }

    public ScheduleTourRespDTO handleCreate(ScheduleTour scheduleEntity) {
        ScheduleTour newSchedule = this.scheduleTourRepository.save(scheduleEntity);

        return entityConverter.convertToScheduleTourRespDTO(newSchedule);
    }

    public List<ScheduleTourRespDTO> handleGetSchedulesOfSpecificTour(Long tourId) {
        List<ScheduleTour> schedules = this.scheduleTourRepository.findSchedulesOfSpecificTour(tourId);
        return schedules.stream().map(entityConverter::convertToScheduleTourRespDTO).collect(Collectors.toList());
    }

    public ScheduleTourRespDTO handleDelete(Long id) {
        ScheduleTour scheduleToDelete = this.scheduleTourRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule with id [%s] is not found".formatted(id)));

        this.scheduleTourRepository.deleteById(id);
        return entityConverter.convertToScheduleTourRespDTO(scheduleToDelete);
    }

    public ScheduleTourRespDTO handleUpdate(Long id, ScheduleTourReqDTO scheduleReqData) {
        ScheduleTour scheduleToUpdate = this.scheduleTourRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule with id [%s] is not found".formatted(id)));

        scheduleToUpdate.setDay(scheduleReqData.getDay());
        scheduleToUpdate.setDescription(scheduleReqData.getDescription());
        scheduleToUpdate.setTitle(scheduleReqData.getTitle());

        scheduleToUpdate = this.scheduleTourRepository.save(scheduleToUpdate);
        return entityConverter.convertToScheduleTourRespDTO(scheduleToUpdate);
    }
}
