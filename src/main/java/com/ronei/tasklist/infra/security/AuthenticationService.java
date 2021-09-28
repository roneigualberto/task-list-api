package com.ronei.tasklist.infra.security;

import com.ronei.tasklist.application.rest.request.CredentialRequest;
import com.ronei.tasklist.application.rest.response.TokenResponse;
import com.ronei.tasklist.domain.user.User;
import com.ronei.tasklist.domain.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService implements UserDetailsService {


    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new UserIdentity(user);
    }

    public TokenResponse getToken(CredentialRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        UserIdentity identity = (UserIdentity) authentication.getPrincipal();
        return tokenService.generateToken(identity);

    }

    public void authenticate(String token) {

        boolean validToken = tokenService.isValidToken(token);

        if (validToken)  {
            UserIdentity identity = getIdentity(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(identity, null, identity.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

    }

    private UserIdentity getIdentity(String token) {
        Long userId = tokenService.getUserId(token);
        User user = userService.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        return new UserIdentity(user);
    }
}
