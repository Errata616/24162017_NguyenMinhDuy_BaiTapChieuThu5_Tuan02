<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dang Nhap</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow-sm mt-4">
                <div class="card-body p-4">
                    <h2 class="card-title text-center mb-4">Trang Dang Nhap</h2>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger py-2"><c:out value="${error}" /></div>
                    </c:if>

                    <form class="needs-validation" novalidate
                          action="${pageContext.request.contextPath}/login" method="post">

                        <div class="mb-3">
                            <label class="form-label">Ten dang nhap</label>
                            <input type="text" name="username" class="form-control"
                                   value="${cookie.username.value}" required />
                            <div class="invalid-feedback">Vui long nhap ten dang nhap.</div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Mat khau</label>
                            <input type="password" name="password" class="form-control" required />
                            <div class="invalid-feedback">Vui long nhap mat khau.</div>
                        </div>

                        <div class="mb-3 form-check">
                            <input type="checkbox" class="form-check-input" name="remember" id="remember"
                                   ${not empty cookie.username ? 'checked' : ''} />
                            <label class="form-check-label" for="remember">Nho dang nhap (Remember me)</label>
                        </div>

                        <button type="submit" class="btn btn-primary w-100">Dang Nhap</button>
                    </form>

                    <p class="text-center mt-3 mb-0">
                        <a href="${pageContext.request.contextPath}/register">Dang ky tai khoan</a>
                        &nbsp;|&nbsp;
                        <a href="${pageContext.request.contextPath}/forgot-password">Quen mat khau</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
