package api;

import models.Question;
import models.User;
import models.Vote;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class VoteApi {
    private SessionFactory sessionFactory;
    private Session session;

    private void openSession(){
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    public VoteApi() {
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

    public List<Vote> getVariantVotes(int questionId, String variant){
        openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Vote> q1 = builder.createQuery(Vote.class);
        Root<Vote> root = q1.from(Vote.class);

        QuestionApi questionApi = new QuestionApi();
        Question question = questionApi.getQuestionById(questionId);

        Predicate predicateQuestionId = builder.equal(root.get("question"),  question);
        Predicate predicateVariant = builder.equal(root.get("variant"), variant);
        Predicate predicateSearch = builder.and(predicateQuestionId, predicateVariant);
        List<Vote> votesList = session.createQuery(q1.where(predicateSearch)).getResultList();
        session.getTransaction().commit();
        if(session.getTransaction().getStatus().equals(TransactionStatus.ACTIVE)) {
            session.getTransaction().commit();
        }
        return votesList;
    }

    public int getVariantVotesCount(int questionId, String variant){
        openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Vote> q1 = builder.createQuery(Vote.class);
        Root<Vote> root = q1.from(Vote.class);

        QuestionApi questionApi = new QuestionApi();
        Question question = questionApi.getQuestionById(questionId);

        Predicate predicateQuestionId = builder.equal(root.get("question"),  question);
        Predicate predicateVariant = builder.equal(root.get("variant"), variant);
        Predicate predicateSearch = builder.and(predicateQuestionId, predicateVariant);

        int fetchSize = 0;

        if(session.createQuery(q1.where(predicateSearch)).getResultList() != null){
            fetchSize = session.createQuery(q1.where(predicateSearch)).getResultList().size();
        }

        session.getTransaction().commit();
        if(session.getTransaction().getStatus().equals(TransactionStatus.ACTIVE)) {
            session.getTransaction().commit();
        }

        return fetchSize;
    }
}
