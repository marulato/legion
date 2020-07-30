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
import org.zenith.legion.common.utils.StringUtils;
import org.zenith.legion.sysadmin.entity.EmailEntity;
import org.zenith.legion.sysadmin.entity.FileNet;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.UUID;

@Service
public class FileNetService {

    private String getFileUUID(byte[] fileData) {
        if (fileData != null) {
            String fileUUID = UUID.nameUUIDFromBytes(fileData).toString().replace("-", "");
            String random = UUID.randomUUID().toString();
            return fileUUID + "$" + random;
        }
        return null;
    }

    public static String getFileUUIDFirstHalf(byte[] fileData) {
        if (fileData != null) {
            return UUID.nameUUIDFromBytes(fileData).toString().replace("-", "");
        }
        return null;
    }

    public static String getEmailStoragePath(String emailAddress) {
        return MessageFormat.format(SystemConsts.FILE_STORAGE_PATH_EMAIL_ATTACHMENT, emailAddress);
    }

    public void saveEmailAttachmentToFileNet(EmailEntity emailEntity, MultipartFile attachment) throws Exception {
        if (emailEntity != null && attachment != null) {
            FileNet fileNet = new FileNet();
            byte[] data = attachment.getBytes();
            fileNet.setSha512(DigestUtils.sha512Hex(data));
            fileNet.setSize((long) attachment.getInputStream().available());
            fileNet.setFileUuid(getFileUUID(data));
            String extension = FilenameUtils.getExtension(fileNet.getFileName()).toUpperCase();
            if (StringUtils.isNotBlank(extension)) {
                if (extension.length() > 4) {
                    extension = extension.substring(0, 4);
                }
                fileNet.setFileType(extension);
            } else {
                fileNet.setFileType(AppConsts.FILE_NET_FILE_TYPE_UNKNOWN);
            }
        }
    }
}
