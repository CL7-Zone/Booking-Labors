package com.example.bookinglabor.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String full_name;
    private String ident_code;
    private String address;
    private String phone_number;
    private  Date birthday_customer;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private UserAccount userAccount;

    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Booking> bookings = new ArrayList<>();
}
