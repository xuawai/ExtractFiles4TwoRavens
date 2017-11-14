import service.CreateDataFileService;
import java.io.*;
import java.util.*;
import Rserve.RemoteDataFrameService;

/**
 * Created by xuawai on 09/10/2017.
 */
public class HelloWorld {

    public static void main(String[] args) throws IOException {
//        TabFileReader tabFileReader = new TabFileReader();
//        //输出有为null的部分
//        tabFileReader.setPath("/Users/xuawai/Public/Project/mengtai/project/ExtractFiles4TwoRavens/src/main/resources/car_sales.tab");
//        tabFileReader.readTabFileFromPath();
//        tabFileReader.showOutputInConsole();
//
//        FileUtil fileUtil = new FileUtil();
//        fileUtil.createDataFile(tabFileReader.getFileName(), tabFileReader.getFileType(), tabFileReader.getSize());
        RemoteDataFrameService prep = new RemoteDataFrameService();
        File prepFile = prep.runDataPreprocessing("/Users/liyuan/Documents/软件工程/ExtractFiles4TwoRavens/src/main/resources/car_sales.tab");

        System.out.println("Success!");
    }
}
