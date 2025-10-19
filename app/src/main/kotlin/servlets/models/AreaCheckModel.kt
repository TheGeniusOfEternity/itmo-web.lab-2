package servlets.models

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@WebServlet(name = "AreaCheckServlet", urlPatterns = ["/area-check"])
class AreaCheckModel: HttpServlet() {
    override fun doGet(request: HttpServletRequest?, response: HttpServletResponse?) {
        val x = (request?.getParameter("x") as String).toFloat()
        val y = (request.getParameter("y") as String).toFloat()
        val r = (request.getParameter("r") as String).toFloat()

        request.setAttribute("message", "HI from model servlet, params: $x, $y, $r")
        request.getRequestDispatcher( "/WEB-INF/jsp/result.jsp").forward(request, response)
    }
}