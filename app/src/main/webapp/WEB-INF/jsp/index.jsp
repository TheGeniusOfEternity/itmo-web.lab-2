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
      <div class="canvas">

      </div>
      <div class="sidebar">
        <div class="form">
          <form id="form">
            <div class="input-block">
              <h4>Значение Х</h4>
              <div class="param-group">
                <div class="param-checkbox">
                  <input value="-2" id="x-2" type="checkbox" name="x-input">
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
                <input id="y" min="-3" max="3" type="number" required name="y-input">
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
