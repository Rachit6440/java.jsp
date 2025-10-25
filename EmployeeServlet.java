import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EmployeeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("empid");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "password");

            Statement st = con.createStatement();
            ResultSet rs;

            if (id != null && !id.isEmpty()) {
                rs = st.executeQuery("SELECT * FROM Employee WHERE EmpID=" + id);
            } else {
                rs = st.executeQuery("SELECT * FROM Employee");
            }

            out.println("<h2>Employee Records</h2>");
            out.println("<form method='get' action='EmployeeServlet'>");
            out.println("Search by EmpID: <input type='text' name='empid'>");
            out.println("<input type='submit' value='Search'>");
            out.println("</form><br>");

            out.println("<table border='1' cellpadding='8'>");
            out.println("<tr><th>EmpID</th><th>Name</th><th>Salary</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("EmpID") + "</td>");
                out.println("<td>" + rs.getString("Name") + "</td>");
                out.println("<td>" + rs.getDouble("Salary") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            con.close();

        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
