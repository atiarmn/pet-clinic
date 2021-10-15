package org.springframework.samples.petclinic.owner;

import io.cucumber.java.eo.Do;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.utility.SimpleDI;

import java.util.Arrays;
import java.util.Collection;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class PetServiceTest {

	private PetService testPetService;
	private final int id;
	private final Pet pet;
	private final PetTimedCache cache;
	private OwnerRepository owner;
	private static Pet Dog, Cat, Bird, Rabbit, Mouse, Dolphin;

	public PetServiceTest (int i, Pet expected) {
		this.id= i;
		this.pet = expected;

		cache = mock(PetTimedCache.class);
		OwnerRepository owner = mock(OwnerRepository.class);
		Logger logger = LoggerFactory.getLogger("");
		testPetService = new PetService(cache, owner, logger);
	}

	@Before
	public void setUp(){
		when(cache.get(1)).thenReturn(Dog);
		when(cache.get(2)).thenReturn(Cat);
		when(cache.get(3)).thenReturn(Bird);
		when(cache.get(4)).thenReturn(Rabbit);
		when(cache.get(5)).thenReturn(Mouse);
		when(cache.get(6)).thenReturn(Dolphin);
	}

	@Parameterized.Parameters
	public static Collection parameters() {
		Dog = new Pet();
		Dog.setId(1);
		Dog.setName("Dog");

		Cat = new Pet();
		Cat.setId(2);
		Cat.setName("Cat");

		Bird = new Pet();
		Bird.setId(3);
		Bird.setName("Bird");

		Rabbit = new Pet();
		Rabbit.setId(4);
		Rabbit.setName("Rabbit");

		Mouse = new Pet();
		Mouse.setId(5);
		Mouse.setName("Mouse");

		Dolphin = new Pet();
		Dolphin.setId(6);
		Dolphin.setName("Dolphin");
		return Arrays.asList (new Object [][]{
			{1, Dog}, {2, Cat}, {3, Bird}, {4, Rabbit}, {5, Mouse}, {6, Dolphin},{7, null}
		});
	}
	@Test
	public void findPetTest() {
		assertEquals(testPetService.findPet(this.id), this.pet);
	}
}
