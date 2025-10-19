<%--
  Created by IntelliJ IDEA.
  User: maksim
  Date: 19.10.2025
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
  <h1>Params are
    <%= request.getAttribute("x") %>,
    <%= request.getAttribute("y") %>,
    <%= request.getAttribute("r") %>
  </h1>
</body>
</html>
