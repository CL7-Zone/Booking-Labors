package com.example.bookinglabor.model;


import com.example.bookinglabor.model.Apply;
import com.example.bookinglabor.model.Post;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "file_uploads")
@Data
@NoArgsConstructor
@Setter
@Getter
public class FileUpload {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    String fileName;
    String fileType;
    @Lob
    private byte[] data;
    @ManyToOne
    @JoinColumn(name = "apply_id", nullable = true)
    private Apply apply;

    public FileUpload(String fileName, String fileType, byte[] data) {

        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
