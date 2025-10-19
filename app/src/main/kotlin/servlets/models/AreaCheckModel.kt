package servlets.models

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.PrintWriter
import kotlin.math.pow


@WebServlet(name = "AreaCheckServlet", urlPatterns = ["/area-check"])
class AreaCheckModel: HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        fun validate(x: Float?, y: Float?, r: Float?): Boolean? {
            if (x == null || y == null || r == null) return null
            if (x > 0 && y > 0) return (x.pow(2) + y.pow(2)) <= r.pow(2) / 4
            if (x <= 0 && y >= 0) return (x + y) <= r
            if (x >= 0 && y <= 0) return x <= r/2 && y >= -r
            return false
        }

        fun sendHtml(x: Float?, y: Float?, r: Float?, isHit: Boolean?) {
            response.contentType = "text/html;charset=UTF-8"
            val out: PrintWriter = response.writer

            val contextPath: String = request.contextPath
            val html = """
                <html>
                    <head>
                        <link rel="stylesheet" type="text/css" href="$contextPath/styles/style.css">
                        <title>My Page</title>
                    </head>
                    <body>
                        <h1>Check result</h1>
                        <div>
                            <span>x: ${x ?: "Параметр не был получен"}</span>
                            <span>y: ${y ?: "Параметр не был получен"}</span>
                            <span>r: ${r ?: "Параметр не был получен"}</span>
                            <h4>Результат: 
                            ${
                                when (isHit) {
                                    null -> "Неверный формат данных"
                                    false -> "Промах"
                                    else -> "Попадание"
                                }
                            }
                            </h4>
                        </div>
                    </body>
                </html>
            """.trimIndent()
            out.println(html)
        }


        val x = request.getParameter("x")?.toFloatOrNull()
        val y = request.getParameter("y")?.toFloatOrNull()
        val r = request.getParameter("r")?.toFloatOrNull()
        val isHit = validate(x, y, r)

        sendHtml(x, y, r, isHit)
    }
}