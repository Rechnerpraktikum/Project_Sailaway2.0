package Seekarte;
 
import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
 
/**
 * Author 
 * 
 */
public class MyShipCard extends Application {
     
    private Scene scene;
    MyBrowser myBrowser;
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
     
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("java-buddy.blogspot.com");
         
        myBrowser = new MyBrowser();
        //Setting the Points comes from the database 
        double lon[] = {15.588385, 15.581853, 15.581853, 15.582797};
        double lat[] = {43.828370, 43.828976 , 43.831902 , 43.831902};
        myBrowser.setPointLon(lon);
        myBrowser.setPointLat(lat);
        double shipLon = 15.588385; 
        double shipLat = 43.828370; 
        myBrowser.setLonShip(shipLon);
        myBrowser.setLatShip(shipLat);
        //After setting points and Ship Position creating the content
        myBrowser.createCOntent();
        scene = new Scene(myBrowser, 600, 400);
         
        primaryStage.setScene(scene);
        primaryStage.show();
    }
     
    class MyBrowser extends Region{
         
        HBox toolbar;
        private double lonShip;
        private double latShip;
        private double pointlon[];
        private double pointlat[];
        
        
 
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
         
        public MyBrowser(){
            super();
        }
        
        public void setLonShip(double lonship){
            this.lonShip = lonship;
        }
        public void setLatShip(double latship){
            this.latShip = latship;
        }
        public double getLonShip(){
            return this.lonShip;
        }
        public double getLatShip(){
            return this.latShip;
        }
        
        public void setPointLon(double pointlon[]){
            this.pointlon = pointlon;
        }
        public double[] getPointLon(){
            return this.pointlon;
        }
        
        public void setPointLat(double pointlat[]){
            this.pointlat = pointlat;
        }
        public double[] getPointLat(){
            return this.pointlat;
        }
        
            private void createCOntent(){
             
            final URL urlGoogleMaps = getClass().getResource("Karte.html");
            webEngine.load(urlGoogleMaps.toExternalForm());
             
            
            getChildren().add(webView);
            webEngine.getLoadWorker().stateProperty().addListener(
        new ChangeListener<State>() {
            public void changed(ObservableValue ov, State oldState, State newState) {
                
                
                webEngine.executeScript("setShipPosition(" + getLonShip() + "," + getLatShip() +" );");
                //webEngine.executeScript("addMarker();");
                for(int i = 0; i< getPointLon().length ; i++){
                    //TODO Looking up if all variables are not null!!
                    webEngine.executeScript("addPoint("+ getPointLon()[i] + "," + getPointLat()[i] +")");
                }
                
            }
        });
         
        }
    }
}
