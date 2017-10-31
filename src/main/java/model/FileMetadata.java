package model;

import javafx.scene.chart.PieChart;

/**
 * Created by xuawai on 17/10/2017.
 */
public class FileMetadata {
    private String label = "";

    private DataFile dataFile;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public DataFile getDataFile() {
        return dataFile;
    }

    public void setDataFile(DataFile dataFile) {
        this.dataFile = dataFile;
    }
}
