package com.GVNCop.app.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {
    private final ResourceLoader resourceLoader;
    private final AccountService accountService;
    @Value("${app.upload.dir}")
    private String uploadDIr;
    @Value("${app.url}")
    private String urlMain;
    public String uploadAvatarAccount(MultipartFile file,String accId){
        Long longId = Long.valueOf(accId);
        try {
            // Get the root path of the project
            Resource resource = resourceLoader.getResource(uploadDIr+"/Avatar");
            String pathStatic = resource.getFile().getAbsolutePath();
            String fileOriginalName = file.getOriginalFilename();
            String fileExtension = StringUtils.getFilenameExtension(fileOriginalName);
            String nameAvt = accId+"-avatar."+fileExtension;
            // Save the uploaded file to the static directory
            Path path = Paths.get(pathStatic, nameAvt);
            System.out.println(path);
            Files.write(path, file.getBytes());
            String fileUrl = urlMain+"/uploads/Avatar/" + nameAvt;
            accountService.updateAvt(fileUrl,longId);
            return "upload avatar success";
        } catch (IOException e) {
            return "false";
        }
    }
    public String getAvtUrl(String nameFile){
        return urlMain+"uploads/Avatar/"+nameFile;
    }
}
