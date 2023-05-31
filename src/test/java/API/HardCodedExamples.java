package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HardCodedExamples {

    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2ODUxMjIzODcsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY4NTE2NTU4NywidXNlcklkIjoiNTI1MCJ9.iVQf1ZerFA28l88zQpjnPutEPcgJnlNokZ_pZXM3ORE";
    static String employee_id;

    @Test
    public void bgetCreatedEmployee(){
        RequestSpecification preparedRequest = given().
                header("Content-Type","application/json").
                header("Authorization", token).
                queryParam("employee_id",employee_id);

        //hitting the endpoint
        Response response = preparedRequest.when().get("/getOneEmployee.php");
        response.prettyPrint();
        //verify the reponse
        response.then().assertThat().statusCode(200);
        String tempEmpId = response.jsonPath().getString("employee.employee_id");
        //we have 2 emp id, one is global and second is local
        Assert.assertEquals(employee_id, tempEmpId);
    }

    @Test
    public void acreateEmployee(){
        //prepare the request
        RequestSpecification preparedRequest = given().
                header("Content-Type","application/json").
                header("Authorization",token).body("{\n" +
                        "  \"emp_firstname\": \"Melania\",\n" +
                        "  \"emp_lastname\": \"Zlobina\",\n" +
                        "  \"emp_middle_name\": \"M\",\n" +
                        "  \"emp_gender\": \"F\",\n" +
                        "  \"emp_birthday\": \"2018-01-26\",\n" +
                        "  \"emp_status\": \"Confirmed\",\n" +
                        "  \"emp_job_title\": \"future SDET\"\n" +
                        "}");

        //hitting the endpoint/send the request
        Response response = preparedRequest.when().post("/createEmployee.php");
        //it will print the output in the console
        response.prettyPrint();
        //verifying the assertions/ get response
        response.then().assertThat().statusCode(201);

        //verifying the firstname in the response body
        response.then().assertThat().
                body("Employee.emp_firstname", equalTo("Melania"));
        response.then().assertThat().
                body("Employee.emp_lastname", equalTo("Zlobina"));

        //we are capturing employee id from the response
        employee_id = response.jsonPath().getString("Employee.employee_id");
        System.out.println(employee_id);

        //verify the response headers
        response.then().assertThat().header("Content-Type","application/json");

        System.out.println("My test case is passed");

    }
    //in homework, create a method to get all employee status and job title
    @Test
    public void cupdateEmployee(){
        RequestSpecification preparedRequest = given().
                header("Content-Type","application/json").
                header("Authorization", token).body("{\n" +
                        "  \"employee_id\": \""+employee_id+"\",\n" +
                        "  \"emp_firstname\": \"visnja\",\n" +
                        "  \"emp_lastname\": \"hasmik\",\n" +
                        "  \"emp_middle_name\": \"msa\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"2003-05-20\",\n" +
                        "  \"emp_status\": \"probation\",\n" +
                        "  \"emp_job_title\": \"CEO\"\n" +
                        "}");

        //hitting the endpoint
        Response response = preparedRequest.when().put("/updateEmployee.php");
        response.then().assertThat().statusCode(200);
        //for verification
        response.then().assertThat().body("Message", equalTo("Employee record Updated"));
    }
    @Test
    public void dgetUpdatedEmployee(){
        RequestSpecification preparedRequest = given().
                header("Content-Type","application/json").
                header("Authorization", token).
                queryParam("employee_id",employee_id);

        Response response = preparedRequest.when().get("/getOneEmployee.php");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        //if you want to verify the body of the response.
        //you can do that using hamcrest matchers

    }

}