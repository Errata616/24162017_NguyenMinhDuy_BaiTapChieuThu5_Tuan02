package duy.packages.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import duy.packages.configs.JPAConfig;
import duy.packages.entity.Category;
import duy.packages.entity.Video;

public class TestJPA {
    public static void main(String[] args) {
        // Mở EntityManager
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();

        try {
            // 1. Khởi tạo dữ liệu
            Category cate = new Category();
            cate.setCategoryname("Iphone");
            cate.setImages("abc.jpg");
            cate.setStatus(1);

            Video video = new Video();
            video.setVideoId("v01");
            video.setTitle("test");
            video.setCategory(cate); // Thiết lập mối quan hệ

            // 2. Thực thi Transaction
            trans.begin();
            
            // Nếu Category đã có CascadeType.ALL trong Video Entity, 
            // chỉ cần persist(video). Nếu chưa có, giữ nguyên cả 2 dòng:
            enma.persist(cate);
            enma.persist(video);

            trans.commit();
            System.out.println(">>> THÀNH CÔNG: Đã thêm dữ liệu thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            // Rollback nếu có lỗi trong quá trình ghi DB
            if (trans != null && trans.isActive()) {
                trans.rollback();
            }
        } finally {
            // Luôn đóng EntityManager để giải phóng kết nối
            if (enma != null && enma.isOpen()) {
                enma.close();
            }
        }
    }
}