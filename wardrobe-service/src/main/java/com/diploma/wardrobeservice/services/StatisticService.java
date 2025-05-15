package com.diploma.wardrobeservice.services;

import com.diploma.wardrobeservice.entities.Clothes;
import com.diploma.wardrobeservice.entities.Outfit;
import com.diploma.wardrobeservice.repositories.CalendarEntryRepository;
import com.diploma.wardrobeservice.entities.CalendarEntry;
import com.diploma.wardrobeservice.repositories.ClothesRepository;
import com.diploma.wardrobeservice.repositories.OutfitRepository;
import com.diploma.wardrobeservice.transfers.*;
import com.diploma.wardrobeservice.entities.OutfitClothes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatisticService {
    private final CalendarEntryRepository calendarEntryRepository;
    private final ClothesRepository clothesRepository;
    private final OutfitRepository outfitRepository;
    private final AccessService accessService;
    private final WardrobeService wardrobeService;

    public StatisticsPerPeriodResponse getCreatedStatisticsPerPeriod(Long userId,
                                                                     Long wardrobeId,
                                                                     StatisticsPeriodRequest request) {
        var wardrobe = wardrobeService.getWardrobeOrThrow(wardrobeId);
        accessService.checkFullEditAccessThrow(userId, wardrobe);

        var start = request.getStartDate();
        var end = request.getEndDate();

        List<Clothes> createdClothes = clothesRepository.findAllByWardrobeIdAndCreatedAtBetweenAndIsDeletedFalse(wardrobeId, start, end);
        List<Outfit> createdOutfits = outfitRepository.findAllByWardrobeIdAndCreatedAtBetweenAndIsDeletedFalse(wardrobeId, start, end);

        return StatisticsPerPeriodResponse.builder()
                .clothesNumber(createdClothes.size())
                .outfitsNumber(createdOutfits.size())
                .build();
    }

    public StatisticsPerPeriodResponse getPlannedStatistics(Long userId, LocalDate startDate, LocalDate endDate) {
        List<CalendarEntry> calendarEntries = calendarEntryRepository
                .findAllByDateBetweenAndUserIdAndNotDeleted(startDate, endDate, userId);

        var plannedOutfits = calendarEntries.stream()
                .map(CalendarEntry::getOutfit)
                .filter(outfit -> !outfit.getIsDeleted())
                .toList();

        var plannedClothes = plannedOutfits.stream()
                .flatMap(outfit -> outfit.getOutfitClothes().stream())
                .map(OutfitClothes::getCloth)
                .filter(clothes -> !clothes.getIsDeleted())
                .toList();

        return StatisticsPerPeriodResponse.builder()
                .outfitsNumber(plannedOutfits.size())
                .clothesNumber(plannedClothes.size())
                .build();
    }

    public StatisticsResponse getStatistics(Long userId, Long wardrobeId) {
        var wardrobe = wardrobeService.getWardrobeOrThrow(wardrobeId);
        accessService.checkFullEditAccessThrow(userId, wardrobe);

        var allClothes = clothesRepository.findAllByWardrobeIdAndIsDeletedFalse(wardrobeId);
        var allOutfits = outfitRepository.findAllByWardrobeIdAndIsDeletedFalse(wardrobeId);

        var favouriteBrand = allClothes.stream()
                .filter(c -> c.getBrand() != null)
                .collect(Collectors.groupingBy(Clothes::getBrand, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .map(BrandResponse::from)
                .orElse(null);

        var favouriteColour = allClothes.stream()
                .filter(c -> c.getColour() != null)
                .collect(Collectors.groupingBy(Clothes::getColour, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .map(ColourResponse::from)
                .orElse(null);

        return StatisticsResponse.builder()
                .allClothesNumber(allClothes.size())
                .allOutfitsNumber(allOutfits.size())
                .favouriteBrand(favouriteBrand)
                .favouriteColour(favouriteColour)
                .build();
    }
}
