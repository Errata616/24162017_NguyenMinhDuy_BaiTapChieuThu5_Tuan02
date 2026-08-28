package duy.packages.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import duy.packages.configs.JPAConfig;
import duy.packages.entity.Product;

public class ProductDao {

    // Top 10 sản phẩm mới nhất (sắp xếp theo ID giảm dần)
    public List<Product> findTop10New() {
        EntityManager enma = JPAConfig.getEntityManager();
        String jpql = "SELECT p FROM Product p ORDER BY p.productId DESC";
        TypedQuery<Product> query = enma.createQuery(jpql, Product.class);
        query.setMaxResults(10);
        return query.getResultList();
    }

    // Phân trang sản phẩm (6 sản phẩm / trang)
    public List<Product> findAllPage(int page, int pageSize) {
        EntityManager enma = JPAConfig.getEntityManager();
        String jpql = "SELECT p FROM Product p";
        TypedQuery<Product> query = enma.createQuery(jpql, Product.class);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    // Đếm tổng số sản phẩm
    public int count() {
        EntityManager enma = JPAConfig.getEntityManager();
        String jpql = "SELECT count(p) FROM Product p";
        Query query = enma.createQuery(jpql);
        return ((Long) query.getSingleResult()).intValue();
    }

    // Lấy chi tiết 1 sản phẩm
    public Product findById(int id) {
        EntityManager enma = JPAConfig.getEntityManager();
        return enma.find(Product.class, id);
    }

    // Lấy tất cả sản phẩm (dùng cho trang quản trị)
    public List<Product> findAll() {
        EntityManager enma = JPAConfig.getEntityManager();
        String jpql = "SELECT p FROM Product p ORDER BY p.productId DESC";
        TypedQuery<Product> query = enma.createQuery(jpql, Product.class);
        return query.getResultList();
    }

    // Thêm sản phẩm
    public void insert(Product product) {
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.persist(product);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }

    // Cập nhật sản phẩm
    public void update(Product product) {
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            enma.merge(product);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }

    // Xóa sản phẩm
    public void delete(int id) throws Exception {
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();
        try {
            trans.begin();
            Product product = enma.find(Product.class, id);
            if (product != null) {
                enma.remove(product);
            } else {
                throw new Exception("Không tìm thấy sản phẩm");
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw e;
        } finally {
            enma.close();
        }
    }
}