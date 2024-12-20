package org.rtss.mosad_backend.service.stock_management_service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemTubeDTO;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTube;
import org.rtss.mosad_backend.model_mapper.stock_management_model_mapper.ItemTubeDTOMapper;
import org.rtss.mosad_backend.repository.stock_management_repository.ItemTubeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ItemTubeService{


    @Autowired
    private ItemTubeRepo itemTubeRepo;

    private ModelMapper modelMapper;

    @Autowired
    private ItemTubeDTOMapper itemTubeDTOMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ItemTubeDTO saveItem(ItemTubeDTO itemTubeDTO) {
        itemTubeRepo.save(itemTubeDTOMapper.ItemTubeDTOToItemTube(itemTubeDTO));
        return itemTubeDTO;
    }


    public List<ItemTubeDTO> getAllItems() {
        List<ItemTube> items = itemTubeRepo.findAll();
        List<ItemTubeDTO> itemList = new ArrayList<>();
        for (ItemTube itemTube : items) {
            ItemTubeDTO dto = itemTubeDTOMapper.ItemTubeToItemTubeDTO(itemTube);
            itemList.add(dto);
        }

        return itemList;
    }


    public ItemTubeDTO updateItem(ItemTubeDTO itemTubeDTO) {
        itemTubeRepo.save(itemTubeDTOMapper.ItemTubeDTOToItemTube(itemTubeDTO));
        return itemTubeDTO;
    }

    public boolean deleteUser(ItemTubeDTO itemTubeDTO) {
        itemTubeRepo.delete(itemTubeDTOMapper.ItemTubeDTOToItemTube(itemTubeDTO));
        return true;
    }


}
