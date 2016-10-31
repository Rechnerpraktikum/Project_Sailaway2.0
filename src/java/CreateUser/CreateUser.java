/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CreateUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
        
        
/**
 *
 * @author manuelsampl
 */
public class CreateUser extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private Connection con=null;
    private PreparedStatement preparedStatement = null;
    
    /**
     *
     */
    @Override
    public void init(){
        
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/users", "sailaway", "sailaway");
        } catch (SQLException ex) {
            Logger.getLogger(CreateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            PrintWriter out = response.getWriter();
            
            String firstname = request.getParameter("firstName");
            String lastname = request.getParameter("lastName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            try {
                String query = "insert into \"USERS\""
                        + " (FIRSTNAME, LASTNAME, EMAIL, PASSWORD)"
                        + " VALUES (?,?,?,?)";
                
                preparedStatement = con.prepareStatement(query);

			preparedStatement.setObject(1, firstname, java.sql.Types.VARCHAR);
			preparedStatement.setObject(2, lastname, java.sql.Types.VARCHAR);
			preparedStatement.setObject(3, email, java.sql.Types.VARCHAR);
			preparedStatement.setObject(4, password, java.sql.Types.VARCHAR);

			// execute insert SQL stetement
			preparedStatement.executeUpdate();
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>SailAway Registrierung</title>");            
                        out.println("</head>");
                        out.println("<body>");
                        out.print("<h1>Hallo "+firstname+", dein Account wurde erfolgreich erstellt. </h1>");
                        out.println("<a href='../'>Zurück</a>");
                        out.println("</body>");
                        out.println("</html>");
                        
                
            } catch (SQLException ex) {
                Logger.getLogger(CreateUser.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            
           
        
        
    }

    

}
