<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><sitemesh:write property='title'/></title>

    <!-- 01 Template Bootstrap dung chung cho toan site (SiteMesh Decorator) -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">

    <style>
        body { background-color: #f4f6f9; }
        main.site-content { min-height: 70vh; padding: 24px 0 40px; }
        footer.site-footer { background:#212529; color:#adb5bd; padding:18px 0; margin-top:40px; }
        .navbar-brand { font-weight: 700; }
        .is-invalid ~ .invalid-feedback { display:block; }
    </style>

    <!-- Cho phep tung trang con nhung them CSS/JS rieng vao <head> -->
    <sitemesh:write property='head'/>
</head>
<body>

    <!-- ================= NAVBAR (Bootstrap) ================= -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm sticky-top">
        <div class="container">
            <a class="navbar-brand" href="<c:url value='/home'/>">
                <i class="bi bi-code-slash"></i> Web Cua Duy
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="mainNavbar">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item"><a class="nav-link" href="<c:url value='/home'/>">Trang chu</a></li>
                    <li class="nav-item"><a class="nav-link" href="<c:url value='/product'/>">San pham</a></li>
                    <c:if test="${not empty sessionScope.account}">
                        <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/categories'/>">Quan ly Category</a></li>
                        <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/products'/>">Quan ly Product</a></li>
                    </c:if>
                </ul>
                <ul class="navbar-nav">
                    <c:choose>
                        <c:when test="${not empty sessionScope.account}">
                            <li class="nav-item"><a class="nav-link" href="<c:url value='/profile'/>">
                                <i class="bi bi-person-circle"></i> ${sessionScope.account.username}</a></li>
                            <li class="nav-item"><a class="nav-link" href="<c:url value='/logout'/>">Dang xuat</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item"><a class="nav-link" href="<c:url value='/login'/>">Dang nhap</a></li>
                            <li class="nav-item"><a class="nav-link" href="<c:url value='/register'/>">Dang ky</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <!-- ================= NOI DUNG TUNG TRANG ================= -->
    <main class="site-content">
        <div class="container">
            <sitemesh:write property='body'/>
        </div>
    </main>

    <!-- ================= FOOTER ================= -->
    <footer class="site-footer text-center">
        <div class="container">
            <small>&copy; 2026 Web Cua Duy - Nguyen Minh Duy. SiteMesh 3 Decorator + Bootstrap 5.</small>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Kich hoat Bootstrap client-side validation cho moi form co class "needs-validation"
        (function () {
            'use strict';
            var forms = document.querySelectorAll('.needs-validation');
            Array.prototype.slice.call(forms).forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        })();
    </script>
</body>
</html>