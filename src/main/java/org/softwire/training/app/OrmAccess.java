package org.softwire.training.app;

import org.softwire.training.models.Cat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

class OrmAccess {

    private final EntityManagerFactory entityManagerFactory;

    OrmAccess() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(
                "org.softwire.training");
    }

    List<Cat> getAllCatsWithHql() {
        EntityManager em = entityManagerFactory.createEntityManager();

        List<Cat> cats = em.createQuery("FROM Cat", Cat.class).getResultList();

        em.close();

        return cats;
    }

    Cat getCatWithSql(int id) {
        EntityManager em = entityManagerFactory.createEntityManager();

        List<Cat> results = em.createNativeQuery("SELECT * FROM cats WHERE id=?1", Cat.class)
                .setParameter(1, id)
                .getResultList();
        em.close();

        return results.isEmpty() ? null : results.get(0);
    }

    Cat getCatWithoutSql(int id) {
        EntityManager em = entityManagerFactory.createEntityManager();

        Cat cat = em.find(Cat.class, id);

        em.close();

        return cat;
    }

    void updateCatWithSql(Cat cat) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.createNativeQuery("UPDATE cats SET name=?1, age=?2, owner_id=?3 WHERE id=?4")
                .setParameter(1, cat.getName())
                .setParameter(2, cat.getAge())
                .setParameter(3, cat.getOwner().getId())
                .setParameter(4, cat.getId())
                .executeUpdate();

        transaction.commit();
        em.close();
    }

    void updateCatWithoutSql(Cat cat) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.merge(cat);

        transaction.commit();
        em.close();
    }
}
