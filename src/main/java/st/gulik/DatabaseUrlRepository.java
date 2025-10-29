package st.gulik;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.transaction.Transactional;
import jakarta.annotation.Priority;


import java.util.Optional;

@ApplicationScoped
@Alternative
@Priority(1)
public class DatabaseUrlRepository implements UrlRepository {

    @Override
    @Transactional // Ensures the save operation happens within a database transaction
    public void save(String key, String originalUrl) {
        UrlMapping mapping = new UrlMapping();
        mapping.shortKey = key;
        mapping.originalUrl = originalUrl;
        mapping.persist(); // Saves the object to the database
    }

    @Override
    public Optional<String> findByKey(String key) {
        Optional<UrlMapping> optionalMapping =
                UrlMapping.find("shortKey", key).firstResultOptional();
        return optionalMapping.map(mapping -> mapping.originalUrl);
    }

    @Override
    public Optional<String> findByOriginalUrl(String originalUrl) {
        Optional<UrlMapping> optionalMapping =
                UrlMapping.find("originalUrl", originalUrl).firstResultOptional();
        return optionalMapping.map(mapping -> mapping.shortKey);
    }

    @Override
    public boolean keyExists(String key) {
        return UrlMapping.count("shortKey", key) > 0;
    }
}
