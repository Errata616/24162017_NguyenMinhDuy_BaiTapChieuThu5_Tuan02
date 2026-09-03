package duy.packages.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import duy.packages.configs.JPAConfig;
import duy.packages.entity.User;

public class UserDao {

    public void insert(User user) {
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(user);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw e;
        } finally { enma.close(); }
    }

    public void update(User user) {
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(user);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw e;
        } finally { enma.close(); }
    }

    public User findByUsernameOrEmail(String keyword) {
        EntityManager enma = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.username = :kw OR u.email = :kw";
            TypedQuery<User> query = enma.createQuery(jpql, User.class);
            query.setParameter("kw", keyword);
            return query.getSingleResult();
        } catch (Exception e) { return null; }
        finally { enma.close(); }
    }

    // Lay 1 User theo id - dung de reload du lieu moi nhat cho trang Profile
    public User findById(int id) {
        EntityManager enma = JPAConfig.getEntityManager();
        try {
            return enma.find(User.class, id);
        } finally {
            enma.close();
        }
    }
}
