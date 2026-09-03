<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:out value="${decoratorTitle}" /></title>
    <style>
        * { box-sizing: border-box; }
        body { font-family: Arial, Helvetica, sans-serif; margin: 0; color: #222; }
        header.site-header {
            background: #2c3e50; color: #fff; padding: 12px 24px;
            display: flex; justify-content: space-between; align-items: center;
        }
        header.site-header a { color: #fff; text-decoration: none; margin-left: 18px; }
        header.site-header a:hover { text-decoration: underline; }
        main.site-content { padding: 24px; max-width: 1100px; margin: 0 auto; }
        footer.site-footer { text-align: center; padding: 16px; color: #888; border-top: 1px solid #eee; margin-top: 40px; }
    </style>
</head>
<body>
    <header class="site-header">
        <div><a href="<c:url value='/home'/>"><b>Web Cua Duy</b></a></div>
        <nav>
            <c:choose>
                <c:when test="${not empty sessionScope.account}">
                    <a href="<c:url value='/home'/>">Trang chu</a>
                    <a href="<c:url value='/product'/>">San pham</a>
                    <a href="<c:url value='/profile'/>">Xin chao, ${sessionScope.account.username}</a>
                    <a href="<c:url value='/logout'/>">Dang xuat</a>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value='/login'/>">Dang nhap</a>
                    <a href="<c:url value='/register'/>">Dang ky</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>

    <main class="site-content">
        ${decoratorBody}
    </main>

    <footer class="site-footer">
        &copy; 2026 Web Cua Duy - Nguyen Minh Duy
    </footer>
</body>
</html>
