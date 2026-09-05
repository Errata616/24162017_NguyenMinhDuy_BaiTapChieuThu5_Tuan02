package duy.packages.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import duy.packages.dao.impl.UserDao;
import duy.packages.entity.User;
import duy.packages.constant.Email;
import duy.packages.utils.ValidationUtils;

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

		// 1. Dang ky tai khoan
		if (url.contains("/register")) {
			String username = req.getParameter("username");
			String email = req.getParameter("email");
			String password = req.getParameter("password");

			Map<String, String> errors = new HashMap<>();

			if (ValidationUtils.isBlank(username)) {
				errors.put("username", "Ten dang nhap khong duoc de trong.");
			} else if (!ValidationUtils.isValidUsername(username)) {
				errors.put("username", "Ten dang nhap chi gom chu, so, dau '_' va dai 4-50 ky tu.");
			} else if (userDao.findByUsernameOrEmail(username.trim()) != null) {
				errors.put("username", "Ten dang nhap da ton tai.");
			}

			if (ValidationUtils.isBlank(email)) {
				errors.put("email", "Email khong duoc de trong.");
			} else if (!ValidationUtils.isValidEmail(email)) {
				errors.put("email", "Email khong dung dinh dang.");
			} else if (userDao.findByUsernameOrEmail(email.trim()) != null) {
				errors.put("email", "Email da duoc su dung.");
			}

			if (ValidationUtils.isBlank(password)) {
				errors.put("password", "Mat khau khong duoc de trong.");
			} else if (!ValidationUtils.isValidPassword(password)) {
				errors.put("password", "Mat khau phai co it nhat 6 ky tu.");
			}

			if (!errors.isEmpty()) {
				req.setAttribute("fieldErrors", errors);
				req.setAttribute("error", "Vui long kiem tra lai thong tin da nhap.");
				req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
				return;
			}

			String otp = String.format("%06d", new Random().nextInt(999999));
			User user = new User();
			user.setUsername(username.trim());
			user.setEmail(email.trim());
			user.setPassword(password);
			user.setStatus(false);
			user.setCode(otp);

			userDao.insert(user);
			Email.sendEmail(email, "Ma kich hoat OTP", "Ma OTP kich hoat tai khoan cua ban la: <b>" + otp + "</b>");

			req.getSession().setAttribute("emailVerify", email);
			resp.sendRedirect(req.getContextPath() + "/verify-otp");
		}

		// Kich hoat tai khoan bang OTP
		else if (url.contains("/verify-otp")) {
			String email = (String) req.getSession().getAttribute("emailVerify");
			String otpInput = req.getParameter("otp");

			if (!ValidationUtils.isValidOtp(otpInput)) {
				req.setAttribute("error", "Ma OTP phai gom du 6 chu so.");
				req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
				return;
			}

			User user = userDao.findByUsernameOrEmail(email);
			if (user != null && otpInput.equals(user.getCode())) {
				user.setStatus(true);
				user.setCode(null);
				userDao.update(user);
				resp.sendRedirect(req.getContextPath() + "/login");
			} else {
				req.setAttribute("error", "Ma OTP khong chinh xac!");
				req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
			}
		}

		// 2. Thuc hien dang nhap
		else if (url.contains("/login")) {
			String username = req.getParameter("username");
			String password = req.getParameter("password");

			if (ValidationUtils.isBlank(username) || ValidationUtils.isBlank(password)) {
				req.setAttribute("error", "Vui long nhap day du ten dang nhap va mat khau!");
				req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
				return;
			}

			User user = userDao.findByUsernameOrEmail(username.trim());
			if (user != null && user.getPassword().equals(password)) {
				if (!user.isStatus()) {
					req.setAttribute("error", "Tai khoan chua duoc kich hoat OTP!");
					req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
					return;
				}
				HttpSession session = req.getSession();
				session.setAttribute("account", user);

				// Xu ly "Remember me" (giu nguyen nghiep vu cookie hien co)
				if ("on".equalsIgnoreCase(req.getParameter("remember"))
						|| "true".equalsIgnoreCase(req.getParameter("remember"))) {
					Cookie cookie = new Cookie("username", username.trim());
					cookie.setMaxAge(7 * 24 * 60 * 60);
					resp.addCookie(cookie);
				}
				resp.sendRedirect(req.getContextPath() + "/home");
			} else {
				req.setAttribute("error", "Sai ten dang nhap hoac mat khau!");
				req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			}
		}

		// 3. Quen mat khau - Gui OTP qua email
		else if (url.contains("/forgot-password")) {
			String email = req.getParameter("email");

			if (!ValidationUtils.isValidEmail(email)) {
				req.setAttribute("error", "Vui long nhap email hop le.");
				req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
				return;
			}

			User user = userDao.findByUsernameOrEmail(email.trim());
			if (user != null) {
				String otp = String.format("%06d", new Random().nextInt(999999));
				user.setCode(otp);
				userDao.insert(user);

				boolean emailSent = Email.sendEmail(email, "Ma kich hoat OTP",
						"Ma OTP kich hoat tai khoan cua ban la: <b>" + otp + "</b>");

				if (!emailSent) {
					req.setAttribute("error", "Khong the gui email OTP. Vui long thu lai sau.");

					req.getRequestDispatcher("/views/register.jsp").forward(req, resp);

					return;
				}

				req.getSession().setAttribute("emailVerify", email);
				resp.sendRedirect(req.getContextPath() + "/verify-otp");
			} else {
				req.setAttribute("error", "Email khong ton tai trong he thong!");
				req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			}
		}

		// Dat lai mat khau moi
		else if (url.contains("/reset-password")) {
			String email = (String) req.getSession().getAttribute("emailReset");
			String otp = req.getParameter("otp");
			String newPassword = req.getParameter("newPassword");

			Map<String, String> errors = new HashMap<>();
			if (!ValidationUtils.isValidOtp(otp)) {
				errors.put("otp", "Ma OTP phai gom du 6 chu so.");
			}
			if (ValidationUtils.isBlank(newPassword)) {
				errors.put("newPassword", "Mat khau moi khong duoc de trong.");
			} else if (!ValidationUtils.isValidPassword(newPassword)) {
				errors.put("newPassword", "Mat khau moi phai co it nhat 6 ky tu.");
			}
			if (!errors.isEmpty()) {
				req.setAttribute("fieldErrors", errors);
				req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
				return;
			}

			User user = userDao.findByUsernameOrEmail(email);
			if (user != null && otp.equals(user.getCode())) {
				user.setPassword(newPassword);
				user.setCode(null);
				userDao.update(user);
				resp.sendRedirect(req.getContextPath() + "/login");
			} else {
				req.setAttribute("error", "Ma OTP khong dung!");
				req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
			}
		}
	}
}
