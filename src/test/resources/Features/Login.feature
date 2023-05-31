Feature: Login Functionalities
  @smoke
  Scenario: Valid Admin login
    #Given open the browser and launch HRMS application (you can remove this step because of HOOKS)
    When user enters valid email and valid password
    And click on login button
    Then user is logged in successfully into the application
    #And close the browser (you can remove this step because of HOOKS)

  @smoke2
  Scenario: Valid Admin login
    #Given open the browser and launch HRMS application (you can remove this step because of HOOKS)
    When user enters valid "Admin" and valid "Hum@nhrm123"
    And click on login button
    Then user is logged in successfully into the application
    #And close the browser (you can remove this step because of HOOKS)


    #HOOKS: For defining pre and post steps in any Cucumber framework
    # Always created inside StepDefinitions folder
    # This class cannot be inherited
    # HOOKS will take care of pre and post conditions irrespective of what goes in between test conditions

@scenarioOutline
#Parameterization/Data Driven
  Scenario Outline: Login with multiple credentials using Scenario Outline
    #Given open the browser and launch HRMS application (you can remove this step because of HOOKS)
    When user enters valid "<username>" and valid "<password>"
    And click on login button
    Then user is logged in successfully into the application
    #And close the browser (you can remove this step because of HOOKS)
  Examples:
    | username | password    |
    | admin    | Hum@nhrm123 |
    | Jason    | Hum@nhrm123 |
    | ADMIN    | Hum@nhrm123 |

  @dataTable
  Scenario: Login with multiple credentials using data table
    When user enters username and password and verifies login
      | username | password    |
      | admin    | Hum@nhrm123 |
      | Jason    | Hum@nhrm123 |
      | ADMIN    | Hum@nhrm123 |
