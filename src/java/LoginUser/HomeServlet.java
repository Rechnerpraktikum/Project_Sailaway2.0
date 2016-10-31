package LoginUser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tomsampl
 */
public class HomeServlet extends HttpServlet {

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String firstname = (String) session.getAttribute("firstname");
        String lastname = (String) session.getAttribute("lastname");
        
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.print("<center><h1>");
        out.print(request.getAttribute("message"));
        out.print(firstname);
        out.print(" ");
        out.print(lastname);
        out.print("</h1>");
        out.print("\n <a href='restricted/index.xhtml'>GO TO APP</a> ");
        out.print("</center>");
        response.setHeader("Refresh", "3; URL=restricted/index.xhtml");
    }

    

}
