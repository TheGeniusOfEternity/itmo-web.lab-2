package tags

import jakarta.servlet.jsp.JspException
import jakarta.servlet.jsp.tagext.BodyTagSupport
import java.io.IOException

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
                    <th onclick="sortTable('$id', this.cellIndex)">${header.trim { it <= ' ' }}</th>
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
            val classNames = mutableListOf<String>()

            if (striped && i % 2 == 1) classNames.add("striped-row")
            when {
                row.contains("true") -> classNames.add("hit")
                row.contains("false") -> classNames.add("miss")
            }
            val rowClass = if (classNames.isEmpty()) ""
                else "class=\"${classNames.joinToString(" ")}\""

            out.print("<tr $rowClass>")
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

        out.println("""
                <div class="buttons">
                    <button id="prevBtn_$id" onclick="prevPage_$id();">Назад</button>
                    <button id="nextBtn_$id" onclick="nextPage_$id();">Вперёд</button>
                </div>
            </div>
            <script>
                let currentPage_$id = 1;
                const pageSize_$id = $pageSize;
                const table_$id = document.getElementById('$id');
                const nextBtn_$id = document.getElementById('nextBtn_$id');
                const prevBtn_$id = document.getElementById('prevBtn_$id');
                const tbody_$id = table_$id.tBodies[0];
                const rows_$id = Array.from(tbody_$id.rows);
                const allData_$id = rows_$id.map(row => {
                    return {
                        classList: Array.from(row.classList),
                        cells: Array.from(row.cells).map(cell => cell.textContent.trim())
                    };
                });
            
                const renderPage_$id = () => {
                    tbody_$id.innerHTML = '';
                    
                    const start = (currentPage_$id - 1) * pageSize_$id;
                    const end = start + pageSize_$id;
            
                    if (currentPage_$id === 1) {
                        prevBtn_$id.disabled = true;
                    } else prevBtn_$id.disabled = false;
            
                    if (currentPage_$id * pageSize_$id >= rows_$id.length) {
                        nextBtn_$id.disabled = true;
                    } else nextBtn_$id.disabled = false;
            
                    allData_$id.forEach((trData, i) => {
                        if (i >= start && i < end) {
                            const tr = document.createElement('tr')
                            trData.cells.forEach(cell => {
                                const td = document.createElement('td');
                                td.innerHTML = cell;
                                tr.appendChild(td);
                            });
                            trData.classList.forEach(className => {
                                tr.classList.add(className);
                            });
                            tbody_$id.appendChild(tr);
                        };
                    });
                };
            
                const nextPage_$id = () => {
                    if (currentPage_$id * pageSize_$id < rows_$id.length) {
                        currentPage_$id++;
                        renderPage_$id();
                    };
                };
            
                const prevPage_$id = () => {
                    if (currentPage_$id > 1) {
                        currentPage_$id--;
                        renderPage_$id();
                    };
                };
            
                renderPage_$id();
            </script>
        """.trimIndent())

        if (sortable) {
            out.println("""
                <script>
                    const sortTable = (tableId, colIndex) => {
                        const table = document.getElementById(tableId);

                        const asc = !table.asc;
                        
                        allData_$id.sort((a, b) => {
                            const aText = a.cells[colIndex];
                            const bText = b.cells[colIndex];
                            
                            return asc ? aText.localeCompare(bText) : bText.localeCompare(aText);
                        });
                        
                        table.asc = asc;
                        
                        renderPage_$id();
                    }
                </script>
            """.trimIndent())
        }

        return EVAL_PAGE
    }
}