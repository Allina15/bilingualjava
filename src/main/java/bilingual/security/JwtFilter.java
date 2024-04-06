package bilingual.security;

import bilingual.entity.UserInfo;
import bilingual.repo.UserInfoRepo;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserInfoRepo authInfoRepo;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull @org.jetbrains.annotations.NotNull HttpServletResponse response,
            @NotNull @org.jetbrains.annotations.NotNull FilterChain filterChain) throws ServletException, IOException {
        final String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            if (StringUtils.hasText(token)) {
                try {
                    String username;
                    try {
                        username = jwtService.validateToken(token);
                    } catch (MalformedJwtException | JWTVerificationException e) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                        return;
                    }
                    String finalUsername = username;
                    UserInfo user = authInfoRepo.getAuthInfosByEmail(username)
                            .orElseThrow(() ->
                                    new EntityNotFoundException("User with email: %s not found".formatted(finalUsername)));
                    SecurityContextHolder.getContext()
                            .setAuthentication(
                                    new UsernamePasswordAuthenticationToken(
                                            user.getUsername(),
                                            null,
                                            user.getAuthorities()
                                    ));
                } catch (JWTVerificationException e) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                            "Invalid token");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}