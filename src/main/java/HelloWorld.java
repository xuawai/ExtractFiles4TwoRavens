import io.TabFileReader;
import service.CreateDataFileService;
import util.FileUtil;

/**
 * Created by xuawai on 09/10/2017.
 */
public class HelloWorld {

    public static void main(String[] args) {
        TabFileReader tabFileReader = new TabFileReader();
        //输出有为null的部分
        tabFileReader.setPath("/Users/xuawai/Public/Project/mengtai/project/ExtractFiles4TwoRavens/src/main/resources/car_sales.tab");
        tabFileReader.readTabFileFromPath();
        tabFileReader.showOutputInConsole();

        FileUtil fileUtil = new FileUtil();
        fileUtil.createDataFile(tabFileReader.getFileName(), tabFileReader.getFileType(), tabFileReader.getSize());
    }
}
