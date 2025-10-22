<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
  <title>Результат проверки</title>
</head>
<body>
  <header>
    <h3>Сафин Максим Владиславович</h3>
    <h3>Группа P3222</h3>
    <h3>Вар №56375</h3>
  </header>
  <main>
    <h1>Params are
      <%= request.getAttribute("x") %>,
      <%= request.getAttribute("y") %>,
      <%= request.getAttribute("r") %>
    </h1>
    <a href="${pageContext.request.contextPath}/main/">Вернуться к форме</a>
  </main>
</body>
</html>
