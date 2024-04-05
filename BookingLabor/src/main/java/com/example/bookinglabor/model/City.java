package com.example.bookinglabor.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cities")
@Getter
@Setter
public class City {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city_name;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    @OneToMany(mappedBy = "city", cascade = CascadeType.REMOVE)
    private List<Labor> labors = new ArrayList<>();
    @OneToMany(mappedBy = "city", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

}
