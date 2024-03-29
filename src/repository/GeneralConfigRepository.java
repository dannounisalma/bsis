package repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.admin.GeneralConfig;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class GeneralConfigRepository {

    @PersistenceContext
    private EntityManager em;

    public List<GeneralConfig> getAll() {
        TypedQuery<GeneralConfig> query = em.createQuery("select gc from GeneralConfig gc", GeneralConfig.class);
        return query.getResultList();

    }

    public void updateAll(List<GeneralConfig> generalConfigs) {
        for (GeneralConfig config : generalConfigs) {
            update(config);
        }
    }

    public GeneralConfig update(GeneralConfig generalConfig) {
        generalConfig = getGeneralConfigById(generalConfig.getId());
    	generalConfig = em.merge(generalConfig);
        return generalConfig;
    }

    public void save(GeneralConfig generalConfig) {
        em.persist(generalConfig);
    }

    public GeneralConfig getGeneralConfigById(Integer id) {
        TypedQuery<GeneralConfig> query = em.createQuery("SELECT gc FROM GeneralConfig gc WHERE gc.id = :id ", GeneralConfig.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public GeneralConfig getGeneralConfigByName(String name) {
        TypedQuery<GeneralConfig> query = em.createQuery("SELECT gc FROM GeneralConfig gc WHERE gc.name = :name ", GeneralConfig.class);
        query.setParameter("name", name);
        GeneralConfig result = null;
        try {
             result = query.getSingleResult();
        } catch (NoResultException ex){}
        return  result;
    }

}