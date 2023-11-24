package com.tourmanagement.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourmanagement.DTOs.Payload.PaginationRequest;
import com.tourmanagement.DTOs.Payload.TourPayload;
import com.tourmanagement.DTOs.Request.SearchTourDTO;
import com.tourmanagement.DTOs.Request.TourDTO;
import com.tourmanagement.DTOs.Response.BookedTourRespDTO;
import com.tourmanagement.DTOs.Response.PaginationRespDTO;
import com.tourmanagement.DTOs.Response.TourRespDTO;
import com.tourmanagement.Dao.Impl.TourDaoImpl;
import com.tourmanagement.Models.BookedTour;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Models.TourGuide;
import com.tourmanagement.Repositorys.TourRepository;
import com.tourmanagement.Shared.Utils.Converter;
import com.tourmanagement.Shared.Utils.EntityDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourService {
    private final TourRepository tourRepository;
    private final ModelMapper modelMapper;
    private final EntityDtoConverter entityDtoConverter;
    private final ImageService imageService;
    private final TourDaoImpl tourDao;

    private final TourGuideService tourGuideService;

    @Autowired
    public TourService(TourRepository tourRepository, ModelMapper modelMapper, EntityDtoConverter entityDtoConverter, ImageService imageService,TourDaoImpl tourDao, TourGuideService tourGuideService) {
        this.tourRepository = tourRepository;
        this.modelMapper = modelMapper;
        this.entityDtoConverter = entityDtoConverter;
        this.imageService = imageService;
        this.tourDao = tourDao;
        this.tourGuideService = tourGuideService;
    }

    public List<TourRespDTO> getTours(){
        List<Tour> tours = tourRepository.findAll();

        return tours.stream()
                .map(entityDtoConverter::convertToTourRespDTO)
                .collect(Collectors.toList());
    }

    public Long getCountTour() {
        return tourRepository.count();
    }

    public Tour getTourById(Long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour with id [%s] is not found!".formatted(id)));
        return tour;
    }

    public Tour createTour(TourPayload tourPayload) {
        if(imageService.isEmptyFilesArray(tourPayload.getImages())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No uploaded images");
        }

        TourDTO tourDTO = tourPayload.convertTourPayloadToTourDTO();
        Tour newTour = modelMapper.map(tourDTO, Tour.class);

        if (tourPayload.getGuide_id() != null) {
            TourGuide guide = tourGuideService.getTourGuideById(tourPayload.getGuide_id());
            newTour.setGuide(guide);
        }

        newTour = tourRepository.save(newTour);

        List<String> images = this.imageService.uploadMultipleImage(tourPayload.getImages(), newTour.getId());
        newTour.setImages(Converter.convertListImagesToJson(images));

        return tourRepository.save(newTour);
    }

    public Tour updateDataTour(Long id, TourPayload tourPayload) {
        getTourById(id);

        TourDTO tourDTO = tourPayload.convertTourPayloadToTourDTO();
        Tour updatedTour = modelMapper.map(tourDTO, Tour.class);
        updatedTour = tourRepository.save(updatedTour);
        updatedTour.setId(id);

        if (tourPayload.getGuide_id() != null) {
            TourGuide guide = tourGuideService.getTourGuideById(tourPayload.getGuide_id());
            updatedTour.setGuide(guide);
        }

        List<String> images = getImages(id);
        updatedTour.setImages(Converter.convertListImagesToJson(images));

        return tourRepository.save(updatedTour);
    }

    public Tour updateuploadImageTour(Long id, MultipartFile file, int index) {
       Tour tourUpdate = tourRepository.findById(id)
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour with id [%s] is not found!".formatted(id)));

        List<String> images = this.imageService.UpdateuploadSingleImage(file, id, index, getImages(id));
        tourUpdate.setImages(Converter.convertListImagesToJson(images));

        return tourRepository.save(tourUpdate);
    }

    public List<String> getImages(Long id) {
        Tour tour = getTourById(id);
        if (tour.getImages() == null) {
            return new ArrayList<>();
        }

        try {
            return new ObjectMapper().readValue(tour.getImages(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void deleteTour(Long id) {
        getTourById(id);
        tourRepository.deleteById(id);
    }

    public List<TourRespDTO> searchTours(SearchTourDTO searchTourDTO) {
        var tours = tourDao.searchTours(searchTourDTO);

        return tours.stream()
                .map(entityDtoConverter::convertToTourRespDTO)
                .collect(Collectors.toList());
    }

    public List<Tour> filterToursByPrice(Double minPrice, Double maxPrice) {
        return tourRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Tour> getTopRatedTours(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Tour> topRatedTours = tourRepository.findTopRatedTours(pageable);
        return topRatedTours;
    }

    public List<TourRespDTO> getTodayTour() {
        List<Tour> tours = tourRepository.findToDayTour(Converter.convertDateUtilToSqlDate(new Date()));

        return tours.stream()
                .map(entityDtoConverter::convertToTourRespDTO)
                .collect(Collectors.toList());
    }

    public void saveTour(Tour tour) {
        tourRepository.save(tour);
    }


    public Page<Tour> getToursByPage(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return tourRepository.findAll(pageable);
    }


    public PaginationRespDTO<TourRespDTO> getAllTour(PaginationRequest pagination) {
        PaginationRespDTO<TourRespDTO> result = new PaginationRespDTO<TourRespDTO>();
        result.setTotal(tourRepository.count());
        result.setPage(pagination.getPage());
        result.setItemsPerPage(pagination.getItemsPerPage());
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getItemsPerPage());

        Page<Tour> TourPage = tourRepository.findAll(pageable);
        result.setData(
                TourPage.getContent().stream()
                        .map(entityDtoConverter::convertToTourRespDTO)
                        .collect(Collectors.toList()));

        return result;
    }

}