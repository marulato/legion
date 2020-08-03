package org.zenith.legion.general.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.zenith.legion.common.consts.AppConsts;
import org.zenith.legion.common.consts.SystemConsts;
import org.zenith.legion.common.persistant.exec.SQLExecutor;
import org.zenith.legion.common.utils.FTPClients;
import org.zenith.legion.common.utils.StringUtils;
import org.zenith.legion.general.entity.FileNet;
import org.zenith.legion.general.ex.FTPUploadException;

import java.io.ByteArrayInputStream;
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

    public void saveToFileNet(String path, String fileName, byte[] data) throws Exception {
        if (StringUtils.isNotBlank(path) && data != null) {
            FileNet fileNet = new FileNet();
            fileNet.setSha512(DigestUtils.sha512Hex(data));
            fileNet.setSize((long) data.length);
            fileNet.setFileUuid(getFileUUID(data));
            fileNet.setFileName(fileName);
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
            InputStream inputStream = new ByteArrayInputStream(data);
            boolean isUploaded = ftpClients.upload(fileNet.getPath(), fileNet.getFileUuid(), inputStream);
            ftpClients.disConnect();
            inputStream.close();
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
