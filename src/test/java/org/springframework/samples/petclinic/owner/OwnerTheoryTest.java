package org.springframework.samples.petclinic.owner;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class OwnerTheoryTest {
	private Owner testOwner;

	@BeforeEach
	public void setUp() {
		testOwner = new Owner();
	}
	@DataPoints
	public static String[] names = {"Dog", "Bird", "Rabbit", "Cat", "Mouse"};

	@DataPoints
	public static List<List<String>> petsList() {
		List<List<String>> list = new ArrayList<>();
		list.add(asList("Bird", "Dog"));
		list.add(asList("Dog", "Bird", "Rabbit", "Cat", "Mouse"));
		list.add(asList("Dog", "Bird", "Rabbit", "Cat"));
		list.add(asList("Mouse"));
		list.add(asList("Dog", "Cat", "Mouse"));
		return list;
	}

	@DataPoints
	public static Boolean[] ignoreNew = {Boolean.TRUE, Boolean.FALSE};

	@Theory
	public void testForFindingExisitngPet(String name, List<String> list){
		assumeTrue(!list.isEmpty());
		assumeTrue(list.contains(name));

		Set<Pet> testPets = new HashSet<>();
		for(String str : list){
			Pet newPet = new Pet();
			newPet.setName(str);
			testPets.add(newPet);
		}
		testOwner.setPetsInternal(testPets);
		Pet expected = new Pet();
		expected.setName(name);

		Pet returned = testOwner.getPet(name);
		assumeTrue("Test for find existing pet failed: Couldn't find the existing pet",returned.equals(expected));
	}
	@Theory
	public void testForNullForNutExistingPet(String name, List<String> list){
		assumeTrue(!list.isEmpty());
		assumeTrue(!list.contains(name));

		Set<Pet> testPets = new HashSet<>();
		for(String str : list){
			Pet newPet = new Pet();
			newPet.setName(str);
			testPets.add(newPet);
		}
		testOwner.setPetsInternal(testPets);
		Pet returned = testOwner.getPet(name);
		assumeTrue("Test for return null for not exisitng pet: Found the pet!",returned.equals(null));
	}

	@Theory
	public void testForExisitngTrueIgnoreNewPet(String name, List<String> list, Boolean ignoreNew){
		assumeTrue(!list.isEmpty());
		assumeTrue(list.contains(name));
		assumeTrue(ignoreNew);

		Set<Pet> testPets = new HashSet<>();
		for(String str : list){
			Pet newPet = new Pet();
			newPet.setName(str);
			testPets.add(newPet);
		}
		testOwner.setPetsInternal(testPets);
		Pet returned = testOwner.getPet(name, ignoreNew);
		assumeTrue("Test for existing new pet failed: Got the new pet!",returned.equals(null));
	}

	@Theory
	public void testForFindExisitngTrueIgnoreNewPet(String name, List<String> list, Boolean ignoreNew){
		assumeTrue(!list.isEmpty());
		assumeTrue(list.contains(name));
		assumeTrue(ignoreNew);

		Set<Pet> testPets = new HashSet<>();
		int i = 0;
		int expectedId = 0;
		for(String str : list){
			if(str.equals(name)){
				expectedId = i;
			}
			Pet newPet = new Pet();
			newPet.setName(str);
			newPet.setId(i);
			testPets.add(newPet);
			i+=1;
		}
		testOwner.setPetsInternal(testPets);
		Pet expected = new Pet();
		expected.setName(name);
		expected.setId(expectedId);

		Pet returned = testOwner.getPet(name, ignoreNew);
		assumeTrue("Test for find new pet failed: Couldn't find the new pet!",returned.equals(expected));
	}


}
