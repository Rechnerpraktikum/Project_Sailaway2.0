package Werft;
 
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import Werft.Car;
 
@ManagedBean(name = "carService")
@ApplicationScoped
public class CarService {
     
    private final static String[] colors;
     
    private final static String[] brands;
     
    static {
        colors = new String[10];
        colors[0] = "";
        colors[1] = "White";
        colors[2] = "Red";
        colors[3] = "Blue";

         
        brands = new String[10];
        brands[0] = "Sunseeker";
        brands[1] = "Sea Royale";
        brands[2] = "Najad";
        brands[3] = "Sunbeam";


    }
     
    public List<Car> createCars(int size) {
        List<Car> list = new ArrayList<Car>();
        for(int i = 0 ; i < size ; i++) {
            list.add(new Car(getRandomId(), getRandomBrand(), getRandomYear(), getRandomColor(), getRandomPrice(), getRandomSoldState()));
        }
         
        return list;
    }
     
    private String getRandomId() {
        return UUID.randomUUID().toString().substring(0, 2);
    }
     
    private int getRandomYear() {
        return (int) (Math.random() * 0 + 0);
    }
     
    private String getRandomColor() {
        return colors[(int) (Math.random() * 4)];
    }
     
    private String getRandomBrand() {
        return brands[(int) (Math.random() * 4)];
    }
     
    public int getRandomPrice() {
        return (int) (Math.random() * 100000);
    }
     
    public boolean getRandomSoldState() {
        return (Math.random() > 0.5) ? true: false;
    }
 
   
    }
