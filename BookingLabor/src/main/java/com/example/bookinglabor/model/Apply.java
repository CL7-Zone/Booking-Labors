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
@Table(name = "applies")
@Getter
@Setter
public class Apply {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String education;
    String experience;
    String about;
    String image_apply;


    @ManyToOne
    @JoinColumn(name = "post_id", nullable = true)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private UserAccount userAccount;

    @OneToMany(mappedBy = "apply", cascade = CascadeType.REMOVE)
    private List<FileUpload> fileUploads = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

}
