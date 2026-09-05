<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sua Category</title>
</head>
<body>
    <h2 class="mb-4">Sua Danh Muc</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-danger py-2"><c:out value="${error}" /></div>
    </c:if>

    <form class="needs-validation" style="max-width:520px;" novalidate
          action="<c:url value="/admin/category/update"/>" method="post" enctype="multipart/form-data">
        <input type="hidden" name="categoryid" value="${cate.categoryId}">

        <div class="mb-3">
            <label class="form-label" for="categoryname">Ten danh muc</label>
            <input type="text" id="categoryname" name="categoryname" value="${cate.categoryname}"
                   class="form-control ${not empty fieldErrors.categoryname ? 'is-invalid' : ''}"
                   maxlength="255" required>
            <div class="invalid-feedback">
                <c:out value="${not empty fieldErrors.categoryname ? fieldErrors.categoryname : 'Vui long nhap ten danh muc.'}" />
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label" for="images">Link anh (neu khong upload file)</label>
            <input type="text" id="images" name="images" value="${cate.images}" class="form-control">
        </div>

        <div class="mb-2">
            <c:choose>
                <c:when test="${not empty cate.images and fn:startsWith(cate.images, 'https')}">
                    <c:url value="${cate.images}" var="imgUrl"/>
                </c:when>
                <c:otherwise>
                    <c:url value="/image?fname=${cate.images}" var="imgUrl"/>
                </c:otherwise>
            </c:choose>
            <img height="120" width="160" style="object-fit:cover;" class="rounded border" src="${imgUrl}" alt="Category Image"/>
        </div>

        <div class="mb-3">
            <label class="form-label" for="images1">Upload anh moi (bo trong neu giu anh cu)</label>
            <input type="file" id="images1" name="images1" accept="image/*" class="form-control">
        </div>

        <div class="mb-3">
            <label class="form-label d-block">Trang thai</label>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" id="ston" name="status" value="1"
                       ${cate.status == 1 ? 'checked' : ''}>
                <label class="form-check-label" for="ston">Hoat dong</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" id="stoff" name="status" value="0"
                       ${cate.status != 1 ? 'checked' : ''}>
                <label class="form-check-label" for="stoff">Khoa</label>
            </div>
            <c:if test="${not empty fieldErrors.status}">
                <div class="text-danger small"><c:out value="${fieldErrors.status}" /></div>
            </c:if>
        </div>

        <button type="submit" class="btn btn-primary">Cap nhat</button>
        <a class="btn btn-outline-secondary" href="<c:url value="/admin/categories"/>">Huy</a>
    </form>
</body>
</html>
