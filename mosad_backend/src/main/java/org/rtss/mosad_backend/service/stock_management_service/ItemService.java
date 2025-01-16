package org.rtss.mosad_backend.service.stock_management_service;

import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.AddTyreItemDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemBranchDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.ItemTyreDTO;
import org.rtss.mosad_backend.dto_mapper.stock_dto_mapper.ItemBranchDTOMapper;
import org.rtss.mosad_backend.dto_mapper.stock_dto_mapper.ItemDTOMapper;
import org.rtss.mosad_backend.dto_mapper.stock_dto_mapper.ItemTyreDTOMapper;
import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemBranch;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyre;
import org.rtss.mosad_backend.repository.branch_management.BranchRepo;
import org.rtss.mosad_backend.repository.stock_management_repository.*;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepo itemRepository;
    private final ItemBranchRepository itemBranchRepository;
    private final CategoryRepo categoryRepository;
    private final BrandRepo brandRepository;
    private final BranchRepo branchRepository;
    private final ItemTyreRepo itemTyreRepo;
    private final ItemDTOMapper itemDTOMapper;
    private final ItemTyreDTOMapper itemTyreDTOMapper;
    private final ItemBranchDTOMapper itemBranchDTOMapper;

    public ItemService(ItemRepo itemRepository, ItemBranchRepository itemBranchRepository, CategoryRepo categoryRepository, BrandRepo brandRepository, BranchRepo branchRepository, ItemTyreRepo itemTyreRepo, ItemDTOMapper itemDTOMapper, ItemTyreDTOMapper itemTyreDTOMapper, ItemBranchDTOMapper itemBranchDTOMapper) {
        this.itemRepository = itemRepository;
        this.itemBranchRepository = itemBranchRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.branchRepository = branchRepository;
        this.itemTyreRepo = itemTyreRepo;
        this.itemDTOMapper = itemDTOMapper;
        this.itemTyreDTOMapper = itemTyreDTOMapper;
        this.itemBranchDTOMapper = itemBranchDTOMapper;
    }

    //get item qty in a branch
    public Integer getItemQty(Long itemId, Long branchId) {
        return itemBranchRepository.findByItemIdAndBranchId(itemId, branchId).getAvailableQuantity();
    }

    //Add item Tyre with branch
    public ResponseDTO addTyreItem(AddTyreItemDTO addTyreItemDTO) {

        //extraction from addTyreItemDTO
//        Item item= itemDTOMapper.toEntity(addTyreItemDTO.getItemDTO());
//        ItemTyre tyre=itemTyreDTOMapper.toEntity(addTyreItemDTO.getItemTyreDTO());
//        ItemBranch branch=itemBranchDTOMapper.toEntity(addTyreItemDTO.getItemBranchDTO());
//
//        item.setCategory(categoryRepo.findById(addTyreItemDTO.getItemDTO().getCategoryId()));

        // Extract individual DTOs
        ItemDTO itemDTO = addTyreItemDTO.getItemDTO();
        ItemTyreDTO itemTyreDTO = addTyreItemDTO.getItemTyreDTO();
        ItemBranchDTO itemBranchDTO = addTyreItemDTO.getItemBranchDTO();

        Item item= itemDTOMapper.toEntity(itemDTO);

        // Fetch Category and Brand entities
        item.setCategory(categoryRepository.findById(itemDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category ID")));
        item.setBrand(brandRepository.findById(itemDTO.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Brand ID")));

        Item savedItem=itemRepository.save(item);

        // Map ItemTyreDTO to ItemTyre entity
        ItemTyre tyre=itemTyreDTOMapper.toEntity(itemTyreDTO);
        //set item to tyre
        tyre.setItem(item);
        ItemTyre savedTyre=itemTyreRepo.save(tyre);

        //Map to ItemBranch
        ItemBranch itemBranch=itemBranchDTOMapper.toEntity(itemBranchDTO);

        // Fetch the Branch entity
        Branch branch = branchRepository.findById(itemBranchDTO.getBranchId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Branch ID"));

        itemBranch.setBranch(branch);

        itemBranch.setItem(item);
        itemBranch.setBranch(branch);
        itemBranch.setAvailableQuantity(itemBranchDTO.getAvailableQuantity());

        itemBranchRepository.save(itemBranch);










        return new ResponseDTO(true, "Item added successfully");
    }

}
