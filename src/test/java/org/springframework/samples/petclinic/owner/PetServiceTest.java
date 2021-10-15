package org.springframework.samples.petclinic.owner;

import io.cucumber.java.eo.Do;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.samples.petclinic.utility.SimpleDI;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
class PetServiceTest {

	private PetService testPetService;
	public int id;
	public Pet pet;

	private static Pet Dog, Cat, Bird, Rabbit, Mouse, Dolphin;

	public void PetServiceTest (int i, Pet expected) {
		this.id= i;
		this.pet = expected;
	}

	@Before
	public void setUp(){
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

	}

	@Parameterized.Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList (new Object [][]{
			{1, Cat}, {2, Dog}, {3, Bird}, {4, Rabbit}, {5, Mouse}, {6, Dolphin}
		});
	}
	@Test
	public void findPetTest() {
		assertEquals("Find pet test failed: Couldn't find pet correctly!", testPetService.findPet(this.id).getName(), this.pet.getName());
	}

	@After
	public void tearDown(){
		Dog = null;
		Cat = null;
		Bird = null;
		Rabbit = null;
		Mouse = null;
		Dolphin = null;
	}
}
