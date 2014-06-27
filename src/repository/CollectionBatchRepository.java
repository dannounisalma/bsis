package repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.collectedsample.CollectedSample;
import model.collectionbatch.CollectionBatch;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CollectionBatchRepository {

  @PersistenceContext
  EntityManager em;

  public CollectionBatchRepository() {
  }

  public CollectionBatch findCollectionBatchByIdEager(Integer batchId) {
    String queryString = "SELECT b FROM CollectionBatch b LEFT JOIN FETCH b.collectionCenter LEFT JOIN FETCH b.collectionSite " +
                         "WHERE b.id = :batchId and b.isDeleted = :isDeleted";
    TypedQuery<CollectionBatch> query = em.createQuery(queryString, CollectionBatch.class);
    query.setParameter("isDeleted", Boolean.FALSE);
    CollectionBatch b = query.setParameter("batchId", batchId).getSingleResult();
    return b;
  }

  public CollectionBatch findCollectionBatchById(Integer batchId) {
    String queryString = "SELECT b FROM CollectionBatch b " +
                         "WHERE b.id = :batchId and b.isDeleted = :isDeleted";
    TypedQuery<CollectionBatch> query = em.createQuery(queryString, CollectionBatch.class);
    query.setParameter("isDeleted", Boolean.FALSE);
    return query.setParameter("batchId", batchId).getSingleResult();
  }

  public CollectionBatch findCollectionBatchByBatchNumber(String batchNumber) {
    String queryString = "SELECT b FROM CollectionBatch b " +
        "WHERE b.batchNumber = :batchNumber and b.isDeleted = :isDeleted";
    TypedQuery<CollectionBatch> query = em.createQuery(queryString, CollectionBatch.class);
    query.setParameter("isDeleted", Boolean.FALSE);
    return query.setParameter("batchNumber", batchNumber).getSingleResult();
  }

  public CollectionBatch findCollectionBatchByBatchNumberIncludeDeleted(String batchNumber) {
    String queryString = "SELECT b FROM CollectionBatch b " +
        "WHERE b.batchNumber = :batchNumber";
    TypedQuery<CollectionBatch> query = em.createQuery(queryString, CollectionBatch.class);
    CollectionBatch batch = null;
    try {
      batch = query.setParameter("batchNumber", batchNumber).getSingleResult();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return batch;
  }

  public CollectionBatch addCollectionBatch(CollectionBatch collectionBatch) {
    em.persist(collectionBatch);
    em.flush();
    em.refresh(collectionBatch);
    return collectionBatch;
  }

  public List<CollectionBatch> findCollectionBatches(String collectionNumber,
      List<Long> centerIds, List<Long> siteIds) {
    String queryStr = "";
    if (StringUtils.isNotBlank(collectionNumber)) {
      queryStr = "SELECT b from CollectionBatch b , CollectedSample c " +
                   "WHERE c.collectionNumber=:collectionNumber AND c.collectionBatch = b.id AND "+
                   "b.collectionCenter.id IN (:centerIds) AND " +
                   "b.collectionSite.id IN (:siteIds) AND " +
                   "b.isDeleted=:isDeleted";
    }
    else {
      queryStr = "SELECT b from CollectionBatch b " +
                 "WHERE b.collectionCenter.id IN (:centerIds) AND " +
                 "b.collectionSite.id IN (:siteIds) AND " +
                 "b.isDeleted=:isDeleted";
    }
    
    TypedQuery<CollectionBatch> query = em.createQuery(queryStr, CollectionBatch.class);
    if (StringUtils.isNotBlank(collectionNumber))
      query.setParameter("collectionNumber", collectionNumber);
    query.setParameter("centerIds", centerIds);
    query.setParameter("siteIds", siteIds);
    query.setParameter("isDeleted", false);
    return query.getResultList();
  }

  public Set<String> findCollectionsInBatch(Integer batchId) {
    CollectionBatch collectionBatch = findCollectionBatchByIdEager(batchId);
    Set<String> collectionNumbers = new HashSet<String>();
    for (CollectedSample c : collectionBatch.getCollectionsInBatch()) {
      collectionNumbers.add(c.getCollectionNumber());
    }
    return collectionNumbers;
  }
}
