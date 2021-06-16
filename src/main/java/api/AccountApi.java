package api;

import com.google.gson.Gson;
import controllers.AuthorizationManager;
import controllers.UsersController;
import models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("account")
public class AccountApi {
    private SessionFactory sessionFactory;
    private Session session;

    private void openSession(){
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    public AccountApi() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Vote.class)
                .addAnnotatedClass(Question.class)
                .addAnnotatedClass(User.class)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect")
                .setProperty("hibernate.connection.datasource", "java:/comp/env/jdbc/vote_system_db")
                .setProperty("hibernate.order_updates", "true")
                .setProperty("show_sql", "true");
        sessionFactory = configuration.buildSessionFactory();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addAccount(User user){
        try
        {
            openSession();
            session.persist(user);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }

        return "";
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String json){
        LoginModel login = new Gson().fromJson(json, LoginModel.class);
        System.out.println("LOGIN: " + login.getLogin() + " " + login.getPassword());
        User account;
        try
        {
            openSession();
            account = getAccountByLoginAndPassword(login.getLogin(), login.getPassword());
            if(session.getTransaction().getStatus().equals(TransactionStatus.ACTIVE)) {
                session.getTransaction().commit();
            }
        }
        finally
        {
            session.close();
        }

        String response = new Gson().toJson("error");

        if(!account.getFullName().equals("")){
            response = new Gson().toJson(account);
            AuthorizationManager.getInstance().saveToSession(account);
            UsersController.getInstance().setCurrentUser(account);
        }

        System.out.println("Response: " + response);

        return Response.ok(response).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(String json){
        UpdateUserModel updateUserModel = new Gson().fromJson(json, UpdateUserModel.class);
        System.out.println("CHANGE LOGIN: " + updateUserModel.getLogin());
        try
        {
            openSession();

            UsersController.getInstance().getCurrentUser().setFullName(updateUserModel.getFullName());
            UsersController.getInstance().getCurrentUser().setLogin(updateUserModel.getLogin());
            UsersController.getInstance().getCurrentUser().setPassword(updateUserModel.getPassword());
            UsersController.getInstance().getCurrentUser().setGroupName(updateUserModel.getGroupName());

            session.update(UsersController.getInstance().getCurrentUser());

            if(session.getTransaction().getStatus().equals(TransactionStatus.ACTIVE)) {
                session.getTransaction().commit();
            }
        }
        finally
        {
            session.close();
        }

        return Response.status(Response.Status.OK).build();
    }

    private User getAccountByLoginAndPassword(String phoneNumber, String getPassword){
        openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> q1 = builder.createQuery(User.class);
        Root<User> root = q1.from(User.class);

        Predicate predicateLogin = builder.equal(root.get("login"), phoneNumber);
        Predicate predicatePassword = builder.equal(root.get("password"), getPassword);
        Predicate predicateSearch = builder.and(predicateLogin, predicatePassword);
        User account = session.createQuery(q1.where(predicateSearch)).getSingleResult();
        System.out.println("Account found: " + account.getFullName());
        session.getTransaction().commit();
        if(session.getTransaction().getStatus().equals(TransactionStatus.ACTIVE)) {
            session.getTransaction().commit();
        }
        return account;
    }
}
