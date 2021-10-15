package org.springframework.samples.petclinic.owner;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
class PetTest {

	private Pet testPet;

	@Before
	public void setUp(){
		testPet = new Pet();
		testPet.setId(1);
	}

	@DataPoints
	public static Visit[][] visits = {
		{
			new Visit().setDate(LocalDate.of(1999, 12, 14)),
			new Visit().setDate(LocalDate.of(2000, 4, 2)),
			new Visit().setDate(LocalDate.of(2020, 10, 5)),
			new Visit().setDate(LocalDate.of(2021, 2, 10))
		},
		{
			new Visit().setDate(LocalDate.of(1920, 1, 3)),
			new Visit().setDate(LocalDate.of(2022, 10, 4)),
			new Visit().setDate(LocalDate.of(2000, 2, 12))
		},
		{
			new Visit().setDate(LocalDate.of(2016, 11, 2)),
			new Visit().setDate(LocalDate.of(2018, 10, 4)),
			new Visit().setDate(LocalDate.of(2001, 2, 12))
		}
	};

	@Theory
	public void testForGetVisitsInOrder(Visit[] visits) {
		assumeTrue(visits.length < 3);

		Collection<Visit> testVisits = new LinkedHashSet<>();

		for(Visit v : visits) {
			testVisits.add((v));
		}
		testPet.setVisitsInternal(testVisits);
		List<Visit> sortedVisits = new ArrayList<>(testPet.getVisits());
		for(Visit v : visits){
			assertTrue("Test for get visits elements failed: The returned visits doesn't contain a visit",sortedVisits.contains(v));
		}
		PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
		assertArrayEquals("Test for get visits in order failed: The order is incorrect!", sortedVisits.toArray(), testVisits.toArray());

	}

	@After
	public void tearDown(){
		testPet = null;
	}
}
