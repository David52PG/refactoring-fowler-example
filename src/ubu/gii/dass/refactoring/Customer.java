package ubu.gii.dass.refactoring;

/**
* Tema  Refactorizaciones 
*
* Ejemplo de aplicaci�n de refactorizaciones. Actualizado para colecciones gen�ricas de java 1.5
*
* @author M. Fowler y <A HREF="mailto:clopezno@ubu.es">Carlos L�pez</A>
* @version 1.1
* @see java.io.File
*
*/
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class Customer {
	private String _name;
	private List<Rental> _rentals;

	public Customer(String name) {
		_name = name;
		_rentals = new ArrayList<Rental>();

	};

	public void addRental(Rental arg) {
		_rentals.add(arg);
	}

	public String getName() {
		return _name;
	};

	public String statement(boolean generateInHTML) {
	    double totalAmount = 0;
	    StringBuilder result = new StringBuilder();
	    int frequentRenterPoints = 0;
	    Iterator<Rental> rentals = _rentals.iterator();

	    if (generateInHTML) {
	        result.append("<h1>");
	    }

	    result.append("Rental Record for ").append(getName()).append("\n");

	    if (generateInHTML) {
	        result.append("</h1>");
	    } else {
	        result.append("\n");
	    }

	    while (rentals.hasNext()) {
	        double thisAmount = 0;
	        Rental each = rentals.next();
	        // determine amounts for each line
	        thisAmount = calculateAmount(thisAmount, each);

	    	frequentRenterPoints = incrementFrequentRenterPoints(frequentRenterPoints, each);

	        // show figures for this rental
	        if (generateInHTML) {
	            result.append("<h2>").append(each.getMovie().getTitle()).append("\t").append(thisAmount).append("</h2>");
	        } else {
	            result.append("\t").append(each.getMovie().getTitle()).append("\t").append(thisAmount).append("\n");
	        }

	        totalAmount += thisAmount;
	    }

	    // add footer lines
	    if (generateInHTML) {
	        result.append("<p> Amount owed is ").append(totalAmount).append("</p>");
	        result.append("<p> You earned ").append(frequentRenterPoints).append(" frequent renter points </p>");
	    } else {
	        result.append("Amount owed is ").append(totalAmount).append("\n");
	        result.append("You earned ").append(frequentRenterPoints).append(" frequent renter points");
	    }

	    if (generateInHTML) {
	    	try (FileWriter fileWriter = new FileWriter("informe.html")) {
		        fileWriter.write(result.toString());
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    }

	    return result.toString();
	}

	private int incrementFrequentRenterPoints(int frequentRenterPoints, Rental each) {
		// add frequent renter points
		frequentRenterPoints++;

		// add bonus for a two day new release rental
		if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && each.getDaysRented() > 1) {
		    frequentRenterPoints++;
		}
		return frequentRenterPoints;
	}

	
	private double calculateAmount(double thisAmount, Rental each) {
		switch (each.getMovie().getPriceCode()) {
		case Movie.REGULAR:
			thisAmount += 2;
			if (each.getDaysRented() > 2)
				thisAmount += (each.getDaysRented() - 2) * 1.5;
			break;
		case Movie.NEW_RELEASE:
			thisAmount += each.getDaysRented() * 3;
			break;
		case Movie.CHILDRENS:
			thisAmount += 1.5;
			if (each.getDaysRented() > 3)
				thisAmount += (each.getDaysRented() - 3) * 1.5;
			break;
		}
		return thisAmount;
	}
}
