package servlets.controllers

import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException

class MainController : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val x: String? = request.getParameter("x")
        val y: String? = request.getParameter("y")
        val r: String? = request.getParameter("r")
        val path: String

        if (x == null || y == null || r == null) {
            path = "/WEB-INF/jsp/index.jsp"
        } else {
            request.setAttribute("x", x)
            request.setAttribute("y", y)
            request.setAttribute("r", r)
            path = "/AreaCheckModel"
        }
        request.getRequestDispatcher(path).forward(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        doGet(request, response)
    }
}