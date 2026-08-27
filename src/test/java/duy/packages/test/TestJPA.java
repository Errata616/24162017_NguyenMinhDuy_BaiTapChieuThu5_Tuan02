package duy.packages.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import duy.packages.configs.JPAConfig;
import duy.packages.entity.Category;
import duy.packages.entity.Video;

public class TestJPA {
    public static void main(String[] args) {
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();

        Category cate = new Category();
        cate.setCategoryname("Iphone");
        cate.setImages("abc.jpg");
        cate.setStatus(1);

        Video video = new Video();
        video.setVideoId("v01");
        video.setTitle("test");
        video.setCategory(cate);

        try {
            trans.begin();
            enma.persist(cate);
            enma.persist(video);
            trans.commit();
            System.out.println(">>> THÀNH CÔNG: Đã tạo bảng và chèn dữ liệu test!");
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) {
                trans.rollback();
            }
        } finally {
            enma.close();
        }
    }
}