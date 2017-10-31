package model;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public abstract class DvObject extends DataverseEntity implements java.io.Serializable {

    public static final String DATAVERSE_DTYPE_STRING = "Dataverse";
    public static final String DATASET_DTYPE_STRING = "Dataset";
    public static final String DATAFILE_DTYPE_STRING = "DataFile";
    public static final List<String> DTYPE_LIST = Arrays.asList(DATAVERSE_DTYPE_STRING, DATASET_DTYPE_STRING, DATAFILE_DTYPE_STRING);


}
