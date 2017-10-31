package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataFile extends DvObject{

    private static final long serialVersionUID = 1L;

    public enum ChecksumType {

        MD5("MD5"),
        SHA1("SHA-1");

        private final String text;

        private ChecksumType(final String text) {
            this.text = text;
        }

        public static ChecksumType fromString(String text) {
            if (text != null) {
                for (ChecksumType checksumType : ChecksumType.values()) {
                    if (text.equals(checksumType.text)) {
                        return checksumType;
                    }
                }
            }
            throw new IllegalArgumentException("ChecksumType must be one of these values: " + Arrays.asList(ChecksumType.values()) + ".");
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public DataFile(){
        this.fileMetadatas = new ArrayList<FileMetadata>();
    }

    private String contentType = "text/tab-separated-values";

    private Timestamp permissionModificationTime;

    private Timestamp modificationTime;

    private List<FileMetadata> fileMetadatas;

    private String fileSystemName;

    private ChecksumType checksumType;

    private String checksumValue;

    private Long filesize;

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public String getFileSystemName() {
        return fileSystemName;
    }

    public void setFileSystemName(String fileSystemName) {
        this.fileSystemName = fileSystemName;
    }

    public ChecksumType getChecksumType() {
        return checksumType;
    }

    public void setChecksumType(ChecksumType checksumType) {
        this.checksumType = checksumType;
    }

    public String getChecksumValue() {
        return checksumValue;
    }

    public void setChecksumValue(String checksumValue) {
        this.checksumValue = checksumValue;
    }

    public String getStorageIdentifier() {
        return this.fileSystemName;
    }

    public void setStorageIdentifier(String storageIdentifier) {
        this.fileSystemName = storageIdentifier;
    }

    public List<FileMetadata> getFileMetadatas() {
        return fileMetadatas;
    }

    public void setFileMetadatas(List<FileMetadata> fileMetadatas) {
        this.fileMetadatas = fileMetadatas;
    }



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Timestamp getPermissionModificationTime() {
        return permissionModificationTime;
    }

    public void setPermissionModificationTime(Timestamp permissionModificationTime) {
        this.permissionModificationTime = permissionModificationTime;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Timestamp getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Timestamp modificationTime) {
        this.modificationTime = modificationTime;
    }
} // end of class




