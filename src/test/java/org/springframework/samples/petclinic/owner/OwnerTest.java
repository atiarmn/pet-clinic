package org.springframework.samples.petclinic.owner;

import io.cucumber.java.hu.Ha;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {
	private Owner testOwner;
	private Set<Pet> testPets;

	private Pet testPet1, testPet2;

	@BeforeEach
	public void setUp() {
		testOwner = new Owner();
		testPets = new HashSet<>();

		testPet1 = new Pet();
		testPet2.setName("Dog");

		testPet2 = new Pet();
		testPet2.setName("Cat");
	}

	@Test
	public void testForGetNullInternalPets() {
		Set<Pet> internalPets = testOwner.getPetsInternal();
		assertTrue("Test for get null internal pets failed: Not an empty set!", internalPets.equals(new HashSet<>()));
		assertFalse("Test for get null internal pets failed: Internal pets set is null!", internalPets.equals(null));
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
		List<Pet> sortedPets = testOwner.getPets();
		assertTrue("Test for get pets failed: The list is not equal to list of pets!", internalPets.equals(sortedPets));
	}

	@Test
	public void testForAddPet() {
		testOwner.addPet(testPet1);
		assertFalse("Test for add pet failed: The new pet wasn't added to the internal pets of the owner!", testOwner.getPetsInternal().equals(new HashSet<>()));
		assertTrue("Test for add pet failed: The owner of the new pet is someone else!", testPet1.getOwner().equals(testOwner));

		testPet2.setId(1);
		testOwner.addPet((testPet2));
		assertTrue("Test for addpet failed: The owner of the old pet is someone else!", testPet2.getOwner().equals(testOwner));

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
	public void testForGetPet() {
		testPets.add(testPet1);
		testPets.add(testPet2);
		testOwner.setPetsInternal(testPets);
		assertTrue("Test for get pet failed: The pet was found! ", testOwner.getPet("Bird").equals(null));
		assertFalse("Test for get pet failed: The pet was not found!", testOwner.getPet("Dog").equals(null));
	}

	@AfterEach
	public void tearDown(){
		testOwner = null;
		testPets = null;
		testPet1 = null;
		testPet2 = null;
	}
}
