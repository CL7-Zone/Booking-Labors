package com.example.bookinglabor.model;


import com.example.bookinglabor.controller.component.EnumComponent;
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
@Table(name = "users")
@Getter
@Setter
public class UserAccount {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private EnumComponent provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "active")
    private EnumComponent active;

    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.REMOVE)
    private List<Labor> labors = new ArrayList<>();
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.REMOVE)
    private List<Customer> customers = new ArrayList<>();
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.REMOVE)
    private List<Header> headers = new ArrayList<>();
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.REMOVE)
    private List<Apply> applies = new ArrayList<>();
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.REMOVE)
    private List<Report> reports = new ArrayList<>();
}
