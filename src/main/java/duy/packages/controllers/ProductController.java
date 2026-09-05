package duy.packages.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import duy.packages.utils.ValidationUtils;

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
            String priceParam = req.getParameter("price");
            String description = req.getParameter("description");
            String categoryIdParam = req.getParameter("categoryid");
            String images = req.getParameter("images");

            Map<String, String> errors = validateProduct(productName, priceParam, categoryIdParam);
            if (!errors.isEmpty()) {
                req.setAttribute("fieldErrors", errors);
                req.setAttribute("error", "Vui long kiem tra lai thong tin san pham.");
                req.setAttribute("listcate", categoryDao.findAll());
                req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);
                return;
            }

            double price = Double.parseDouble(priceParam.trim());
            int categoryId = Integer.parseInt(categoryIdParam.trim());

            Product product = new Product();
            product.setProductName(productName.trim());
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
            String productIdParam = req.getParameter("productid");
            String productName = req.getParameter("productname");
            String priceParam = req.getParameter("price");
            String description = req.getParameter("description");
            String categoryIdParam = req.getParameter("categoryid");
            String images = req.getParameter("images");

            int productId = Integer.parseInt(productIdParam);
            Map<String, String> errors = validateProduct(productName, priceParam, categoryIdParam);
            if (!errors.isEmpty()) {
                req.setAttribute("fieldErrors", errors);
                req.setAttribute("error", "Vui long kiem tra lai thong tin san pham.");
                req.setAttribute("product", productDao.findById(productId));
                req.setAttribute("listcate", categoryDao.findAll());
                req.getRequestDispatcher("/views/admin/product-edit.jsp").forward(req, resp);
                return;
            }

            double price = Double.parseDouble(priceParam.trim());
            int categoryId = Integer.parseInt(categoryIdParam.trim());

            Product product = productDao.findById(productId);
            String fileold = product.getImages();
            product.setProductName(productName.trim());
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

    /** Validate du lieu form them/sua San pham. Tra ve Map rong neu hop le. */
    private Map<String, String> validateProduct(String productName, String priceParam, String categoryIdParam) {
        Map<String, String> errors = new HashMap<>();

        if (ValidationUtils.isBlank(productName)) {
            errors.put("productname", "Ten san pham khong duoc de trong.");
        } else if (!ValidationUtils.maxLength(productName.trim(), 255)) {
            errors.put("productname", "Ten san pham toi da 255 ky tu.");
        }

        if (!ValidationUtils.isPositiveNumber(priceParam)) {
            errors.put("price", "Gia san pham phai la so va lon hon 0.");
        }

        if (!ValidationUtils.isPositiveInt(categoryIdParam)) {
            errors.put("categoryid", "Vui long chon danh muc san pham.");
        }

        return errors;
    }

    public static void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}
