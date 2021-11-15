package org.springframework.samples.petclinic.model.priceCalculators;


	import org.junit.After;
	import org.junit.Before;
	import org.junit.Test;
	import org.springframework.samples.petclinic.model.Pet;
	import org.springframework.samples.petclinic.model.PetType;
	import org.springframework.samples.petclinic.model.UserType;
	import org.springframework.security.core.parameters.P;

	import java.util.*;
	import static org.mockito.Mockito.*;
	import static org.junit.Assert.*;

	import static org.junit.Assert.*;

public class CustomerDependentPriceCalculatorTest {
	CustomerDependentPriceCalculator customerDependentPriceCalculator;
	private List<Pet> pets;
	private PetType petType1,petType2;
	private Date date1,date2;
	private double delta = 0.001;

	@Before
	public void setup(){
		pets = new ArrayList<>();

		petType1 = mock(PetType.class);
		petType2 = mock(PetType.class);

		date1 = new GregorianCalendar(2000, Calendar.OCTOBER, 12).getTime();
		date2 = new GregorianCalendar(2020, Calendar.OCTOBER, 12).getTime();

		customerDependentPriceCalculator = new CustomerDependentPriceCalculator();
	}
	@After
	public void tearDown() {

	}
	private List<Pet> petsForLessThanMin(){
		pets = new ArrayList<>();
		Pet pet1 = new Pet();
		Pet pet2 = new Pet();
		Pet pet3 = new Pet();
		Pet pet4 = new Pet();

		pet1.setType(petType1);
		pet2.setType(petType2);
		pet3.setType(petType2);
		pet4.setType(petType1);

		pet1.setBirthDate(date1);
		pet2.setBirthDate(date2);
		pet3.setBirthDate(date1);
		pet4.setBirthDate(date2);

		pets.add(pet1);
		pets.add(pet2);
		pets.add(pet3);
		pets.add(pet4);
		return pets;
	}
	private List<Pet> petsForMoreThanMin(){
		pets = new ArrayList<>();
		Pet pet1 = new Pet();
		Pet pet2 = new Pet();
		Pet pet3 = new Pet();
		Pet pet4 = new Pet();
		Pet pet5 = new Pet();
		Pet pet6 = new Pet();
		Pet pet7 = new Pet();

		pet1.setType(petType1);
		pet2.setType(petType1);
		pet3.setType(petType1);
		pet4.setType(petType1);
		pet5.setType(petType1);
		pet6.setType(petType2);
		pet7.setType(petType2);

		pet1.setBirthDate(date2);
		pet2.setBirthDate(date2);
		pet3.setBirthDate(date2);
		pet4.setBirthDate(date2);
		pet5.setBirthDate(date2);
		pet6.setBirthDate(date2);
		pet7.setBirthDate(date1);

		pets.add(pet1);
		pets.add(pet2);
		pets.add(pet3);
		pets.add(pet4);
		pets.add(pet5);
		pets.add(pet6);
		pets.add(pet7);

		return pets;
	}
	@Test
	public void CalcPriceNEWAndDiscountScoreLessThanMin() {
		pets = petsForLessThanMin();
		when(petType1.getRare()).thenReturn(true);
		when(petType2.getRare()).thenReturn(false);
		double res = customerDependentPriceCalculator.calcPrice(pets, 0.1, 1, UserType.NEW);
		assertEquals(res, 5.08, delta);
	}
	@Test
	public void CalcPriceGOLDAndDiscountScoreLessThanMin() {
		pets = petsForLessThanMin();
		when(petType1.getRare()).thenReturn(true);
		when(petType2.getRare()).thenReturn(false);
		double res = customerDependentPriceCalculator.calcPrice(pets, 0.1, 1, UserType.GOLD);
		assertEquals(res, 4.164, delta);
	}
	@Test
	public void CalcPriceNEWAndDiscountScoreMoreThanMin() {
		pets = petsForMoreThanMin();
		when(petType1.getRare()).thenReturn(true);
		when(petType2.getRare()).thenReturn(false);
		double res = customerDependentPriceCalculator.calcPrice(pets, 0.1, 1, UserType.NEW);
		assertEquals(res, 10.169, delta);
	}
	@Test
	public void CalcPriceGOLDAndDiscountScoreMoreThanMin() {
		pets = petsForMoreThanMin();
		when(petType1.getRare()).thenReturn(true);
		when(petType2.getRare()).thenReturn(false);
		double res = customerDependentPriceCalculator.calcPrice(pets, 0.1, 1, UserType.GOLD);
		assertEquals(res, 8.56, delta);
	}

}
