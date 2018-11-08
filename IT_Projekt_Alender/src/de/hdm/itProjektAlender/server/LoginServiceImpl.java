package de.hdm.itProjektAlender.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm.itProjektAlender.client.LoginInfo;
import de.hdm.itProjektAlender.shared.LoginService;

/**
 * Servlet, das den Login ueber die GoogleAccountsAPI verwaltet.
 */
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService{
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param requestUri
	 * @return loginInfo
	 */
	@Override
	public LoginInfo login(String requestUri) {
		
		
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		LoginInfo loginInfo = new LoginInfo();
		
		if(user != null){
			loginInfo.setLoggedIn(true);
			loginInfo.setEmailAddress(user.getEmail());
			loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
		} else{
			loginInfo.setLoggedIn(false);
			loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
		}
		return loginInfo;
	}

}
