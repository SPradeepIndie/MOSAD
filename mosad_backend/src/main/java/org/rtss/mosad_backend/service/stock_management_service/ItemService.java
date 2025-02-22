package org.rtss.mosad_backend.service.stock_management_service;

import org.modelmapper.ModelMapper;
import org.rtss.mosad_backend.dto.ResponseDTO;
import org.rtss.mosad_backend.dto.stock_management_dto.*;
import org.rtss.mosad_backend.dto_mapper.stock_dto_mapper.ItemBranchDTOMapper;
import org.rtss.mosad_backend.dto_mapper.stock_dto_mapper.ItemDTOMapper;
import org.rtss.mosad_backend.dto_mapper.stock_dto_mapper.ItemTyreDTOMapper;
import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.rtss.mosad_backend.entity.stock_management_entity.*;
import org.rtss.mosad_backend.repository.branch_management.BranchRepo;
import org.rtss.mosad_backend.repository.stock_management_repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
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
    private final BranchRepo branchRepo;

    public ItemService(ItemRepo itemRepository, ItemBranchRepository itemBranchRepository, CategoryRepo categoryRepository, BrandRepo brandRepository, BranchRepo branchRepository, ItemTyreRepo itemTyreRepo, ItemDTOMapper itemDTOMapper, ItemTyreDTOMapper itemTyreDTOMapper, ItemBranchDTOMapper itemBranchDTOMapper, BranchRepo branchRepo) {
        this.itemRepository = itemRepository;
        this.itemBranchRepository = itemBranchRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.branchRepository = branchRepository;
        this.itemTyreRepo = itemTyreRepo;
        this.itemDTOMapper = itemDTOMapper;
        this.itemTyreDTOMapper = itemTyreDTOMapper;
        this.itemBranchDTOMapper = itemBranchDTOMapper;
        this.branchRepo = branchRepo;
    }

    public List<BranchDTO>  getBranches(){
        List<Branch> branches=branchRepo.findAll();
        System.out.println("Branches: " + branches);
        ModelMapper modelMapper=new ModelMapper();
        List<BranchDTO> branchDTOS=new ArrayList<>();
        for(Branch branch:branches){
            branchDTOS.add(modelMapper.map(branch,BranchDTO.class));
        }
        return branchDTOS;
    }
    //get item qty in a branch
    public Integer getItemQty(Long itemId, Long branchId) {
        return itemBranchRepository.findByItemIdAndBranchId(itemId, branchId).getAvailableQuantity();
    }

    //Add item Tyre with branch
    public ResponseDTO addItem(AddItemDTO addItemDTO) {

        // Extract individual DTOs
        ItemDTO itemDTO = addItemDTO.getItemDTO();

        ItemTyreDTO itemTyreDTO = addItemDTO.getItemTyreDTO();
        ItemBranchDTO itemBranchDTO = addItemDTO.getItemBranchDTO();

        Item item= itemDTOMapper.toEntity(itemDTO);

        // Fetch Category and Brand entities

        item.setCategory(categoryRepository.findCategoryByCategoryName(itemDTO.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category ")));
        item.setBrand(brandRepository.findByBrandName(itemDTO.getBrand())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Brand ")));

        itemRepository.save(item);

        //Map to ItemBranch

        ItemBranch itemBranch=new ItemBranch();

        // Fetch the Branch entity
        Branch branch = branchRepository.findById(itemBranchDTO.getBranchId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Branch ID"));

        itemBranch.setBranch(branch);

        itemBranch.setItem(item);
        itemBranch.setAvailableQuantity(itemBranchDTO.getAvailableQuantity());
        itemBranchRepository.save(itemBranch);

        if(!itemDTO.getCategory().equals("Tyre")) {
            return new ResponseDTO(true, "Item added successfully");
        }

        // Map ItemTyreDTO to ItemTyre entity
        ItemTyre tyre=itemTyreDTOMapper.toEntity(itemTyreDTO);
        //set item to tyre
        tyre.setItem(item);
        itemTyreRepo.save(tyre);



        return new ResponseDTO(true, "Item added successfully");
    }

    public ResponseDTO updateItem(AddItemDTO updateTyreItemDTO) {
        // Extract individual DTOs
        ItemDTO itemDTO = updateTyreItemDTO.getItemDTO();
        ItemTyreDTO itemTyreDTO = updateTyreItemDTO.getItemTyreDTO();
        ItemBranchDTO itemBranchDTO = updateTyreItemDTO.getItemBranchDTO();

        // Fetch the existing Item entity using itemId from ItemDTO
        Long itemId = itemDTO.getItemId();
        if (itemId == null) {
            throw new IllegalArgumentException("Item ID is required for updating an item.");
        }

        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + itemId));

        // Update fields in the Item entity
        existingItem.setItemName(itemDTO.getItemName());
        existingItem.setCompanyPrice(itemDTO.getCompanyPrice());
        existingItem.setRetailPrice(itemDTO.getRetailPrice());
        existingItem.setDiscount(itemDTO.getDiscount());
        existingItem.setItemDescription(itemDTO.getItemDescription());
        existingItem.setCategory(categoryRepository.findCategoryByCategoryName(itemDTO.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category ID")));
        existingItem.setBrand(brandRepository.findByBrandName(itemDTO.getBrand())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Brand ID")));

        // Save updated Item entity
        Item savedItem = itemRepository.save(existingItem);

        // Fetch the existing ItemBranch entity using itemId and branchId
        Long branchId = itemBranchDTO.getBranchId();
        if (branchId == null) {
            throw new IllegalArgumentException("Branch ID is required for updating item branch information.");
        }

        ItemBranch existingItemBranch = itemBranchRepository.findByItemIdAndBranchId(itemId, branchId);
        if (existingItemBranch == null) {
            throw new IllegalArgumentException("ItemBranch not found for Item ID: " + itemId +
                    " and Branch ID: " + branchId);
        }

        // Update fields in the ItemBranch entity
        existingItemBranch.setAvailableQuantity(itemBranchDTO.getAvailableQuantity());
        existingItemBranch.setBranch(branchRepository.findById(branchId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Branch ID")));

        // Save updated ItemBranch entity
        itemBranchRepository.save(existingItemBranch);

        if(!itemDTO.getCategory().equals("Tyre")) {
            return new ResponseDTO(true, "Item updated successfully");
        }

        ItemTyre existingTyre = itemTyreRepo.findByItem(existingItem);

        // Update fields in the ItemTyre entity
        existingTyre.setItem(savedItem);
        existingTyre.setTyreSize(itemTyreDTO.getTyreSize());
        existingTyre.setPattern(itemTyreDTO.getPattern());
        existingTyre.setVehicleType(itemTyreDTO.getVehicleType());

        // Save updated ItemTyre entity
        itemTyreRepo.save(existingTyre);



        return new ResponseDTO(true, "ItemTyre updated successfully");
    }

    public ResponseDTO deleteItem(Long itemId) {
        // Validate itemId
        if (itemId == null) {
            throw new IllegalArgumentException("Item ID is required for deletion.");
        }

        // Fetch and delete the Item entity
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + itemId));
        itemTyreRepo.deleteItemTyreByItem(existingItem);

        itemRepository.delete(existingItem);

        return new ResponseDTO(true, "Item deleted successfully.");
    }

    //Get all items
    public List<AddItemDTO> getAllItems(String cat, String brnd, Long branchId) {
        List<AddItemDTO> addItemDTOS = new ArrayList<>();

        // Check if category exists
        Category category = categoryRepository.findCategoryByCategoryName(cat)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Category not found"));

        // Check if brand exists
        Brand brand = brandRepository.findByBrandName(brnd)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Brand not found"));

        // Fetch items
        List<Item> items = itemRepository.findByCategoryAndBrand(category, brand);

        for (Item item : items) {
            ItemDTO itemDTO = itemDTOMapper.toDTO(item);

            ItemTyreDTO itemTyreDTO = null;
            if ("Tyre".equals(cat)) {
                ItemTyre tyre = itemTyreRepo.findByItem(item);
                if (tyre != null) {
                    itemTyreDTO = itemTyreDTOMapper.toDTO(tyre);
                } else {
                    throw new HttpServerErrorException(HttpStatus.NOT_FOUND, "Tyre not found for this item");
                }
            }

            // Fetch branch item details
            ItemBranch itemBranch = itemBranchRepository.findByItemIdAndBranchId(item.getItemId(), branchId);
            if (itemBranch == null) {
                continue; // Skip this item if branch mapping is missing
            }

            ItemBranchDTO itemBranchDTO = itemBranchDTOMapper.toDTO(itemBranch);

            // Construct AddItemDTO
            AddItemDTO addItemDTO = new AddItemDTO(itemDTO, "Tyre".equals(cat) ? itemTyreDTO : null, itemBranchDTO);
            addItemDTOS.add(addItemDTO);
        }

        return addItemDTOS;
    }


    public List<AddItemDTO> searchItems(String brand,String size,Long branchId) {
        // Implement search logic here
        List<ItemTyre> itemTyres = itemTyreRepo.findByItem_Brand_BrandNameAndTyreSize(brand, size);

        List<AddItemDTO> addItemDTOS = new ArrayList<>();
        for (ItemTyre itemTyre : itemTyres) {
            ItemDTO itemDTO = itemDTOMapper.toDTO(itemTyre.getItem());
            ItemTyreDTO itemTyreDTO = itemTyreDTOMapper.toDTO(itemTyre);
            ItemBranch itemBranch = itemBranchRepository.findByItemIdAndBranchId(itemTyre.getItem().getItemId(), branchId);
            ItemBranchDTO itemBranchDTO = itemBranchDTOMapper.toDTO(itemBranch);
            AddItemDTO addItemDTO = new AddItemDTO(itemDTO, itemTyreDTO, itemBranchDTO);
            addItemDTOS.add(addItemDTO);
        }
        return addItemDTOS;
    }


    public List<AddItemDTO> searchItemsByName(String cat,String brnd,String name,String tyreSize, Long branchId) {



        List<AddItemDTO> addItemDTOS = new ArrayList<>();

        if(Objects.equals(cat, "Tyre") && tyreSize != null && !tyreSize.isEmpty()) {
            addItemDTOS=searchItems(brnd,tyreSize,branchId);

            return addItemDTOS;
        }


        // Check if category exists
        Category category = categoryRepository.findCategoryByCategoryName(cat)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Category not found"));

        // Check if brand exists
        Brand brand = brandRepository.findByBrandName(brnd)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Brand not found"));

        // Fetch items
        List<Item> items=null;
        if(name != null && !name.isEmpty()) {
             items = itemRepository.findItemsByItemNameAndCategoryAndBrand(name, category.getCategoryId(), brand.getBrandId());
        }
        else{
            items = itemRepository.findByCategoryAndBrand(category, brand);
        }


        for (Item item : items) {
            ItemDTO itemDTO = itemDTOMapper.toDTO(item);
            ItemTyreDTO itemTyreDTO = null;

            // Only fetch tyre details if it's a tyre
            if ("Tyre".equals(category.getCategoryName())) {


                ItemTyre tyre = itemTyreRepo.findByItem(item);


                if (tyre != null) {
                    itemTyreDTO = itemTyreDTOMapper.toDTO(tyre);
                    itemDTO=itemDTOMapper.toDTO(tyre.getItem());
                }
            }


            // Fetch branch item details
            ItemBranch itemBranch = itemBranchRepository.findByItemIdAndBranchId(item.getItemId(), branchId);
            if (itemBranch == null) {
                continue; // Skip this item if branch mapping is missing
            }

            ItemBranchDTO itemBranchDTO = itemBranchDTOMapper.toDTO(itemBranch);

            // Construct AddItemDTO for both tyre and non-tyre items
            AddItemDTO addItemDTO = new AddItemDTO(itemDTO, itemTyreDTO, itemBranchDTO);
            addItemDTOS.add(addItemDTO);
        }


        return addItemDTOS;
    }


}
