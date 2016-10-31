/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginUser;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
        
        
/**
 *
 * @author manuelsampl
 */
public class Logout extends HttpServlet {
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html");  
            PrintWriter out=response.getWriter();  
            
            HttpSession session=request.getSession();  
            session.invalidate();  
            out.print("<center><h1>");
            out.print("Du wurdest abgemeldet!");
            out.print("</h1>");
            out.print("\n <a href='index.xhtml'>Gehe zur Satrtseite</a> ");
            out.print("</center>");
            response.setHeader("Refresh", "3; URL=index.xhtml");
            
            
            out.close();

        
    }

    

}
