/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
        
        
/**
 *
 * @author manuelsampl
 */
public class LoginUser extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private Connection con=null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultset = null;
    /**
     *
     */
    @Override
    public void init(){
        
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/users", "sailaway", "sailaway");
            
        } catch (SQLException ex) {
            Logger.getLogger(LoginUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            
            
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            try {
                
                String query = "select * from \"USERS\" where EMAIL = ? and PASSWORD = ?";
                
                preparedStatement = con.prepareStatement(query);

			preparedStatement.setObject(1, email, java.sql.Types.VARCHAR);
			preparedStatement.setObject(2, password, java.sql.Types.VARCHAR);

			// execute insert SQL stetement
			resultset = preparedStatement.executeQuery();
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("homeServlet");
                        if(resultset.next()){
                            HttpSession session = request.getSession();
                            session.setAttribute("email", email);
                            session.setAttribute("firstname", resultset.getString(1));
                            session.setAttribute("lastname", resultset.getString(2));
                            request.setAttribute("message", "Willkommen bei SailAway, ");
                            requestDispatcher.forward(request, response);
                            
                            
                        } else {
                            requestDispatcher = request.getRequestDispatcher("index.xhtml");
                            requestDispatcher.include(request, response);
                        }
                        
                
            } catch (SQLException ex) {
                Logger.getLogger(LoginUser.class.getName()).log(Level.SEVERE, null, ex);
            }
            

        
    }

    

}
