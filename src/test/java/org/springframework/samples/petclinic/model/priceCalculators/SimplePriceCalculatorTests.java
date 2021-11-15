package org.springframework.samples.petclinic.model.priceCalculators;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;

import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import static org.junit.Assert.*;

public class SimplePriceCalculatorTests {
	SimplePriceCalculator simplePriceCalculator;
	private List<Pet> pets;
	private Pet pet;
	private PetType petType;

	private double delta = 0.0001;

	@Before
	public void setup(){
		pets = new ArrayList<>();
		pet = new Pet();
		petType = mock(PetType.class);
		pet.setType(petType);
		simplePriceCalculator = new SimplePriceCalculator();
	}
	@After
	public void tearDown() {
		pet = null;
		pets = null;
	}
	@Test
	public void CalcPriceTestJustFirstIf() {
		when(petType.getRare()).thenReturn(true);
		pets.add(pet);
		double res = simplePriceCalculator.calcPrice(pets, 0.1, 1, UserType.GOLD);
		assertEquals(res, 1.3, delta);
	}
	@Test
	public void CalcPriceTestJustFirstElse(){
		when(petType.getRare()).thenReturn(false);
		pets.add(pet);
		double res = simplePriceCalculator.calcPrice(pets, 0.1, 1, UserType.GOLD);
		assertEquals(res, 1.1, delta);
	}

	@Test
	public void CalcPriceTestFirstIfSecondIf() {
		when(petType.getRare()).thenReturn(true);
		pets.add(pet);
		double res = simplePriceCalculator.calcPrice(pets, 0.1, 1, UserType.NEW);
		assertEquals(res, 1.235, delta);
	}
	@Test
	public void CalcPriceTestFirstElseSecondIf() {
		when(petType.getRare()).thenReturn(false);
		pets.add(pet);
		double res = simplePriceCalculator.calcPrice(pets, 0.1, 1, UserType.NEW);
		assertEquals(res, 1.045, delta);
	}
	@Test
	public void CalcPriceTestJustSecondIf() {
		double res = simplePriceCalculator.calcPrice(pets, 0.1, 1, UserType.NEW);
		assertEquals(res, 0.095, delta);
	}
	@Test
	public void CalcPriceTestNoIf() {
		double res = simplePriceCalculator.calcPrice(pets, 0.1, 1, UserType.GOLD);
		assertEquals(res, 0.1, delta);
	}
}
