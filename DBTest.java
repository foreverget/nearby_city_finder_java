//importing libraries
import org.junit.Test;
import static org.junit.Assert.*;

//tTest class
public class DBTest {

    @Test
    public void strassenTest() {

        //making object
        CityDB cdb = new CityDB();

        //input
        String usr = "root";
        String pwd = "";

        //testing
        assertEquals(true, cdb.checkDB(usr,pwd));

    }
}