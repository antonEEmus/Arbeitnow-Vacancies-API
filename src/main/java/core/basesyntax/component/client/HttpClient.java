package core.basesyntax.component.client;

public interface HttpClient {
    <T> T get(String url, Class<T> clazz);
}
