<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dang ky tai khoan</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow-sm mt-4">
                <div class="card-body p-4">
                    <h2 class="card-title text-center mb-4">Dang ky tai khoan</h2>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger py-2"><c:out value="${error}" /></div>
                    </c:if>

                    <form class="needs-validation" novalidate
                          action="${pageContext.request.contextPath}/register" method="post">

                        <div class="mb-3">
                            <label class="form-label">Ten dang nhap</label>
                            <input type="text" name="username" value="${param.username}"
                                   class="form-control ${not empty fieldErrors.username ? 'is-invalid' : ''}"
                                   minlength="4" maxlength="50" required>
                            <div class="invalid-feedback">
                                <c:out value="${not empty fieldErrors.username ? fieldErrors.username : 'Vui long nhap ten dang nhap (4-50 ky tu).'}" />
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" value="${param.email}"
                                   class="form-control ${not empty fieldErrors.email ? 'is-invalid' : ''}" required>
                            <div class="invalid-feedback">
                                <c:out value="${not empty fieldErrors.email ? fieldErrors.email : 'Vui long nhap email hop le.'}" />
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Mat khau</label>
                            <input type="password" name="password"
                                   class="form-control ${not empty fieldErrors.password ? 'is-invalid' : ''}"
                                   minlength="6" required>
                            <div class="invalid-feedback">
                                <c:out value="${not empty fieldErrors.password ? fieldErrors.password : 'Mat khau toi thieu 6 ky tu.'}" />
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary w-100">Dang Ky</button>
                    </form>

                    <p class="text-center mt-3 mb-0">
                        Da co tai khoan? <a href="${pageContext.request.contextPath}/login">Dang nhap</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
