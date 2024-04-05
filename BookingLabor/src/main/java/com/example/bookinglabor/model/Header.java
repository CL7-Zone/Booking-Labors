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
    private String id;
    private String name;
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private UserAccount userAccount;

}
