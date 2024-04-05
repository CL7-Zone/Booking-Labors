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
@Table(name = "job_details")
@Getter
@Setter
public class JobDetail {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String official_work_address;
    private String experience;
    private String education;
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = true)
    private Job job;
    @ManyToOne
    @JoinColumn(name = "labor_id", nullable = true)
    private Labor labor;

    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "jobDetail", cascade = CascadeType.REMOVE)
    private List<Booking> bookings = new ArrayList<>();
    @OneToMany(mappedBy = "jobDetail", cascade = CascadeType.REMOVE)
    List<CommentSkill> commentSkills = new ArrayList<>();
}
