package com.tourmanagement.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourmanagement.Models.Tour;
import com.tourmanagement.Services.TourService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ImageUploadController {

    private final TourService tourService;

    public ImageUploadController(TourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping("/uploads")
    public String uploadImage(@RequestParam("images")MultipartFile file,
                              @RequestParam(value = "id_tour", required = false) Long id) throws Exception{
        try {
            String Path_Directory = "src/main/resources/static.uploads";
            Files.copy(file.getInputStream(), Paths.get(Path_Directory + File.separator + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            Tour tour = tourService.getTourById(id);
            String updatedImages;
            if (tour != null) {
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
                imageList.add(file.getOriginalFilename());

                try {
                    updatedImages = new ObjectMapper().writeValueAsString(imageList);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return "Error occurred";
                }
                tour.setImages(updatedImages);

                tourService.saveTour(tour);
            }
            return "Success!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload and update the image.";
        }

    }
}
