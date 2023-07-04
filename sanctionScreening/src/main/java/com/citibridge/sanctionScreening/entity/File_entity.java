package com.citibridge.sanctionScreening.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "file")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class File_entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String fileId;


    private String fileName;

    public File_entity(String fileName) {
        this.fileName = fileName;
    }
}
