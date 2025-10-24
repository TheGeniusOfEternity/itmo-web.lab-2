<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title><%= request.getAttribute("pageTitle") %></title>
  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
  <header>
    <h3><%= request.getAttribute("username") %></h3>
    <h3>Группа <%= request.getAttribute("group") %></h3>
    <h3>Вариант №<%= request.getAttribute("taskId") %></h3>
  </header>
