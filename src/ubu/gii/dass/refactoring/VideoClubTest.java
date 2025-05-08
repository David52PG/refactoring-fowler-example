package ubu.gii.dass.refactoring;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * Tema Refactorizaciones
 * 
 * Ejemplo de aplicación de refactorizaciones. Actualizado para colecciones
 * genéricas de java 1.5
 * 
 * @author M. Fowler y <A HREF="mailto:clopezno@ubu.es">Carlos L�pez</A>
 * @version 1.1

 * 
 */
public class VideoClubTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream newOut = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        // Redirect standard output to capture it.
        System.setOut(new PrintStream(newOut));
    }

    @After
    public void tearDown() {
        // Restore original standard output.
        System.setOut(originalOut);
    }

    @Test
    public void testVideoClubApplicationMain() {
        VideoClubAplicacion.main(new String[0]);
        String output = newOut.toString();
        assertTrue("Debería imprimir el encabezado del cliente.", output.contains("Rental Record for Manuel"));
    }

    @Test
    public void testRegularBranchDaysGreaterThanTwo() {
        Customer customer = new Customer("Test");
        Movie movie = new Movie("Regular Movie", Movie.REGULAR);
        Rental rental = new Rental(movie, 4);
        customer.addRental(rental);
        String statement = customer.statement();
        assertTrue("Debería calcular correctamente la valoración.", statement.contains("\tRegular Movie\t5.0"));
    }

<<<<<<< HEAD
		c1.addRental(r1);
		c1.addRental(r2);
		c1.addRental(r3);

		String salida = c1.statement(false);

		String salidaEsperada = new String("Rental Record for Manuel\n"
				+ "\tSky Captain\t15.0\n" + "\tAccion Mutante\t2.0\n"
				+ "\tHermano Oso\t12.0\n" + "Amount owed is 29.0\n"
				+ "You earned 4 frequent renter points");

		assertTrue("Calcula mal el alquiler", salidaEsperada.equals(salida));
	}
=======
    @Test
    public void testSetPriceCode() {
        Movie movie = new Movie("Title", Movie.CHILDRENS);
        movie.setPriceCode(Movie.NEW_RELEASE);
        assertEquals("Debería actualizar el precio de la película.", Movie.NEW_RELEASE, movie.getPriceCode());
    }
>>>>>>> branch 'master' of git@github.com:David52PG/refactoring-fowler-example.git

}
