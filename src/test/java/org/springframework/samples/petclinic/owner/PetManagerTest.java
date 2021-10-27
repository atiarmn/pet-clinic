package org.springframework.samples.petclinic.owner;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.utility.SimpleDI;

import org.slf4j.Logger;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class PetManagerTest {
	private static final Integer ownerId = 1, petId = 1;
	@Mock
	private PetTimedCache pets;
	@Mock
	private OwnerRepository owners;
	@Mock
	private Logger log;

	@InjectMocks
	private PetManager petManager;

	@BeforeEach
	public void SetUp(){
		this.pets = mock(PetTimedCache.class);
		this.owners = mock(OwnerRepository.class);
		this.log = mock(Logger.class);
		this.petManager = new PetManager(pets, owners, log);
	}

	//Mock - state and behavior verification - Mockisty
	@Test
	public void FindOwner_ExistingOwner_FindTheOwner() {
		Owner expectedOwner = mock(Owner.class);
		expectedOwner.setId(ownerId);
		when(owners.findById(ownerId)).thenReturn(expectedOwner);
		Owner returnedOwner =  petManager.findOwner(ownerId);
		verify(log).info("find owner {}", ownerId);
		assertEquals("Test for find existing owner failed: Returned owner is not equal to expected owner!",expectedOwner, returnedOwner);
	}

	//Mock - state and behavior verification - Mockisty
	@Test
	public void FindOwner_MissingOwner_ReturnNull() {
		when(owners.findById(ownerId)).thenReturn(null);
		Owner returnedOwner =  petManager.findOwner(ownerId);

		verify(log).info("find owner {}", ownerId);
		assertEquals("Test for find missing owner failed: Returned owner is not equal null!",null, returnedOwner);
	}

	//Mock - state and behavior verification - Mockisty
	@Test
	public void NewPet_GivenOwner_AddPetSuccessfully() {
		Owner owner = mock(Owner.class);
		when(owner.getId()).thenReturn(ownerId);
		petManager.newPet(owner);
		verify(log).info("add pet for owner {}", ownerId);
		verify(owner).addPet(isA(Pet.class));
	}

	//Mock - state and behavior verification - Mockisty
	@Test
	public void FindPet_ExistingPet_FindThePet() {
		Pet expectedPet = mock(Pet.class);
		expectedPet.setId(petId);
		when(pets.get(petId)).thenReturn(expectedPet);
		Pet returnedPet =  petManager.findPet(petId);
		verify(log).info("find pet by id {}", petId);
		assertEquals("Test for find existing pet failed: Returned pet is not equal to expected pet!",returnedPet, expectedPet);
	}

	//Mock - state and behavior verification - Mockisty
	@Test
	public void FindPet_MissingPet_FindThePet() {
		when(pets.get(petId)).thenReturn(null);
		Pet returnedPet =  petManager.findPet(petId);
		verify(log).info("find pet by id {}", petId);
		assertEquals("Test for find missing pet failed: Returned pet is not equal to expected pet!",null, returnedPet);
	}

	//Mock - state and behavior verification - Mockisty
	@Test
	public void GetOwnerPets_ExisitngOwner_ReturnOwnerPets() {
		Owner owner = mock(Owner.class);
		owner.setId(ownerId);

		Pet dog = new Pet();
		dog.setId(2);
		dog.setName("dog");
		dog.setOwner(owner);

		Pet bird = new Pet();
		bird.setId(3);
		bird.setName("bird");
		bird.setOwner(owner);

		Pet cat = new Pet();
		cat.setId(4);
		cat.setName("cat");
		cat.setOwner(owner);

		List<Pet> petList = new ArrayList<>();
		petList.add(dog);
		petList.add(bird);
		petList.add(cat);

		when(owner.getPets()).thenReturn(petList);
		when(petManager.findOwner(ownerId)).thenReturn(owner);

		List<Pet> returnedPets = petManager.getOwnerPets(ownerId);
		assertEquals("Test for get existing owner pets failed: Returned pets are not equal to expected pets!", petList,returnedPets);
		verify(log).info("finding the owner's pets by id {}", ownerId);
		verify(owner).getPets();
	}

	//Mock - state and behavior verification - Mockisty

	/*@Test
	public void GetOwnerPets_MissingOwner_ReturnNull() {
		when(petManager.findOwner(ownerId)).thenReturn(null);

		List<Pet> returnedPets = petManager.getOwnerPets(ownerId);
		assertEquals("Test for get missing owner pets failed: Returned pets are not equal to null!", null,returnedPets);
		verify(log).info("finding the owner's pets by id {}", ownerId);
	}*/

	//Mock - state and behavior verification - Mockisty

	@Test
	public void GetOwnerPetType_ExistingOwner_ReturnTypes(){
		Owner owner = mock(Owner.class);
		owner.setId(ownerId);
		when(petManager.findOwner(ownerId)).thenReturn(owner);

		Pet dog = new Pet();
		PetType dogType = new PetType();
		dogType.setName("dogType");
		dog.setType(dogType);
		dog.setId(2);
		dog.setName("dog");

		Pet bird = new Pet();
		PetType birdType = new PetType();
		birdType.setName("birdType");
		bird.setType(birdType);
		bird.setId(3);
		bird.setName("bird");

		Pet cat = new Pet();
		PetType catType = new PetType();
		catType.setName("catType");
		cat.setType(catType);
		cat.setId(4);
		cat.setName("cat");
		cat.setOwner(owner);

		List<Pet> petList = new ArrayList<>();
		petList.add(dog);
		petList.add(bird);
		petList.add(cat);

		Set<PetType> petTypes =  new HashSet<>();
		petTypes.add(dogType);
		petTypes.add(birdType);
		petTypes.add(catType);

		when(owner.getPets()).thenReturn(petList);
		Set<PetType> returnedTypes = petManager.getOwnerPetTypes(ownerId);

		assertEquals("Test for get existing owner pet types failed: Returned pet types are not equal to expected pet types!", petTypes,returnedTypes);
		verify(owner).getPets();
		verify(log).info("finding the owner's petTypes by id {}", ownerId);
	}

	//Mock - state and behavior verification - Mockisty
	/*@Test
	public void GetOwnerPetType_MissingOwner_ReturnNull(){
		when(petManager.findOwner(ownerId)).thenReturn(null);
		Set<PetType> returnedTypes = petManager.getOwnerPetTypes(ownerId);
		assertEquals("Test for get missing owner pettypes failed: Returned pet types are not equal to null!", null, returnedTypes);
		verify(log).info("finding the owner's petTypes by id {}", ownerId);
	}*/

	//Mock - behavior verification - Mockisty
	@Test
	public void SavePet_GivenOwnerAndPet_SavePetSuccessfully() {
		Owner testOwner = mock(Owner.class);
		Pet testPet = mock(Pet.class);

		when(testOwner.getId()).thenReturn(ownerId);
		when(testPet.getId()).thenReturn(petId);
		this.petManager.savePet(testPet, testOwner);

		verify(testOwner).addPet(testPet);
		verify(pets).save(testPet);
		verify(log).info("save pet {}", petId);

	}

	//Mock - state and behavior verification - Mockisty
	@Test
	public void GetVisitsBetween_GivenDateAndPet_ReturnVisitsBetween() {
		Pet testPet = mock(Pet.class);
		when(pets.get(petId)).thenReturn(testPet);

		Visit testVisit1 = mock(Visit.class);
		Visit testVisit2 = mock(Visit.class);
		Visit testVisit3 = mock(Visit.class);
		List<Visit> expectedVisitsList = new ArrayList<>();
		expectedVisitsList.add(testVisit1);
		expectedVisitsList.add(testVisit2);
		expectedVisitsList.add(testVisit3);

		LocalDate testStartDate = LocalDate.of(2021, 10, 13);
		LocalDate testEndDate = LocalDate.of(2021, 10, 27);

		when(testPet.getVisitsBetween(testStartDate, testEndDate)).thenReturn(expectedVisitsList);

		List<Visit> actualVisitsList = petManager.getVisitsBetween(petId, testStartDate, testEndDate);
		assertArrayEquals("Get Visits Between Test Failed: Incompatible Visits List", expectedVisitsList.toArray(), actualVisitsList.toArray());
		verify(log).info("get visits for pet {} from {} since {}", petId, testStartDate, testEndDate);
	}

	@AfterEach
	public void TearDown(){
		this.pets = null;
		this.owners = null;
		this.log = null;
		this.petManager = null;
	}
}
