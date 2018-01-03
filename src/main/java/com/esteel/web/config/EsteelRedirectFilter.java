package com.esteel.web.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2018-01-03
 * Time: 09:37
 */
public class EsteelRedirectFilter extends GenericFilterBean
        implements ApplicationEventPublisherAware, MessageSourceAware {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        System.out.println("000000000000000000000000000000");
        System.out.println("000000000000000000000000000000");
//        System.out.println(req.getClass().getName().endsWith("SavedRequestAwareWrapper"));
        if (req.getClass().getName().endsWith("SavedRequestAwareWrapper")){
            System.out.println(request.getParameter("ssss"));
        }
        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURL());
        System.out.println("000000000000000000000000000000");
        System.out.println("000000000000000000000000000000");


        String ssss = request.getParameter("ssss");
        if ("0000".equals(ssss)){

            response.sendRedirect("/sss");
        }

        chain.doFilter(req,res);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }

    @Override
    public void setMessageSource(MessageSource messageSource) {

    }
}
