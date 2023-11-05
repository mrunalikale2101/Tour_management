package com.tourmanagement.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourmanagement.Models.Tour;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    private final TourService tourService;

    private final String path = "http://localhost:8080/api/v1/static/uploads/";

    public ImageService(TourService tourService) {
        this.tourService = tourService;
    }

    public String uploadImageAndAddToTour(MultipartFile[] files, Long tourId) {
        try {
            String baseDirectory  = "src/main/resources/static.uploads";

            String tourDirectory = baseDirectory + File.separator + tourId;

            File directory = new File(tourDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File[] existingImages = directory.listFiles();

            int newImageIndex = existingImages.length + 1;

            List<String> newImagePaths = new ArrayList<>();

            for (MultipartFile file : files) {
                String newFileName = newImageIndex + ".jpg";
                newImageIndex++;

                Files.copy(file.getInputStream(), Paths.get(tourDirectory + File.separator + newFileName), StandardCopyOption.REPLACE_EXISTING);

                newImagePaths.add(path + tourId + "/" + newFileName);
            }
            Tour tour = tourService.getTourById(tourId);
            if (tour != null) {
                String updatedImages = updateTourImages(tour, newImagePaths);
                tour.setImages(updatedImages);
                tourService.saveTour(tour);
                return "Success!";
            }
            return "Tour not found";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload and update the image.";
        }
    }

    private String updateTourImages(Tour tour, List<String> newImage) {
        String images = tour.getImages();
        List<String> imageList = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                imageList = objectMapper.readValue(images, List.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        imageList.addAll(newImage);

        try {
            return new ObjectMapper().writeValueAsString(imageList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }


}
