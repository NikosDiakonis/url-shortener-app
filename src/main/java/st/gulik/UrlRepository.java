package st.gulik;

import java.util.Optional;

public interface UrlRepository {
    void save(String key, String originalUrl);
    Optional<String> findByKey(String key);
    Optional<String> findByOriginalUrl(String originalUrl);
    boolean keyExists(String key);
}
