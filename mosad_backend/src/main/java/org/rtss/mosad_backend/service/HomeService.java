package org.rtss.mosad_backend.service;

import org.rtss.mosad_backend.dto.BillCountDTO;
import org.rtss.mosad_backend.dto.HomeCalDTO;
import org.rtss.mosad_backend.dto.HomeStatsDTO;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.entity.credit.Credit;
import org.rtss.mosad_backend.repository.bill_repository.BillItemRepository;
import org.rtss.mosad_backend.repository.bill_repository.BillRepository;
import org.rtss.mosad_backend.repository.credit_repository.CreditRepository;
import org.rtss.mosad_backend.repository.stock_management_repository.BrandRepo;
import org.rtss.mosad_backend.repository.stock_management_repository.CategoryRepo;
import org.rtss.mosad_backend.repository.stock_management_repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HomeService {

    private final CategoryRepo categoryRepo;
    private final BrandRepo brandRepo;
    private final ItemRepo itemRepo;
    private final BillRepository billRepository;
    private final BillItemRepository billItemRepository;
    private final CreditRepository creditRepository;

    @Autowired
    public HomeService(CategoryRepo categoryRepo, BrandRepo brandRepo, ItemRepo itemRepo, BillRepository billRepository, BillItemRepository billItemRepository, CreditRepository creditRepository) {
        this.categoryRepo = categoryRepo;
        this.brandRepo = brandRepo;
        this.itemRepo = itemRepo;
        this.billRepository = billRepository;
        this.billItemRepository = billItemRepository;
        this.creditRepository = creditRepository;
    }

    public long getTotalCategories() {
        return categoryRepo.count();
    }

    public long getTotalBrands() {
        return brandRepo.count();
    }

    public long getTotalItems() {
        return itemRepo.count();
    }

    public long getTotalBillsToday() {
        return billRepository.count();
    }
    public long getCountBillsByToday() {
        LocalDate today = LocalDate.now();  // Get today's date
        return billRepository.countByDate(today);  // Call repository with today's date
    }

    public ArrayList<HomeCalDTO> getDueDAtes() {
        List<Credit> allCredits = creditRepository.findAll();
        ArrayList<HomeCalDTO> dueDatesDTO = new ArrayList<>();
        Set<String> uniqueDueDates = new HashSet<>(); // To ensure distinct dates

        if (allCredits != null) {
            for (Credit credit : allCredits) {
                String dueDate = String.valueOf(credit.getDueDate());
                if (dueDate != null && uniqueDueDates.add(dueDate)) {
                    dueDatesDTO.add(new HomeCalDTO(dueDate));
                }
            }
        }

        return dueDatesDTO;
    }

    public List<BillCountDTO> getBillsFromLast7Days() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minus(7, ChronoUnit.DAYS);  // Get date 7 days ago

        List<BillCountDTO> billCountDTOList = new ArrayList<>();

        // Loop through each day in the past 7 days and get the count of bills for that day
        for (int i = 0; i < 7; i++) {
            LocalDate date = sevenDaysAgo.plus(i, ChronoUnit.DAYS);
            long count = billRepository.countByDate(date);  // Get count of bills for that date
            billCountDTOList.add(new BillCountDTO(date, count));  // Add to the list as BillCountDTO
        }

        return billCountDTOList;
    }


    public HomeStatsDTO getStats() {
        return new HomeStatsDTO((int) getTotalCategories(), (int) getTotalBrands(), (int) getTotalItems(), (int) getCountBillsByToday(), getBillsFromLast7Days());
    }
}