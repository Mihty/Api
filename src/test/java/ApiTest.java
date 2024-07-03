import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiTest {

    @Test
    public void testEmojiApiContainsArticulatedLorry() throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet("https://api.github.com/emojis");

        CloseableHttpResponse response = httpClient.execute(request);

        try {
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);

            JSONObject jsonResponse = new JSONObject(responseString);

            Assert.assertTrue(jsonResponse.has("articulated_lorry"), "Response does not contain 'articulated_lorry' key");
        } finally {
            response.close();
            httpClient.close();
        }
    }

    @Test
    public void testEmojiApiContainsArticulatedLorr() {
        Response response = RestAssured.get("https://api.github.com/emojis");

        Assert.assertEquals(response.getStatusCode(), 200, "Response status is not 200");

        String responseBody = response.getBody().asString();

        Assert.assertTrue(responseBody.contains("articulated_lorry"), "Response does not contain 'articulated_lorry' key");
    }
}
