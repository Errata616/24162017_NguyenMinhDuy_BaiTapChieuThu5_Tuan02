<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<form action="<c:url value="/admin/category/insert"/>" method="post" enctype="multipart/form-data">
    <!-- Category Name -->
    <label for="categoryname">Category name:</label><br>
    <input type="text" id="categoryname" name="categoryname"><br><br>

    <!-- Link Images -->
    <label for="images">Link images:</label><br>
    <input type="text" id="images" name="images"><br><br>

    <!-- Upload Images -->
    <label for="images1">Upload images:</label><br>
    <input type="file" id="images1" name="images1"><br><br>

    <!-- Status -->
    <label>Status:</label><br>
    <input type="radio" id="ston" name="status" value="1">
    <label for="ston">Hoạt động</label><br>

    <input type="radio" id="stoff" name="status" value="0">
    <label for="stoff">Khóa</label>
    <br><br>

    <input type="submit" value="Insert">
</form>