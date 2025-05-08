package ubu.gii.dass.refactoring;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;



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
        String statement = customer.statement(false);
        assertTrue("Debería calcular correctamente la valoración.", statement.contains("\tRegular Movie\t5.0"));
    }

    @Test
    public void testSetPriceCode() {
        Movie movie = new Movie("Title", Movie.CHILDRENS);
        movie.setPriceCode(Movie.NEW_RELEASE);
        assertEquals("Debería actualizar el precio de la película.", Movie.NEW_RELEASE, movie.getPriceCode());
    }

     @Test
    public void testHTMLStatementGeneration() throws IOException {
        Customer customer = new Customer("HTMLClient");
        Movie movie = new Movie("HTML Movie", Movie.NEW_RELEASE);
        Rental rental = new Rental(movie, 3);
        customer.addRental(rental);

        customer.statement(true);

        File htmlFile = new File("informe.html");
        assertTrue("El archivo HTML debería haberse creado.", htmlFile.exists());

        String content = new String(Files.readAllBytes(htmlFile.toPath()));
        assertTrue("Debe contener etiqueta <h1>.", content.contains("<h1>"));
        assertTrue("Debe contener el título de la película dentro de <h2>.", content.contains("<h2>HTML Movie"));
        assertTrue("Debe mostrar el total adeudado.", content.contains("<p> Amount owed is"));
        assertTrue("Debe mostrar puntos de cliente frecuente.", content.contains("frequent renter points"));

        htmlFile.delete();
    }

}
