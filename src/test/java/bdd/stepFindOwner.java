package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class stepFindOwner {
	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	private Owner owner;
	private Owner foundOwner;

	@Given("There are some owners with specific IDs")
	public void the_client_issues_find_owner() {
		owner = new Owner();
		owner.setFirstName("mark");
		owner.setLastName("paterson");
		owner.setAddress("gheytarie");
		owner.setCity("tehran");
		owner.setTelephone("09213824900");
		owner.setId(3);
		ownerRepository.save(owner);
	}

	@When("We want to find an owner with ID = 3")
	public void find_owner_by_id() {
		foundOwner = petService.findOwner(3);
	}

	@Then("The owner with ID = 3 will be found and returned successfully")
	public void owner_found() {
		assertEquals(owner.getId(), foundOwner.getId());
	}
}
