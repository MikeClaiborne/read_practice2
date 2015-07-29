import java.io.*;
import javax.json.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ReadFromFileServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        
        
        String fileName, jsonContent, lesson = request.getParameter("lesson");
        jsonContent = "not init's";

        if (null==lesson) {
            fileName= "0001.json";
        } else
            fileName= "0001-"+lesson+".json";
        //ReadFromFileServlet.getServletContext().log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>filename: "+fileName);
        this.getServletContext().log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>filename: "+fileName);
        //JsonReader reader = Json.createReader(new FileReader("0001.json"));
        //InputStream input = getServletContext().getClass().getClassLoader().getResourceAsStream("0001.json");
        
        
        InputStream input = ReadFromFileServlet.class.getResourceAsStream(fileName);
        boolean success = null==input;
        //int i = input.available();
       
        try {
            JsonReader reader = Json.createReader(input);
            JsonStructure jsonst = reader.read();
            jsonContent = jsonst.toString();

        } catch (NullPointerException npe) {
            this.getServletContext().log(">>>>  NullPointerException:  filename: "+fileName);
        }
        

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet: Hello Mike!</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet: Mike World!</h1>");
        out.println("=============== begin JSON:<br/>");
        out.println("path: "+ getServletContext().getContextPath()+"<br/>");
        //out.println("success: "+ success+"<br/>");
        //out.println(jsonst.toString());
        out.println(jsonContent);
        out.println("<br/>=============== end JSON");
        out.println("<br/> <br/> <br/>");
        out.println("<a href='"+ getServletContext().getContextPath()+"/MWS'>no lesson</a><br/>");
        out.println("<a href='"+ getServletContext().getContextPath()+"/MWS?lesson=1'>lesson 1</a><br/>");
        out.println("<a href='"+ getServletContext().getContextPath()+"/MWS?lesson=2'>lesson 2</a><br/>");
        out.println("<a href='"+ getServletContext().getContextPath()+"/MWS?lesson=3'>lesson 3</a><br/>");
        out.println("</body>");
        out.println("</html>");
    }
}