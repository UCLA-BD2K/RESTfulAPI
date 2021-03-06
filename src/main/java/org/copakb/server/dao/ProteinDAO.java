package org.copakb.server.dao;

import org.copakb.server.dao.model.*;

import java.util.List;
import java.util.Set;

/**
 * ProteinDAO interface.
 * Created by vincekyi on 5/2/15.
 */
public interface ProteinDAO {
    /**
     * Adds a ProteinCurrent object to the database.
     *
     * @param prot Protein object to be added
     * @return Uniprot ID of p if successful, empty string otherwise.
     */
    String addProteinCurrent(ProteinCurrent prot);

    void updateProteinCurrent(String protein_acc, ProteinCurrent p);

    boolean deleteProteinCurrent(String protein_acc);

    /**
     * @param a
     * @param b
     * @return true if equal, false otherwise
     */
    boolean compareProteinCurrent(ProteinCurrent a, ProteinCurrent b);

    String addProteinHistory(ProteinHistory p);

    ProteinHistory searchProteinHistory(String uniprot_id);

    String addSpectrumProteinHistory(SpectrumProteinHistory p);

    boolean deleteSpectrumProtein(int id);

    SpectrumProteinHistory searchSpectrumProteinHistory(String protein_acc, int spec_id);

    int addVersion(Version version);

    Version searchVersion(int version);

    Version searchLatestVersion();

    /**
     * Searches for a protein with the given Uniprot ID.
     *
     * @param uniprotID Uniprot ID of the protein as given by www.uniprot.org
     * @return ProteinCurrent object that contains the given Uniprot ID, null if not found.
     */
    ProteinCurrent searchByID(String uniprotID);

    /**
     * Searches for all proteins within the given list of Uniprot IDs.
     *
     * @param uniprotIDs A list of Uniprot IDs to be searched
     * @return A list of ProteinCurrent objects that are found with the given Uniprot IDs, empty list if none found.
     */
    List<ProteinCurrent> searchByIDs(List<String> uniprotIDs);

    /**
     * Searches for a protein with the given UniProt ID and returns it fully initialized.
     *
     * @param uniprotID UniProt ID of the protein.
     * @return ProteinCurrent object, fully initialized. Null if not found.
     */
    ProteinCurrent getInitializedProtein(String uniprotID);

    /**
     * Searches all proteins that start with the given UniProt ID prefix.
     *
     * @param idPrefix UniProt ID prefix.
     * @return List of proteins objects that start with the given UniProt ID prefix.
     */
    List<ProteinCurrent> searchByLikeID(String idPrefix);

    /**
     * Searches all proteins containing the partial ID.
     *
     * @param idFragment UniProt ID fragment.
     * @return List of proteins containing the partial ID.
     */
    List<ProteinCurrent> searchByPartialID(String idFragment);

    /**
     * Searches for a protein with the given name.
     *
     * @param proteinName Protein name.
     * @return First ProteinCurrent object found that contains the given protein name, null if not found.
     */
    ProteinCurrent searchByName(String proteinName);

    /**
     * Searches all proteins that start with the given name prefix.
     *
     * @param namePrefix Name prefix.
     * @return List of all proteins starting with the given name prefix.
     */
    List<ProteinCurrent> searchByLikeName(String namePrefix);

    /**
     * Search for proteins from a given species.
     *
     * @param species_id Database species ID to search.
     * @return A list of proteins from the given species.
     */
    List<ProteinCurrent> searchBySpecies(int species_id);

    /**
     * Search for proteins from a given species.
     *
     * @param speciesName Species name to search.
     * @return A list of proteins from the given species.
     */
    List<ProteinCurrent> searchBySpeciesName(String speciesName);

    /**
     * Searches for proteins containing the partial sequence.
     *
     * @param sequence Partial sequence.
     * @return List of proteins containing the partial sequence.
     */
    List<ProteinCurrent> searchByPartialSequence(String sequence);

    /**
     * Searches database for all objects in the database that matches the ProteinCurrent specifications
     *
     * @return all the ProteinCurrent objects contained in the database
     */
    List<ProteinCurrent> list();

    /**
     * Searches a limited list of ProteinCurrent objects from the database.
     *
     * @param start  beginning index for list.
     * @param length number of Protein Currents to be returned.
     * @return partial list of specified length of ProteinCurrent objects beginning at the start index.
     */
    List<ProteinCurrent> limitedList(int start, int length);

    /**
     * Returns the first DBRef which references the given PDB ID.
     *
     * @param uniprotID The PDB ID to search for.
     * @return A protein that references the given PDB IDl.
     */
    DBRef searchDbRefByID(String uniprotID);

    /**
     * Returns the first protein which references the given PDB ID.
     *
     * @param pdbID The PDB ID to search for.
     * @return A protein that references the given PDB IDl.
     */
    ProteinCurrent searchByPDB(String pdbID);

    /**
     * Searches for a gene with the given Ensembl ID.
     *
     * @param ensemblID Ensembl ID to search.
     * @return Gene object; null if not found.
     */
    Gene searchGene(String ensemblID);

    /**
     * Searches for a gene with the given Ensembl ID, and initializes
     * lazy loaded fields.
     *
     * @param ensemblId
     * @return
     */
    Gene searchGeneInitialized(String ensemblId);

    /**
     * Searches for a gene with the given Ensembl ID, and initializes
     * the set of associated proteins.
     * @param ensemblId
     * @return
     */
    Gene searchGeneInitializedWithProteins(String ensemblId);

    /**
     * Searches for a gene with the given Ensembl ID, and initializes
     * the set of associated diseases.
     * @param ensemblId
     * @return
     */
    Gene searchGeneInitializedWithDiseases(String ensemblId);

    /**
     * Searches for proteins with genes having the given gene symbol.
     *
     * @param geneSymbol Gene symbol to search.
     * @return List of proteins with the given gene.
     */
    List<ProteinCurrent> searchByGeneSymbol(String geneSymbol);

    /**
     * Searches for proteins with genes having the given gene ID.
     *
     * @param ensemblID Gene ID to search.
     * @return List of proteins with the given gene.
     */
    List<ProteinCurrent> searchByGeneID(String ensemblID);

    /**
     * Searches for a protein with a gene with the given ensembl ID.
     *
     * @param ensemblID Ensembl id of the protein as given by the www.ensembl.org
     * @return ProteinCurrent object that contains the given Ensembl ID
     */
    @Deprecated
    ProteinCurrent searchByEnsg(String ensemblID);

    /**
     * Searches for proteins in the database with the given uniprot ID and
     * matches it with all the relevant gene information
     *
     * @param uniprotID Uniprot ID of the protein as given by www.uniprot.org
     * @return Protein object with defined gene information
     */
    ProteinCurrent getProteinWithGenes(String uniprotID);

    /**
     * Add GoTerms to all relevant proteins.
     *
     * @param goTerms Set of GoTerms to add.
     * @return GoTerm accession number
     */
    int addGoTerms(GoTerms goTerms);

    /**
     * Searches for proteins in the database with the given GO accession ID
     *
     * @param GO_accession GO accession of the protein as automatically defined in the database entries
     * @return GOTerms object which includes all of the relevant proteins and terms
     */
    GoTerms searchByGOAccession(int GO_accession);

    /**
     * Searches for proteins in the database with the given uniprot ID and
     * matches it with all the relevant GO terms
     *
     * @param uniprotID Uniprot ID of the protein as given by www.uniprot.org
     * @return Project object with defined GO terms
     */
    ProteinCurrent getProteinWithGoTerms(String uniprotID);

    /**
     * Add a species to the database
     *
     * @param sp defined species object with name, id, and list of relevant proteins
     * @return species id if successful, -1 otherwise
     */
    int addSpecies(Species sp);

    /**
     * Searches for a species object by checking species names
     *
     * @param name name of species
     * @return species object that matches the given name
     */
    Species searchSpecies(String name);

    /**
     * Add SpectrumProtein object
     *
     * @param p defined SpectrumProtein object to be added
     * @return Auto-generated ID if successful, -1 otherwise
     */
    int addSpectrumProtein(SpectrumProtein p);

    /**
     * Search for protein and spectrum information
     *
     * @param uniprotID Uniprot ID of the protein as given by www.uniprot.org
     * @return ProteinCurrent object with the set of spectra defined
     */
    ProteinCurrent getProteinWithSpectra(String uniprotID);

    /**
     * Searches for SpectrumProtein object from the database
     *
     * @param spectrum spectrum
     * @param protein  protein
     * @return defined SpectrumProtein object
     */
    SpectrumProtein searchSpectrumProtein(Spectrum spectrum, ProteinCurrent protein);

    List<SpectrumProtein> searchSpectrumProteins(ProteinCurrent proteinCurrent);

    List<SpectrumProtein> searchSpectrumProteins(Spectrum spectrum);

    List<ProteinCurrent> searchProteinsByPeptide(Peptide peptide);

    String addHPAProtein(HPAProtein protein);

    HPAProtein searchHPAByID(String ensemblID);

    /**
     * Gets a fully initialized HPAProtein.
     *
     * @param ensemblID EnsemblID to search.
     * @return Fully initialized HPAProtein; null if not found.
     */
    HPAProtein getInitializedHPAProtein(String ensemblID);

    String addAntibody(Antibody antibody);

    Antibody searchAntibodyByID(String antibodyID);

    /**
     * Search for protein and PTM information
     *
     * @param uniprotID Uniprot ID of the protein as given by www.uniprot.org
     * @return ProteinCurrent object with the PTM defined
     */
    ProteinCurrent getProteinWithPTMs(String uniprotID);

    /**
     * Returns a list of proteins satisfying the search term.
     * Searches first for partial protein IDs, then species, then partial protein names, then partial gene symbols,
     * and finally partial gene names.
     *
     * @param searchTerm String to search.
     * @return List of proteins satisfying the search term.
     */
    List<ProteinCurrent> smartSearch(String searchTerm);

    /**
     * Returns the number of unique genes for a given species
     * @param species_id the species to
     * @return count of unique genes
     */
    int getNumUniqueGenesForSpecies(int species_id);

    /**
     * Retrieves a set of minimal (incomplete) ProteinCurrents for a specified disease_id, optionally
     * filtered by species id. "Minimal" in the sense that not all fields may be initialized. As of now,
     * this returns ProteinCurrent instances with at least the protein_acc member set.
     * @param disease_id the disease id
     * @param species_id optional species id, -1 if no filtering desired.
     * @return
     */
    Set<ProteinCurrent> getProteinsForDisease(String disease_id, int species_id);

    /**
     * Returns a list of all species currently recorded in the database.
     * @return list of Species objects
     */
    List<Species> getAllSpecies();
}
