<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Test JSP</title>
  </head>
  <body>
    <h1>Hello from JSP!</h1>
    <h1><%= request.getAttribute("message") %></h1>
    <p>
  </body>
</html>
