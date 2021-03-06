package org.copakb.server.dao.model;

import org.copakb.server.dao.DAOObject;

import javax.persistence.*;
import java.util.Set;

/**
 * ProteinCurrent model
 * Created by Kevin on 4/30/2015.
 */
@Entity
@Table(name = "protein_current")
public class ProteinCurrent extends Model {
    private String protein_acc;
    private String sequence;
    private String protein_name;
    private double molecular_weight;
    private String transmembrane_domain;
    private String cytoplasmatic_domain;
    private String noncytoplasmatic_domain;
    private String signal_peptide;
    private DBRef dbRef;
    private String keywords;
    private String feature_table;
    private Species species;
    private Set<Gene> genes;
    private Set<GoTerms> goTerms;
    private Set<PTM> PTMs;
    private Set<SpectrumProtein> spectra;

    @Id
    @Column(name = "protein_acc")
    public String getProtein_acc() {
        return protein_acc;
    }

    public void setProtein_acc(String protein_acc) {
        this.protein_acc = protein_acc;
    }

    @Column(name = "sequence")
    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    @Column(name = "protein_name")
    public String getProtein_name() {
        return protein_name;
    }

    public void setProtein_name(String protein_name) {
        this.protein_name = protein_name;
    }

    @Column(name = "molecular_weight")
    public double getMolecular_weight() {
        return molecular_weight;
    }

    public void setMolecular_weight(double molecular_weight) {
        this.molecular_weight = molecular_weight;
    }

    @Column(name = "transmembrane_domain")
    public String getTransmembrane_domain() {
        return transmembrane_domain;
    }

    public void setTransmembrane_domain(String transmembrane_domain) {
        this.transmembrane_domain = transmembrane_domain;
    }

    @Column(name = "cytoplasmatic_domain")
    public String getCytoplasmatic_domain() {
        return cytoplasmatic_domain;
    }

    public void setCytoplasmatic_domain(String cytoplasmatic_domain) {
        this.cytoplasmatic_domain = cytoplasmatic_domain;
    }

    @Column(name = "noncytoplasmatic_domain")
    public String getNoncytoplasmatic_domain() {
        return noncytoplasmatic_domain;
    }

    public void setNoncytoplasmatic_domain(String noncytoplasmatic_domain) {
        this.noncytoplasmatic_domain = noncytoplasmatic_domain;
    }

    @Column(name = "signal_peptide")
    public String getSignal_peptide() {
        return signal_peptide;
    }

    public void setSignal_peptide(String signal_peptide) {
        this.signal_peptide = signal_peptide;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protein_acc", unique = true, insertable = false, updatable = false)
    public DBRef getDbRef() {
        return dbRef;
    }

    public void setDbRef(DBRef dbRef) {
        this.dbRef = dbRef;
    }

    @Column(name = "keywords")
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Column(name = "feature_table")
    public String getFeature_table() {
        return feature_table;
    }

    public void setFeature_table(String feature_table) {
        this.feature_table = feature_table;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "species_id", nullable = false, updatable = false)
    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "protein_gene", joinColumns = {
            @JoinColumn(name = "protein_acc", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "ensembl_id",
                    nullable = false, updatable = false)})
    public Set<Gene> getGenes() {
        return genes;
    }

    public void setGenes(Set<Gene> gene) {
        this.genes = gene;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "proteinCurrent")
    public Set<PTM> getPTMs() {
        return PTMs;
    }

    public void setPTMs(Set<PTM> PTMs) {
        this.PTMs = PTMs;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "go_protein", joinColumns = {
            @JoinColumn(name = "protein_acc", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "GO_accession",
                    nullable = false, updatable = false)})
    public Set<GoTerms> getGoTerms() {
        return goTerms;
    }

    public void setGoTerms(Set<GoTerms> goTerms) {
        this.goTerms = goTerms;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "protein")
    public Set<SpectrumProtein> getSpectra() {
        return spectra;
    }

    public void setSpectra(Set<SpectrumProtein> spectra) {
        this.spectra = spectra;
    }

    @Override
    public ProteinCurrent initialize() {
        ProteinCurrent initialized = DAOObject.getInstance().getProteinDAO().getInitializedProtein(protein_acc);
        if (initialized != null) {
            setDbRef(initialized.getDbRef());
            setSpecies(initialized.getSpecies());
            setGenes(initialized.getGenes());
            setGoTerms(initialized.getGoTerms());
            setPTMs(initialized.getPTMs());
            setSpectra(initialized.getSpectra());
        }

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProteinCurrent that = (ProteinCurrent) o;

        return protein_acc.equals(that.protein_acc);

    }

    @Override
    public int hashCode() {
        return protein_acc.hashCode();
    }

    @Override
    public String toString() {
        return "ProteinCurrent{" +
                "signal_peptide='" + signal_peptide + '\'' +
                ", protein_acc='" + protein_acc + '\'' +
                ", sequence='" + sequence + '\'' +
                ", molecular_weight=" + molecular_weight +
                ", transmembrane_domain='" + transmembrane_domain + '\'' +
                ", cytoplasmatic_domain='" + cytoplasmatic_domain + '\'' +
                ", noncytoplasmatic_domain='" + noncytoplasmatic_domain + '\'' +
                ", keywords='" + keywords + '\'' +
                ", feature_table='" + feature_table + '\'' +
                ", protein_name='" + protein_name + '\'' +
                '}';
    }
}
