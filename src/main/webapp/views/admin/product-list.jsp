<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %> <!-- Thêm taglib fn -->

<h2>Quản lý Sản phẩm</h2>
<a href="<c:url value="/admin/product/add"/>">Add Product</a>
| <a href="<c:url value="/admin/categories"/>">Quản lý Category</a>
| <a href="<c:url value="/product"/>">Xem trang sản phẩm</a>
<hr>

<table border="1" width="100%">
<tr>
    <th>STT</th>
    <th>Images</th>
    <th>Product name</th>
    <th>Price</th>
    <th>Category</th>
    <th>Action</th>
</tr>
<c:forEach items="${listproduct}" var="p" varStatus="STT">
<tr>
    <td>${STT.index + 1}</td>
    
    <!-- Cột hiển thị ảnh an toàn -->
    <td>
        <c:choose>
            <c:when test="${not empty p.images and fn:startsWith(p.images, 'https')}">
                <c:url value="${p.images}" var="imgUrl"/>
            </c:when>
            <c:otherwise>
                <c:url value="/image?fname=${p.images}" var="imgUrl"/>
            </c:otherwise>
        </c:choose>
        <img height="100" width="130" src="${imgUrl}" alt="${p.productName}" />
    </td>

    <td>${p.productName}</td>
    <td>${p.price} VNĐ</td>
    
    <!-- Cột Category an toàn với Null -->
    <td>${not empty p.category ? p.category.categoryname : 'Chưa phân loại'}</td>

    <!-- Cột Thao tác -->
    <td>
        <a href="<c:url value='/admin/product/edit?id=${p.productId}'/>">Sửa</a> | 
        <a href="<c:url value='/admin/product/delete?id=${p.productId}'/>" 
           onclick="return confirm('Xóa sản phẩm này?');">Xóa</a>
    </td>
</tr>
</c:forEach>
</table>