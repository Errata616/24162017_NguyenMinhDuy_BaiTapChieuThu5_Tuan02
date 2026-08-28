<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dang Nhap</title>
</head>
<body>
    <div class="login-container">
        <h2>Trang Dang Nhap</h2>

        <!-- Display error message from Servlet -->
        <c:if test="${not empty error}">
            <p class="error-message"><c:out value="${error}" /></p>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label>Ten dang nhap:</label>
                <input type="text" name="username" value="${cookie.username.value}" required />
            </div>

            <div class="form-group">
                <label>Mat khau:</label>
                <input type="password" name="password" required />
            </div>

            <!-- ADDED: Remember Me Checkbox for Cookie Assignment -->
            <div class="remember-group">
                <input type="checkbox" name="remember" id="remember" ${not empty cookie.username ? 'checked' : ''} />
                <label for="remember">Nho dang nhap (Remember me)</label>
            </div>

            <button type="submit">Dang Nhap</button>
        </form>

        <p><a href="${pageContext.request.contextPath}/register">Đăng ký tài khoản</a>
        | <a href="${pageContext.request.contextPath}/forgot-password">Quên mật khẩu</a></p>
    </div>
</body>
</html>