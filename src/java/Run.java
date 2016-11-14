import wu.com.Artikel;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;




public class Run {
    
public static void main(String args[]){
    
Artikel p = new Artikel();
p.setArtikelname("Hendro Steven");
p.setArtikelnummer(1);

Run demo = new Run();
demo.persist(p);
}

public void persist(Object object) {
    
EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("SailAwayJCIPU");
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();
try {
em.persist(object);
em.getTransaction().commit();
} catch (Exception e) {
e.printStackTrace();
em.getTransaction().rollback();
} finally {
em.close();
}
}

}