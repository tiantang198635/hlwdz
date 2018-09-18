package com.ncbx.user.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ncbx.entity.User;
import com.ncbx.user.dao.UserDao;
import com.ncbx.user.service.AuthService;
import com.ncbx.util.JwtTokenUtil;
import com.ncbx.util.JwtUser;
import com.ncbx.util.JwtUserFactory;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthServiceImpl implements AuthService {

    //private AuthenticationManager authenticationManager;
    //private UserDetailsService userDetailsService;
	private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
	@Autowired
    private UserDao userDao;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

   /* @Autowired
    public AuthServiceImpl(
            //AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserDao userDao) {
        //this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDao = userDao;
    }*/

    @Override
    public User register(User userToAdd) {
        final String username = userToAdd.getName();
        if(userDao.findUserByName(username)!=null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPwd();
        userToAdd.setPwd(encoder.encode(rawPassword));
        return userDao.insert(userToAdd);
    }

    @Override
    public String login(String username, String password) {
        //UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        /*// Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
*/
        // Reload password post-security so we can generate token
        User user = userDao.findUserByName(username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //String encPwd = encoder.encode(password);
        if(user!=null&&(encoder.matches(password, user.getPwd()))){
        	Map<String, Object> claims = new HashMap<>();
            claims.put(JwtTokenUtil.CLAIM_KEY_USERNAME, username);
            claims.put(JwtTokenUtil.CLAIM_KEY_CREATED, new Date());
            
        	String token = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(jwtTokenUtil.generateExpirationDate())
                    .signWith(SignatureAlgorithm.HS256, jwtTokenUtil.getSecret())
                    .compact();
        	return token;
        	
        	/*SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;  
            
            long nowMillis = System.currentTimeMillis();  
            Date now = new Date(nowMillis);  
               
            //生成签名密钥  
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtTokenUtil.getSecret());  
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());  
               
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtTokenUtil.CLAIM_KEY_USERNAME, username);
            claims.put(JwtTokenUtil.CLAIM_KEY_CREATED, new Date());
            
              //添加构成JWT的参数  
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")  
                                             .setClaims(claims)
                                            .signWith(signatureAlgorithm, signingKey);  
             //添加Token过期时间  
            if (TTLMillis >= 0) {  
                long expMillis = nowMillis + TTLMillis;  
                Date exp = new Date(expMillis);  
                builder.setExpiration(exp).setNotBefore(now);  
            }  
               
             //生成JWT  
            return builder.compact();  */
        }else{
        	return null;
        }
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        //JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        User ouser = this.userDao.findUserByName(username);
        JwtUser user = JwtUserFactory.create(ouser);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}
