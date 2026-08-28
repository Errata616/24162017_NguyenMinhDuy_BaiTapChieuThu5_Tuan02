<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<h2>Chi Tiết Sản Phẩm</h2>
<div>
    <img src="${product.images}" width="300px"/>
    <h3>Tên SP: ${product.productName}</h3>
    <p>Giá: ${product.price} VNĐ</p>
    <p>Mô tả: ${product.description}</p>
    <p>Danh mục: ${product.category.categoryname}</p>
</div>
<a href="javascript:history.back()">Quay lại</a>