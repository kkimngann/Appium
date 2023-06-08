@tag
Feature: Authorization
  Background:
    Given The application launched
  @Test @Login @Authorize @Invalid
  Scenario: Verify that user can not login with invalid account
    When User go to login screen
    And User input email "Ngan" and password "12345678" and Login
    Then Error email message show correctly "Please enter a valid email address"

  @Test @Login @Authorize @Invalid
  Scenario: Verify that user can not login with invalid account
    When User go to login screen
    And User input email "ngan@gmail.com" and password "" and Login
    Then Error password message show correctly "Please enter at least 8 characters"


  @Test @Login @Authorize @Valid
  Scenario: Verify that user can login with valid account
    When User go to login screen
    And User input email "kkimngann.jk+1@gmail.com" and password "12345678" and submit
    Then Show pop up login correctly

