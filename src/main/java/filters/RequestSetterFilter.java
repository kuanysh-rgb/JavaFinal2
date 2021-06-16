package filters;

import controllers.AuthorizationManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

public class RequestSetterFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("RequestLogFilter init!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        SetRequestThread setRequest = new SetRequestThread(req);
        LogThread log = new LogThread(req);

        Thread setRequestThread = new Thread(setRequest);
        Thread logThread = new Thread(log);

        setRequestThread.start();
        logThread.start();

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("RequestLogFilter destroy!");
    }
}
