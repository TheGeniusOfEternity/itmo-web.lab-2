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
        val isRootWithParams = path == "/main" && (query == null || query.isNotEmpty())

        if (isStaticResource(path)) {
            chain.doFilter(request, response)
            return
        }

        if (!isRootWithParams &&
            req.getAttribute("javax.servlet.forward.request_uri") == null) {
            resp.sendRedirect("/main")
            return
        }
        chain.doFilter(request, response)
    }

    private fun isStaticResource(path: String): Boolean {
        return path.endsWith(".css") ||
                path.endsWith(".js") ||
                path.endsWith(".png") ||
                path.endsWith(".jpg") ||
                path.endsWith(".ico") ||
                path.endsWith(".woff") ||
                path.endsWith(".woff2") ||
                path.endsWith(".ttf") ||
                path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/")
    }
}