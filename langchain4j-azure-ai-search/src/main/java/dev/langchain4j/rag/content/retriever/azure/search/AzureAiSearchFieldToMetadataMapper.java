package dev.langchain4j.rag.content.retriever.azure.search;

import com.azure.search.documents.models.SearchResult;
import java.util.Map;

public interface AzureAiSearchFieldToMetadataMapper {
    Map<String, Object> map(SearchResult result);
}
