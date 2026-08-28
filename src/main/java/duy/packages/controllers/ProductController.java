package duy.packages.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import duy.packages.constant.Constants;
import duy.packages.dao.impl.CategoryDao;
import duy.packages.dao.impl.ProductDao;
import duy.packages.entity.Category;
import duy.packages.entity.Product;

// 4.1 CRUD cho bảng Products (khu vực quản trị)
@MultipartConfig()
@WebServlet(urlPatterns = { "/admin/products", "/admin/product/add", "/admin/product/insert",
        "/admin/product/edit", "/admin/product/update", "/admin/product/delete" })
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDao productDao = new ProductDao();
    private CategoryDao categoryDao = new CategoryDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        if (url.contains("/admin/products")) {
            List<Product> list = productDao.findAll();
            req.setAttribute("listproduct", list);
            req.getRequestDispatcher("/views/admin/product-list.jsp").forward(req, resp);
        } else if (url.contains("/admin/product/add")) {
            List<Category> listcate = categoryDao.findAll();
            req.setAttribute("listcate", listcate);
            req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);
        } else if (url.contains("/admin/product/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = productDao.findById(id);
            List<Category> listcate = categoryDao.findAll();
            req.setAttribute("product", product);
            req.setAttribute("listcate", listcate);
            req.getRequestDispatcher("/views/admin/product-edit.jsp").forward(req, resp);
        } else if (url.contains("/admin/product/delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            try {
                productDao.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        if (url.contains("/admin/product/insert")) {
            String productName = req.getParameter("productname");
            double price = Double.parseDouble(req.getParameter("price"));
            String description = req.getParameter("description");
            int categoryId = Integer.parseInt(req.getParameter("categoryid"));
            String images = req.getParameter("images");

            Product product = new Product();
            product.setProductName(productName);
            product.setPrice(price);
            product.setDescription(description);
            product.setCategory(categoryDao.findById(categoryId));

            String uploadPath = Constants.DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                uploadDir.mkdir();
            try {
                Part part = req.getPart("images1");
                if (part != null && part.getSize() > 0) {
                    String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    int index = filename.lastIndexOf(".");
                    String ext = filename.substring(index + 1);
                    String fname = System.currentTimeMillis() + "." + ext;
                    part.write(uploadPath + "/" + fname);
                    product.setImages(fname);
                } else if (images != null && !images.isEmpty()) {
                    product.setImages(images);
                } else {
                    product.setImages("avatar.png");
                }
            } catch (FileNotFoundException fne) {
                fne.printStackTrace();
            }

            productDao.insert(product);
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }

        if (url.contains("/admin/product/update")) {
            int productId = Integer.parseInt(req.getParameter("productid"));
            String productName = req.getParameter("productname");
            double price = Double.parseDouble(req.getParameter("price"));
            String description = req.getParameter("description");
            int categoryId = Integer.parseInt(req.getParameter("categoryid"));
            String images = req.getParameter("images");

            Product product = productDao.findById(productId);
            String fileold = product.getImages();
            product.setProductName(productName);
            product.setPrice(price);
            product.setDescription(description);
            product.setCategory(categoryDao.findById(categoryId));

            String uploadPath = Constants.DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                uploadDir.mkdir();
            try {
                Part part = req.getPart("images1");
                if (part != null && part.getSize() > 0) {
                    if (fileold != null && !fileold.startsWith("https")) {
                        deleteFile(uploadPath + "/" + fileold);
                    }
                    String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    int index = filename.lastIndexOf(".");
                    String ext = filename.substring(index + 1);
                    String fname = System.currentTimeMillis() + "." + ext;
                    part.write(uploadPath + "/" + fname);
                    product.setImages(fname);
                } else if (images != null && !images.isEmpty()) {
                    product.setImages(images);
                } else {
                    product.setImages(fileold);
                }
            } catch (FileNotFoundException fne) {
                fne.printStackTrace();
            }

            productDao.update(product);
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }

    public static void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}
