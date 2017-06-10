package com.viglet.vecchio.security.jetty;

import java.security.Principal;

import javax.security.auth.Subject;
import javax.servlet.ServletRequest;

import org.eclipse.jetty.security.DefaultIdentityService;
import org.eclipse.jetty.security.DefaultUserIdentity;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.component.AbstractLifeCycle;


public class VecLoginService extends AbstractLifeCycle implements LoginService {
    
    private String name;
    protected IdentityService identityService = new DefaultIdentityService();

    /**
     * Constructor.
     */
    public VecLoginService() {
    }

    /**
     * @see org.eclipse.jetty.security.LoginService#getName()
     */
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        System.out.println("Setting MyLoginService name to: " + name);
        this.name = name;
    }

    /**
     * @see org.eclipse.jetty.security.LoginService#login(java.lang.String, java.lang.Object)
     */
    
    @Override
	public UserIdentity login(String username, Object credentials, ServletRequest request) {
    	 System.out.println("Logging in user: " + username);
         Subject subject = new Subject();
         Principal principal = new Principal() {
             @Override
             public String getName() {
                 return username;
             }
         };
         subject.getPrincipals().add(principal);
         DefaultUserIdentity identity = new DefaultUserIdentity(subject, principal, new String[] { "user", "manager", "admin" });
         return identity;
	}
      

    /**
     * @see org.eclipse.jetty.security.LoginService#validate(org.eclipse.jetty.server.UserIdentity)
     */
    @Override
    public boolean validate(UserIdentity user) {
        return true;
    }

    /**
     * @see org.eclipse.jetty.security.LoginService#getIdentityService()
     */
    @Override
    public IdentityService getIdentityService() {
        return identityService;
    }

    /**
     * @see org.eclipse.jetty.security.LoginService#setIdentityService(org.eclipse.jetty.security.IdentityService)
     */
    @Override
    public void setIdentityService(IdentityService service) {
        this.identityService = service;
    }

    /**
     * @see org.eclipse.jetty.security.LoginService#logout(org.eclipse.jetty.server.UserIdentity)
     */
    @Override
    public void logout(UserIdentity user) {
        System.out.println("Logging out user: " + user.getUserPrincipal().getName());
    }

	

}