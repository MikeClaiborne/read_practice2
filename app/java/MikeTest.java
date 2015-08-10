import java.io.*;
import javax.json.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MikeTest extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        

        out.println("<html>");
        out.println("<head>");
        out.println("<title>MikeTest Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet: MikeTest!</h1>");
        out.println("<br/> <br/> <br/>");
        out.println("<h3>This servlet will test config of Tomcat deployment</h3>");
        out.println("<br/> <br/> <br/>");
        out.println("<a href='"+ getServletContext().getContextPath()+"/MWS'>no lesson</a><br/>");
        out.println("<a href='"+ getServletContext().getContextPath()+"/MWS?lesson=1'>lesson 1</a><br/>");
        out.println("<a href='"+ getServletContext().getContextPath()+"/MWS?lesson=2'>lesson 2</a><br/>");
        out.println("<a href='"+ getServletContext().getContextPath()+"/MWS?lesson=3'>lesson 3</a><br/>");
        out.println("=============== begin Response:<br/>");
        out.println("path: "+ getServletContext().getContextPath()+"<br/>");
        out.println("<br/>=============== end Response");
        out.println("</body>");
        out.println("</html>");
    }
}