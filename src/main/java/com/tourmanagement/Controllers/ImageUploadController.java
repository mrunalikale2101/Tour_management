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
                              @RequestParam(value = "id_tour", required = false) Long id,
                              @RequestParam(value = "index") int index) throws Exception{
        try {
            String Path_Directory = "src/main/resources/static/uploads";
            String destinationPath = Path_Directory + File.separator + id + File.separator + index + "." + "jpg";
            Files.copy(file.getInputStream(), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
            return "Success!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to update the image.";
        }

    }
}