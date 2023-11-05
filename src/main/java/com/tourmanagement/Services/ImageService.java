package com.tourmanagement.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    @Value("${app.url}")
    public String HOST;
    private final String URL_PATH = "/api/v1/static/uploads/";

    public List<String> uploadMultipleImage(MultipartFile[] files, Long tourId) {
        try {
            String BASE_DIRECTORY = "src/main/resources/static/uploads";
            String tourDirectory = BASE_DIRECTORY + File.separator + tourId;

            File directory = new File(tourDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File[] existingImages = directory.listFiles();

            if (existingImages == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request!");
            }

            int newImageIndex = existingImages.length + 1;

            List<String> newImagePaths = new ArrayList<>();
            String hostUrl = HOST + URL_PATH + tourId + "/";
            for (MultipartFile file : files) {
                String newFileName = newImageIndex + ".jpg";
                newImageIndex++;

                Files.copy(file.getInputStream(), Paths.get(tourDirectory + File.separator + newFileName), StandardCopyOption.REPLACE_EXISTING);
                newImagePaths.add(hostUrl + newFileName);
            }

            return newImagePaths;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to upload");
        }
    }

    public boolean isEmptyFilesArray(MultipartFile[] files) {
        if (files == null) {
            return true;
        }

        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
