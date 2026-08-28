<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %> <!-- Thêm taglib fn -->

<form action="<c:url value="/admin/category/update"/>" method="post" enctype="multipart/form-data">
    <!-- ID ẩn -->
    <input type="hidden" name="categoryid" value="${cate.categoryId}">

    <!-- Category Name -->
    <label for="categoryname">Category name:</label><br>
    <input type="text" id="categoryname" name="categoryname" value="${cate.categoryname}"><br><br>

    <!-- Link images -->
    <label for="images">Link images:</label><br>
    <input type="text" id="images" name="images" value="${cate.images}"><br><br>
    
    <!-- Hiển thị ảnh xem trước an toàn -->
    <c:choose>
        <c:when test="${not empty cate.images and fn:startsWith(cate.images, 'https')}">
            <c:url value="${cate.images}" var="imgUrl"/>
        </c:when>
        <c:otherwise>
            <c:url value="/image?fname=${cate.images}" var="imgUrl"/>
        </c:otherwise>
    </c:choose>
    <img height="150" width="200" src="${imgUrl}" alt="Category Image"/><br><br>
    
    <!-- Upload images -->
    <label for="images1">Upload images:</label><br>
    <input type="file" id="images1" name="images1"><br><br>

    <!-- Status -->
    <label>Status:</label><br>
    <input type="radio" id="ston" name="status" value="1" ${cate.status == 1 ? 'checked' : ''}>
    <label for="ston">Hoạt động</label><br>

    <input type="radio" id="stoff" name="status" value="0" ${cate.status != 1 ? 'checked' : ''}>
    <label for="stoff">Khóa</label>
    <br><br>

    <input type="submit" value="Update">
</form>