
package kaos.core;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A dummy to trigger data table generation
 * Don't need to run it, tables created anyway
 * @author hajo
 */
@WebServlet(name = "DummyServlet", urlPatterns = {"/dummy"})
public class DummyServlet extends HttpServlet {
    // Inject Entitymanager, will trigger creation of tables
    @PersistenceContext(unitName = "kaos_pu")
    private EntityManager em;
   
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Unused
    }
}
