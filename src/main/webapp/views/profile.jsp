<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thong tin ca nhan</title>
</head>
<body>
    <h2 class="mb-3">Thong tin ca nhan</h2>

    <c:if test="${not empty success}">
        <div class="alert alert-success py-2"><c:out value="${success}" /></div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger py-2"><c:out value="${error}" /></div>
    </c:if>

    <div class="row g-4">
        <div class="col-auto text-center">
            <c:choose>
                <c:when test="${not empty user.avatar}">
                    <img src="<c:url value='/image'/>?fname=${user.avatar}"
                         width="150" height="150" class="rounded object-fit-cover border" />
                </c:when>
                <c:otherwise>
                    <div class="d-flex align-items-center justify-content-center bg-light border rounded text-muted"
                         style="width:150px;height:150px;">
                        Chua co anh
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="col">
            <form class="needs-validation" novalidate style="max-width:420px;"
                  action="<c:url value='/profile/update'/>" method="post" enctype="multipart/form-data">

                <div class="mb-3">
                    <label class="form-label">Ten dang nhap</label>
                    <input type="text" value="${user.username}" class="form-control" disabled />
                </div>

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="text" value="${user.email}" class="form-control" disabled />
                </div>

                <div class="mb-3">
                    <label class="form-label">Ho va ten</label>
                    <input type="text" name="fullname" value="${user.fullName}"
                           class="form-control ${not empty fieldErrors.fullname ? 'is-invalid' : ''}"
                           maxlength="100" placeholder="Nhap ho ten" />
                    <div class="invalid-feedback"><c:out value="${fieldErrors.fullname}" /></div>
                </div>

                <div class="mb-3">
                    <label class="form-label">So dien thoai</label>
                    <input type="text" name="phone" value="${user.phone}"
                           class="form-control ${not empty fieldErrors.phone ? 'is-invalid' : ''}"
                           pattern="0[0-9]{9,10}" placeholder="VD: 0912345678" />
                    <div class="invalid-feedback">
                        <c:out value="${not empty fieldErrors.phone ? fieldErrors.phone : 'So dien thoai khong hop le.'}" />
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Anh dai dien</label>
                    <input type="file" name="avatar" accept="image/*" class="form-control" />
                </div>

                <button type="submit" class="btn btn-primary">Luu thay doi</button>
            </form>
        </div>
    </div>
</body>
</html>
