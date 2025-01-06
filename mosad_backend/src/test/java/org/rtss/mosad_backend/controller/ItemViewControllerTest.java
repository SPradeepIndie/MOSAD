//package org.rtss.mosad_backend.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.rtss.mosad_backend.controller.ItemViewController;
//import org.rtss.mosad_backend.entity.stock_management_entity.ItemBattery;
//import org.rtss.mosad_backend.entity.stock_management_entity.ItemTube;
//import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyreAtlander;
//import org.rtss.mosad_backend.service.ItemViewService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ItemViewController.class)
//public class ItemViewControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ItemViewService itemViewService;
//
//    @Test
//    public void testGetItems_Battery() throws Exception {
//        // Prepare mock service behavior
//        List<ItemBattery> batteryList = new ArrayList<>();
//        batteryList.add(new ItemBattery());
//        Mockito.when(itemViewService.getItems("battery", null)).thenReturn(batteryList);
//
//        // Perform the GET request
//        mockMvc.perform(MockMvcRequestBuilders.get("/items?category=battery")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
//    }
//
//    @Test
//    public void testGetItems_Tube() throws Exception {
//        // Prepare mock service behavior
//        List<ItemTube> tubeList = new ArrayList<>();
//        tubeList.add(new ItemTube());
//        Mockito.when(itemViewService.getItems("tube", null)).thenReturn(tubeList);
//
//        // Perform the GET request
//        mockMvc.perform(MockMvcRequestBuilders.get("/items?category=tube")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
//    }
//
//    @Test
//    public void testGetItems_Tyre_Atlander() throws Exception {
//        // Prepare mock service behavior
//        List<ItemTyreAtlander> atlanderList = new ArrayList<>();
//        atlanderList.add(new ItemTyreAtlander());
//        Mockito.when(itemViewService.getItems("tyre", "atlander")).thenReturn(atlanderList);
//
//        // Perform the GET request
//        mockMvc.perform(MockMvcRequestBuilders.get("/items?category=tyre&brand=atlander")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
//    }
//
//    @Test
//    public void testGetItems_InvalidCategory() throws Exception {
//        // Prepare mock service behavior
//        Mockito.when(itemViewService.getItems("invalidCategory", null)).thenThrow(new IllegalArgumentException("Invalid category: invalidCategory"));
//
//        // Perform the GET request and expect 400 Bad Request
//        mockMvc.perform(MockMvcRequestBuilders.get("/items?category=invalidCategory")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
//
//    // Add more test cases for other categories and brands,
//    // as well as for error handling (e.g., invalid brand)
//}