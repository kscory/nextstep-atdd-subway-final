package nextstep.configuration.auth;

import lombok.RequiredArgsConstructor;
import nextstep.auth.domain.entity.LoginMember;
import nextstep.auth.domain.entity.TokenPrincipal;
import nextstep.auth.infrastructure.jwt.JwtTokenProvider;
import nextstep.base.exception.AuthenticationException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        AuthenticationPrincipal annotation = parameter.getParameterAnnotation(AuthenticationPrincipal.class);
        if (annotation == null) {
            throw new AuthenticationException();
        }

        String authorization = webRequest.getHeader("Authorization");
        if (authorization == null && !annotation.required()) {
            return null;
        }

        if (!"bearer".equalsIgnoreCase(authorization.split(" ")[0])) {
            throw new AuthenticationException();
        }
        String token = authorization.split(" ")[1];

        TokenPrincipal principal = jwtTokenProvider.getPrincipal(token);
        return new LoginMember(principal.getSubject(), principal.getEmail(), principal.getAge());
    }
}
