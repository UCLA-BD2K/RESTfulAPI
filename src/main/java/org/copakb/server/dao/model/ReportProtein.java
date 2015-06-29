package org.copakb.server.dao.model;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 5/28/2015.
 */


public class ReportProtein {
    private String iPI;
    private String cOPaID;
    private String proteinName;
    private String geneSymbol;
    private String organism;
    private int spectraCount;
    private double normalizCount;
    private int probability;
    private int length;
    private ArrayList<ScanPeptide> scanPeptides;

    public ReportProtein(String iPI, String cOPaID, String proteinName,
                         String geneSymbol, String organism, int spectraCount,
                         double normalizCount, int probability, int length,
                         ArrayList<ScanPeptide> scanPeptides) {
        this.iPI = iPI;
        this.cOPaID = cOPaID;
        this.proteinName = proteinName;
        this.geneSymbol = geneSymbol;
        this.organism = organism;
        this.spectraCount = spectraCount;
        this.normalizCount = normalizCount;
        this.probability = probability;
        this.length = length;
        this.scanPeptides = scanPeptides;
    }

    public ReportProtein(String iPI, String cOPaID, String proteinName,
                         String geneSymbol, String organism, int spectraCount,
                         double normalizCount, int probability, int length) {
        this.iPI = iPI;
        this.cOPaID = cOPaID;
        this.proteinName = proteinName;
        this.geneSymbol = geneSymbol;
        this.organism = organism;
        this.spectraCount = spectraCount;
        this.normalizCount = normalizCount;
        this.probability = probability;
        this.length = length;
    }

    public ReportProtein() {
        //default constructor
    }

    public String getiPI() {
        return iPI;
    }

    public void setiPI(String iPI) {
        this.iPI = iPI;
    }

    public String getcOPaID() {
        return cOPaID;
    }

    public void setcOPaID(String cOPaID) {
        this.cOPaID = cOPaID;
    }

    public String getProteinName() {
        return proteinName;
    }

    public void setProteinName(String proteinName) {
        this.proteinName = proteinName;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public int getSpectraCount() {
        return spectraCount;
    }

    public void setSpectraCount(int spectraCount) {
        this.spectraCount = spectraCount;
    }

    public double getNormalizCount() {
        return normalizCount;
    }

    public void setNormalizCount(double normalizCount) {
        this.normalizCount = normalizCount;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }


    @Field("scanPeptides")
    public ArrayList<ScanPeptide> getScanPeptides() {
        return scanPeptides;
    }

    public void setScanPeptides(ArrayList<ScanPeptide> scanPeptides) {
        this.scanPeptides = scanPeptides;
    }

    @Override
    public String toString() {
        String result = "iPI: " + iPI + "\n" +
                        "cOPaID: " + cOPaID + "\n" +
                        "proteinName: " + proteinName + "\n" +
                        "geneSymbol: " + geneSymbol + "\n" +
                        "organism: " + organism + "\n" +
                        "spectraCount: " + spectraCount + "\n" +
                        "normalizCount: " + normalizCount + "\n" +
                        "probability: " + probability + "\n" +
                        "length: " + length + "\n";

        return result;
    }
}