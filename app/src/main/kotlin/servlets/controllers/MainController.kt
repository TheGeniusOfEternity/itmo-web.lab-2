package servlets.controllers

import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException

class MainController() : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        // Здесь можно добавить логику обработки запроса

        // Форвардим запрос на JSP-страницу, например "/jsp/index.jsp"
        request.setAttribute("message", "Hello from servlet")
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        doGet(request, response)
    }
}