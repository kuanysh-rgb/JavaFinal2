<%@ page import="models.Question" %>
<%@ page import="api.QuestionApi" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.reflect.Type" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<%@ page import="api.VoteApi" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/Views/Header.jsp"%>

<style>
    .vote-btn{
        width: 100px;
        height: 30px;
        text-align: center;
        background-color: white;
        border: 1px solid black;
        color: black;
        margin: 20px;
    }

    .question-text{
        margin-left: 20px;
        margin-top: 10px;
    }

    .question-block{
        text-align: center;
        border: 1px solid black;
        width: 400px;
        margin-bottom: 20px;
    }
</style>

<center>
<%
    Type itemsListType = new TypeToken<List<Question>>() {}.getType();
    List<Question> questionList = new Gson().fromJson(new QuestionApi().getAllQuestions(), itemsListType);

    VoteApi voteApi = new VoteApi();

    for(Question question : questionList){
%>
        <div class="question-block">
            <p class="question-text">Question: <%= question.getQuestion_text() %></p>
            <button class="vote-btn" onclick="vote('<%= question.getId() %>', '<%= question.getVariant1() %>')"><%= question.getVariant1() %></button>
            <%= voteApi.getVariantVotesCount(question.getId(), question.getVariant1()) %> votes
            <br>

            <button class="vote-btn" onclick="vote('<%= question.getId() %>', '<%= question.getVariant2() %>')"><%= question.getVariant2() %></button>
            <%= voteApi.getVariantVotesCount(question.getId(), question.getVariant2()) %> votes
            <br>
        </div>
<%
    }
%>
</center>
<script>
    let vote = (questionId, variant) => {
        let vote = {
            "questionId": questionId,
            "variant": variant
        };

        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath() %>/api/question/vote",
            dataType: "text/plain",
            contentType: "text/plain",
            data: JSON.stringify(vote),
            success: function(result){
                if(result['status'] === 200){
                    window.location.replace("<%=request.getContextPath() %>/homeServlet");
                }else{
                    console.log("error");
                }
            },
            error: function(result){
                if(result['status'] === 200){
                    window.location.replace("<%=request.getContextPath() %>/homeServlet");
                }else{
                    console.log("error");
                }
            }
        });
    }
</script>


<%@include file="/Views/Footer.jsp"%>