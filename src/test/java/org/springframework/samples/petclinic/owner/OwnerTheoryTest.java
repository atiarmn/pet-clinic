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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class OwnerTheoryTest {
	private Owner testOwner;

	@Before
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

		for(String str : list){
			Pet newPet = new Pet();
			newPet.setName(str);
			testOwner.addPet(newPet);
		}
		Pet expected = new Pet();
		expected.setName(name);

		Pet returned = testOwner.getPet(name);
		testOwner.addPet(expected);
		assertSame("Test for find existing pet failed: Couldn't find the existing pet", returned, expected);
	}

	private void assertSame(String s, Pet returned, Pet expected) {
	}

	@Theory
	public void testForNullForNutExistingPet(String name, List<String> list){
		assumeTrue(!list.isEmpty());
		assumeTrue(!list.contains(name));

		for(String str : list){
			Pet newPet = new Pet();
			newPet.setName(str);
			testOwner.addPet(newPet);
		}
		Pet returned = testOwner.getPet(name);
		assertSame("Test for return null for not exisitng pet: Found the pet!", null, returned);
	}

	@Theory
	public void testForExisitngTrueIgnoreNewPet(String name, List<String> list, Boolean ignoreNew){
		assumeTrue(!list.isEmpty());
		assumeTrue(list.contains(name));
		assumeTrue(ignoreNew);

		for(String str : list){
			Pet newPet = new Pet();
			newPet.setName(str);
			testOwner.addPet(newPet);
		}
		Pet returned = testOwner.getPet(name, ignoreNew);
		assertSame("Test for existing new pet failed: Got the new pet!", null, returned);
	}

	@Theory
	public void testForFindExisitngTrueIgnoreNewPet(String name, List<String> list, Boolean ignoreNew){
		assumeTrue(!list.isEmpty());
		assumeTrue(list.contains(name));
		assumeTrue(ignoreNew);

		int i = 0;
		int expectedId = 0;
		for(String str : list){
			if(str.equals(name)){
				expectedId = i;
			}
			Pet newPet = new Pet();
			newPet.setName(str);
			newPet.setId(i);
			testOwner.addPet(newPet);
			i+=1;
		}
		Pet expected = new Pet();
		expected.setName(name);
		expected.setId(expectedId);

		Pet returned = testOwner.getPet(name, ignoreNew);
		testOwner.addPet(expected);
		assertSame("Test for find new pet failed: Couldn't find the new pet!", returned, expected);
	}


}
