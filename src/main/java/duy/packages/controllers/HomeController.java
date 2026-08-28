package duy.packages.controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import duy.packages.dao.impl.ProductDao;
import duy.packages.entity.Product;

@WebServlet(urlPatterns = { "/home", "/product", "/product-detail" })
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDao productDao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        // 2. Hiển thị 10 sản phẩm mới nhất lên trang chủ
        if (url.contains("/home")) {
            List<Product> top10 = productDao.findTop10New();
            req.setAttribute("top10Products", top10);
            req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
        }

        // 3. Hiển thị phân trang 6sp/trang tại URL /product
        else if (url.contains("/product")) {
            int page = 1;
            int pageSize = 6;
            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }
            List<Product> list = productDao.findAllPage(page, pageSize);
            int totalProducts = productDao.count();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            req.setAttribute("productList", list);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.getRequestDispatcher("/views/product-list.jsp").forward(req, resp);
        }

        // 4. Hiển thị chi tiết 01 sản phẩm khi bấm vào
        else if (url.contains("/product-detail")) {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = productDao.findById(id);
            req.setAttribute("product", product);
            req.getRequestDispatcher("/views/product-detail.jsp").forward(req, resp);
        }
    }
}