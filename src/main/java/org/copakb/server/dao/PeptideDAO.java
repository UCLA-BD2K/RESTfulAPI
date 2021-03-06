package org.copakb.server.dao;

import org.copakb.server.dao.model.*;

import java.util.List;

/**
 * PeptideDAO interface
 * Created by vincekyi on 4/16/15.
 */
public interface PeptideDAO {

    /**
     * Adds a peptide into the MySQL database
     *
     * @param p model of the Peptide that needs to be added
     * @return On success: the ID of the peptide that is added
     * On failure: returns -1
     */
    int addPeptide(Peptide p);

    /**
     * Searches the MySQL database for all Peptides
     *
     * @return list of all Peptides in the database
     */
    List<Peptide> list();

    /**
     * Searches the MySQL database for a limited number of Peptides
     *
     * @param start  the starting index of the desired list
     * @param length the length of the desired list
     * @return List of Peptides with desired starting index and length
     */
    List<Peptide> limitedList(int start, int length);

    int getLocation(Peptide peptide, ProteinCurrent protein);

    /**
     * Searches the database for a specific Peptide
     *
     * @param peptide_id the ID of the desired Peptide
     * @return the specific Peptide with the provided ID
     */
    Peptide searchById(int peptide_id);

    /**
     * Gets a fully initialized peptide.
     *
     * @param peptide_id Peptide ID to search.
     * @return A fully initialized peptide; null if not found.
     */
    Peptide getInitializedPeptide(int peptide_id);

    /**
     * Searches the database for a specific Peptide
     *
     * @param peptide_sequence the peptide sequence of the desired Peptide
     * @return the specific Peptide with the provided peptide sequence
     */
    Peptide searchBySequence(String peptide_sequence);

    /**
     * Searches for all peptides related to a protein.
     *
     * @param protein ProteinCurrent object to search.
     * @return List of all related peptides.
     */
    List<Peptide> searchPeptidesByProtein(ProteinCurrent protein);

    /**
     * Searches the database for a specific Spectrum
     *
     * @param id the ID of the desired Spectrum
     * @return the Spectrum with the provided ID
     */
    Spectrum searchBySpecId(Integer id);

    /**
     * Returns a list of peptides containing the partial sequence.
     *
     * @param sequence Partial sequence to search.
     * @return List of peptides containing the partial sequence.
     */
    List<Peptide> searchByPartialSequence(String sequence);

    /**
     * Adds a spectrum into the MySQL database
     *
     * @param s model of the Spectrum that needs to be added
     * @return On success: returns the ID of the spectrum that is added
     * On failure: returns -1
     */
    int addSpectrum(Spectrum s);

    // TODO Return string of spectrum file (stored on harddrive)
    String getSpectrum(int spec_id);

    /**
     * Gets a fully initialized Spectrum.
     *
     * @param spec_id SpectrumID to search.
     * @return Fully initialized Spectrum; null if not found.
     */
    Spectrum getInitializedSpectrum(int spec_id);

    void updateSpectrumSpecies(int spec_id, Spectrum spectrum);

    void updateSpectrumFeature(int spec_id, Spectrum spectrum);

    /**
     * Searches the MySQL database for a specific Spectrum
     *
     * @param ptm_seq ptm_seq of the Spectrum
     * @param mod_id  mod_id of the Spectrum
     * @param charge  charge of the Spectrum
     * @return The spectrum with the specified ptm_seq, mod_id, and charge
     */
    Spectrum searchSpectrum(String ptm_seq, int mod_id, int charge);

    /**
     * Searches the MySQL database for all specta with the given sequence.
     *
     * @param ptm_seq Sequence to search for.
     * @return A list of matching spectra.
     */
    List<Spectrum> searchSpectrumBySequence(String ptm_seq);

    /**
     * Searches the MySQL database for all specta with the given sequence and charge.
     *
     * @param ptm_seq Sequence to search for.
     * @param charge  Charge to search for.
     * @return A list of matching spectra.
     */
    List<Spectrum> searchSpectrumBySequenceAndCharge(String ptm_seq, int charge);

    /**
     *
     * @param ptm_seq
     * @param mod_id
     * @param charge
     * @param xcorr
     * @param rawfileid
     * @return
     */
    Spectrum searchSpectrumByAll(String ptm_seq, int mod_id, int charge, double xcorr, String rawfileid);

    /**
     * Add a species to the database
     *
     * @param spec defined species object with name, id, and list of relevant proteins
     * @return species id if successful, -1 otherwise
     */
    int addSpecies(Species spec);

    /* Species add & search */

    /**
     * Searches for a species object by checking species names
     *
     * @param name name of species
     * @return species object that matches the given name
     */
    Species searchSpecies(String name);

    Species searchSpecies(int id);

    /**
     * Adds a library module to the database
     *
     * @param libmod defined LibraryModule object to be added
     * @return autogenerated library module id if successful, -1 otherwise
     */
    int addLibraryModule(LibraryModule libmod);

    /**
     * Searches for a library module in the database
     *
     * @param id ID of library module as autogenerated by the database
     * @return defined LibraryModule object
     */
    LibraryModule searchLibraryModuleWithId(int id);

    /**
     * Searches for library module in the database
     *
     * @param lib_mod name of the module; formatted all lower case with underscores between words (ex. human_heart_proteasome)
     * @return defined LibraryModule object
     */
    LibraryModule searchLibraryModuleWithModule(String lib_mod);

    /**
     * Retrieves all library modules
     * @return A list of all library modules
     */
    List<LibraryModule> getLibraryModules();

    /**
     * Add PTM information to all relevant protein objects
     *
     * @param type defined PTM object to be added
     * @return PTM type if successful, empty string otherwise
     */
    int addPtmType(PTM_type type);

    /**
     * Returns PTM object
     *
     * @param id PTM type as defined in the database. Ranges from 1-255 using the following code:
     *           1	Carbamidomethylation	C,K,H	57.02000
     *           2	Acetylation	K,N-term	42.01000
     *           4	Oxidation	M	15.99000
     *           8	Phosphorylation	S,T	79.97000
     *           16	Succinylation	K	100.01860
     *           32	Propionamide	C	71.03712
     *           64	Pyro-carbamidomethyl	C	39.99492
     *           128	Pyro-glu	E	-17.03000
     * @return PTM object
     */
    PTM_type searchPtmType(int id);
}
