package com.tourmanagement.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class ImageService {
    @Value("${app.url}")
    public String HOST;
    private final String URL_PATH = "/api/v1/static/uploads/";

    public void deleteImage(String folderName, String fileName) {
        try {
            String BASE_DIRECTORY = "src/main/resources/static/uploads";
            String directoryFolder = BASE_DIRECTORY + File.separator + folderName;

            String fullPath = directoryFolder + File.separator + fileName;

            File fileToDelete = new File(fullPath);

            if (fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    System.out.println("File deleted successfully: " + fullPath);
                } else {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete file");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to delete file");
        }
    }
    public String uploadSingleImage(MultipartFile file, String folderName) {
        try {
            String BASE_DIRECTORY = "src/main/resources/static/uploads";
            String directoryFolder = BASE_DIRECTORY + File.separator + folderName;

            File folder = new File(directoryFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String randomFileName = StringUtils.cleanPath(StringUtils.getFilename(UUID.randomUUID().toString() + fileName));

            String fullPath = directoryFolder + File.separator + randomFileName;

            Path destinationPath = Path.of(fullPath);
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            return HOST + URL_PATH + folderName + "/" + randomFileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to upload");
        }
    }

    public List<String> uploadMultipleImages(List<MultipartFile> files, String folderName) {
        try {
            String BASE_DIRECTORY = "src/main/resources/static/uploads";
            String directoryFolder = BASE_DIRECTORY + File.separator + folderName;

            File folder = new File(directoryFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            List<String> uploadedFileUrls = new ArrayList<>();

            for (MultipartFile file : files) {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                String randomFileName = StringUtils.cleanPath(StringUtils.getFilename(UUID.randomUUID().toString() + fileName));

                String fullPath = directoryFolder + File.separator + randomFileName;

                Path destinationPath = Path.of(fullPath);
                Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                String uploadedFileUrl = HOST + URL_PATH + folderName + "/" + randomFileName;
                uploadedFileUrls.add(uploadedFileUrl);
            }

            return uploadedFileUrls;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to upload");
        }
    }

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

    public boolean isEmptyFile(MultipartFile file){
        return file == null || file.isEmpty();
    }

}
