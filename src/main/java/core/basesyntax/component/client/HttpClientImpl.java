package core.basesyntax.component.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class HttpClientImpl implements HttpClient {
    private static final int TOO_MANY_REQUESTS = 429;
    private static final int INDEX = 0;
    private static final String RETRY_AFTER_HEADER = "retry-after";

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> T get(String url, Class<T> clazz) {
        HttpGet request = new HttpGet(url);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == TOO_MANY_REQUESTS) {
                response.close();
                try {
                    Thread.sleep(Integer.parseInt(response.getHeaders(RETRY_AFTER_HEADER)[INDEX]
                            .getElements()[INDEX].getName()));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                response = httpClient.execute(request);
            }
            return objectMapper.readValue(response.getEntity().getContent(), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Cannot fetch info from url: " + url, e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    throw new RuntimeException("Cannot close response: " + response, e);
                }
            }
        }
    }
}
