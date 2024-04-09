package com.example.bookinglabor.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "headers")
@Getter
@Setter
public class Header {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String name;
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private UserAccount userAccount;

}
