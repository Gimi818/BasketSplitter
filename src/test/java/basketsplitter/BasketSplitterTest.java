package basketsplitter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BasketSplitterTest {

    private BasketSplitter basketSplitter;

    @BeforeEach
    void setUp() {
        basketSplitter = new BasketSplitter("src/test/java/basketsplitter/testresources/config.json");
    }

    @Test
    @DisplayName("Should contain only Courier when all items have Courier delivery method")
    void shouldContainOnlyCourier() {
        List<String> items = Arrays.asList("Product1", "Product2");
        Map<String, List<String>> result = basketSplitter.split(items);

        assertTrue(result.containsKey("Courier") && result.get("Courier").size() == 2);
        assertFalse(result.containsKey("Pick-up point"));

    }

    @Test
    @DisplayName("Should correctly group Pick-up point and Courier items")
    void shouldCorrectlyGroupItems() {
        List<String> items = Arrays.asList("Product2", "Product3", "Product4");
        Map<String, List<String>> result = basketSplitter.split(items);

        assertTrue(result.containsKey("Pick-up point") && result.get("Pick-up point").size() == 2);
        assertTrue(result.containsKey("Courier") && result.get("Courier").size() == 1);

    }

    @Test
    @DisplayName("Should return empty list when split empty item list")
    void shouldReturnEmptyMapForEmptyItemList() {
        List<String> items = Arrays.asList();
        Map<String, List<String>> result = basketSplitter.split(items);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when item list is null")
    void shouldThrowIllegalArgumentExceptionForNullItemList() {
        assertThrows(IllegalArgumentException.class, () -> basketSplitter.split(null));
    }

    @Test
    @DisplayName("Should return Courier as the method with the highest count")
    void shouldFindMaxDeliveryMethod() {
        Map<String, Integer> deliveryMethodCount = Map.of(
                "Courier", 5,
                "Pick-up point", 3,
                "Parcel locker", 1
        );
        String maxMethod = basketSplitter.findMaxDeliveryMethod(deliveryMethodCount);
        assertEquals("Courier", maxMethod);
    }

    @Test
    @DisplayName("Should correctly count each delivery method")
    void shouldCorrectlyCountDeliveryMethods() {
        Set<String> items = Set.of("Product1", "Product2", "Product3", "Product4");
        Map<String, Integer> deliveryMethodCount = basketSplitter.countDeliveryMethods(items);

        assertAll(
                () -> assertEquals(3, deliveryMethodCount.get("Courier")),
                () -> assertEquals(3, deliveryMethodCount.get("Pick-up point")),
                () -> assertEquals(1, deliveryMethodCount.get("Parcel locker"))
        );
    }
}

