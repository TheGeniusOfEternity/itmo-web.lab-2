package tags

import jakarta.servlet.jsp.JspException
import jakarta.servlet.jsp.tagext.BodyTagSupport
import jakarta.servlet.jsp.tagext.SimpleTagSupport
import java.io.IOException
import java.io.StringWriter
import kotlin.reflect.typeOf

class CSVDataTableTag : BodyTagSupport() {
    private var separator = ","
    private var sortable = false
    private var striped = false
    private var pageSize = 0

    override fun setId(id: String?) {
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

    override fun doStartTag() = EVAL_BODY_BUFFERED

    @Throws(JspException::class, IOException::class)
    override fun doEndTag(): Int {
        val body = bodyContent?.string?.trim() ?: return SKIP_BODY

        val lines = body.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val rows: MutableList<Array<String>> = ArrayList()

        val out = pageContext.out

        for (line in lines) {
            val trimmedLine = line.trim()
            if (trimmedLine.isEmpty()) continue
            val cols = trimmedLine.split(separator)
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .toTypedArray()
            rows.add(cols)
        }

        out.println("""
            <div class="table">
                <table id="$id" class="${if (striped) "striped" else ""}">
                    <thead>
                        <tr>
        """.trimIndent())

        for (header in rows[0]) {
            if (sortable) {
                out.println("""
                    <th onclick="sortTable('$id', this.cellIndex})">${header.trim { it <= ' ' }}</th>
                """.trimIndent())
            } else {
                out.println("""
                    <th>${header.trim { it <= ' ' }}</th>
                """.trimIndent())
            }
        }
        out.println("""
                </tr>
            </thead>
            <tbody>
        """.trimIndent())

        for (i in 1 until rows.size) {
            val row = rows[i]
            var rowClass = " class=\""

            rowClass += if (striped && i % 2 == 1) "striped-row " else " "
            when {
                row.contains("true") -> rowClass += "hit"
                row.contains("false") -> rowClass += "miss"
            }
            rowClass += "\""

            out.print("<tr$rowClass>")
            for (col in row) {
                out.println("""
                    <td>
                    ${
                        when(col) {
                            "true" -> "Попадание"
                            "false" -> "Промах"
                            else -> col.trim { it <= ' ' }
                        }
                    }
                    </td>
                """.trimIndent())
            }
            out.println("</tr>")
        }
        out.println("""
                </tbody>
            </table>
        """.trimIndent())

        if (sortable) {
            out.println("""
                <script>
                    const sortTable = (tableId, colIndex) => {
                        const table = document.getElementById(tableId);
                        const tbody = table.tBodies[0];
                        const rows = Array.from(tbody.rows);
                        const asc = !table.asc;
                        
                        rows.sort((a, b) => {
                            const aText = a.cells[colIndex].textContent.trim();
                            const bText = b.cells[colIndex].textContent.trim();
                            
                            return asc ? aText.localeCompare(bText) : bText.localeCompare(aText);
                        })
                        
                        rows.forEach((row) => {
                            tbody.appendChild(row); 
                        });
                        table.asc = asc;
                    }
                </script>
            """.trimIndent())
        }

        if (pageSize > 0) {
            out.println("""
                <div class="buttons">
                    <button id="prevBtn_$id" onclick="prevPage_$id();">Назад</button>
                    <button id="nextBtn_$id" onclick="nextPage_$id();">Вперёд</button>
                </div>
                <script>
                    let currentPage_$id = 1;
                    const pageSize_$id = $pageSize;
                    const table_$id = document.getElementById('$id');
                    const nextBtn_$id = document.getElementById('nextBtn_$id');
                    const prevBtn_$id = document.getElementById('prevBtn_$id');
                    const tbody_$id = table_$id.tBodies[0];
                    const rows_$id = Array.from(tbody_$id.rows);
                    
                    const renderPage_$id = () => {
                        const start = (currentPage_$id - 1) * pageSize_$id;
                        const end = start + pageSize_$id;
                        
                        if (currentPage_$id === 1) {
                            prevBtn_$id.disabled = true;
                        } else prevBtn_$id.disabled = false;
                        
                        if (currentPage_$id * pageSize_$id >= rows_$id.length) {
                            nextBtn_$id.disabled = true;
                        } else nextBtn_$id.disabled = false;
                        
                        rows_$id.forEach((r, i) => {
                            r.style.display = (i >= start && i < end) ? '' : 'none';
                        })
                    }
                    
                    const nextPage_$id = () => {
                        if (currentPage_$id * pageSize_$id < rows_$id.length) {
                            currentPage_$id++; 
                            renderPage_$id();
                        }
                    }
                    
                    const prevPage_$id = () => {
                        if (currentPage_$id > 1) {
                            currentPage_$id--; 
                            renderPage_$id();
                        }
                    }
                    
                    renderPage_$id()
                </script>
            """.trimIndent())
        }
        out.println("</div>")
        return EVAL_PAGE
    }
}