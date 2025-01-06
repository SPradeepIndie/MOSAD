package org.rtss.mosad_backend.service.stock_management_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemBattery;
import org.rtss.mosad_backend.repository.stock_management_repository.*;
import org.rtss.mosad_backend.service.ItemViewService;

import java.util.ArrayList;
import java.util.List;

public class ItemViewServiceUnitTest {

    @Test
    public void testGetItems_Battery() {
        ItemViewService itemViewService = new ItemViewService();

        // Mock the ItemBatteryRepo
        ItemBatteryRepo itemBatteryRepoMock = Mockito.mock(ItemBatteryRepo.class);
        List<ItemBattery> batteryList = new ArrayList<>();
        batteryList.add(new ItemBattery());
        Mockito.when(itemBatteryRepoMock.findAll()).thenReturn(batteryList);
        itemViewService.setItemBatteryRepository(itemBatteryRepoMock);

        // Call the getItems method with category "battery" and any brand
        List<?> items = itemViewService.getItems("battery", null);

        // Verify that the method returns the list from the ItemBatteryRepo
        Assertions.assertEquals(batteryList, items);
    }

    @Test
    @Disabled
    public void testGetItems_Tube() {
        ItemViewService itemViewService = new ItemViewService();

        // Mock the ItemTubeRepo
        ItemTubeRepo itemTubeRepoMock = Mockito.mock(ItemTubeRepo.class);
        List<Object> tubeList = new ArrayList<>();
        tubeList.add(new Object());
//        Mockito.when(itemTubeRepoMock.findAll()).thenReturn(tubeList);
        itemViewService.setItemTubeRepository(itemTubeRepoMock);

        // Call the getItems method with category "tube" and any brand
        List<?> items = itemViewService.getItems("tube", null);

        // Verify that the method returns the list from the ItemTubeRepo
        Assertions.assertEquals(tubeList, items);
    }

    @Test
    @Disabled
    public void testGetItems_TyreAtlander() {
        ItemViewService itemViewService = new ItemViewService();

        // Mock the ItemTyreAtlanderRepo
        ItemTyreAtlanderRepo itemTyreAtlanderRepoMock = Mockito.mock(ItemTyreAtlanderRepo.class);
        List<Object> atlanderList = new ArrayList<>();
        atlanderList.add(new Object());
//        Mockito.when(itemTyreAtlanderRepoMock.findAll()).thenReturn(atlanderList);
        itemViewService.setItemTyreAtlanderRepository(itemTyreAtlanderRepoMock);

        // Call the getItems method with category "tyre" and brand "atlander"
        List<?> items = itemViewService.getItems("tyre", "atlander");

        // Verify that the method returns the list from the ItemTyreAtlanderRepo
        Assertions.assertEquals(atlanderList, items);
    }

    @Test
    public void testGetItems_TyreInvalidBrand() {
        ItemViewService itemViewService = new ItemViewService();

        // Test for invalid brand with category "tyre"
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            itemViewService.getItems("tyre", "invalidBrand");
        });
    }

    @Test
    public void testGetItems_InvalidCategory() {
        ItemViewService itemViewService = new ItemViewService();

        // Test for invalid category
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            itemViewService.getItems("invalidCategory", null);
        });
    }

}