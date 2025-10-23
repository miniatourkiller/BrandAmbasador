package com.gym.GoldenGym.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.gym.GoldenGym.entities.FileEntity;
import com.gym.GoldenGym.repos.FileEntityRepo;
import com.gym.GoldenGym.utils.FileServices;
import com.gym.GoldenGym.utils.Props;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class FilesServices {
    private final FileEntityRepo fileEntityRepo;
    private final Props props;
    private final FileServices fileServices;

     public List<FileEntity> fileUploads(List<MultipartFile> files, String nameChars)
            throws Exception {
        List<FileEntity> filesToBeSaved = new ArrayList<>();
        for (MultipartFile file : files) {
            FileEntity fileEntity = new FileEntity();
            String fileExtension = fileServices.fileExtension(file);
            String fileName = fileServices.generateFileName(nameChars);
            fileEntity.setContentType(fileExtension);
            fileEntity.setDisplayName(
                    file.getOriginalFilename().length() > 20 ? file.getOriginalFilename().substring(0, 20) + "..."
                            : file.getOriginalFilename());
            fileEntity.setStoreFileName(fileName + "." + fileExtension);
            fileEntity.setSize(fileServices.fileSize(file));

            // transfer file
            String transferLocation = props.fileStorePath();

            fileServices.uploadFile(file, transferLocation, fileName);
            filesToBeSaved.add(fileEntity);
        }
        List<FileEntity> savedFiles = fileEntityRepo.saveAll(filesToBeSaved);
        return savedFiles;
    }
}
