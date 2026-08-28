package duy.packages.controllers;

import java.io.IOException;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import duy.packages.dao.impl.UserDao;
import duy.packages.entity.User;
import duy.packages.constant.Email;

@WebServlet(urlPatterns = { "/register", "/verify-otp", "/login", "/logout", "/forgot-password", "/reset-password" })
public class AccountController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        if (url.contains("/register")) {
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        } else if (url.contains("/verify-otp")) {
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
        } else if (url.contains("/login")) {
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        } else if (url.contains("/logout")) {
            HttpSession session = req.getSession();
            session.removeAttribute("account");
            resp.sendRedirect(req.getContextPath() + "/login");
        } else if (url.contains("/forgot-password")) {
            req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
        } else if (url.contains("/reset-password")) {
            req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        // 1. Đăng ký tài khoản
        if (url.contains("/register")) {
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            String otp = String.format("%06d", new Random().nextInt(999999));
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setStatus(false);
            user.setCode(otp);

            userDao.insert(user);
            Email.sendEmail(email, "Mã kích hoạt OTP", "Mã OTP kích hoạt tài khoản của bạn là: <b>" + otp + "</b>");

            req.getSession().setAttribute("emailVerify", email);
            resp.sendRedirect(req.getContextPath() + "/verify-otp");
        } 
        
        // Kích hoạt tài khoản bằng OTP
        else if (url.contains("/verify-otp")) {
            String email = (String) req.getSession().getAttribute("emailVerify");
            String otpInput = req.getParameter("otp");

            User user = userDao.findByUsernameOrEmail(email);
            if (user != null && user.getCode().equals(otpInput)) {
                user.setStatus(true);
                user.setCode(null);
                userDao.update(user);
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                req.setAttribute("error", "Mã OTP không chính xác!");
                req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
            }
        } 

        // 2. Thực hiện đăng nhập
        else if (url.contains("/login")) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            User user = userDao.findByUsernameOrEmail(username);
            if (user != null && user.getPassword().equals(password)) {
                if (!user.isStatus()) {
                    req.setAttribute("error", "Tài khoản chưa được kích hoạt OTP!");
                    req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
                    return;
                }
                HttpSession session = req.getSession();
                session.setAttribute("account", user);
                resp.sendRedirect(req.getContextPath() + "/home");
            } else {
                req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            }
        } 

        // 3. Quên mật khẩu - Gửi OTP qua email
        else if (url.contains("/forgot-password")) {
            String email = req.getParameter("email");
            User user = userDao.findByUsernameOrEmail(email);
            if (user != null) {
                String otp = String.format("%06d", new Random().nextInt(999999));
                user.setCode(otp);
                userDao.update(user);
                Email.sendEmail(email, "Mã khôi phục mật khẩu", "Mã OTP đặt lại mật khẩu là: <b>" + otp + "</b>");
                req.getSession().setAttribute("emailReset", email);
                resp.sendRedirect(req.getContextPath() + "/reset-password");
            } else {
                req.setAttribute("error", "Email không tồn tại trong hệ thống!");
                req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
            }
        } 

        // Đặt lại mật khẩu mới
        else if (url.contains("/reset-password")) {
            String email = (String) req.getSession().getAttribute("emailReset");
            String otp = req.getParameter("otp");
            String newPassword = req.getParameter("newPassword");

            User user = userDao.findByUsernameOrEmail(email);
            if (user != null && user.getCode().equals(otp)) {
                user.setPassword(newPassword);
                user.setCode(null);
                userDao.update(user);
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                req.setAttribute("error", "Mã OTP không đúng!");
                req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
            }
        }
    }
}