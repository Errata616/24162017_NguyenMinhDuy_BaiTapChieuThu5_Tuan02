package duy.packages.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import duy.packages.constant.Constants;

// Phục vụ ảnh đã upload qua Multipart (category-list.jsp / product-list.jsp gọi /image?fname=...)
@WebServlet(urlPatterns = { "/image" })
public class ImageController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fname = req.getParameter("fname");
        if (fname == null || fname.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        File file = new File(Constants.DIR, fname);
        if (!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mimeType = Files.probeContentType(file.toPath());
        resp.setContentType(mimeType != null ? mimeType : "application/octet-stream");
        resp.setContentLengthLong(file.length());

        try (FileInputStream in = new FileInputStream(file)) {
            in.transferTo(resp.getOutputStream());
        }
    }
}
