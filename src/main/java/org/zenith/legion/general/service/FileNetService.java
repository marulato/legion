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

    public void saveFileNetFTP(String path, String fileName, byte[] data) throws Exception {
        if (StringUtils.isNotBlank(path) && data != null) {
            FileNet fileNet = new FileNet();
            fileNet.setSha512(DigestUtils.sha512Hex(data).toUpperCase());
            fileNet.setSize((long) data.length);
            fileNet.setFileUuid(getFileUUID(data));
            fileNet.setFileName(fileName);
            fileNet.setPath(path);
            fileNet.setStorageType(AppConsts.FILE_NET_STORAGE_TYPE_FTP);
            fileNet.setStatus(AppConsts.FILE_NET_STATUS_STORED);
            String extension = FilenameUtils.getExtension(fileNet.getFileName()).toUpperCase();
            fileNet.setFileType(StringUtils.isNotBlank(extension) ? extension : AppConsts.FILE_NET_FILE_TYPE_UNKNOWN);
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

    public void saveFileNetDB(String fileName, byte[] data) {
        if (StringUtils.isNotBlank(fileName) && data != null) {
            FileNet fileNet = new FileNet();
            fileNet.setSha512(DigestUtils.sha512Hex(data).toUpperCase());
            fileNet.setSize((long) data.length);
            fileNet.setFileUuid(getFileUUID(data));
            fileNet.setFileName(fileName);
            fileNet.setStorageType(AppConsts.FILE_NET_STORAGE_TYPE_DATABASE);
            fileNet.setStatus(AppConsts.FILE_NET_STATUS_STORED);
            fileNet.setData(data);
            String extension = FilenameUtils.getExtension(fileNet.getFileName()).toUpperCase();
            fileNet.setFileType(StringUtils.isNotBlank(extension) ? extension : AppConsts.FILE_NET_FILE_TYPE_UNKNOWN);
           SQLExecutor.save(fileNet);

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
