package servlets.controllers

import jakarta.servlet.ServletException
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException

@WebServlet(name = "ControllerServlet", urlPatterns = ["/"])
class MainController : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val x = request.getParameter("x")
        val y = request.getParameter("y")
        val r = request.getParameter("r")
        var path = "/WEB-INF/jsp/index.jsp"

        if (x != null && y != null && r != null) {
            path = "/area-check"
            request.setAttribute("x", x)
            request.setAttribute("y", y)
            request.setAttribute("r", r)
        }
        request.getRequestDispatcher(path).forward(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        doGet(request, response)
    }
}