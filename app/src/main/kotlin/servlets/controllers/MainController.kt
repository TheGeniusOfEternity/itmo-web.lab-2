package servlets.controllers

import jakarta.servlet.ServletException
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException

@WebServlet(name = "ControllerServlet", urlPatterns = ["/main/*"])
class MainController : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val xValues = request.getParameterValues("x")
        val y = request.getParameter("y")
        val r = request.getParameter("r")
        var path = "/WEB-INF/jsp/index.jsp"

        if (xValues != null && y != null && r != null) path = "/area-check"

        request.setAttribute("username", "Сафин Максим Владиславович")
        request.setAttribute("group", "P3222")
        request.setAttribute("taskId", 56375)

        request.getRequestDispatcher(path).forward(request, response)
    }
}