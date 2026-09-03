package duy.packages.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*", dispatcherTypes = DispatcherType.REQUEST)
public class DecoratorFilter implements Filter {

    private static final Pattern TITLE_PATTERN =
            Pattern.compile("<title[^>]*>(.*?)</title>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private static final Pattern BODY_PATTERN =
            Pattern.compile("<body[^>]*>(.*?)</body>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    // Nhung duong dan KHONG decorate: anh upload, file tinh...
    private static final String[] EXCLUDES = {
            "/image", ".css", ".js", ".png", ".jpg", ".jpeg", ".gif", ".ico", ".woff", ".svg"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (isExcluded(req.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        CharResponseWrapper wrapper = new CharResponseWrapper(resp);
        chain.doFilter(request, wrapper);

        // Servlet da sendRedirect (vd sau khi login/logout) -> khong lam gi them
        if (resp.isCommitted()) {
            return;
        }

        String content = wrapper.getCaptured();
        if (content == null || content.isEmpty()) {
            return;
        }

        Matcher bodyMatcher = BODY_PATTERN.matcher(content);
        if (!bodyMatcher.find()) {
            // Trang dang fragment (chua co <body>) -> tra nguyen ban, khong decorate
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write(content);
            out.flush();
            return;
        }

        String bodyContent = bodyMatcher.group(1);
        String title = "Web Cua Duy";
        Matcher titleMatcher = TITLE_PATTERN.matcher(content);
        if (titleMatcher.find()) {
            title = titleMatcher.group(1).trim();
        }

        request.setAttribute("decoratorTitle", title);
        request.setAttribute("decoratorBody", bodyContent);
        request.getRequestDispatcher("/views/layout.jsp").forward(request, response);
    }

    private boolean isExcluded(String uri) {
        for (String ex : EXCLUDES) {
            if (uri.contains(ex)) return true;
        }
        return false;
    }
}
