package com.GVNCop.app.Controller;

import com.GVNCop.app.Service.FileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Validated
public class FileController {
    private final FileService fileService;
    @PostMapping("image")
    public ResponseEntity<?> uploadFile( @RequestParam("file")MultipartFile file,@RequestParam("accId") String accId){
        System.out.println(file+" -- "+accId);
        return ResponseEntity.ok(fileService.uploadAvatarAccount(file,accId));
    }
}
