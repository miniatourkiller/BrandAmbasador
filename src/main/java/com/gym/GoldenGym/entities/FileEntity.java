package com.gym.GoldenGym.entities;

import com.gym.GoldenGym.utils.FileServices;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class FileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String storeFileName;
    private String displayName;
    private String contentType;
    private Long size;

    public FileEntity(String storeFileName, String displayName, String contentType, Long size) {
        this.storeFileName = storeFileName;
        this.displayName = displayName;
        this.contentType = contentType;
        this.size = size;
    }

    public String getFileSize() {
        return FileServices.realFileSize(this.size);
    }
}
