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

        // Extract individual DTOs
        ItemDTO itemDTO = addTyreItemDTO.getItemDTO();
        ItemTyreDTO itemTyreDTO = addTyreItemDTO.getItemTyreDTO();
        ItemBranchDTO itemBranchDTO = addTyreItemDTO.getItemBranchDTO();


        System.out.println("\n\nmappting started to Item\n\n");
        Item item= itemDTOMapper.toEntity(itemDTO); //ModelMapper confused here..
        System.out.println("Mapped to Item\n\n");
        //Map ItemDTO to Item entity manually
//        Item item=new Item();
//        item.setItemName(itemDTO.getItemName());
//        item.setCompanyPrice(itemDTO.getCompanyPrice());
//        item.setDiscount(itemDTO.getDiscount());
//        item.setItemDescription(itemDTO.getItemDescription());
//        item.setRetailPrice(itemDTO.getRetailPrice());

        // Fetch Category and Brand entities
        item.setCategory(categoryRepository.findById(itemDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category ID")));
        item.setBrand(brandRepository.findById(itemDTO.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Brand ID")));

        System.out.println("Before Item save "+item.getItemId()+"\n\n");
        Item savedItem=itemRepository.save(item);
        System.out.println("After Item saved itemId of item "+item.getItemId()+"\n\n");
        System.out.println("After Item saved itemId of savedItem"+savedItem.getItemId()+"\n\n");

        // Map ItemTyreDTO to ItemTyre entity
        ItemTyre tyre=itemTyreDTOMapper.toEntity(itemTyreDTO);
        //set item to tyre
        tyre.setItem(item);
        ItemTyre savedTyre=itemTyreRepo.save(tyre);
        System.out.println("Tyre saved"+savedTyre.getItem().getItemId()+"\n\n");

        //Map to ItemBranch
//        ItemBranch itemBranch=itemBranchDTOMapper.toEntity(itemBranchDTO);
//        System.out.println("ItemBranch mapped\n\n");
        ItemBranch itemBranch=new ItemBranch();

        // Fetch the Branch entity
        Branch branch = branchRepository.findById(itemBranchDTO.getBranchId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Branch ID"));

        itemBranch.setBranch(branch);
        System.out.println("Branch set : "+itemBranch.getBranch().getBranchId()+"\n");

        itemBranch.setItem(item);
        System.out.println("Item set : "+itemBranch.getItem().getItemId()+"\n");
        itemBranch.setAvailableQuantity(itemBranchDTO.getAvailableQuantity());
        System.out.println("Available Quantity set : "+itemBranch.getAvailableQuantity()+"\n");

        System.out.println("ItemBranch all set\n\n");
        itemBranchRepository.save(itemBranch);
        System.out.println("ItemBranch saved\n\n");

        return new ResponseDTO(true, "Item added successfully");
    }

}
