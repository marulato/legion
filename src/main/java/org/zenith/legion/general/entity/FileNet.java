package org.zenith.legion.general.entity;

import org.zenith.legion.common.base.BasePO;
import org.zenith.legion.common.persistant.annotation.Persistant;
import org.zenith.legion.common.persistant.annotation.PrimaryKey;

@Persistant(tableName = "CM_FILE_NET")
public class FileNet extends BasePO {

    @PrimaryKey(autoIncrement = true)
    private Long fileNetId;
    private String fileName;
    private String fileType;
    private Integer size;
    private String status;
    private String storageType;
    private String mimeType;
    private String fileUuid;
    private String path;
    private String sha512;
    private byte[] data;

    public Long getFileNetId() {
        return fileNetId;
    }

    public void setFileNetId(Long fileNetId) {
        this.fileNetId = fileNetId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStorageType() {
        return storageType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getFileUuid() {
        return fileUuid;
    }

    public void setFileUuid(String fileUuid) {
        this.fileUuid = fileUuid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSha512() {
        return sha512;
    }

    public void setSha512(String sha512) {
        this.sha512 = sha512;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
