package duy.packages.filters;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

// Bo dem noi dung ma JSP render ra, de DecoratorFilter "bat" lai truoc khi
// tra ve client - day la ky thuat lam nen cua moi bo decorator kieu SiteMesh.
public class CharResponseWrapper extends HttpServletResponseWrapper {
    private final CharArrayWriter buffer = new CharArrayWriter();
    private PrintWriter writer;

    public CharResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() {
        if (writer == null) {
            writer = new PrintWriter(buffer);
        }
        return writer;
    }

    public String getCaptured() {
        if (writer != null) {
            writer.flush();
        }
        return buffer.toString();
    }
}
