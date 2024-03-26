package info.touret.ai.carbooking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Configuration

public class VectorStoreConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(VectorStoreConfiguration.class);

    @Value("classpath:data/terms-of-use.txt")
    private Resource resource;

    @Bean()
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public VectorStore createAndLoadVectorStore(JdbcTemplate jdbcTemplate, EmbeddingClient embeddingClient) {
        LOGGER.info("Creating Embeddings...");
        VectorStore vectorStore = new PgVectorStore(jdbcTemplate, embeddingClient);
        vectorStore.add(loadText());
        return vectorStore;
    }

    private List<Document> loadText() {
        TextReader textReader = new TextReader(resource);
        LOGGER.info("Loading data/terms-of-use.txt...");
        textReader.getCustomMetadata().put("filename", "data/terms-of-use.txt");
        return textReader.get();
    }
}
