@API @REGRESSION
Feature: API Feature

  Scenario: Login
    When I get baseurl for API

    And I submit POST request for POSTSAPI
    Then I verify statuscode as "201"

    And I submit GET request for POSTSAPI
    Then I verify statuscode as "200"

    And I submit PUT request for POSTSAPI
    Then I verify statuscode as "200"

    And I submit DELETE request for POSTSAPI
    Then I verify statuscode as "200"