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

    private AmazonS3 s3client;

    @Value("${accessKey}")
    private String accessKey;

    @Value("${secretKey}")
    private String secretKey;

    @Value("${endpointUrl}")
    private String endpointUrl;

    @Value("${bucketName}")
    private String bucketName;

    @Value("${outgoingURL}")
    private String outgoingURL;

    private final Environment environment;
    private OutputStream outputStream;
    private BufferedOutputStream bufferedOutputStream;

    public static final String TEMP_FILE_PATH = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\resources\\math";
    //public static final String TEMP_FILE_PATH = "/resource/temp";

    public static final int MULTIPART_FILE_SAVE_ERROR = 300;

    @PostConstruct
    private void initializeAmazon() {
        log.debug("Start...");
        log.debug("accessKey : " + accessKey);
        log.debug("secretKey : " + secretKey);
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    @Autowired
    public FileWriter(Environment environment) {
        this.environment = environment;
    }

    // save multipart file to s3 bucket
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
                case "file":
                    fileDownloadDir = outgoingURL + FOLDER_PATH_CSV;
                    fileUploadDir = FOLDER_PATH_CSV.substring(1);
                    break;
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

            uploadFileTos3bucket(fileUploadDir + fileName, serverFile);

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

    //    upload files in s3 bucket
    private void uploadFileTos3bucket(String fileName, File file) {
        log.info("Start function uploadFileTos3bucket : " + fileName);
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.Private));
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

    // get file from s3 bucket
    public void getFileFromS3bucket(String downloadDirectory, String fileName) {
        try {
            log.debug("file location : " + downloadDirectory);
            log.debug("file name : " + fileName);
            InputStream inputStream = s3client.getObject(bucketName, downloadDirectory + fileName).getObjectContent();
            FileUtils.copyInputStreamToFile(inputStream, new File(TEMP_FILE_PATH + fileName));
        } catch (Exception e) {
        	e.printStackTrace();
            log.error("getFileFroms3bucket : " + e.getMessage(), e);
        }
    }
}
