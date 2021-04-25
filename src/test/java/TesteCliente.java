import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class TesteCliente {

    String enderecoApiCliente ="http://localhost:8080/";
    String endpointCliente ="cliente";
    @Test
    @DisplayName("Quando pegar todos os clientes sem cadastrar clientes, então a lista deve estar vazia.")
    public void pegarTodosOsCliente(){

        String respostaEsperada = "{}";

        given()
                .contentType(ContentType.JSON)
        .when()
                .get(enderecoApiCliente)
        .then()
                .statusCode(200)
                .assertThat().body(new IsEqual<>(respostaEsperada));
    }

    @Test
    @DisplayName("Quando cadastrar um cliente, então ele deve estar disponível no resultado.")
    public void cadastraClientes(){

        String clienteParaCadastrar ="{\n" +
                "    \"id\":1004,\n" +
                "    \"idade\": 25,\n" +
                "    \"nome\": \"Adan\",\n" +
                "    \"risco\": 0\n" +
                "}";


        String respostaEsperada = "{\"1004\":{\"nome\":\"Adan\",\"idade\":25,\"id\":1004,\"risco\":0}}";

        given()
                .contentType(ContentType.JSON)
                .body(clienteParaCadastrar)
        .when()
                .post(enderecoApiCliente+endpointCliente)
        .then()
                .statusCode(201)
                .assertThat().body(containsString(respostaEsperada));
    }

    @Test
    @DisplayName("Quando eu atualizar um cliente, então o cliente deve ser atualizado.")
    public void atualizaClientes(){

        String clienteParaCadastrar ="{\n" +
                "    \"id\":1004,\n" +
                "    \"idade\": 25,\n" +
                "    \"nome\": \"Adan\",\n" +
                "    \"risco\": 0\n" +
                "}";

        String clienteAtualizado ="{\n" +
                "    \"id\":1004,\n" +
                "    \"idade\": 22,\n" +
                "    \"nome\": \"Ian\",\n" +
                "    \"risco\": 0\n" +
                "}";
        String respostaEsperada ="{\"1004\":{\"nome\":\"Ian\",\"idade\":22,\"id\":1004,\"risco\":0}}";



        given()
                .contentType(ContentType.JSON)
                .body(clienteParaCadastrar)
                .when()
                .post(enderecoApiCliente+endpointCliente)
                .then()
                .statusCode(201);


        given()
                .contentType(ContentType.JSON)
                .body(clienteAtualizado)
        .when()
                .put(enderecoApiCliente+endpointCliente)
        .then()
                .statusCode(200)
                .assertThat().body(containsString(respostaEsperada));
    }

    @Test
    @DisplayName("Quando deletar um cliente, então ele deve ser removido com sucesso.")
    public void deletarClientesPorId(){

        String clienteParaCadastrar ="{\n" +
                "    \"id\":1004,\n" +
                "    \"idade\": 25,\n" +
                "    \"nome\": \"Adan\",\n" +
                "    \"risco\": 0\n" +
                "}";

        String respostaEsperada = "CLIENTE REMOVIDO: { NOME: Adan, IDADE: 25, ID: 1004 }";

        given()
                .contentType(ContentType.JSON)
                .body(clienteParaCadastrar)
        .when()
                .post(enderecoApiCliente+endpointCliente)
        .then()
                .statusCode(201);


        given()
                .contentType(ContentType.JSON)
        .when()
                .delete(enderecoApiCliente+endpointCliente+"/1004")
        .then()
                .statusCode(200)
                .body(new IsEqual<>(respostaEsperada));
    }


}
