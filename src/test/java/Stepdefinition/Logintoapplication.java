package Stepdefinition;

import Pages.MintableStore;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

import java.io.IOException;
import java.net.MalformedURLException;

public class Logintoapplication {

    @Steps
    MintableStore store;


    @Given("user is in store page")
    public void user_is_in_store_page(){
        store.openApplication();
    }

    @When("user successfully navigated to the page")
    public void user_successfully_navigated_to_the_page(){
        store.isCollectionLabelExist();
    }

    @Then("validate for broken image")
    public void validate_for_broken_image() throws IOException {
        store.isBrokenImagesAvailable();
    }



    @When("user type as {string}")
    public void user_type_as_bored_ape(String searchfor) {
        store.searchFor(searchfor);
    }

    @Then("validate {string} is visible")
    public void validate_is_visible(String item){
        store.visible(item);
    }

    @When("user type as {string} in username")
    public void user_type_as_in_username(String username){
        store.enterUsername(username);
    }


    @When("user type as {string} in password")
    public void user_type_as_in_password(String password){
        store.enterPassword(password);
    }

    @Then("system allows user to login")
    public void system_allows_user_to_login(){
        store.clickLogin();
    }
}
