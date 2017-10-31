/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;



public class DataTable implements Serializable {

    public DataTable() {
    }
    private static final long serialVersionUID = 1L;
    private Long id;

    private String unf;

    private Long caseQuantity;

    private Long varQuantity;

    private Long recordsPerCase;

    private DataFile dataFile;

//    private List<DataVariable> dataVariables;

    /*
     * originalFileType: the format of the file from which this data table was
     * extracted (STATA, SPSS, R, etc.)
     * Note: this was previously stored in the StudyFile.
     */
    private String originalFileFormat;

    /*
     * originalFormatVersion: the version/release number of the original file
     * format; for example, STATA 9, SPSS 12, etc.
     */
    private String originalFormatVersion;

    /*
     * Getter and Setter methods:
     */
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnf() {
        return this.unf;
    }

    public void setUnf(String unf) {
        this.unf = unf;
    }

    public Long getCaseQuantity() {
        return this.caseQuantity;
    }

    public void setCaseQuantity(Long caseQuantity) {
        this.caseQuantity = caseQuantity;
    }

    public Long getVarQuantity() {
        return this.varQuantity;
    }

    public void setVarQuantity(Long varQuantity) {
        this.varQuantity = varQuantity;
    }

    public Long getRecordsPerCase() {
        return recordsPerCase;
    }

    public void setRecordsPerCase(Long recordsPerCase) {
        this.recordsPerCase = recordsPerCase;
    }

    public DataFile getDataFile() {
        return this.dataFile;
    }

    public void setDataFile(DataFile dataFile) {
        this.dataFile = dataFile;
    }


//    public List<DataVariable> getDataVariables() {
//        return this.dataVariables;
////    }
//
//
//    public void setDataVariables(List<DataVariable> dataVariables) {
//        this.dataVariables = dataVariables;
//    }

    public String getOriginalFileFormat() {
        return originalFileFormat;
    }

    public void setOriginalFileFormat(String originalFileType) {
        this.originalFileFormat = originalFileType;
    }


    public String getOriginalFormatVersion() {
        return originalFormatVersion;
    }

    public void setOriginalFormatVersion(String originalFormatVersion) {
        this.originalFormatVersion = originalFormatVersion;
    }

    /*
     * Custom overrides for hashCode(), equals() and toString() methods:
     */

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

//    @Override
//    public boolean equals(Object object) {
//        if (!(object instanceof DataTable)) {
//            return false;
//        }
//        DataTable other = (DataTable)object;
//        return !(!Objects.equals(this.id, other.id) && (this.id == null || !this.id.equals(other.id)));
//    }

    @Override
    public String toString() {
        return "edu.harvard.iq.dataverse.DataTable[ id=" + id + " ]";
    }

}
