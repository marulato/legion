package org.zenith.legion.sysadmin.service;

import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zenith.legion.common.consts.AppConsts;
import org.zenith.legion.common.consts.SystemConsts;
import org.zenith.legion.common.persistant.exec.SQLExecutor;
import org.zenith.legion.common.utils.FTPClients;
import org.zenith.legion.common.utils.StringUtils;
import org.zenith.legion.sysadmin.entity.EmailEntity;
import org.zenith.legion.sysadmin.entity.FileNet;
import org.zenith.legion.sysadmin.ex.FTPUploadException;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.UUID;

@Service
public class FileNetService {


    public static String getFileUUIDFirstHalf(byte[] fileData) {
        if (fileData != null) {
            return UUID.nameUUIDFromBytes(fileData).toString().replace("-", "");
        }
        return null;
    }

    public static String getEmailStoragePath(String emailAddress) {
        return MessageFormat.format(SystemConsts.FILE_STORAGE_PATH_EMAIL_ATTACHMENT, emailAddress);
    }

    public void saveToFileNet(String path, MultipartFile attachment) throws Exception {
        if (StringUtils.isNotBlank(path) && attachment != null) {
            FileNet fileNet = new FileNet();
            byte[] data = attachment.getBytes();
            fileNet.setSha512(DigestUtils.sha512Hex(data));
            fileNet.setSize((long) attachment.getInputStream().available());
            fileNet.setFileUuid(getFileUUID(data));
            fileNet.setFileName(attachment.getOriginalFilename());
            fileNet.setPath(path);
            fileNet.setSourceType(AppConsts.FILE_NET_SRC_TYPE_EMAIL_ATTACHMENT);
            fileNet.setStorageType(AppConsts.FILE_NET_STORAGE_TYPE_FTP);
            fileNet.setStatus(AppConsts.FILE_NET_STATUS_STORED);
            String extension = FilenameUtils.getExtension(fileNet.getFileName()).toUpperCase();
            if (StringUtils.isNotBlank(extension)) {
                if (extension.length() > 4) {
                    extension = extension.substring(0, 4);
                }
                fileNet.setFileType(extension);
            } else {
                fileNet.setFileType(AppConsts.FILE_NET_FILE_TYPE_UNKNOWN);
            }
            FTPClients ftpClients = new FTPClients(true);
            boolean isUploaded = ftpClients.upload(fileNet.getPath(), fileNet.getFileUuid(), attachment.getInputStream());
            ftpClients.disConnect();
            if (isUploaded) {
                SQLExecutor.save(fileNet);
            } else {
                throw new FTPUploadException("Upload file to FTP FAILED");
            }
        }
    }

    private String getFileUUID(byte[] fileData) {
        if (fileData != null) {
            String fileUUID = UUID.nameUUIDFromBytes(fileData).toString().replace("-", "");
            String random = UUID.randomUUID().toString();
            return fileUUID + "$" + random;
        }
        return null;
    }
}
