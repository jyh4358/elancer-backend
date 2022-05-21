package com.example.elancer.s3.controller;

import com.example.elancer.s3.controller.S3FileControllerPath;
import com.example.elancer.s3.dto.S3FileResponse;
import com.example.elancer.s3.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class S3FileController {
    private final S3UploadService s3UploadService;

    @PostMapping(S3FileControllerPath.UPLOAD_FILE)
    public ResponseEntity<S3FileResponse> uploadFile(MultipartFile file) {
        String filePath = s3UploadService.uploadForMultiFile(file);
        S3FileResponse s3FileResponse = new S3FileResponse(file.getOriginalFilename(), filePath);
        return new ResponseEntity<S3FileResponse>(s3FileResponse, HttpStatus.OK);
    }
}
