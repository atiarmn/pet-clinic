package org.springframework.samples.petclinic.owner;

import io.cucumber.java.hu.Ha;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import java.util.*;

import org.junit.*;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class OwnerTest {
	private Owner testOwner;
	private Set<Pet> testPets;

	private Pet testPet1, testPet2, testPet3;

	@Before
	public void setup() {
		testOwner = new Owner();
		testPets = new HashSet<>();

		testPet1 = new Pet();
		testPet1.setName("Dog");

		testPet2 = new Pet();
		testPet2.setName("Cat");

		testPet3 = new Pet();
		testPet3.setName("Bat");

	}
	// From Here


	@Test
	public void testForGetSetAddress() {
		String testAddress = "Iran-Tehran";
		testOwner.setAddress(testAddress);
		Assertions.assertEquals(testOwner.getAddress(), testAddress, "Test for get/set address failed: Address is not set correctly!");
	}

	@Test
	public void testForGetSetCity() {
		String testCity = "Tehran";
		testOwner.setCity(testCity);
		Assertions.assertEquals(testOwner.getCity(), testCity, "Test for get/set city failed: City is not set correctly!");
	}

	@Test
	public void testForGetSetTelephone() {
		String testTel = "09123456789";
		testOwner.setTelephone(testTel);
		Assertions.assertEquals(testOwner.getTelephone(), testTel, "Test for get/set phone number failed: Telephone is not set correctly!");
	}

	//Up to Here


	@Test
	public void testForGetNullInternalPets() {
		Set<Pet> internalPets = testOwner.getPetsInternal();
		assertEquals("Test for get null internal pets failed: Not an empty set!", internalPets, new HashSet<>());
		assertNotNull("Test for get null internal pets failed: Internal pets set is null!", internalPets);
	}

	@Test
	public void testForGetNotNullInternalPets() {
		testPets.add(testPet1);
		testPets.add(testPet2);
		testOwner.setPetsInternal(testPets);
		Set<Pet> internalPets = testOwner.getPetsInternal();
		assertFalse("Test for get not null internal pets failed: An empty set!", internalPets.equals(new HashSet<>()));
	}

	@Test
	public void testForNullGetPets() {
		List<Pet> sortedPets = testOwner.getPets();
		assertTrue("Test for null get pets failed: Returned list is not empty!", sortedPets.isEmpty());
	}

	@Test
	public void testForNotNullGetPets() {
		testPets.add(testPet1);
		testPets.add(testPet2);
		testOwner.setPetsInternal(testPets);
		List<Pet> internalPets = new ArrayList<>();
		internalPets.add(testPet1);
		internalPets.add(testPet2);
		PropertyComparator.sort(internalPets, new MutableSortDefinition("name", true, true));
		List<Pet> sortedPets = testOwner.getPets();
		assertEquals("Test for get pets failed: The list is not equal to list of pets!", internalPets, sortedPets);
	}

	@Test
	public void testForAddPet() {
		testOwner.addPet(testPet1);
		assertNotEquals("Test for add pet failed: The new pet wasn't added to the internal pets of the owner!", testOwner.getPetsInternal(), new HashSet<>());
		assertEquals("Test for add pet failed: The owner of the new pet is someone else!", testPet1.getOwner(), testOwner);

		testPet2.setId(1);
		testOwner.addPet((testPet2));
		assertEquals("Test for add pet failed: The owner of the old pet is someone else!", testPet2.getOwner(), testOwner);

	}

	@Test
	public void testForRemovePet() {
		testPets.add(testPet1);
		testPets.add(testPet2);
		testOwner.setPetsInternal(testPets);
		testOwner.removePet(testPet1);
		assertFalse("Test for remove a pet failed: The pet wasn't removed!", testOwner.getPetsInternal().contains(testPet1));
	}

	@Test
	public void testForGetAllPets() {
		testPets.add(testPet1);
		testPets.add(testPet2);
		testOwner.setPetsInternal(testPets);
		assertNull("Test for get pet failed: The pet was not supposed to be found!", testOwner.getPet("Bird"));
		assertEquals("Test for get pet failed: The pet was not found!", "Dog", testOwner.getPet("Dog").getName());
	}

	@Test
	public void testForGetOldPets() {
		testPets.add(testPet3); // ant
		testOwner.setPetsInternal(testPets);
		assertTrue("Test for get pet failed: The pet was not supposed to be found!", testOwner.getPet("Bird", true) == null);
	}

	@After
	public void tearDown(){
		testOwner = null;
		testPets = null;
		testPet1 = null;
		testPet2 = null;
	}
}
