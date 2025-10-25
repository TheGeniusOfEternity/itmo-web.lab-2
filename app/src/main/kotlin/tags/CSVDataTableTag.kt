package tags

import jakarta.servlet.jsp.JspException
import jakarta.servlet.jsp.tagext.SimpleTagSupport
import java.io.IOException
import java.io.StringWriter

class CSVDataTableTag : SimpleTagSupport() {
    private var id: String? = null
    private var separator = ","
    private var sortable = false
    private var striped = false
    private var pageSize = 0

    fun setId(id: String?) {
        this.id = id
    }

    fun setSeparator(separator: String) {
        this.separator = separator
    }

    fun setSortable(sortable: Boolean) {
        this.sortable = sortable
    }

    fun setStriped(striped: Boolean) {
        this.striped = striped
    }

    fun setPageSize(pageSize: Int) {
        this.pageSize = pageSize
    }

    @Throws(JspException::class, IOException::class)
    override fun doTag() {
        val ctx = jspContext
        val sw = StringWriter()
        jspBody.invoke(sw)
        val body = sw.toString().trim { it <= ' ' }

        // Разбор строк
        val lines = body.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (lines.isEmpty()) return

        // Парсинг CSV, 1-я строка - заголовки
        val rows: MutableList<Array<String>> = ArrayList()
        for (line in lines) {
            val cols = line.split(separator.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            rows.add(cols)
        }

        val out = ctx.out

        // Генерация HTML таблицы
        out.println("<table id=\"" + id + "\" class=\"" + (if (striped) "striped" else "") + "\">")

        // Заголовок
        out.println("<thead><tr>")
        for (header in rows[0]) {
            if (sortable) {
                out.println("<th onclick=\"sortTable('" + id + "', this.cellIndex)\">" + header.trim { it <= ' ' } + "</th>")
            } else {
                out.println("<th>" + header.trim { it <= ' ' } + "</th>")
            }
        }
        out.println("</tr></thead>")

        // Тело таблицы
        out.println("<tbody>")
        for (i in 1 until rows.size) {
            val row = rows[i]
            val rowClass = if ((striped && i % 2 == 1)) " class=\"striped-row\"" else ""
            out.print("<tr$rowClass>")
            for (col in row) {
                out.print("<td>" + col.trim { it <= ' ' } + "</td>")
            }
            out.println("</tr>")
        }
        out.println("</tbody>")
        out.println("</table>")

        // Сценарии сортировки и пагинации (если включены)
        if (sortable) {
            out.println("<script>")
            out.println("function sortTable(tableId, colIndex) {")
            out.println("  var table = document.getElementById(tableId);")
            out.println("  var tbody = table.tBodies[0];")
            out.println("  var rows = Array.from(tbody.rows);")
            out.println("  var asc = !table.asc;")
            out.println("  rows.sort(function(a, b) {")
            out.println("    var aText = a.cells[colIndex].textContent.trim();")
            out.println("    var bText = b.cells[colIndex].textContent.trim();")
            out.println("    return asc ? aText.localeCompare(bText) : bText.localeCompare(aText);")
            out.println("  });")
            out.println("  rows.forEach(function(row) { tbody.appendChild(row); });")
            out.println("  table.asc = asc;")
            out.println("}")
            out.println("</script>")
        }

        if (pageSize > 0) {
            // Простейшая пагинация (можно доработать)
            out.println("<script>")
            out.println("var currentPage_$id = 1;")
            out.println("var pageSize_$id = $pageSize;")
            out.println("var table_$id = document.getElementById('$id');")
            out.println("var tbody_$id = table_$id.tBodies[0];")
            out.println("var rows_$id = Array.from(tbody_$id.rows);")
            out.println("function renderPage_$id() {")
            out.println("  var start = (currentPage_$id - 1) * pageSize_$id;")
            out.println("  var end = start + pageSize_$id;")
            out.println("  rows_$id.forEach(function(r, i) {")
            out.println("    r.style.display = (i >= start && i < end) ? '' : 'none';")
            out.println("  });")
            out.println("}")
            out.println("function nextPage_$id() {")
            out.println("  if (currentPage_$id * pageSize_$id < rows_$id.length) { currentPage_$id++; renderPage_$id(); }")
            out.println("}")
            out.println("function prevPage_$id() {")
            out.println("  if (currentPage_$id > 1) { currentPage_$id--; renderPage_$id(); }")
            out.println("}")
            out.println("renderPage_$id();")
            out.println("</script>")

            out.println("<button onclick=\"prevPage_$id();\">Назад</button>")
            out.println("<button onclick=\"nextPage_$id();\">Вперёд</button>")
        }
    }
}