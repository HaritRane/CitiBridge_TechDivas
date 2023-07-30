package com.citibridge.sanctionScreening.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "file")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class File_entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long fileId;

    @Column(unique = true)
    private String fileName;
    @Column(unique = true)
    private String filePath;

    public File_entity(String fileName) {
        this.fileName = fileName;
    }
}
