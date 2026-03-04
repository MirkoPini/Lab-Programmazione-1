package forme;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RettangoloTest {

    @ParameterizedTest
    @CsvSource({
            "3.0, 4.0, 12.0",
            "5.0, 5.0, 25.0",
            "1.0, 10.0, 10.0",
            "2.5, 4.0, 10.0"
    })
    public void testArea(double base, double altezza, double areaAttesa){
        //Arrange
        Rettangolo r = new Rettangolo(base, altezza);
        //Act
        double risultato = r.area();
        //Assert
        assertEquals(areaAttesa, risultato, 0.001, "Non funziona");
    }

    @ParameterizedTest
    @CsvSource({
            "3.0, 4.0, 14.0",
            "5.0, 5.0, 20.0",
            "1.0, 10.0, 22.0",
            "2.5, 4.0, 13.0"
    })
    public void testPerimetro(double base, double altezza, double perimetroAtteso){
        //Arrange
        Rettangolo r = new Rettangolo(base, altezza);
        //Act
        double risultato = r.perimetro();
        //Assert
        assertEquals(perimetroAtteso, risultato, 0.001, "Non funziona");
    }
}