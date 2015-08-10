package sightwords;

import java.io.*;
import javax.json.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.InitialContext;

import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.Enumeration;

import org.apache.catalina.Context;

public class Performance extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        String jdbcSuccess="connected!!";
        Connection conn = null;
        
        //test code for checking POST body
        /*
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        InputStream inputStream = request.getInputStream();
        if (inputStream != null) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        } else {
            stringBuilder.append("");
        }
        getServletContext().log("Performance-log POST body: " + stringBuilder.toString());
        */

        //another test of POST body
        /*
        StringBuilder stringBuilder = new StringBuilder();
        for (Enumeration<String> es = request.getParameterNames(); es.hasMoreElements();)
        stringBuilder.append(es.nextElement());
        getServletContext().log("Performance-log POST parm names: " + stringBuilder.toString()); 
        */


        //Context initCtx = new InitialContext();
        //Context envCtx = (Context) initCtx.lookup("java:comp/env");
        //String dbUser = (String)envCtx.lookup("DB_NAME");
        String dbName = System.getProperty("DB_NAME");
        String dbUser = System.getProperty("DB_USER");
        String dbPwd = System.getProperty("DB_PASSWORD");
        
        String studentID = request.getParameter("studentID");
        String wordID = request.getParameter("wordID");
        String correct = request.getParameter("correct");
        int correctInt = 0;
        if ( null!=correct && ((new String(correct)).equals("true"))) {
            correctInt=1;
        } 
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // MySQL wants '2012-12-31 11:30:45'
        String dateTime = sdf.format(new java.util.Date()); 

        String dbQuery = "INSERT INTO word_perf_detail (fk_l, fk_w, correct, d_t) "+
                         "VALUES ("+studentID+","+wordID+","+correctInt+",'"+dateTime+"')";  
        getServletContext().log("Performance-log Query: " + dbQuery);

        String resStr = "init";

        Statement stmt = null;


        try {
            
            // Load the database driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            // Get a connection to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName+"?user="+dbUser+"&password="+dbPwd);
             
            stmt = conn.createStatement();

            stmt.execute(dbQuery);

            /*  DB TEST CODE
            ResultSet rs = stmt.executeQuery( "Select * from learner where pk=1" );
            resStr = "";
            while( rs.next() ) {
                System.out.println( rs.getString("fname") );
                resStr = resStr + rs.getString("fname");
            }
            */

            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            getServletContext().log("Performance-log SQLException: : " + ex.getMessage());
            jdbcSuccess=ex.getMessage();
            try { 
                conn.close();
                stmt.close();
            } catch (Exception ex2) {}
        
        } catch (Exception ex3) {

            jdbcSuccess=ex3.toString();
            getServletContext().log("Performance-log Exception: : " + ex3.getMessage());
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Performance</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet: Performance!</h1>");
        out.println("<br/> <br/> <br/>");
        out.println("<h3>resStr:"+resStr+"</h3>");
        out.println("<br/> <br/> <br/>");
        out.println("<h3>This servlet records sightwords performance</h3>");
        out.println("<br/> <br/> <br/>");
        out.println("<h3>JDBC success:"+jdbcSuccess+"</h3>");
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
    } //end POST

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Performance</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet: Performance!</h1>");
        out.println("<br/> <br/> <br/>");
        out.println("<h3>GET response</h3>");
        out.println("<br/> <br/> <br/>");
        out.println("<form action=\"/service/sightwords/Performance\" method=\"POST\">");
        out.println("studentID: <input type=\"text\" name=\"studentID\"><br>");
        out.println("wordID: <input type=\"text\" name=\"wordID\"><br>");
        out.println("correct: <input type=\"text\" name=\"correct\"><br>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("<br/> <br/> <br/>");
        out.println("</body>");
        out.println("</html>");


    } // end GET

}