package org.springframework.samples.petclinic.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PriceCalculatorTest {
	private static int INFANT_YEARS = 2;
	private static double RARE_INFANCY_COEF = 1.4;
	private static double BASE_RARE_COEF = 1.2;
	private static int DISCOUNT_MIN_SCORE = 10;
	private static int DISCOUNT_PRE_VISIT = 2;

	private List<Pet> pets;
	private LocalDate today;
	private Pet pet1;
	private Pet pet2;
	private Pet pet3;
	private Pet pet4;
	private Pet pet5;
	private Pet pet6;
	private Pet pet7;

	@BeforeEach
	void setUp() {
		pets = new ArrayList<>();
		today = LocalDate.now();
		Visit visit1 = mock(Visit.class);
		when(visit1.getDate()).thenReturn(today.minusDays(10));
		Visit visit2 = mock(Visit.class);
		when(visit2.getDate()).thenReturn(today.minusDays(110));
		Visit visit3 = mock(Visit.class);
		when(visit3.getDate()).thenReturn(today.minusDays(100));

		// age <= INFANT_YEARS and daysFromLastVisit < 100
		pet1 = new Pet();
		pet1.setBirthDate(today.minusYears(1));
		pet1.addVisit(visit1);

		// age <= INFANT_YEARS and daysFromLastVisit > 100
		pet2 = new Pet();
		pet2.setBirthDate(today.minusYears(2));
		pet2.addVisit(visit2);

		// age > INFANT_YEARS and daysFromLastVisit < 100
		pet3 = new Pet();
		pet3.setBirthDate(today.minusYears(20));
		pet3.addVisit(visit1);

		// age > INFANT_YEARS and daysFromLastVisit > 100
		pet4 = new Pet();
		pet4.setBirthDate(today.minusYears(10));
		pet4.addVisit(visit2);

		// age <= INFANT_YEARS and no visits
		pet5 = new Pet();
		pet5.setBirthDate(today.minusYears(1));

		// age > INFANT_YEARS and no visits
		pet6 = new Pet();
		pet6.setBirthDate(today.minusYears(10));

		pet7 = new Pet();
		pet7.setBirthDate(today.minusYears(20));
		pet7.addVisit(visit3);

	}
	@Test
	public void AgeLessThanINFAndVisitWithin100Days() {
		pets.add(pet1);
		assertEquals(10 * BASE_RARE_COEF * RARE_INFANCY_COEF, PriceCalculator.calcPrice(pets, 0, 10));
	}
	@Test
	public void AgeMoreThanINFAndVisitWithin100Days() {
		pets.add(pet3);
		assertEquals(10 * BASE_RARE_COEF, PriceCalculator.calcPrice(pets, 0, 10));
	}
	@Test
	public void SeveralPetsAgesLessThanINFAndVisitsWithin100Days() {
		for (int i = 0; i < 5; i++) {
			pets.add(pet1);
		}
		double price = 10 * BASE_RARE_COEF * RARE_INFANCY_COEF;
		double totalPrice = 4 * price;
		assertEquals((totalPrice * DISCOUNT_PRE_VISIT) + 10 + price, PriceCalculator.calcPrice(pets, 10, 10));
	}
	@Test
	public void SeveralPetsAgesLessThanINFAndVisitsMoreThan100Days() {
		for (int i = 0; i < 5; i++) {
			pets.add(pet2);
		}
		double price = 10 * BASE_RARE_COEF * RARE_INFANCY_COEF;
		double totalPrice = ((4 * price) + 10) * (110 / 100 + 1);
		assertEquals(totalPrice + price, PriceCalculator.calcPrice(pets, 10, 10));
	}
	@Test
	public void SeveralPetsAgesMoreThanINFAndVisitsWithin100Days() {
		for (int i = 0; i < 10; i++) {
			pets.add(pet3);
		}
		double price = 10 * BASE_RARE_COEF;
		double totalPrice = 9 * price;
		assertEquals((totalPrice * DISCOUNT_PRE_VISIT) + 10 + price, PriceCalculator.calcPrice(pets, 10, 10));
	}
	@Test
	public void SeveralPetsAgesMoreThanINFAndVisitsMoreThan100Days() {
		for (int i = 0; i < 10; i++) {
			pets.add(pet4);
		}
		double price = 10 * BASE_RARE_COEF;
		double totalPrice = ((9 * price) + 10) * (110 / 100 + 1);
		assertEquals(totalPrice + price, PriceCalculator.calcPrice(pets, 10, 10));
	}
	@Test
	public void SeveralPetsAgesLessThanINFAndNoVisists() {
		for (int i = 0; i < 5; i++) {
			pets.add(pet5);
		}
		double price = 10 * BASE_RARE_COEF * RARE_INFANCY_COEF;
		double totalPrice = 4 * price;
		assertEquals((totalPrice * DISCOUNT_PRE_VISIT) + 10 + price, PriceCalculator.calcPrice(pets, 10, 10));
	}

	@Test
	public void SeveralPetsAgesMoreThanINFAndNoVisits() {
		for (int i = 0; i < 10; i++) {
			pets.add(pet6);
		}
		double price = 10 * BASE_RARE_COEF;
		double totalPrice = 9 * price;
		assertEquals((totalPrice * DISCOUNT_PRE_VISIT) + 10 + price, PriceCalculator.calcPrice(pets, 10, 10));
	}

	@Test
	public void SeveralAllPets() {
		pets.add(pet1);
		pets.add(pet2);
		pets.add(pet3);
		pets.add(pet4);
		pets.add(pet5);
		pets.add(pet6);
		pets.add(pet7);
		pets.add(pet1);
		pets.add(pet2);
		assertEquals(909.5, PriceCalculator.calcPrice(pets, 10, 10),0.1);
	}
}
