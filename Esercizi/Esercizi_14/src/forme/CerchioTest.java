package forme;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CerchioTest {

    @ParameterizedTest
    @CsvSource({
            "1.0, 3.1416",
            "2.0, 12.5664",
            "3.0, 28.2744",
            "4.0, 50.2656"
    })
    public void testArea(double raggio, double areaAttesa){
        //Arrange
        Cerchio c = new Cerchio(raggio);
        //Act
        double risultato = c.area();
        //Assert
        assertEquals(areaAttesa, risultato, 0.001, "Non funziona");
    }

    @ParameterizedTest
    @CsvSource({
            "1.0, 6.2832",
            "2.0, 12.5664",
            "3.0, 18.8496",
            "4.0, 25.1328",
            "5.0, 31.4160"
    })
    public void testPerimetro(double raggio, double perimetroAtteso){
        //Arrange
        Cerchio c = new Cerchio(raggio);
        //Act
        double risultato = c.perimetro();
        //Assert
        assertEquals(perimetroAtteso, risultato, 0.001, "Non funziona");
    }
}
