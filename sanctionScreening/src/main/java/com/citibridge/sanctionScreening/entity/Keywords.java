package com.citibridge.sanctionScreening.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "keyword")
public class Keywords {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
private long occurrences;
    public Keywords(String name) {
        this.name = name;
    }
    public void occured(){
        this.occurrences++;
    }
}
