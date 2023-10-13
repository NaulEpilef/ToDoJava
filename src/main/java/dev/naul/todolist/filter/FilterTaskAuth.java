package dev.naul.todolist.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dev.naul.todolist.user.IUserRepository;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        var servletPath = request.getServletPath();

        if (servletPath.equals("/tasks")) {
            var auth = request.getHeader("Authorization");
            var authBase64 = auth.replace("Basic", "").trim();
            
            byte[] authDecoded = Base64.getDecoder().decode(authBase64);
            
            var authString = new String(authDecoded);
            
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];
            
            var user = this.userRepository.findByUsername(username);
            
            if (user == null) {
                response.sendError(401);
            } else {
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                
                if (passwordVerify.verified) {
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
