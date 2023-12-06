package com.uob.mathpuzzle.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.uob.mathpuzzle.exception.GameException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.uob.mathpuzzle.constant.S3BucketFolderConstant.FOLDER_PATH_CSV;
import static com.uob.mathpuzzle.constant.S3BucketFolderConstant.FOLDER_PATH_IMAGE;

@Component
@Log4j2
public class FileWriter{

    @Value("${outgoingURL}")
    private String outgoingURL;

    private final Environment environment;
    private OutputStream outputStream;
    private BufferedOutputStream bufferedOutputStream;

    public static final String TEMP_FILE_PATH = "C:\\xampp\\htdocs\\game\\image";

    public static final int MULTIPART_FILE_SAVE_ERROR = 300;

    @Autowired
    public FileWriter(Environment environment) {
        this.environment = environment;
    }

    // save multipart file to htdocs
    public String saveMultipartFile(MultipartFile file, String type) {

        log.info("Start Function saveMultipartFile " + type);
        File serverFile = null;
        try {
            String fileUrl = null;
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileUploadDir = null;
            String fileDownloadDir = null;
            String name = UUID.randomUUID().toString();

            switch (type) {
                case "image":
                    fileDownloadDir = outgoingURL + FOLDER_PATH_IMAGE;
                    fileUploadDir = FOLDER_PATH_IMAGE.substring(1);
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null) {
                String[] split = originalFilename.split("\\.");
                extension = split[(split.length - 1)];
            }

            String fileName = name + FilenameUtils.EXTENSION_SEPARATOR_STR + extension;

            serverFile = convertMultiPartToFile(file, fileName);

            fileUrl = fileDownloadDir + fileName;

            log.info("Function saveMultipartFile ATTACHMENT PATH : " + fileUrl);

            return fileUrl;

        } catch (Exception e) {
            log.error("Function saveMultipartFile : " + e.getMessage(), e);
            e.printStackTrace();
            throw new GameException(MULTIPART_FILE_SAVE_ERROR, "Error occurred while saving attachment");
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                    if (serverFile != null) serverFile.delete();
                } catch (IOException e) {
                    log.error("Function saveMultipartFile : " + e.getMessage(), e);
                    throw new GameException(MULTIPART_FILE_SAVE_ERROR, "Error occurred while saving attachment");
                }
            }
        }

    }

    // Convert any multipart file to file
    private File convertMultiPartToFile(MultipartFile file, String fileName) throws IOException {
        log.info("Start function convertMultiPartToFile @ param fileName : " + fileName);
        FileOutputStream fos = null;
        try {

            Path path = Paths.get(TEMP_FILE_PATH, fileName);
            File convFile = new File(path.toString());
            fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            return convFile;
        } catch (Exception e) {
            log.error("convertMultiPartToFile : {}", e.getMessage());

            throw e;
        } finally {
            if (fos != null) fos.close();
        }

    }
}
