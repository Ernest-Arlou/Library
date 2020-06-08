package by.jwd.library.controller.filter;


import javax.servlet.*;
import java.io.IOException;


public class CharsetFilter implements Filter {

    private String encoding;
    private static final String CHAR_ENCODING = "characterEncoding";


    @Override
    public void init (FilterConfig filterConfig) throws ServletException{
         encoding = filterConfig.getInitParameter(CHAR_ENCODING);
    }

    @Override
    public void doFilter (ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy (){

    }
}
