<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %> <!-- Thêm taglib fn -->

<a href="<c:url value="/admin/category/add"/>">Add Category</a><br>
<hr>
<table border="1" width="100%">
<tr>
    <th>STT</th>
    <th>Images</th>
    <th>Category name</th>
    <th>Status</th>
    <th>Action</th>
</tr>
<c:forEach items="${listcate}" var="cate" varStatus="STT">
<tr>
    <td>${STT.index + 1}</td>
    
    <!-- Cột hiển thị ảnh xử lý an toàn -->
    <td>
        <c:choose>
            <c:when test="${not empty cate.images and fn:startsWith(cate.images, 'https')}">
                <c:url value="${cate.images}" var="imgUrl"/>
            </c:when>
            <c:otherwise>
                <c:url value="/image?fname=${cate.images}" var="imgUrl"/>
            </c:otherwise>
        </c:choose>
        <img height="150" width="200" src="${imgUrl}" alt="${cate.categoryname}" />
    </td>

    <td>${cate.categoryname}</td>
    
    <!-- Cột trạng thái -->
    <td>
        <c:choose>
            <c:when test="${cate.status == 1}">Hoạt động</c:when>
            <c:otherwise>Khóa</c:otherwise>
        </c:choose>
    </td>

    <!-- Cột hành động -->
    <td>
        <a href="<c:url value='/admin/category/edit?id=${cate.categoryId}'/>">Sửa</a> | 
        <a href="<c:url value='/admin/category/delete?id=${cate.categoryId}'/>" 
           onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này không?');">Xóa</a>
    </td>
</tr>
</c:forEach>
</table>