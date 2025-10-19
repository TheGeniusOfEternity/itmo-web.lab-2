package filters

import jakarta.servlet.*
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException

@WebFilter(filterName = "internalAccessFilter", urlPatterns = ["/*"])
class InternalServletAccessFilter: Filter {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest
        val resp = response as HttpServletResponse

        val path = req.requestURI
        val query = req.queryString
        val isRootWithParams = path == "/" && (query == null || query.isNotEmpty())

        if (!isRootWithParams &&
            req.getAttribute("javax.servlet.forward.request_uri") == null) {
            resp.sendRedirect("/")
            return
        }
        chain.doFilter(request, response)
    }
}