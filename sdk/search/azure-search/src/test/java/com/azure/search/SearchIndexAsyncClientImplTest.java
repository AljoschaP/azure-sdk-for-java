// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.search;

import com.azure.core.exception.ResourceNotFoundException;
import com.azure.core.http.rest.PagedFluxBase;
import com.azure.search.models.GeoPoint;
import com.azure.search.models.SearchOptions;
import com.azure.search.models.SearchResult;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class SearchIndexAsyncClientImplTest extends SearchIndexClientTestBase {

    private static final String INDEX_NAME = "hotels";
    private SearchIndexAsyncClient asyncClient;

    @Override
    protected void beforeTest() {
        super.beforeTest();
        createHotelIndex();
        asyncClient = getSearchIndexClientBuilder(INDEX_NAME).buildAsyncClient();
    }

    @Test
    public void canGetDynamicDocument() {
        Map<String, Object> addressDoc = new HashMap<>();
        addressDoc.put("StreetAddress", "677 5th Ave");
        addressDoc.put("City", "New York");
        addressDoc.put("StateProvince", "NY");
        addressDoc.put("Country", "USA");
        addressDoc.put("PostalCode", "10022");

        ArrayList<String> room1Tags = new ArrayList<>();
        room1Tags.add("vcr/dvd");

        HashMap<String, Object> room1Doc = new HashMap<>();
        room1Doc.put("Description", "Budget Room, 1 Queen Bed (Cityside)");
        room1Doc.put("Description_fr", "Chambre Économique, 1 grand lit (côté ville)");
        room1Doc.put("Type", "Budget Room");
        room1Doc.put("BaseRate", 9.69);
        room1Doc.put("BedOptions", "1 Queen Bed");
        room1Doc.put("SleepsCount", 2);
        room1Doc.put("SmokingAllowed", true);
        room1Doc.put("Tags", room1Tags);

        ArrayList<String> room2Tags = new ArrayList<>();
        room2Tags.add("vcr/dvd");
        room2Tags.add("jacuzzi tub");

        HashMap<String, Object> room2Doc = new HashMap<>();
        room2Doc.put("Description", "Budget Room, 1 King Bed (Mountain View)");
        room2Doc.put("Description_fr", "Chambre Économique, 1 très grand lit (Mountain View)");
        room2Doc.put("Type", "Budget Room");
        room2Doc.put("BaseRate", 8.09);
        room2Doc.put("BedOptions", "1 King Bed");
        room2Doc.put("SleepsCount", 2);
        room2Doc.put("SmokingAllowed", true);
        room2Doc.put("Tags", room2Tags);

        ArrayList<HashMap<String, Object>> rooms = new ArrayList<>();
        rooms.add(room1Doc);
        rooms.add(room2Doc);

        ArrayList<String> tags = new ArrayList<>();
        tags.add("pool");
        tags.add("air conditioning");
        tags.add("concierge");

        HashMap<String, Object> expectedDoc = new HashMap<>();
        expectedDoc.put("HotelId", "1");
        expectedDoc.put("HotelName", "Secret Point Motel");
        expectedDoc.put(
            "Description",
            "The hotel is ideally located on the main commercial artery of the city in the heart of New York. A few "
                + "minutes away is Time's Square and the historic centre of the city, as well as other places of "
                + "interest that make New York one of America's most attractive and cosmopolitan cities.");
        expectedDoc.put(
            "Description_fr",
            "L'hôtel est idéalement situé sur la principale artère commerciale de la ville en plein cœur de New York."
                + " A quelques minutes se trouve la place du temps et le centre historique de la ville, ainsi que "
                + "d'autres lieux d'intérêt qui font de New York l'une des villes les plus attractives et cosmopolites "
                + "de l'Amérique.");
        expectedDoc.put("Category", "Boutique");
        expectedDoc.put("Tags", tags);
        expectedDoc.put("ParkingIncluded", false);
        expectedDoc.put("SmokingAllowed", true);
        expectedDoc.put("LastRenovationDate", OffsetDateTime.parse("1970-01-18T00:00:00Z"));
        expectedDoc.put("Rating", 3);
        expectedDoc.put("Address", addressDoc);
        expectedDoc.put("Rooms", rooms);
        expectedDoc.put("Location", GeoPoint.create(40.760586, -73.975403));

        uploadDocument(asyncClient, expectedDoc);

        Mono<Document> futureDoc = asyncClient.getDocument("1");

        StepVerifier
            .create(futureDoc)
            .assertNext(result -> Assert.assertEquals(expectedDoc, result))
            .verifyComplete();
    }

    @Test
    public void getDocumentThrowsWhenDocumentNotFound() {
        Mono<Document> futureDoc = asyncClient.getDocument("1000000001");
        StepVerifier
            .create(futureDoc)
            .verifyErrorSatisfies(error -> assertEquals(ResourceNotFoundException.class, error.getClass()));
    }

    @Test
    public void getDocumentThrowsWhenRequestIsMalformed() {

        HashMap<String, Object> hotelDoc = new HashMap<>();
        hotelDoc.put("HotelId", "2");
        hotelDoc.put("Description", "Surprisingly expensive");

        List<String> selectedFields = Arrays.asList("HotelId", "ThisFieldDoesNotExist");

        uploadDocument(asyncClient, hotelDoc);
        assertHttpResponseExceptionAsync(
            asyncClient.getDocument("2", selectedFields, null),
            HttpResponseStatus.BAD_REQUEST,
            "Invalid expression: Could not find a property named 'ThisFieldDoesNotExist' "
                + "on type 'search.document'."
        );
    }

    @Test
    public void canGetPaginatedDocuments() throws Exception {
        List<Document> docs = new LinkedList<>();

        for (int i = 1; i <= 200; i++) {
            Document doc = new Document();
            doc.put("HotelId", String.valueOf(i));
            doc.put("HotelName", "Hotel " + i);
            docs.add(doc);
        }

        uploadDocuments(asyncClient, docs);

        AtomicBoolean failed = new AtomicBoolean(false);

        Runnable runnable1 = () -> {
            try {
                processResult(asyncClient.search("*"), 200);
            } catch (Exception ex) {
                failed.set(true);
            }
        };

        Runnable runnable2 = () -> {
            try {
                processResult(asyncClient.search("*"), 200);
            } catch (Exception ex) {
                failed.set(true);
            }
        };


        Runnable runnable3 = () -> {
            try {
                processResult(asyncClient.search("*"), 200);
            } catch (Exception ex) {
                failed.set(true);
            }
        };

        Thread thread1 = new Thread(runnable1);
        thread1.start();
        thread1.join();

        Thread thread2 = new Thread(runnable2);
        thread2.start();
        thread2.join();

        Thread thread3 = new Thread(runnable3);
        thread3.start();
        thread3.join();

        if (failed.get()) {
            Assert.fail();
        }
    }

    @Test
    public void canGetPaginatedDocumentsWithSearchOptions() throws Exception {
        List<Document> docs = new LinkedList<>();

        for (int i = 1; i <= 200; i++) {
            Document doc = new Document();
            doc.put("HotelId", String.valueOf(i));
            doc.put("HotelName", "Hotel " + i);
            docs.add(doc);
        }

        asyncClient.uploadDocuments(docs).block();
        waitForIndexing();

        AtomicBoolean failed = new AtomicBoolean(false);

        Runnable searchWithNoSkip = () -> {
            try {
                SearchOptions sp = new SearchOptions();
                processResult(asyncClient.search("*", sp, generateRequestOptions()), 200);
            } catch (Exception ex) {
                failed.set(true);
            }
        };

        Runnable searchWithSkip10 = () -> {
            try {
                SearchOptions sp = new SearchOptions().setSkip(10);
                processResult(asyncClient.search("*", sp, generateRequestOptions()), 190);
            } catch (Exception ex) {
                failed.set(true);
            }
        };


        Runnable searchWithSkip30 = () -> {
            try {
                SearchOptions sp = new SearchOptions().setSkip(30);
                processResult(asyncClient.search("*", sp, generateRequestOptions()), 170);
            } catch (Exception ex) {
                failed.set(true);
            }
        };

        Thread noSkipThread = new Thread(searchWithNoSkip);
        noSkipThread.start();
        noSkipThread.join();

        Thread threadWithSkip10 = new Thread(searchWithSkip10);
        threadWithSkip10.start();
        threadWithSkip10.join();

        Thread threadWithSkip30 = new Thread(searchWithSkip30);
        threadWithSkip30.start();
        threadWithSkip30.join();

        if (failed.get()) {
            Assert.fail();
        }
    }

    private void processResult(PagedFluxBase<SearchResult, SearchPagedResponse> result, Integer expectedCount) throws Exception {
        if (result == null) {
            throw new Exception("Result is null");
        }

        AtomicInteger actualCount = new AtomicInteger(0);
        result.toIterable().forEach(e -> actualCount.getAndIncrement());

        if (expectedCount != actualCount.get()) {
            throw new Exception(String.format("Expected %d documents, got %d documents", expectedCount, actualCount.get()));
        }
    }
}
