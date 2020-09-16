package com.smartmenu.common.service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.configs.sftp.SftpConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class SftpService {

    @Autowired
    private SftpConfig sftpConfig;

    @Value("${sftp.remote.directory.upload}")
    private String sftpRemoteUploadDirectory;

    @Value("${sftp.local.directory.upload}")
    private String sftpLocaleUploadDirectory;

    public ServiceResult<String> uploadFileLocal(MultipartFile file) {
        ServiceResult<String> serviceResult = new ServiceResult<>();

        String randomFileName;
        try {
            randomFileName = RandomStringUtils.random(32, true, true) + "." + file.getContentType().split("/")[1];
            new File(sftpLocaleUploadDirectory + randomFileName).createNewFile();
            file.transferTo(new File(sftpLocaleUploadDirectory + randomFileName));
        } catch (IOException e) {
            e.printStackTrace();
            serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            serviceResult.setMessage(e.getMessage());
            return serviceResult;
        }

        serviceResult.setValue(randomFileName);
        serviceResult.setHttpStatus(HttpStatus.OK);

        return serviceResult;
    }

    public ServiceResult<Boolean> deleteFileLocal(String fileName) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<>();

        File file = new File(sftpLocaleUploadDirectory + fileName);
        if (!file.delete()) {
            serviceResult.setValue(false);
            serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            serviceResult.setMessage("File can not delete form file server!");
        }

        serviceResult.setValue(true);
        serviceResult.setHttpStatus(HttpStatus.OK);
        return serviceResult;
    }

    public ServiceResult<String> uploadFile(MultipartFile file) {
        ServiceResult<String> serviceResult = new ServiceResult<>();

        ChannelSftp channelSftp = sftpConfig.getSftpConnection();
        if (channelSftp == null) {
            serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            serviceResult.setMessage("File server connection failed!");
            return serviceResult;
        }

        String randomFileName;
        try {
            randomFileName = RandomStringUtils.random(32, true, true) + "." + file.getContentType().split("/")[1];

            channelSftp = sftpConfig.getSftpSession();
            channelSftp.connect();
            channelSftp.put(file.getInputStream(), sftpRemoteUploadDirectory + randomFileName);
        } catch (SftpException | IOException | JSchException e) {
            e.printStackTrace();
            serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            serviceResult.setMessage(e.getMessage());
            return serviceResult;
        }

        channelSftp.exit();
        serviceResult.setValue(randomFileName);
        serviceResult.setHttpStatus(HttpStatus.OK);

        return serviceResult;
    }

    public ServiceResult<Set<String>> uploadAllFiles(Set<MultipartFile> files) {
        ServiceResult<Set<String>> serviceResult = new ServiceResult<>();

        System.out.println("Conecting to SFTP server");
//        logger.info("Conecting to SFTP server");
        ChannelSftp channelSftp = sftpConfig.getSftpConnection();
        if (channelSftp == null) {
            System.out.println("Can not connect to SFTP server");
//            logger.error("Can not connect to SFTP server");
            serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            serviceResult.setMessage("File server connection failed!");
            return serviceResult;
        }

        Set<String> uploadedFileNames = new HashSet<>();

        System.out.println("Files will upload to remote repository");
//        logger.info("Files will upload to remote repository");
        for (MultipartFile file : files) {

            try {
                String contentType = file.getContentType();
                String randomFileName = RandomStringUtils.random(32, true, true) + "." + contentType.split("/")[1];
                channelSftp.put(file.getInputStream(), sftpRemoteUploadDirectory + randomFileName);

                uploadedFileNames.add(randomFileName);
            } catch (Exception e) {
                System.out.println("File can not upload to remote repository. Filename is : " + file.getOriginalFilename());
//                logger.error("File can not upload to remote repository. Filename is : " + file.getOriginalFilename());
            }
        }

        System.out.println("Closing SFTP connection");
//        logger.info("Closing SFTP connection");
        channelSftp.exit();
        serviceResult.setHttpStatus(HttpStatus.OK);
        serviceResult.setValue(uploadedFileNames);
        return serviceResult;
    }

    private String generateImageName(String contentType) {
        String randomFileName = RandomStringUtils.random(32, true, true) + "." + contentType.split("/")[1];
        randomFileName = randomFileName.toUpperCase();
        return randomFileName;
    }

    public ServiceResult<Boolean> deleteFile(String fileName) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<>();

        ChannelSftp channelSftp = sftpConfig.getSftpConnection();
        if (channelSftp == null) {
            serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            serviceResult.setMessage("File server connection failed!");
            return serviceResult;
        }

        try {
            channelSftp.rm(sftpRemoteUploadDirectory + fileName);
        } catch (SftpException e) {
            serviceResult.setValue(false);
            serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            serviceResult.setMessage("File can not delete form file server!");
        }

        channelSftp.exit();
        serviceResult.setValue(true);
        serviceResult.setHttpStatus(HttpStatus.OK);
        return serviceResult;
    }

    public ServiceResult<Set<String>> deleteAllFiles(Set<String> fileNames) {
        ServiceResult<Set<String>> serviceResult = new ServiceResult<>();

        System.out.println("Connecting to SFTP server");
//        logger.info("Connecting to SFTP server");
        ChannelSftp channelSftp = sftpConfig.getSftpConnection();
                if (channelSftp == null) {
                    System.out.println("Failed SFTP connection");
//                    logger.error("Failed SFTP connection");
                    serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                    serviceResult.setMessage("File server connection failed!");
                    return serviceResult;
                }

        Set<String> deletedFileNames = new HashSet<>();

        System.out.println("Documents will be deleted from remote repository");
//        logger.info("Documents will be deleted from remote repository");
        for (String fileName : fileNames) {
            try {
                channelSftp.rm(sftpRemoteUploadDirectory + fileName);
                deletedFileNames.add(fileName);
            } catch (Exception e) {
                System.out.println("File cant delete from remote repository. Filename is : " + fileName);
//                logger.error("File cant delete from remote repository. Filename is : " + fileName);
            }
        }

        System.out.println("Closing SFTP connection");
//        logger.info("Closing SFTP connection");
        channelSftp.exit();
        serviceResult.setHttpStatus(HttpStatus.OK);
        serviceResult.setValue(deletedFileNames);
        return serviceResult;
    }

}
