<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Vote system</title>
    <script
            src="https://code.jquery.com/jquery-3.5.1.min.js"
            integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.css">
</head>

<style>
    header{
        width: 100%;
        height: 120px;
    }

    ul{
        width: 100%;
        text-align: center;
        list-style: none;
    }

    li{
        display: inline-block;
        margin-left: 20px;
        margin-right: 20px;
        margin-top: 10px;
    }

    a{
        text-decoration: none;
    }
</style>

<body>
    <header>
        <ul>
            <li>
                <a href="<%=request.getContextPath() %>/adminServlet">Admin panel</a>
            </li>
            <li>
                <a href="<%=request.getContextPath() %>/homeServlet">Home page</a>
            </li>
            <li>
                <a href="<%=request.getContextPath() %>/statisticsServlet">Statistics</a>
            </li>
            <li>
                <a href="<%=request.getContextPath() %>/editProfile">Edit profile</a>
            </li>
        </ul>
    </header>