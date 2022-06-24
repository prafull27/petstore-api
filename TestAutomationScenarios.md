# *swagger-petstore*
## List of test cases proposed for automation:

Below are test scenarios for PET, STORE and USER APIs. Have covered positive, negative test scenarios.<br/>
Will cover scenarios either in Unit, Integration or API Regression level.

#### Pros/Cons of covering in Unit level: 
    - Gives quick feedback, faster execution, can cover multiple scenarios with different mocks.
    - Should have for more Unit test coverage, at lower level
    - If issue is identified at lower level, cost of fixing issue is very less
    - Can not catch integration issue between two components
    - Mostly maintained by devs, good Dev-QA collaboration is required to verify coverage/scenarios
    
#### Pros/Cons of covering in API Regression level: 
    - Can validate interactions between components
    - Can have e2e tests to cover possible integrations, so that number of tests can be minimized
    - Should have less number of scenarios in API regression level
    - Gives a delayed feedback compared to Unit
    - Difficult to maintain, if more number of tests are present
    - If issue is identified at higher level, cost of fixing issue is high

### PET endpoints

| SrNo | Endpoint                           | Scenario                                                                                                         | To be covered in |
|------|------------------------------------|------------------------------------------------------------------------------------------------------------------|------------------|
| 1    | POST /pet                          | Verify able to create a Pet with correct pet data                                                                | Unit, Regression |
| 2    | POST /pet                          | Verify response `405 Invalid Input` with invalid pet data <br/> [String in id field]                             | Unit             |
| 3    | POST /pet                          | Verify response `405 Invalid Input` with missing field in pet data <br/> [no id field]                           | Unit             |
| 4    | POST /pet                          | Verify if able to create Pet with duplicate id, should throw error/validation                                    | Regression       |
| 5    | PUT /pet                           | Verify able to update existing Pet successfully                                                                  | Unit, Regression |
| 6    | PUT /pet                           | Verify response as `404 Pet not found` with valid non-existing petId                                             | Regression       |
| 7    | PUT /pet                           | Verify response as `405 Validation exception` with invalid request body<br/> [string value in id field]          | Unit             |
| 8    | PUT /pet                           | Verify response as `405 Validation exception` with invalid request body<br/> [invalid value for pet status enum] | Unit             | 
| 9    | PUT /pet                           | Verify response as `400 Invalid ID supplied` with invalid petId                                                  | Unit             |
| 10   | GET /pet/{petId}                   | Verify able to get the Pet by existing petId                                                                     | Unit             |
| 11   | GET /pet/{petId}                   | Verify response as `404 Pet not found` with non-existing petId                                                   | Unit             |
| 12   | DELETE /pet/{petId}                | Verify able to delete the Pet by existing petId                                                                  | Unit, Regression |
| 13   | DELETE /pet/{petId}                | Verify response as `404 Pet not found` with non-existing petId                                                   | Unit             |
| 14   | POST /pet/{petId}                  | Verify able to update existing Pet                                                                               | Unit, Regression |
| 15   | POST /pet/{petId}                  | Verify response while updating non-existing Pet                                                                  | Unit             |
| 16   | POST /pet/{petId}                  | Verify response as `405 Invalid input` with invalid value for `status`<br/>[invalid value for pet status enum]   | Unit             |
| 17   | POST<br/> /pet/{petId}/uploadImage | Verify able to upload the image successfully                                                                     | Unit             |
| 18   | POST<br/> /pet/{petId}/uploadImage | Verify response when trying to upload image for non-existing petId                                               | Unit             |
| 19   | GET /pet/findByStatus              | Verify able to get correct Pets array with valid pet status, when there are more than 1 matching pets            | Unit, Regression |
| 20   | GET /pet/findByStatus              | Verify response as `400 Invalid status value` with invalid pet status value                                      | Unit             |
| 21   | GET /pet/findByStatus              | Verify able to get single pet, when there is only one Pet available for given pet status                         | Regression       |
| 22   | GET /pet/findByStatus              | Verify response when there are no Pets available for given pet status                                            | Regression       |
| 23   | GET /pet/findByTags                | Verify able to get correct Pets array with valid tags, when there are more than 1 matching pets                  | Regression       |
| 24   | GET /pet/findByTags                | Verify response when there are no Pets available for given tag                                                   | Unit             |
| 25   | GET /pet/findByTags                | Verify response when same tag value is provided with 2 or more queryParams                                       | Unit             |
| 25   | GET /pet/findByTags                | Verify response when only one tag is provided as queryParam                                                      | Unit             |

### STORE endpoints

| SrNo | Endpoint                      | Scenario                                                           | To be covered in |
|------|-------------------------------|--------------------------------------------------------------------|------------------|
| 1    | POST /store/order             | Verify able to place a new order with valid request body           | Unit, Regression |
| 2    | POST /store/order             | Verify response with invalid request body                          | Unit             |
| 3    | POST /store/order             | Verify if we can place same order with same orderId again          | Regression       |
| 5    | GET /store/order/{orderId}    | Verify able to get the Order for an existing order                 | Unit, Regression |
| 4    | GET /store/order/{orderId}    | Verify response `404 Order not found` for non-existing order       | Unit             |
| 6    | DELETE /store/order/{orderId} | Verify able to delete the Order for the existing purchase          | Unit, Regression |
| 7    | DELETE /store/order/{orderId} | Verify response as `404 Order not found` with non-existing orderId | Unit             |
| 8    | GET /store/inventory          | Verify able to get correct inventory response                      | Unit             |

### USER endpoints

| SrNo | Endpoint                  | Scenario                                                                                        | To be covered in |
|------|---------------------------|-------------------------------------------------------------------------------------------------|------------------|
| 1    | POST /user                | Verify able to crete a new user with valid request body                                         | Unit, Regression |
| 2    | POST /user                | Verify response with invalid request body                                                       | Unit             |
| 3    | Post /user/createWithList | Verify able to crete new users with valid multiple users as list                                | Regression       |
| 4    | Post /user/createWithList | Verify response when empty list is provided as input                                            | Unit             |
| 5    | Post /user/createWithList | Verify response when only one user is provided in the list                                      | Unit             |
| 6    | Post /user/createWithList | Verify response when invalid request body is provided                                           | Unit             |
| 7    | GET /user/login           | Verify user is able to login with valid username and password                                   | Regression       |
| 8    | GET /user/login           | Verify user is unable to login with `valid username and invalid password` AND vice-versa        | Unit             |
| 9    | GET /user/login           | Verify user is unable to login when only username is provided as querystring param              | Unit             |
| 10   | GET /user/login           | Verify user is unable to login when only password is provided as querystring param              | Unit             |
| 11   | GET /user/login           | Verify user gets error/validation when trying loggin in with non-existing username and password | Unit             |
| 12   | GET /user/logout          | Verify user gets logged out                                                                     | Regression       |
| 13   | GET /user/{username}      | Verify able to get the user details for an existing user                                        | Regression       |
| 14   | GET /user/{username}      | Verify response as `404 User not found` for non-existing user                                   | Unit             |
| 15   | GET /user/{username}      | Verify response as `400 Invalid username supplied` for invalid username                         | Unit             |
| 16   | PUT /user/{username}      | Verify user details are updated correctly for valid existing username                           | Regression       |
| 17   | PUT /user/{username}      | Verify response as `404 User not found` for non-existing username                               | Unit             |
| 18   | DELETE /user/{username}   | Verify able to delete the user for the existing user                                            | Regression       |
| 19   | DELETE /user/{username}   | Verify response as `404 User not found` with non-existing username                              | Unit             |



