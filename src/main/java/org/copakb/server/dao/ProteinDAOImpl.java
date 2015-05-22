package org.copakb.server.dao;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import org.copakb.server.dao.model.*;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by vincekyi on 5/2/15.
 */
public class ProteinDAOImpl implements ProteinDAO {

    private SessionFactory sessionFactory;

    /**
     * Default sets the session factory
     * @param sessionFactory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Searches database for all objects in the database that matches the ProteinCurrent specifications
     * @return all the ProteinCurrent objects contained in the database
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ProteinCurrent> list() throws HibernateException {
        Session session = this.sessionFactory.openSession();
        List<ProteinCurrent> proteinList = session.createCriteria(ProteinCurrent.class).list();
        session.close();
        return proteinList;
    }

    /**
     * Searches a limited list of ProteinCurrent objects from the database
     * @param start beginning index for list
     * @param length number of Protein Currents to be returned
     * @return partial list of specified length of ProteinCurrent objects beginning at the start index
     */
    public List<ProteinCurrent> limitedList(int start, int length) {
        Session session = this.sessionFactory.openSession();
        List<ProteinCurrent> proteinList = session.createCriteria(ProteinCurrent.class).setFirstResult(start).setMaxResults(length).list();
        session.close();
        return proteinList;
    }

    /**
     * Adds a ProteinCurrent object to the database
     * @param p object to be added
     * @return Uniprot ID of p if successful, empty string otherwise
     * @throws HibernateException
     */
    public String addProteinCurrent(ProteinCurrent p) throws HibernateException{
        String uniprot = "";
        try {
            Session session = this.sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            uniprot = (String) session.save(p);
            tx.commit();
            session.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return uniprot;
    }

    /**
     * Add a species to the database and to the each of the protein objects listed under the Species object
     * @param sp defined species object with name, id, and list of relevant proteins
     * @return species id if successful, -1 otherwise
     * @throws HibernateException
     */
    public int addSpecies(Species sp) throws HibernateException {
        int result = -1;
        // look for relevant species
        Species existingSpecies = searchSpecies(sp.getSpecies_name()); // add param
        if (existingSpecies != null)
            return existingSpecies.getSpecies_id();

        if (sp.getProteinCurrents() == null) {
            return -1;
        }
        // if no existing species, then add in according to species
        Iterator iterator = sp.getProteinCurrents().iterator(); //iterate through the Species' Set of assoc. ProteinCurrents

        // check values
        while (iterator.hasNext()) {
            ProteinCurrent temp = (ProteinCurrent) iterator.next(); // current ProteinCurrent object being iterated over
            ProteinCurrent prot = this.searchByID(temp.getProtein_acc());
            if (prot == null) {
                this.addProteinCurrent(temp);
                //add protein to the associated species list
                //temp.setProtein_acc(temp.getProtein_acc()); // how to set protein accession? uniprot?
                Set<ProteinCurrent> tempSet = sp.getProteinCurrents();
                tempSet.add(temp);
            } else {
                //temp.setProtein_acc(protein_id);
            }
        }
        return result;
    }

    /**
     * Searches for a species object by checking species names
     * @param name name of species
     * @return species object that matches the given name
     */
    public Species searchSpecies(String name) {
        Session session = this.sessionFactory.openSession();

        Criteria criteria = session.createCriteria(Species.class);

        Transaction tx = session.beginTransaction();
        try {
            Criterion nameRestriction = Restrictions.eq("species_name", name);

            criteria.add(Restrictions.and(nameRestriction));
            List<Species> results = criteria.list();
            tx.commit();
            if(results.isEmpty())
                return null;
            return results.get(0);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return null;
        }finally{
            session.close();
        }
    }

    /**
     * Searches for an object in the database with the given Uniprot ID
     * @param protein_acc Uniprot ID of the protein as given by www.uniprot.org
     * @return ProteinCurrent object that contains the given Uniprot ID
     */
    public ProteinCurrent searchByID(String protein_acc){
        Session session = this.sessionFactory.openSession();
        ProteinCurrent protein = null;

        Transaction tx = session.beginTransaction();
        try {
            protein = (ProteinCurrent)session.get(ProteinCurrent.class, protein_acc);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return null;
        }finally{
            session.close();
        }
        return protein;
    }

    /**
     * Searches for an object in the database with the given protein name
     * @param protein_name name of the protein
     * @return ProteinCurrent object that contains the given protein name
     */
    public ProteinCurrent searchByName(String protein_name){
        Session session = this.sessionFactory.openSession();
        ProteinCurrent protein = null;

        Criteria criteria = session.createCriteria(ProteinCurrent.class);

        Transaction tx = session.beginTransaction();
        try {
            Criterion c = Restrictions.eq("protein_name", protein_name);
            criteria.add(c);
            List<ProteinCurrent> results = criteria.list();
            tx.commit();
            if(results.isEmpty())
                return null;
            return results.get(0);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return null;
        }finally{
            session.close();
        }
    }

    /**
     *
     * @param ref_kb_id
     * @return
     */
    public ProteinCurrent searchByRef(String ref_kb_id){
        Session session = this.sessionFactory.openSession();

        Criteria criteria = session.createCriteria(ProteinCurrent.class);

        Transaction tx = session.beginTransaction();
        try {
            Criterion c = Restrictions.eq("ref_kb_id", ref_kb_id);
            criteria.add(c);
            List<ProteinCurrent> results = criteria.list();
            tx.commit();
            if(results.isEmpty())
                return null;
            return results.get(0);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return null;
        }finally{
            session.close();
        }
    }

    /**
     * Searches for an object in the database with the given Ensembl ID
     * @param ENSG_ID Ensembl id of the protein as given by the www.ensembl.org
     * @return ProteinCurrent object that contains the given Ensembl ID
     */
    public ProteinCurrent searchByEnsg(String ENSG_ID){
        Session session = this.sessionFactory.openSession();

        Criteria criteria = session.createCriteria(ProteinCurrent.class);

        Transaction tx = session.beginTransaction();
        try {
            Criterion c = Restrictions.eq("ENSG_ID", ENSG_ID);
            criteria.add(c);
            List<ProteinCurrent> results = criteria.list();
            tx.commit();
            if(results.isEmpty())
                return null;
            return results.get(0);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return null;
        }finally{
            session.close();
        }
    }


    /**
     * Searches for proteins in the database with the given uniprot ID and
     * matches it with all the relevant GO terms
     * @param uniprotID Uniprot ID of the protein as given by www.uniprot.org
     * @return Project object with defined GO terms
     */
    public ProteinCurrent getProteinWithGoTerms(String uniprotID){
        Session session = this.sessionFactory.openSession();
        ProteinCurrent protein = null;
        Transaction tx = session.beginTransaction();

        try {
            protein = (ProteinCurrent)session.get(ProteinCurrent.class, uniprotID);
            Hibernate.initialize(protein.getGoTerms());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }

        return protein;
    }

    /**
     * Searches for proteins in the database with the given GO accession ID
     * @param GO_accession GO accession of the protein as automatically defined in the database entries
     * @return GOTerms object which includes all of the relevant proteins and terms
     */
    public GoTerms searchByGOAccession(int GO_accession) {
        Session session = this.sessionFactory.openSession();
        GoTerms go = null;

        Transaction tx = session.beginTransaction();
        try {
            go = (GoTerms)session.get(GoTerms.class, GO_accession);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return null;
        }finally{
            session.close();
        }
        return go;
    }
//
//    public String addGoTerm(GoTerms g) throws HibernateException {
//        String result = "";
//
//        PTM existingPTM = searchByPTMType(g.getGO_accession());
//        if(existingPTM != null)
//            return existingPTM.getPtm_type();
//
//        ProteinCurrent protein = this.searchByID(p.getProteinCurrent().getProtein_acc());
//        if(protein == null) {
//            String protein_id = this.addProteinCurrent(p.getProteinCurrent());
//            p.getProteinCurrent().setProtein_acc(protein_id);
//        } else{
//            p.setProteinCurrent(protein);
//        }
//
//        Session session = this.sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//        result = String.valueOf(session.save(p));
//
//        tx.commit();
//        session.close();
//
//        return result;
//    }


    /**
     * Searches for proteins in the database with the given uniprot ID and
     * matches it with all the relevant gene information
     * @param uniprotID Uniprot ID of the protein as given by www.uniprot.org
     * @return Protein object with defined gene information
     */
    public ProteinCurrent getProteinWithGenes(String uniprotID){
        Session session = this.sessionFactory.openSession();
        ProteinCurrent protein = null;
        Transaction tx = session.beginTransaction();

        try {
            protein = (ProteinCurrent)session.get(ProteinCurrent.class, uniprotID);
            Hibernate.initialize(protein.getGenes());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }

        return protein;
    }

    /**
     * Searches for an object in the database with the given gene name
     * @param name Gene name of the protein
     * @return ProteinGene object that contains the gene information, disease relevance, and relevant proteins
     */
    public ProteinGene searchByGeneName(String name) {
        Session session = this.sessionFactory.openSession();
        ProteinGene gene = null;

        Transaction tx = session.beginTransaction();
        try {
            gene = (ProteinGene)session.get(Gene.class, name);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return null;
        }finally{
            session.close();
        }
        return gene;
    }

    /**
     * Add gene information to all relevant protein objects
     * @param g defined ProteinGene to be added
     * @return gene name if successful, empty string otherwise
     * @throws HibernateException
     */
    public String addGene(ProteinGene g) throws HibernateException {
        String result = "";

        ProteinGene existingGene = searchByGeneName(g.getGene().getGene_name());
        if(existingGene != null)
            return existingGene.getGene().getGene_name();

        ProteinCurrent protein = this.searchByID(g.getProteinCurrent().getProtein_acc());
        if(protein == null) {
            String protein_id = this.addProteinCurrent(g.getProteinCurrent());
            g.getProteinCurrent().setProtein_acc(protein_id);
        } else{
            g.setProteinCurrent(protein);
        }

        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        result = String.valueOf(session.save(g));

        tx.commit();
        session.close();

        return result;
    }

    /**
     * Search for protein and PTM information
     * @param uniprotID Uniprot ID of the protein as given by www.uniprot.org
     * @return ProteinCurrent object with the PTM defined
     */
    public ProteinCurrent getProteinWithPTMs(String uniprotID) {
        Session session = this.sessionFactory.openSession();
        ProteinCurrent protein = null;
        Transaction tx = session.beginTransaction();

        try {
            protein = (ProteinCurrent)session.get(ProteinCurrent.class, uniprotID);
            Hibernate.initialize(protein.getPTMs());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }

        return protein;
    }

    // TODO: Should return a list of PTMs because there can be multiple (b.c of indexing etc)
    /**
     * Returns PTM object
     * @param ptm_type ptm type
     * @return PTM object
     */
    public PTM searchByPTMType(String ptm_type) {
        Session session = this.sessionFactory.openSession();
        PTM ptm = null;

        Transaction tx = session.beginTransaction();
        try {
            ptm = (PTM)session.get(PTM.class, ptm_type);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return null;
        }finally{
            session.close();
        }
        return ptm;
    }

    /**
     * Add PTM information to all relevant protein objects
     * @param p defined PTM object to be added
     * @return PTM type if successful, empty string otherwise
     * @throws HibernateException
     */
    public String addPTM(PTM p) throws HibernateException {
        String result = "";

        PTM existingPTM = searchByPTMType(p.getPtm_type());
        if(existingPTM != null)
            return existingPTM.getPtm_type();

        ProteinCurrent protein = this.searchByID(p.getProteinCurrent().getProtein_acc());
        if(protein == null) {
            String protein_id = this.addProteinCurrent(p.getProteinCurrent());
            p.getProteinCurrent().setProtein_acc(protein_id);
        } else{
            p.setProteinCurrent(protein);
        }

        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        result = String.valueOf(session.save(p));

        tx.commit();
        session.close();

        return result;
    }

}
