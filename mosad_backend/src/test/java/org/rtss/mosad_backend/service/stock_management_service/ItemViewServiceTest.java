package org.rtss.mosad_backend.service.stock_management_service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rtss.mosad_backend.entity.stock_management_entity.*;
import org.rtss.mosad_backend.repository.stock_management_repository.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemViewServiceTest {

    @InjectMocks
    private ItemViewService itemViewService;

    @Mock
    private ItemBatteryRepo itemBatteryRepo;

    @Mock
    private ItemTubeRepo itemTubeRepo;

    @Mock
    private ItemTyreAtlanderRepo itemTyreAtlanderRepo;

    @Mock
    private ItemTyreLinglongRepo itemTyreLinglongRepo;

    @Mock
    private ItemTyrePresaRepo itemTyrePresaRepo;

    @Mock
    private ItemTyreRapidRepo itemTyreRapidRepo;

    @Mock
    private ItemOtherAccessoriesRepo itemOtherAccessoriesRepo;

    @Test
    void shouldReturnBatteryItems() {
        // Arrange
        ItemBattery mockBattery = new ItemBattery(); // Replace with actual fields if needed
        when(itemBatteryRepo.findAll()).thenReturn(Collections.singletonList(mockBattery));

        // Act
        List<?> result = itemViewService.getItems("battery", null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockBattery, result.getFirst());
        verify(itemBatteryRepo, times(1)).findAll();
    }

    @Test
    void shouldReturnTubeItems() {
        // Arrange
        ItemTube mockTube = new ItemTube(); // Replace with actual fields if needed
        when(itemTubeRepo.findAll()).thenReturn(Collections.singletonList(mockTube));

        // Act
        List<?> result = itemViewService.getItems("tube", null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockTube, result.getFirst());
        verify(itemTubeRepo, times(1)).findAll();
    }

    @Test
    void shouldReturnTyreItemsForBrandPresa() {
        // Arrange
        ItemTyrePresa mockTyre = new ItemTyrePresa();
        when(itemTyrePresaRepo.findAll()).thenReturn(Collections.singletonList(mockTyre));

        // Act
        List<?> result = itemViewService.getItems("tyre", "presa");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockTyre, result.getFirst());
        verify(itemTyrePresaRepo, times(1)).findAll();
    }

    @Test
    void shouldReturnTyreItemsForBrandAtlander() {
        // Arrange
        ItemTyreAtlander mockTyre = new ItemTyreAtlander();
        when(itemTyreAtlanderRepo.findAll()).thenReturn(Collections.singletonList(mockTyre));

        // Act
        List<?> result = itemViewService.getItems("tyre", "atlander");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockTyre, result.getFirst());
        verify(itemTyreAtlanderRepo, times(1)).findAll();
    }

    @Test
    void shouldReturnTyreItemsForBrandLinglong() {
        // Arrange
        ItemTyreLinglong mockTyre = new ItemTyreLinglong(); // Replace with actual fields if needed
        when(itemTyreLinglongRepo.findAll()).thenReturn(Collections.singletonList(mockTyre));

        // Act
        List<?> result = itemViewService.getItems("tyre", "linglong");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockTyre, result.getFirst());
        verify(itemTyreLinglongRepo, times(1)).findAll();
    }

    @Test
    void shouldReturnTyreItemsForBrandRapid() {
        // Arrange
        ItemTyreRapid mockTyre = new ItemTyreRapid(); // Replace with actual fields if needed
        when(itemTyreRapidRepo.findAll()).thenReturn(Collections.singletonList(mockTyre));

        // Act
        List<?> result = itemViewService.getItems("tyre", "Rapid");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockTyre, result.getFirst());
        verify(itemTyreRapidRepo, times(1)).findAll();
    }

    @Test
    void shouldReturnOtherAccessoriesItems() {
        // Arrange
        ItemOtherAccessories mockAccessory = new ItemOtherAccessories(); // Replace with actual fields if needed
        when(itemOtherAccessoriesRepo.findAll()).thenReturn(Collections.singletonList(mockAccessory));

        // Act
        List<?> result = itemViewService.getItems("other", null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockAccessory, result.getFirst());
        verify(itemOtherAccessoriesRepo, times(1)).findAll();
    }

    @Test
    void shouldThrowExceptionForInvalidCategory() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> itemViewService.getItems("invalid", null));
        assertEquals("Invalid category: invalid", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForTyreWithoutBrand() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> itemViewService.getItems("tyre", null));
        assertEquals("Brand must be specified for Tyre category.", exception.getMessage());
    }
}
