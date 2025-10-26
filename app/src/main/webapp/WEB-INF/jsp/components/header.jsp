<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>
    <%=
      request.getAttribute("pageTitle") != null
        ? request.getAttribute("pageTitle")
        : "Untitled Page"
    %>
  </title>
  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table.css">
</head>
<body>
  <header>
    <h3>
      <%=
        request.getAttribute("username") != null
          ? request.getAttribute("username")
          : "ФИО студента"
      %>
    </h3>
    <h3>
      Группа
      <%=
        request.getAttribute("group") != null
          ? request.getAttribute("group")
          : "-"
      %>
    </h3>
    <h3>
      Вариант №
      <%=
        request.getAttribute("taskId") != null
          ? request.getAttribute("taskId")
          : "-"
      %>
    </h3>
  </header>
