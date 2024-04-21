package com.example.bookinglabor.model;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bookings")
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int accept;
    private int status;
    private double total_price;
    private String book_address;
    private String message;
    private String city_name;
    private Date checkin;
    private Date checkout;
    private LocalDateTime cancel_time;
    @ManyToOne
    @JoinColumn(name = "job_detail_id", nullable = true)
    private JobDetail jobDetail;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;


}
