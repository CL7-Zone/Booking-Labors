package com.example.bookinglabor.model;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "jobs")
@Getter
@Setter
public class Job {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameJob;
    private String imageJob;
    private String description;
    private double price;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private CategoryJob categoryJob;

    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE)
    private List<JobDetail> jobDetails = new ArrayList<>();

}
