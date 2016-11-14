/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Schiff;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author h1028320
 */

public class Schiff implements ServletContextListener {
    
    public Schiff(){
        System.out.println("Schiff ist da!");
    }
    
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        ServletContext servletContext = arg0.getServletContext();
        System.out.println("*********ServletContextListener started*********");

        int delay = 1000;
        Timer timer = new Timer();
        //final Calendar calendar = Calendar.getInstance();
        //System.out.println("Tweet at Time = " + calendar.getTime());
        //calendar.add(Calendar.SECOND, -60);
        timer.scheduleAtFixedRate(new TimerTask(){
        public void run(){
        System.out.println("Die Zeit wird jede Sekunde aktualisiert....");
        }//End of Run
        },delay, 1000);
        servletContext.setAttribute ("timer", timer);
        }

        /**
     * @param arg0
        * @see ServletContextListener#contextDestroyed(ServletContextEvent)
        */
    @Override
        public void contextDestroyed(ServletContextEvent arg0) {
        ServletContext servletContext = arg0.getServletContext();
        // get our timer from the Context
        Timer timer = (Timer)servletContext.getAttribute ("timer");

        // cancel all pending tasks in the timers queue
        if (timer != null)
        timer.cancel();

        // remove the timer from the servlet context
        servletContext.removeAttribute ("timer");
        System.out.println("ServletContextListener destroyed");

        }
    
}
