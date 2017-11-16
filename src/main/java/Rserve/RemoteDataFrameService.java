package Rserve;
import java.io.*;
import java.util.*;
import java.util.logging.*;

import org.rosuda.REngine.*;
import org.rosuda.REngine.Rserve.*;

public class RemoteDataFrameService {
    private static Logger dbgLog = Logger.getLogger(RemoteDataFrameService.class.getPackage().getName());
//    private static String TMP_TABDATA_FILE_EXT = ".tab";
//    private static String TMP_RDATA_FILE_EXT = ".RData";
    private static String PREPROCESS_FILE_PREFIX = "dataversePreprocess_";
//    public static String LOCAL_TEMP_DIR = System.getProperty("java.io.tmpdir");
    // 保存结果文件路径
    public static String LOCAL_TEMP_DIR = "/Users/liyuan/Documents/软件工程/ExtractFiles4TwoRavens/src/main/resources";
    // 配置Rserve文件
    private static String RSERVE_HOST = "127.0.0.1";
    private static String RSERVE_USER = null;
    private static String RSERVE_PWD = null;
    private static int    RSERVE_PORT = 6311;
    // R脚本路径
    private static String DATAVERSE_R_FUNCTIONS = "scripts/dataverse_r_functions.R";
    private static String DATAVERSE_R_PREPROCESSING = "scripts/preprocess.R";
    // 传入的Rserve的临时文件的路径
    public static String RSERVE_TMP_DIR="/Users/liyuan/Documents/Junk/Rserv";

    public String PID = null;
    public String tempFileNameIn = "test";
    public String tempFileNameOut = "result";


    // 如果工作文件夹路径不存在就创建
    public void setupWorkingDirectory(RConnection c) {

        try {
            // check the temp directory; try to create it if it doesn't exist:

            String checkWrkDir = "if (!file_test('-d', '" + RSERVE_TMP_DIR + "')) {dir.create('" + RSERVE_TMP_DIR + "', showWarnings = FALSE, recursive = TRUE);}";
            c.voidEval(checkWrkDir);

        } catch (RserveException rse) {
            rse.printStackTrace();
        }
    }

    public int getFileSize(RConnection c, String targetFilename){
        dbgLog.fine("targetFilename="+targetFilename);
        int fileSize = 0;
        try {
            String fileSizeLine = "round(file.info('"+targetFilename+"')$size)";
            fileSize = c.eval(fileSizeLine).asInteger();
        } catch (RserveException rse) {
            rse.printStackTrace();
        } catch (REXPMismatchException mme) {
            mme.printStackTrace();
        }
        return fileSize;
    }

    public File transferRemoteFile(RConnection c, String targetFilename,
                                   String tmpFilePrefix, String tmpFileExt, int fileSize) {

        // set up a local temp file:

        File tmprsltfl = null;
        String resultFile = tmpFilePrefix + PID + "." + tmpFileExt;

        RFileInputStream ris = null;
        OutputStream outbr = null;
        try {
            tmprsltfl = new File(LOCAL_TEMP_DIR, resultFile);
            outbr = new BufferedOutputStream(new FileOutputStream(tmprsltfl));
            // open the input stream
            ris = c.openFile(targetFilename);

            if (fileSize < 1024 * 1024 * 500) {
                int bfsize = fileSize;
                byte[] obuf = new byte[bfsize];
                ris.read(obuf);
                outbr.write(obuf, 0, bfsize);
            }
            ris.close();
            outbr.close();
            return tmprsltfl;
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
            dbgLog.fine("FileNotFound exception occurred");
            return tmprsltfl;
        } catch (IOException ie) {
            ie.printStackTrace();
            dbgLog.fine("IO exception occurred");
        } finally {
            if (ris != null) {
                try {
                    ris.close();
                } catch (IOException e) {

                }
            }

            if (outbr != null) {
                try {
                    outbr.close();
                } catch (IOException e) {

                }
            }

        }

        // delete remote file:

        try {
            String deleteLine = "file.remove('"+targetFilename+"')";
            c.eval(deleteLine);
        } catch (Exception ex) {
            // do nothing.
        }

        return tmprsltfl;
    }

    public File runDataPreprocessing(String path) throws IOException {
        File preprocessedDataFile = null;

        File originFile = new File(path);

        try {

            // Set up an Rserve connection
            // 修改RSERVE_HOST，和 RSERVE_PORT
            RConnection c = new RConnection(RSERVE_HOST, RSERVE_PORT);

            InputStream in = new FileInputStream(originFile);
            // 在Rserve端创建一个临时文件，存储原始文件
            RFileOutputStream os = c.createFile(tempFileNameIn);

            int bufsize;
            byte[] bffr = new byte[4 * 8192];

            while ((bufsize = in.read(bffr)) != -1) {
                os.write(bffr, 0, bufsize);
            }

            in.close();
            os.close();
            String loadlib = "library(rjson)";
            // 调用一个R包
            c.voidEval(loadlib);
            String rscript = readLocalResource(DATAVERSE_R_PREPROCESSING);
            // 读入R语言脚本
            c.voidEval(rscript);

            String runPreprocessing = "json<-preprocess(filename=\""+ tempFileNameIn +"\")";
            //执行脚本
            c.voidEval(runPreprocessing);

            // Save the output in a temp file:

            String saveResult = "write(json, file='"+ tempFileNameOut +"')";
            //保存结果
            c.voidEval(saveResult);

            // Finally, transfer the saved file back on the application side:

            int fileSize = getFileSize(c,tempFileNameOut);
            preprocessedDataFile = transferRemoteFile(c, tempFileNameOut, PREPROCESS_FILE_PREFIX, "prep", fileSize);

            String deleteLine = "file.remove('"+tempFileNameOut+"')";
            c.eval(deleteLine);

            c.close();

        } catch (RserveException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return preprocessedDataFile;

    }

    private static String readLocalResource(String path) {

        dbgLog.fine(String.format("Data Frame Service: readLocalResource: reading local path \"%s\"", path));

        // Get stream
        InputStream resourceStream = RemoteDataFrameService.class.getResourceAsStream(path);
        String resourceAsString = "";

        // Try opening a buffered reader stream
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(resourceStream, "UTF-8"));

            String line = null;
            while ((line = rd.readLine()) != null) {
                resourceAsString = resourceAsString.concat(line + "\n");
            }
            resourceStream.close();
        } catch (IOException ex) {
            dbgLog.warning(String.format("RDATAFileReader: (readLocalResource) resource stream from path \"%s\" was invalid", path));
        }

        // Return string
        return resourceAsString;
    }
}
