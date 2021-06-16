<%@ page import="java.lang.reflect.Type" %>
<%@ page import="models.Question" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="api.QuestionApi" %>
<%@ page import="api.VoteApi" %>
<%@ page import="models.Vote" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/Views/Header.jsp"%>

<%
    Type itemsListType = new TypeToken<List<Question>>() {}.getType();
    List<Question> questionList = new Gson().fromJson(new QuestionApi().getAllQuestions(), itemsListType);

    VoteApi voteApi = new VoteApi();

    for(Question question : questionList){
%>
        <p>Question: <%= question.getQuestion_text() %></p>
        <p>Voted for "<%= question.getVariant1() %>:"</p>
        <ol>
<%
        List<Vote> variant1Votes = voteApi.getVariantVotes(question.getId(), question.getVariant1());
        for(Vote vote : variant1Votes){
%>
            <li> <%= vote.getUser().getFullName() %> [<%= vote.getUser().getGroupName() %>]</li>
<%
        }
%>
        </ol>

        <p>Voted for "<%= question.getVariant2() %>:"</p>
        <ol>
<%
        List<Vote> variant2Votes = voteApi.getVariantVotes(question.getId(), question.getVariant2());
        for(Vote vote : variant2Votes){
%>
            <li> <%= vote.getUser().getFullName() %> [<%= vote.getUser().getGroupName() %>]</li>
<%
        }
%>
</ol>

<br>
<%
    }
%>

<%@include file="/Views/Footer.jsp"%>