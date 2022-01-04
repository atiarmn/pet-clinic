package org.springframework.samples.petclinic.owner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.samples.petclinic.utility.SimpleDI;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
class PetServiceTest {


}
