package com.amak.app.api.annotation.profile;

import com.amak.app.application.dto.auth.ProfileResponse;
import com.amak.app.infrastructure.jpa.RoleRepository;
import com.amak.app.infrastructure.jpa.UserRepository;
import com.amak.app.shared.constants.Error;
import com.amak.app.shared.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Profile.class) &&
                parameter.getParameterType().equals(ProfileResponse.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof Jwt jwt)) {
            return null;
        }

        String username = jwt.getSubject();

        ProfileResponse existingUser = userRepository.findProfileByUsernameOrEmail(username).orElseThrow(
                () -> new CustomException(Error.TOKEN_INVALID)
        );

        List<String> roles = roleRepository.findRoleForAccount(existingUser.getId());

        existingUser.setRoles(roles);
        return existingUser;
    }
}
