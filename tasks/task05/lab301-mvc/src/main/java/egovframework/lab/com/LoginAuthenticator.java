package egovframework.lab.com;

import javax.naming.AuthenticationException;

import org.springframework.stereotype.Component;

@Component("LoginAuthenticator")
public class LoginAuthenticator implements Authenticator {

	public boolean authenticate(String id, String password) throws AuthenticationException {
		return true;
	}
}
