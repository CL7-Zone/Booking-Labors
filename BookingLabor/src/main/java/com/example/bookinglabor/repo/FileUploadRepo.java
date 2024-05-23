package com.example.bookinglabor.repo;

import com.example.bookinglabor.model.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileUploadRepo extends JpaRepository<FileUpload, String> {

    FileUpload findFileUploadByApplyId(Long apply_id);

    @Modifying
    @Query(value ="DELETE FROM file_uploads WHERE " +
                    "file_uploads.apply_id = :apply_id",
                    nativeQuery = true)
    void deleteByApply_Id(@Param("apply_id") Long apply_id);
}
