<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thong tin ca nhan</title>
</head>
<body>
    <h2>Thong tin ca nhan</h2>

    <c:if test="${not empty success}">
        <p style="color:green"><c:out value="${success}" /></p>
    </c:if>
    <c:if test="${not empty error}">
        <p style="color:red"><c:out value="${error}" /></p>
    </c:if>

    <div style="display:flex; gap:30px; align-items:flex-start; flex-wrap:wrap;">
        <div>
            <c:choose>
                <c:when test="${not empty user.avatar}">
                    <img src="<c:url value='/image'/>?fname=${user.avatar}"
                         width="150" height="150"
                         style="object-fit:cover; border-radius:8px; border:1px solid #ddd;" />
                </c:when>
                <c:otherwise>
                    <div style="width:150px;height:150px;background:#eee;border-radius:8px;
                                display:flex;align-items:center;justify-content:center;color:#999;">
                        Chua co anh
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <form action="<c:url value='/profile/update'/>" method="post"
              enctype="multipart/form-data" style="flex:1; min-width:280px;">

            <div style="margin-bottom:12px;">
                <label>Ten dang nhap</label><br/>
                <input type="text" value="${user.username}" disabled style="width:100%; padding:6px;" />
            </div>

            <div style="margin-bottom:12px;">
                <label>Email</label><br/>
                <input type="text" value="${user.email}" disabled style="width:100%; padding:6px;" />
            </div>

            <div style="margin-bottom:12px;">
                <label>Ho va ten</label><br/>
                <input type="text" name="fullname" value="${user.fullName}"
                       placeholder="Nhap ho ten" style="width:100%; padding:6px;" />
            </div>

            <div style="margin-bottom:12px;">
                <label>So dien thoai</label><br/>
                <input type="text" name="phone" value="${user.phone}"
                       placeholder="Nhap so dien thoai" style="width:100%; padding:6px;" />
            </div>

            <div style="margin-bottom:16px;">
                <label>Anh dai dien</label><br/>
                <input type="file" name="avatar" accept="image/*" />
            </div>

            <button type="submit" style="padding:8px 20px;">Luu thay doi</button>
        </form>
    </div>
</body>
</html>
