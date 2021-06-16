<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/Views/Header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1>Edit profile</h1>

<%
    final String currentUserFullName = (String) request.getAttribute ("currentUserFullName");
    final String currentUserLogin = (String) request.getAttribute ("currentUserLogin");
    final String currentUserPassword = (String) request.getAttribute ("currentUserPassword");
    final String currentUserGroupName = (String) request.getAttribute ("currentUserGroupName");
%>

<p id="message"></p>

<p>Full name:</p>
<input type="text" id="fullName" value="<%= currentUserFullName %>"> <br>

<p>Login:</p>
<input type="text" id="login" value="<%= currentUserLogin %>"> <br>

<p>Password:</p>
<input type="password" id="password" value="<%= currentUserPassword %>"> <br>

<p>Group name:</p>
<input type="text" id="groupName" value="<%= currentUserGroupName %>"> <br>

<button id="editBtn">Edit</button>

<script>
    let fullNameInput = document.getElementById("fullName");
    let loginInput = document.getElementById("login");
    let passwordInput = document.getElementById("password");
    let groupNameInput = document.getElementById("groupName");
    let editBtn = document.getElementById("editBtn");

    let editButtonPressed = () => {
        let userData = {
            "fullName": fullNameInput.value,
            "login": loginInput.value,
            "password": passwordInput.value,
            "groupName": groupNameInput.value,
        };

        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/api/account/update",
            async: true,
            dataType: "text/plain",
            contentType: "text/plain",
            data: JSON.stringify(userData),
            success: function(result){
                if(result['status'] === 200){
                    let paragraph = document.getElementById("message");
                    paragraph.innerText = "User info updated successfully!";
                }else{
                    $("#message").append("User not found!");
                }
            },
            error: function(result){
                if(result['status'] === 200){
                    let paragraph = document.getElementById("message");
                    paragraph.innerText = "User info updated successfully!";
                }else{
                    $("#message").append("User not found!");
                }
            }
        });
    }

    editBtn.addEventListener("click", editButtonPressed);
</script>

<%@include file="/Views/Footer.jsp"%>