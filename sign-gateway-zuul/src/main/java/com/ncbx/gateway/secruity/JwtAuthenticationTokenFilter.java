package com.ncbx.gateway.secruity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ncbx.gateway.configure.GatewayConfigure;
import com.netflix.zuul.context.RequestContext;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    /*@Autowired
    private UserDetailsService userDetailsService;*/

	@Autowired
	private GatewayConfigure configure;  

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
    	String token = request.getHeader(configure.tokenHeader);
        if (token != null && token.startsWith(configure.tokenHead)) {
        	logger.debug("get token success");
            token = token.substring(configure.tokenHead.length()); // 获取token值
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(configure.secret)
                        .parseClaimsJws(token)
                        .getBody();
                String username = claims.getSubject();//从token中得到用户名
                List<String> authorities = new ArrayList<String>();//claims.get("authorities", List.class);
                if (username != null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
                            authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    
                	/**
                	 * 将解析出来的用户名称传到其他微服务，微服务可以获取该用户名进行相应的数据获取
                	 */
                    RequestContext requestContext = RequestContext.getCurrentContext();
                    requestContext.addZuulRequestHeader(configure.curUserName,username);
                } 
            } catch (Exception ignore) {
                SecurityContextHolder.clearContext();
            }
        }
        chain.doFilter(request, response);
    }
}
