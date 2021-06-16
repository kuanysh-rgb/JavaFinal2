package api;

import com.google.gson.Gson;
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
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("question")
public class QuestionApi {
    private SessionFactory sessionFactory;
    private Session session;

    private void openSession(){
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    public QuestionApi() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Question.class)
                .addAnnotatedClass(Vote.class)
                .addAnnotatedClass(User.class)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect")
                .setProperty("hibernate.connection.datasource", "java:/comp/env/jdbc/vote_system_db")
                .setProperty("hibernate.order_updates", "true")
                .setProperty("show_sql", "true");
        sessionFactory = configuration.buildSessionFactory();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllQuestions(){
        openSession();
        List<Question> questionList = session.createQuery("SELECT a FROM QuestionEntity a", Question.class).getResultList();
        session.close();
        return new Gson().toJson(questionList);
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addQuestion(String json){
        QuestionModel model = new Gson().fromJson(json, QuestionModel.class);
        Question question = new Question();
        question.setQuestion_text(model.getQuestionText());
        question.setVariant1(model.getVariant1());
        question.setVariant2(model.getVariant2());
        try
        {
            openSession();
            session.persist(question);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }

        return Response.ok(model).build();
    }

    @POST
    @Path("/delete")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteQuestion(String id){
        int questionId = Integer.parseInt(id);
        Question question = getQuestionById(questionId);
        try
        {
            openSession();
            session.delete(question);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }

        return Response.ok(question).build();
    }

    @POST
    @Path("/vote")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response vote(String json){
        VoteModel model = new Gson().fromJson(json, VoteModel.class);

        Question question = getQuestionById(Integer.parseInt(model.getQuestionId()));

        Vote vote = new Vote();
        vote.setQuestion(question);
        vote.setUser(UsersController.getInstance().getCurrentUser());
        vote.setVariant(model.getVariant());

        try
        {
            openSession();
            session.save(vote);
            session.getTransaction().commit();
        }
        finally
        {
            session.close();
        }

        return Response.ok(question).build();
    }

    public Question getQuestionById(int id){
        openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Question> q1 = builder.createQuery(Question.class);
        Root<Question> root = q1.from(Question.class);

        Predicate predicateId = builder.equal(root.get("id"), id);
        Predicate predicateSearch = builder.and(predicateId);
        Question question = session.createQuery(q1.where(predicateSearch)).getSingleResult();
        System.out.println("Question found: " + question.getQuestion_text());
        session.getTransaction().commit();
        if(session.getTransaction().getStatus().equals(TransactionStatus.ACTIVE)) {
            session.getTransaction().commit();
        }
        return question;
    }
}
