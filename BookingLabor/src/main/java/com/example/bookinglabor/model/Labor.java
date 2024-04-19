package com.example.bookinglabor.model;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "labors")
@Getter
@Setter
public class Labor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String full_name;
    private String address;
    private String phone_number;
    private int status;
    private Date birthday;
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = true)
    private City city;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private UserAccount userAccount;

    @OneToMany(mappedBy = "labor", cascade = CascadeType.REMOVE)
    private List<JobDetail> jobDetails = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

}
