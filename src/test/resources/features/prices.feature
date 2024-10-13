Feature: Prices

  Scenario Outline: Get applicable price
    Given a valid request
    When criteria given is <brandId>, <productId>, "<applicationDate>"
    Then system should respond with an applicable price <price> with currency "EUR"

    Examples:
      | brandId | productId | applicationDate      | price |
      | 1       | 35455     | 2020-06-14T10:00:00Z | 35.5  |
      | 1       | 35455     | 2020-06-14T16:00:00Z | 25.45 |
      | 1       | 35455     | 2020-06-14T21:00:00Z | 35.5  |
      | 1       | 35455     | 2020-06-15T10:00:00Z | 30.5  |
      | 1       | 35455     | 2020-06-16T21:00:00Z | 38.95 |

  Scenario Outline: Invalid request
    Given an invalid request
    When criteria given is <brandId>, <productId>, "<applicationDate>"
    Then system should return a 400 error with message "<errorMessage>"

    Examples:
      | brandId | productId | applicationDate      | errorMessage                                    |
      | 1       | -1        | 2020-06-14T10:00:00Z | Mandatory parameter is not provided             |
      | 1       | 35455     | invalidDate          | Provided parameter does not match expected type |
      | 0       | 0         | 2020-06-14T21:00:00Z | Provided parameter/s are invalid                |

  Scenario: Price not found
    Given a valid request
    When criteria given is 1, 35456, "2020-06-14T10:00:00Z"
    Then system should return a 404 error with message "Entity not found by given parameters"