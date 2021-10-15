package org.springframework.samples.petclinic.owner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.*;

@RunWith(Theories.class)
class PetTest {

	private Pet testPet;
	public static Set[] Pets = {

	};


	@Before
	public void setUp(){
		testPet = new Pet();
	}

	@Theory
	public void testForGetVisits() {
		List<Visit> sortedVisits = new ArrayList<>(testPet.getVisitsInternal());
		PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
	}

	@After
	public void tearDown(){
		testPet = null;
	}
}
