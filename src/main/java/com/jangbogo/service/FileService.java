package com.jangbogo.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class FileService {

    @Autowired
    private ResourceLoader resourceLoader;

    public String uploadFile(String originalFileName, byte[] fileData) throws Exception{
        String uploadpath = resourceLoader.getResource("file:/Users/jeon-eunbi/Desktop/jangbogo_main/src/main/frontend/public/img/profile").getFile().getAbsolutePath();

        UUID uuid = UUID.randomUUID();

        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //확장자
        String savedFileName = uuid.toString() + extension; //파일 이름
        String fileUploadFullUrl = uploadpath + "/" + savedFileName; //경로

        log.info("파일 저장 처리");
        log.info("파일 저장 경로 = {}", fileUploadFullUrl);

        //FileOutputStream : 예외처리 필
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception{
        String uploadpath = resourceLoader.getResource("file:/Users/jeon-eunbi/Desktop/jangbogo_main/src/main/frontend/public/img").getFile().getAbsolutePath();

        String deleteFilepath = uploadpath + filePath;

        File deleteFile = new File(deleteFilepath);
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
