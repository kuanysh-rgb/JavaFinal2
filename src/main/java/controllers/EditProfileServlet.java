package controllers;

import models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = UsersController.getInstance().getCurrentUser();
        req.setAttribute("currentUserFullName", currentUser.getFullName());
        req.setAttribute("currentUserLogin", currentUser.getLogin());
        req.setAttribute("currentUserPassword", currentUser.getPassword());
        req.setAttribute("currentUserGroupName", currentUser.getGroupName());
        req.getRequestDispatcher("/Views/EditProfile.jsp").forward(req, resp);
    }
}
