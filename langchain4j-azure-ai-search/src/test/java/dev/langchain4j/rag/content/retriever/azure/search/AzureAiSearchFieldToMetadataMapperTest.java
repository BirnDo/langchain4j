package dev.langchain4j.rag.content.retriever.azure.search;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.azure.search.documents.models.SearchResult;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AzureAiSearchFieldToMetadataMapperTest {
    SearchResult result;

    @BeforeEach
    void init() {
        result = mock(SearchResult.class);
        when(result.getDocument(Map.class))
                .thenReturn(Map.of(
                        "testKey1", "testValue1",
                        "testKey2", "testValue2"));
    }

    @Test
    void testExtractNoneWithDefaultConstructor() {
        // given
        var mapper = new DefaultAzureAiSearchFieldToMetadataMapper();

        // when
        var mapperResult = mapper.map(result);

        // then
        assertEquals(0, mapperResult.size());
    }

    @Test
    void testExtractCorrectKeyWithOverloadedConstructor() {
        // given
        var mapper = new DefaultAzureAiSearchFieldToMetadataMapper(List.of("testKey1"));

        // when
        var mapperResult = mapper.map(result);

        // then
        assertAll(
                () -> assertEquals(1, mapperResult.size()),
                () -> assertTrue(mapperResult.containsKey("testKey1")),
                () -> assertEquals("testValue1", mapperResult.get("testKey1")));
    }

    @Test
    void testCustomMapper() {
        // given
        AzureAiSearchFieldToMetadataMapper mapper = searchResult -> Map.of("CustomKey", "CustomValue");

        // when
        var mapperResult = mapper.map(result);

        assertAll(
                () -> assertEquals(1, mapperResult.size()),
                () -> assertTrue(mapperResult.containsKey("CustomKey")),
                () -> assertEquals("CustomValue", mapperResult.get("CustomKey")));
    }
}
