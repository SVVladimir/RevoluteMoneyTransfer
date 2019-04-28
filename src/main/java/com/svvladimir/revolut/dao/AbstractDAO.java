package com.svvladimir.revolut.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static javax.persistence.Persistence.createEntityManagerFactory;

public abstract class AbstractDAO<T> {

    private final static String PERSISTENCE_UNIT = "com.svvladimir.revolut.data";

    private static EntityManagerFactory entityManagerFactory = createEntityManagerFactory(PERSISTENCE_UNIT);
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    protected T add(T value) {
        entityManager.getTransaction().begin();
        entityManager.persist(value);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return value;
    }

    protected T get(Class<T> tClass, long id) {
        return entityManager.find(tClass, id);
    }

    protected List<T> getAll(Class<T> tClass) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(tClass);
        Root<T> rootEntry = cq.from(tClass);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    protected T update(T value) {
        entityManager.getTransaction().begin();
        entityManager.merge(value);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return value;
    }

    protected void delete(T value) {
        entityManager.getTransaction().begin();
        entityManager.remove(value);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
}
