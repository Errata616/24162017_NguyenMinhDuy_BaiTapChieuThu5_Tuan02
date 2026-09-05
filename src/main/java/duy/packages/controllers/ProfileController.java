package duy.packages.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import duy.packages.constant.Constants;
import duy.packages.dao.impl.UserDao;
import duy.packages.entity.User;
import duy.packages.utils.ValidationUtils;

// Quan ly trang Profile: xem + cap nhat fullname, phone, avatar (upload multipart)
// Cung co che upload voi ProductController (Part -> Constants.DIR), phuc vu lai boi ImageController (/image?fname=)
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // gioi han 5MB / anh
@WebServlet(urlPatterns = { "/profile", "/profile/update" })
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User account = currentAccount(req);
        if (account == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Lay du lieu moi nhat tu DB thay vi dung thang du lieu cu trong session
        User user = userDao.findById(account.getId());
        req.setAttribute("user", user);
        req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User account = currentAccount(req);
        if (account == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String url = req.getRequestURI();
        if (url.contains("/profile/update")) {
            User user = userDao.findById(account.getId());
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            String fullName = req.getParameter("fullname");
            String phone = req.getParameter("phone");

            // ===== Validation phia server =====
            Map<String, String> errors = new HashMap<>();
            if (!ValidationUtils.maxLength(fullName, 100)) {
                errors.put("fullname", "Ho va ten toi da 100 ky tu.");
            }
            if (!ValidationUtils.isValidPhone(phone)) {
                errors.put("phone", "So dien thoai khong hop le (VD: 0912345678).");
            }
            if (!errors.isEmpty()) {
                req.setAttribute("fieldErrors", errors);
                req.setAttribute("error", "Vui long kiem tra lai thong tin da nhap.");
                req.setAttribute("user", user);
                req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
                return;
            }

            user.setFullName(fullName);
            user.setPhone(phone);

            String error = null;
            String uploadPath = Constants.DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            try {
                Part part = req.getPart("avatar");
                if (part != null && part.getSize() > 0) {
                    String oldAvatar = user.getAvatar();
                    String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    int index = filename.lastIndexOf(".");
                    String ext = index >= 0 ? filename.substring(index + 1) : "jpg";
                    String fname = "avatar_" + user.getId() + "_" + System.currentTimeMillis() + "." + ext;
                    part.write(uploadPath + "/" + fname);
                    user.setAvatar(fname);

                    // Xoa anh cu de khong rac thu muc upload
                    if (oldAvatar != null && !oldAvatar.isEmpty()) {
                        new File(uploadPath, oldAvatar).delete();
                    }
                }
            } catch (FileNotFoundException fne) {
                error = "Khong the luu anh dai dien, vui long thu lai.";
            }

            userDao.update(user);
            req.getSession().setAttribute("account", user); // cap nhat lai session cho khop du lieu moi

            if (error != null) {
                req.setAttribute("error", error);
            } else {
                req.setAttribute("success", "Cap nhat thong tin ca nhan thanh cong!");
            }
            req.setAttribute("user", user);
            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
        }
    }

    private User currentAccount(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (User) session.getAttribute("account");
    }
}
