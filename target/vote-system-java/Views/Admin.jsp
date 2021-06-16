<%@ page import="models.Question" %>
<%@ page import="java.util.List" %>
<%@ page import="api.QuestionApi" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.lang.reflect.Type" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/Views/Header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1>Add question</h1>

<input type="text" id="questionText" placeholder="question text"> <br>
<input type="text" id="variant1" placeholder="variant 1"> <br>
<input type="text" id="variant2" placeholder="variant 2"> <br>
<button id="addQuestionBtn">Add question</button>

<br><br>

<%
    Type itemsListType = new TypeToken<List<Question>>() {}.getType();
    List<Question> questionList = new Gson().fromJson(new QuestionApi().getAllQuestions(), itemsListType);

    for(Question question : questionList){
%>
    <p>Question: <%= question.getQuestion_text() %></p>
    <p>Variant 1: <%= question.getVariant1() %></p>
    <p>Variant 2: <%= question.getVariant2() %></p>
    <button class="deleteQuestionBtn" value="<%=question.getId()%>">Delete</button>
    <br>
<%
    }
%>

<script>
    let addQuestionBtn = document.getElementById("addQuestionBtn");
    let deleteQuestionButtons = document.getElementsByClassName("deleteQuestionBtn");

    for(let i = 0; i < deleteQuestionButtons.length; i++){
        deleteQuestionButtons[i].addEventListener("click", function(){
            let questionId = deleteQuestionButtons[i].value;
            $.ajax({
                type: "POST",
                url: "<%=request.getContextPath() %>/api/question/delete",
                dataType: "text/plain",
                contentType: "text/plain",
                data: String(questionId),
                success: function(result){
                    if(result['status'] === 200){
                        window.location.replace("<%=request.getContextPath() %>/adminServlet");
                    }else{
                        console.log("error");
                    }
                },
                error: function(result){
                    if(result['status'] === 200){
                        window.location.replace("<%=request.getContextPath() %>/adminServlet");
                    }else{
                        console.log("error");
                    }
                }
            });
        });
    }

    let addQuestion = () => {
        let question = document.getElementById("questionText").value;
        let variant1 = document.getElementById("variant1").value;
        let variant2 = document.getElementById("variant2").value;

        let data = {
            "questionText": question,
            "variant1": variant1,
            "variant2": variant2,
        }

        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath() %>/api/question/add",
            dataType: "text/plain",
            contentType: "text/plain",
            data: JSON.stringify(data),
            success: function(result){
                if(result['status'] === 200){
                    window.location.replace("<%=request.getContextPath() %>/adminServlet");
                }else{
                    console.log("error");
                }
            },
            error: function(result){
                if(result['status'] === 200){
                    window.location.replace("<%=request.getContextPath() %>/adminServlet");
                }else{
                    console.log("error");
                }
            }
        });
    }

    addQuestionBtn.addEventListener("click", addQuestion);
</script>

<%@include file="/Views/Footer.jsp"%>