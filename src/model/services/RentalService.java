package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	
	private Double pricePerHour;
	private Double pricePerDay;
	
	private TaxService ts;

	public RentalService(Double pricePerHour, Double pricePerDay, TaxService ts) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.ts = ts;
	}
	
	public void processInvoice(CarRental carRental) {
		
		double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
		double hours = minutes / 60;
		
		double basicPayment;
		if (hours <= 12) {
			basicPayment =  Math.ceil(hours) * pricePerHour ;
		} else {
			basicPayment = Math.ceil(hours / 24) * pricePerDay;
		}
		
		double tax = ts.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
	

}
