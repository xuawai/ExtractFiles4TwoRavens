package util;

import model.DataFile;
import model.FileMetadata;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by xuawai on 17/10/2017.7
 */
public class FileUtil {
    private DataFile dataFile;

    public void createDataFile(String fileName, String fileType, Long fileSize){
        this.dataFile = new DataFile();
        this.dataFile.setModificationTime(new Timestamp(new Date().getTime()));
        this.dataFile.setPermissionModificationTime(new Timestamp(new Date().getTime()));
        this.dataFile.setFilesize(fileSize);
        FileMetadata fmd = new FileMetadata();
        fmd.setLabel(fileName+"."+fileType);
        fmd.setDataFile(this.dataFile);
        this.dataFile.getFileMetadatas().add(fmd);
        generateStorageIdentifier(this.dataFile);
        this.dataFile.setChecksumType(DataFile.ChecksumType.MD5);
        //这里，我把文件名由临时名datafile.getStorageIdentifier()更换为原文件名
        this.dataFile.setChecksumValue(CalculateCheckSum(getFilesTempDirectory() + "/" + fileName + "." + fileType, this.dataFile.getChecksumType()));
        System.out.println("Here equals to the 985th in FileUtil.java or 1815th in EditDatafilesPage.java in Dataverse");

        //下面的代码对应EditDatafilesPage.java 1043行的save()方法
        //下面的代码对应IngestServiceBean.java 671行的方法


    }

    public static void generateStorageIdentifier(DataFile dataFile) {
        dataFile.setStorageIdentifier(FileUtil.generateStorageIdentifier());
    }

    public String getFilesTempDirectory(){
        return "/Users/xuawai/Public/Project/mengtai/project/ExtractFiles4TwoRavens/src/main/resources";
    }


    // from MD5Checksum.java
    public static String CalculateCheckSum(String datafile, DataFile.ChecksumType checksumType) {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(datafile);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        return CalculateChecksum(fis, checksumType);
    }

    // from MD5Checksum.java
    public static String CalculateChecksum(InputStream in, DataFile.ChecksumType checksumType) {
        MessageDigest md = null;
        try {
            // Use "SHA-1" (toString) rather than "SHA1", for example.
            md = MessageDigest.getInstance(checksumType.toString());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] dataBytes = new byte[1024];

        int nread;
        try {
            while ((nread = in.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                in.close();
            } catch (Exception e) {
            }
        }

        byte[] mdbytes = md.digest();
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String generateStorageIdentifier() {

        UUID uid = UUID.randomUUID();

        String hexRandom = uid.toString().substring(24);

        String hexTimestamp = Long.toHexString(new Date().getTime());

        String storageIdentifier = hexTimestamp + "-" + hexRandom;

        return storageIdentifier;
    }
}
