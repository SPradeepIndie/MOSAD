package org.rtss.mosad_backend.service.stock_management_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyreAtlander;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyreLinglong;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyrePresa;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyreRapid;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemSearchServiceTest {

    @Mock
    private ItemTyreAtlanderService atlanderService;

    @Mock
    private ItemTyreLinglongService linglongService;

    @Mock
    private ItemTyrePresaService presaService;

    @Mock
    private ItemTyreRapidService rapidService;

    @InjectMocks
    private ItemSearchService itemSearchService;

    @BeforeEach
    public void setUp() {
        // This will automatically inject the mocks into the itemSearchService.
    }

    @Test
    public void testSearchByBrandAndSizeAtlander() {
        String brand = "atlander";
        String size = "215/60R16";
        List<?> mockResult = List.of("Tire 1", "Tire 2");

        // Mock the response from the Atlander service
        when(atlanderService.searchBySize(size)).thenReturn((List<ItemTyreAtlander>) mockResult);

        // Call the method
        List<?> result = itemSearchService.searchByBrandAndSize(brand, size);

        // Assert that the result matches the mocked response
        assertEquals(mockResult, result);
        verify(atlanderService, times(1)).searchBySize(size);
    }

    @Test
    public void testSearchByBrandAndSizeLinglong() {
        String brand = "linglong";
        String size = "205/55R16";
        List<?> mockResult = List.of("Tire A", "Tire B");

        // Mock the response from the Linglong service
        when(linglongService.searchBySize(size)).thenReturn((List<ItemTyreLinglong>) mockResult);

        // Call the method
        List<?> result = itemSearchService.searchByBrandAndSize(brand, size);

        // Assert that the result matches the mocked response
        assertEquals(mockResult, result);
        verify(linglongService, times(1)).searchBySize(size);
    }

    @Test
    public void testSearchByBrandAndSizeInvalidBrand() {
        String brand = "unknownBrand";
        String size = "225/65R17";

        // Expect an IllegalArgumentException when an invalid brand is passed
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            itemSearchService.searchByBrandAndSize(brand, size);
        });

        assertEquals("Invalid brand name: unknownBrand", thrown.getMessage());
    }

    @Test
    public void testSearchByBrandAndSizePresa() {
        String brand = "prasa"; // Note the typo "prasa" is used here
        String size = "185/65R15";
        List<?> mockResult = List.of("Tire X", "Tire Y");

        // Mock the response from the Presa service
        when(presaService.searchBySize(size)).thenReturn((List<ItemTyrePresa>) mockResult);

        // Call the method
        List<?> result = itemSearchService.searchByBrandAndSize(brand, size);

        // Assert that the result matches the mocked response
        assertEquals(mockResult, result);
        verify(presaService, times(1)).searchBySize(size);
    }

    @Test
    public void testSearchByBrandAndSizeRapid() {
        String brand = "rapid";
        String size = "255/50R18";
        List<?> mockResult = List.of("Tire Q", "Tire R");

        // Mock the response from the Rapid service
        when(rapidService.searchBySize(size)).thenReturn((List<ItemTyreRapid>) mockResult);

        // Call the method
        List<?> result = itemSearchService.searchByBrandAndSize(brand, size);

        // Assert that the result matches the mocked response
        assertEquals(mockResult, result);
        verify(rapidService, times(1)).searchBySize(size);
    }
}
