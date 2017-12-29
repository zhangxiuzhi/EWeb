package com.esteel.web.web;

import com.esteel.web.service.IndexService;
import com.esteel.web.vo.ProvinceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-11-21
 * Time: 13:24
 */
@Controller
public class IndexController {

    @Autowired
    IndexService indexService;

    /**
     * web系统首页
     *
     * @return
     */
    @RequestMapping("/")
    @ResponseBody
    public List<ProvinceVo>  index(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication.getPrincipal().getClass().getName());

        System.out.println("88899999999999999");
        System.out.println("88899999999999999");
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println("88899999999999999");
        System.out.println("88899999999999999");

//        indexService.tfsTest();

        List<ProvinceVo> ports = indexService.getPort(1);



//        System.out.println("0000000000000000000");
//        System.out.println(port);
//        System.out.println("0000000000000000000");
        return ports;
    }


    @RequestMapping("/register/dologin")
    @ResponseBody
    public String dologin(HttpServletRequest request){

        List<GrantedAuthority> authorities = new ArrayList<>();

        User user = new User("zxz","",authorities);
        Authentication auth = new UsernamePasswordAuthenticationToken("system", "", user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        return "ok";
    }

}
