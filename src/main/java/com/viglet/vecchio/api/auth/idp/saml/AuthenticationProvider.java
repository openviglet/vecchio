package com.viglet.vecchio.api.auth.idp.saml;

import com.viglet.vecchio.api.auth.idp.IdpConfiguration;

import java.util.Arrays;

import static com.viglet.vecchio.api.auth.idp.AuthenticationMethod.ALL;

public class AuthenticationProvider {

  private final IdpConfiguration idpConfiguration;

  public AuthenticationProvider(IdpConfiguration idpConfiguration) {
    this.idpConfiguration = idpConfiguration;
  }

  /*@Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (idpConfiguration.getAuthenticationMethod().equals(ALL)) {
      return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), Arrays.asList(
        new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER")
      ));
    } else {
      return idpConfiguration.getUsers().stream()
        .filter(token ->
          token.getPrincipal().equals(authentication.getPrincipal()) &&
            token.getCredentials().equals(authentication.getCredentials()))
        .findFirst().map(usernamePasswordAuthenticationToken -> new UsernamePasswordAuthenticationToken(
          //need top copy or else credentials are erased for future logins
          usernamePasswordAuthenticationToken.getPrincipal(),
          usernamePasswordAuthenticationToken.getCredentials(),
          usernamePasswordAuthenticationToken.getAuthorities()
        ))
        .orElseThrow(() -> new AuthenticationException("User not found or bad credentials") {
        });
    }
  }*/

 /* @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }*/
}
