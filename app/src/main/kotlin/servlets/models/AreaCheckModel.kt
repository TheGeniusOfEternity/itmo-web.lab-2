package servlets.models

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import java.io.PrintWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.pow
data class ShotResult(
    val x: Float?,
    val y: Float?,
    val r: Float?,
    val isHit: Boolean?,
    val timestamp: LocalDateTime = LocalDateTime.now()
) {
    fun getFormattedTimestamp(): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        return timestamp.format(formatter)
    }
}

@WebServlet(name = "AreaCheckServlet", urlPatterns = ["/area-check"])
class AreaCheckModel: HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        fun validate(x: Float?, y: Float?, r: Float?): Boolean? {
            if (x == null || y == null || r == null) return null
            if (x > 0 && y > 0) return (x.pow(2) + y.pow(2)) <= r.pow(2) / 4
            if (x <= 0 && y >= 0) return (y <= x + r) && (x >= -r) && (y <= r)
            if (x >= 0 && y <= 0) return x <= r/2 && y >= -r
            return false
        }

        fun sendHtml(x: Float?, y: Float?, r: Float?, isHit: Boolean?) {
            response.contentType = "text/html;charset=UTF-8"
            val out: PrintWriter = response.writer
            val contextPath: String = request.contextPath
            val html = """
                <!DOCTYPE html>
                <html>
                    <head>
                        <link rel="stylesheet" type="text/css" href="${contextPath}/css/main.css">
                        <link rel="stylesheet" type="text/css" href="${contextPath}/css/result.css">
                        <title>Результат</title>
                    </head>
                    <body>
                        <header>
                           <h3>${request.getAttribute("username")}</h3>
                           <h3>Группа ${request.getAttribute("group")}</h3>
                           <h3>Вариант №${request.getAttribute("taskId")}</h3>
                         </header>
                        <main class="fade-in">
                            <div>
                                <h2>Результат проверки</h2>
                            </div>
                            <div>
                                <span>x</span>
                                <span>y</span>
                                <span>r</span>
                                <span>Статус</span>
                                <span>${x ?: "Параметр не был получен"}</span>
                                <span>${y ?: "Параметр не был получен"}</span>
                                <span>${r ?: "Параметр не был получен"}</span>
                                <span> 
                                ${
                                    when (isHit) {
                                        null -> "Неверный формат данных"
                                        false -> "Промах"
                                        else -> "Попадание"
                                    }
                                }
                                </span>
                            </div>
                            <div>
                                <a href="$contextPath/main">Назад</a>
                            </div>
                        </main>
                    </body>
                </html>
            """.trimIndent()
            out.println(html)
        }


        val x = request.getParameter("x")?.toFloatOrNull()
        val y = request.getParameter("y")?.toFloatOrNull()
        val r = request.getParameter("r")?.toFloatOrNull()
        val isHit = validate(x, y, r)
        saveResult(request.session, x, y, r, isHit)
        sendHtml(x, y, r, isHit)
    }

    private fun saveResult(session: HttpSession, x: Float?, y: Float?, r: Float?, isHit: Boolean?) {
        val result = ShotResult(x, y, r, isHit, LocalDateTime.now())
        val sessionId = session.id

        val context = servletContext
        @Suppress("UNCHECKED_CAST")
        var allUsersResults = context.getAttribute("allUsersShotResults") as? MutableMap<String, MutableList<ShotResult>>

        if (allUsersResults == null) {
            allUsersResults = mutableMapOf()
            context.setAttribute("allUsersShotResults", allUsersResults)
        }

        var userResults = allUsersResults[sessionId]
        if (userResults == null) {
            userResults = mutableListOf()
            allUsersResults[sessionId] = userResults
        }

        userResults.add(result)

        session.setAttribute("userSessionId", sessionId)
    }
}