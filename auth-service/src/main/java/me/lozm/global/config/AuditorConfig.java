package me.lozm.global.config;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.lozm.code.AuditorCode;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.Optional;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditorConfig implements AuditorAware<Long> {

    private final Environment environment;


    @Override
    public Optional<Long> getCurrentAuditor() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        Jwt jwt = getJwt((ServletRequestAttributes) requestAttributes);
        if (isEmpty(jwt)) {
            return Optional.of(AuditorCode.SYSTEM.getCode());
        }

        Map<String, Object> jwtBody = (Map<String, Object>) jwt.getBody();
        return Optional.ofNullable(Long.valueOf((String) jwtBody.get("sub")));
    }

    private Jwt getJwt(ServletRequestAttributes requestAttributes) {
        try {
            String jwt = requestAttributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer", "");
            Jwt parsedJwt = Jwts.parser().setSigningKey(environment.getProperty("jwt-token.secret-key")).parse(jwt);
            return parsedJwt;
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

}
