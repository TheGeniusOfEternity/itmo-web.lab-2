<%@ page import="servlets.models.ShotResult" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="eternal" uri="/WEB-INF/data.tld" %>
<%@ include file="components/header.jsp"%>
    <main>
      <div class="table">
        <%
          request.setAttribute("pageTitle", "Главная");
          // Получаем ID сессии текущего пользователя
          String userSessionId = (String) session.getAttribute("userSessionId");
          Map<String, List<ShotResult>> allUsersResults =
                  (Map<String, List<ShotResult>>) application.getAttribute("allUsersShotResults");

          List<ShotResult> userResults = null;
          if (userSessionId != null && allUsersResults != null) {
            userResults = allUsersResults.get(userSessionId);
          }
        %>
        <eternal:csv-data-table id="users" separator=";" sortable="true" striped="true" pageSize="2">
          Имя;Возраст;Город;Зарплата
          Иван Петров;25;Москва;80000
          Мария Сидорова;30;Санкт-Петербург;95000
          Петр Иванов;28;Казань;70000
          Иван Петров;25;Москва;80000
          Мария Сидорова;30;Санкт-Петербург;95000
          Петр Иванов;28;Казань;70000
        </eternal:csv-data-table>
      </div>
      <div class="image">
        <svg viewBox="0 0 300 300" id="svg-graph">
          <polygon points="30,150 150,150 150,30" fill="#b8c0ff"></polygon>
          <rect height="120" width="60" x="150" y="150" fill="#b8c0ff"></rect>
          <path d="M 150 150
                L 150 90
                A 60 60 0 0 1 210 150
                Z"
                fill="#b8c0ff">
          </path>

          <!-- Оси -->
          <line x1="0" y1="150" x2="300" y2="150" stroke="black"></line>
          <line x1="150" y1="0" x2="150" y2="300" stroke="black"></line>

          <!-- Стрелки-->
          <polygon class="arrow" points="150,0 144,15 150,10 156,15"></polygon>
          <polygon class="arrow" points="300,150 285,156 290,150 285,144"></polygon>

          <!-- Насечки по Y -->
          <line x1="145" x2="155" y1="30" y2="30" stroke="black"></line>
          <line x1="145" x2="155" y1="90" y2="90" stroke="black"></line>
          <line x1="145" x2="155" y1="210" y2="210" stroke="black"></line>
          <line x1="145" x2="155" y1="270" y2="270" stroke="black"></line>

          <!-- Насечки по X -->
          <line y1="145" y2="155" x1="30" x2="30" stroke="black"></line>
          <line y1="145" y2="155" x1="90" x2="90" stroke="black"></line>
          <line y1="145" y2="155" x1="210" x2="210" stroke="black"></line>
          <line y1="145" y2="155" x1="270" x2="270" stroke="black"></line>

          <!-- Подписи осей -->
          <text x="290" y="140" font-size="16" fill="black">x</text>
          <text x="160" y="10" font-size="16" fill="black">y</text>

          <!-- Подписи R по Y -->
          <text x="160" y="35" font-size="16" fill="black">R</text>
          <text x="160" y="95" font-size="16" fill="black">R/2</text>
          <text x="160" y="215" font-size="16" fill="black">-R/2</text>
          <text x="160" y="275" font-size="16" fill="black">-R</text>

          <!-- Подписи R по X -->
          <text y="140" x="20" font-size="16" fill="black">-R</text>
          <text y="140" x="75" font-size="16" fill="black">-R/2</text>
          <text y="140" x="200" font-size="16" fill="black">R/2</text>
          <text y="140" x="265" font-size="16" fill="black">R</text>
        </svg>
      </div>
      <div class="form">
        <form id="form">
          <div class="input-block">
            <h4>Значение Х</h4>
            <div class="param-group">
              <div class="param-checkbox">
                <input value="-2" id="x-2" type="checkbox" name="x-input" checked>
                <label for="x-2">-2</label>
              </div>
              <div class="param-checkbox">
                <input value="-1.5" id="x-1.5" type="checkbox" name="x-input">
                <label for="x-1.5">-1.5</label>
              </div>
              <div class="param-checkbox">
                <input value="-1" id="x-1" type="checkbox" name="x-input">
                <label for="x-1">-1</label>
              </div>
              <div class="param-checkbox">
                <input value="-0.5" id="x-0.5" type="checkbox" name="x-input">
                <label for="x-0.5">-0.5</label>
              </div>
              <div class="param-checkbox">
                <input value="0" id="x0" type="checkbox" name="x-input">
                <label for="x0">0</label>
              </div>
              <div class="param-checkbox">
                <input value="0.5" id="x0.5" type="checkbox" name="x-input">
                <label for="x0.5">0.5</label>
              </div>
              <div class="param-checkbox">
                <input value="1" id="x1" type="checkbox" name="x-input">
                <label for="x1">1</label>
              </div>
              <div class="param-checkbox">
                <input value="1.5" id="x1.5" type="checkbox" name="x-input">
                <label for="x1.5">1.5</label>
              </div>
              <div class="param-checkbox">
                <input value="2" id="x2" type="checkbox" name="x-input">
                <label for="x2">2</label>
              </div>
            </div>
            <p id="x-error" class="error-text"></p>
          </div>
          <div class="input-block">
            <div class="param-text">
              <label for="y">Значение Y</label>
              <input id="y" value="0" required name="y-input">
            </div>
            <p id="y-error" class="error-text"></p>
          </div>
          <div class="input-block">
            <div class="param-select">
              <label for="r">Значение R</label>
              <div class="wrapper">
                <?xml version="1.0" encoding="utf-8"?>
                <svg width="800px" height="800px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path fill-rule="evenodd" clip-rule="evenodd" d="M12.7071 4.29289C12.3166 3.90237 11.6834 3.90237 11.2929 4.29289L7.29289 8.29289C6.90237 8.68342 6.90237 9.31658 7.29289 9.70711C7.68342 10.0976 8.31658 10.0976 8.70711 9.70711L12 6.41421L15.2929 9.70711C15.6834 10.0976 16.3166 10.0976 16.7071 9.70711C17.0976 9.31658 17.0976 8.68342 16.7071 8.29289L12.7071 4.29289ZM7.29289 15.7071L11.2929 19.7071C11.6834 20.0976 12.3166 20.0976 12.7071 19.7071L16.7071 15.7071C17.0976 15.3166 17.0976 14.6834 16.7071 14.2929C16.3166 13.9024 15.6834 13.9024 15.2929 14.2929L12 17.5858L8.70711 14.2929C8.31658 13.9024 7.68342 13.9024 7.29289 14.2929C6.90237 14.6834 6.90237 15.3166 7.29289 15.7071Z" fill="#000000"></path>
                </svg>
                <select required name="r-input" id="r">
                  <option value="1" selected>1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                  <option value="4">4</option>
                  <option value="5">5</option>
                </select>
              </div>
            </div>
            <p id="r-error" class="error-text"></p>
          </div>
          <input class="param-submit" type="submit" value="Отправить">
        </form>
      </div>
    </main>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
  </body>
</html>