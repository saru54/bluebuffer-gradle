package com.core.interceptor;



import com.core.attribute.AuthRole;
import com.core.attribute.NotAuth;
import com.core.util.JwtUtil;
import com.core.util.LocalStoreUtil;
import com.core.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@Slf4j
public class JwtAuthInterceptor implements HandlerInterceptor {
    @Autowired
    public JwtUtil jwtTokenUtil;
    @Value("${token.jwt.expiresTime}")
    private Long expiresTime;
    @Autowired
    LocalStoreUtil localStoreHelper;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())) {
            System.out.println("OPTIONS请求，放行");
            return true;
        }
        //验证NotAuth
        if(handler instanceof HandlerMethod handlerMethod){
            NotAuth att=handlerMethod.getMethod().getAnnotation(NotAuth.class);
            if(att!=null){
                return true;
            }
        }

        String token = request.getHeader("token");
        if (null == token || token.trim().isEmpty()) {
            log.info("缺少token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        var map = jwtTokenUtil.parseJwtToken(token);
        var userId = map.get("userId");
        var roles = map.get("role");
        var timeOfUser = System.currentTimeMillis() - Long.parseLong(map.get("timeStamp"));
        if (timeOfUser>expiresTime){
            log.info("登录失效");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        try{
            if (!redisUtil.get(map.get("name")).equals(map.get("version"))) {
                log.info("用户重复登录");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }catch (Exception e){
            log.error(e.toString());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return  false;
        }


        //验证role
        if(handler instanceof HandlerMethod handlerMethod){

            AuthRole roleAuthenticate = handlerMethod.getBeanType().getAnnotation(AuthRole.class);
            if(roleAuthenticate!=null){
                boolean isAuth = false;
                for(var authRole : roleAuthenticate.roles()){
                    for(var userRole : roles.split(",")){
                        userRole = userRole.toUpperCase();
                        if(userRole.equals(authRole.toString())){
                            isAuth = true;
                        }
                    }
                }
                if(!isAuth){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return  false;
                }

            }


        }



        log.info("校验成功");
        localStoreHelper.setUserId(userId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
