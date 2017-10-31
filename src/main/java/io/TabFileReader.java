package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuawai on 17/10/2017.
 */
public class TabFileReader {

    private String path;
    private String fileName;

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

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }

    public List<String[]> getValues() {
        return values;
    }

    public void setValues(List<String[]> values) {
        this.values = values;
    }

    private String fileType;
    private long lastModified;
    private long size;
    private String[] properties;
    private List<String[]> values = new ArrayList<String[]>();


    public void readTabFileFromPath(){
        File file = new File(this.path);
        try {
            String[] fullFileName = file.getName().split("\\.");
            this.fileName = fullFileName[0];
            this.fileType = fullFileName[1];
            this.lastModified = file.lastModified();
            this.size = file.length();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            this.properties =  br.readLine().split("\t");
            while ((line = br.readLine()) != null) { //循环读取行
                this.values.add(line.split("\t")); //按tab分割
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void showOutputInConsole(){
        System.out.println(this.path);
        System.out.println(this.fileName);
        System.out.println(this.fileType);
        System.out.println(this.lastModified);
        System.out.println(this.size);
        for(int j=0;j<properties.length;j++) {
            System.out.println(properties[j]);
        }
        System.out.println("共" + properties.length + "个属性");
        for(int i=0; i<values.size(); i++) {
            for (int j = 0; j < values.get(i).length; j++) {
                System.out.println(values.get(i)[j]);
            }
            System.out.println("--------------"+values.get(i).length+"-------------");
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
