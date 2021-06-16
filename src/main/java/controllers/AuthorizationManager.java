package controllers;

import models.User;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationManager {
    private static AuthorizationManager instance;
    private static HttpServletRequest req;

    public void setReq(HttpServletRequest req) {
        AuthorizationManager.req = req;
    }

    public static AuthorizationManager getInstance() {
        if(instance == null){
            return new AuthorizationManager();
        }
        return instance;
    }

    public void saveToSession(User user){
        req.getSession().setAttribute("currentAccountFullName", user.getFullName());
        req.getSession().setAttribute("currentAccountLogin", user.getLogin());
        req.getSession().setAttribute("currentAccountGroupName", user.getGroupName());
        req.getSession().setMaxInactiveInterval(-1);
    }

    public void deleteFromSession(){
        req.getSession().removeAttribute("currentAccountFullName");
        req.getSession().removeAttribute("currentAccountLogin");
        req.getSession().removeAttribute("currentAccountGroupName");
    }
}
