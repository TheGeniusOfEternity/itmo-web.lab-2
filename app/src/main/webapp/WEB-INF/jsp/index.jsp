<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <title>Главная</title>
  </head>
  <body>
    <header>
      <h3>Сафин Максим Владиславович</h3>
      <h3>Группа P3222</h3>
      <h3>Вар №56375</h3>
    </header>
    <main>
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
      <div class="sidebar">
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
                <input id="y" min="-3" value="0" max="3" type="number" required name="y-input">
              </div>
              <p id="y-error" class="error-text"></p>
            </div>
            <div class="input-block">
              <div class="param-select">
                <label for="r">Значение R</label>
                <select required name="r-input" id="r">
                  <option value="1" selected>1</option>
                  <option value="2" selected>2</option>
                  <option value="3" selected>3</option>
                  <option value="4" selected>4</option>
                  <option value="5" selected>5</option>
                </select>
              </div>
              <p id="r-error" class="error-text"></p>
            </div>
            <input class="param-submit" type="submit" value="Отправить">
          </form>
        </div>
        <div class="table"></div>
      </div>
    </main>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
  </body>
</html>
