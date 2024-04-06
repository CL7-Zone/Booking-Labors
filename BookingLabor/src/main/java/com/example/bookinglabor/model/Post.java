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
@Table(name = "posts")
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String you_are;
    private String business_name;
    private String phone_number;
    private String official_address;
    private String image_workplace;
    private String title;
    private int quantity;
    private String pay_form;
    private double max_payroll;
    private double min_payroll;
    private String description;
    private String max_age;
    private String min_age;
    private String education;
    private String experience;
    private String requirement;
    private int re_image;

    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = true)
    private City city;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private CategoryJob categoryJob;
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = true)
    private Job job;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private UserAccount userAccount;


}
















