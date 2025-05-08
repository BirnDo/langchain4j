package dev.langchain4j.rag.content.retriever.azure.search;

import com.azure.search.documents.models.SearchResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultAzureAiSearchFieldToMetadataMapper implements AzureAiSearchFieldToMetadataMapper {

    private final List<String> fieldsToExtract;

    public DefaultAzureAiSearchFieldToMetadataMapper() {
        this.fieldsToExtract = List.of();
    }

    public DefaultAzureAiSearchFieldToMetadataMapper(final List<String> fieldsToExtract) {
        this.fieldsToExtract = fieldsToExtract;
    }

    @Override
    public Map<String, Object> map(final SearchResult result) {
        if (fieldsToExtract.isEmpty()) {
            return Map.of();
        }

        var metadataMap = new HashMap<String, Object>();
        var extractedFields = result.getDocument(Map.class);

        for (final String field : fieldsToExtract) {
            metadataMap.put(field, extractedFields.get(field));
        }

        return metadataMap;
    }
}
