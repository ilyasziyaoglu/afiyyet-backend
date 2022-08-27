package com.afiyyet.common.service;

import com.afiyyet.client.file.FileResponse;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.common.configs.sftp.SftpConfig;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FileService {

    private final SftpConfig sftpConfig;

    @Value("${app.baseCdnUrl}")
    private String baseCdnUrl;

    @Value("${sftp.remote.directory.upload}")
    private String sftpRemoteUploadDirectory;

    @Value("${sftp.local.directory.upload}")
    private String sftpLocaleUploadDirectory;

    public ServiceResult<FileResponse> uploadFileLocal(MultipartFile file) {
        String randomFileName;
        try {
            randomFileName = RandomStringUtils.random(32, true, true) + "." + file.getContentType().split("/")[1];
            new File(sftpLocaleUploadDirectory + randomFileName).createNewFile();
            file.transferTo(new File(sftpLocaleUploadDirectory + randomFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return new ServiceResult<>(new FileResponse(baseCdnUrl + randomFileName), HttpStatus.OK);
    }

    public ServiceResult<Boolean> deleteFileLocal(String fileName) {
        File file = new File(sftpLocaleUploadDirectory + fileName);
        if (!file.delete()) {
            return new ServiceResult<>(false, HttpStatus.INTERNAL_SERVER_ERROR, "File can not delete form file server!");
        }
        return new ServiceResult<>(true, HttpStatus.OK);
    }

    public ServiceResult<FileResponse> uploadFile(MultipartFile file) {
        ChannelSftp channelSftp = sftpConfig.getSftpConnection();
        if (channelSftp == null) {
            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "File server connection failed!");
        }

        String randomFileName;
        try {
            randomFileName = RandomStringUtils.random(32, true, true) + "." + file.getContentType().split("/")[1];

            channelSftp = sftpConfig.getSftpSession();
            channelSftp.connect();
            channelSftp.put(file.getInputStream(), sftpRemoteUploadDirectory + randomFileName);
        } catch (SftpException | IOException | JSchException e) {
            e.printStackTrace();
            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        channelSftp.exit();
        return new ServiceResult<>(new FileResponse(baseCdnUrl + randomFileName), HttpStatus.OK);
    }

    public ServiceResult<Set<String>> uploadAllFiles(Set<MultipartFile> files) {
        ChannelSftp channelSftp = sftpConfig.getSftpConnection();
        if (channelSftp == null) {
            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Can not connect to SFTP server");
        }

        Set<String> uploadedFileNames = new HashSet<>();
        for (MultipartFile file : files) {

            try {
                String contentType = file.getContentType();
                String randomFileName = RandomStringUtils.random(32, true, true) + "." + contentType.split("/")[1];
                channelSftp.put(file.getInputStream(), sftpRemoteUploadDirectory + randomFileName);

                uploadedFileNames.add(baseCdnUrl + randomFileName);
            } catch (Exception e) {
//                logger.error("File can not upload to remote repository. Filename is : " + file.getOriginalFilename());
            }
        }

//        logger.info("Closing SFTP connection");
        channelSftp.exit();
        return new ServiceResult<>(uploadedFileNames, HttpStatus.OK);
    }

    private String generateImageName(String contentType) {
        String randomFileName = RandomStringUtils.random(32, true, true) + "." + contentType.split("/")[1];
        randomFileName = randomFileName.toUpperCase();
        return randomFileName;
    }

    public ServiceResult<Boolean> deleteFile(String fileName) {
        ChannelSftp channelSftp = sftpConfig.getSftpConnection();
        if (channelSftp == null) {
            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "File server connection failed!");
        }

        try {
            channelSftp.rm(sftpRemoteUploadDirectory + fileName);
        } catch (SftpException e) {
            channelSftp.exit();
            return new ServiceResult<>(false, HttpStatus.INTERNAL_SERVER_ERROR, "File can not delete form file server!");
        }

        channelSftp.exit();
        return new ServiceResult<>(true, HttpStatus.OK);
    }

    public ServiceResult<Set<String>> deleteAllFiles(Set<String> fileNames) {

//        logger.info("Connecting to SFTP server");
        ChannelSftp channelSftp = sftpConfig.getSftpConnection();
        if (channelSftp == null) {
//          logger.error("Failed SFTP connection");
            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "File server connection failed!");
        }

        Set<String> deletedFileNames = new HashSet<>();

//        logger.info("Documents will be deleted from remote repository");
        for (String fileName : fileNames) {
            try {
                channelSftp.rm(sftpRemoteUploadDirectory + fileName);
                deletedFileNames.add(baseCdnUrl + fileName);
            } catch (Exception e) {
//                logger.error("File cant delete from remote repository. Filename is : " + fileName);
            }
        }

//        logger.info("Closing SFTP connection");
        channelSftp.exit();
        return new ServiceResult<>(deletedFileNames, HttpStatus.OK);
    }

}
