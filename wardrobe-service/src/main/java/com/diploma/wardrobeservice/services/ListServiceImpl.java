package com.diploma.wardrobeservice.services;

import com.diploma.wardrobeservice.entities.Brand;
import com.diploma.wardrobeservice.entities.Colour;
import com.diploma.wardrobeservice.entities.Season;
import com.diploma.wardrobeservice.entities.Type;
import com.diploma.wardrobeservice.repositories.BrandRepository;
import com.diploma.wardrobeservice.repositories.ColourRepository;
import com.diploma.wardrobeservice.repositories.SeasonRepository;
import com.diploma.wardrobeservice.repositories.TypeRepository;
import com.diploma.wardrobeservice.service_interfaces.ListService;
import com.diploma.wardrobeservice.transfers.BrandResponse;
import com.diploma.wardrobeservice.transfers.ColourResponse;
import com.diploma.wardrobeservice.transfers.SeasonResponse;
import com.diploma.wardrobeservice.transfers.TypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListServiceImpl implements ListService {
    private final BrandRepository brandRepository;
    private final ColourRepository colourRepository;
    private final SeasonRepository seasonRepository;
    private final TypeRepository typeRepository;

    private BrandResponse toDTO(Brand brand) {
        return BrandResponse.builder()
                .id(Long.valueOf(brand.getId()))
                .name(brand.getName())
                .build();
    }

    private SeasonResponse toDTO(Season season) {
        return SeasonResponse.builder()
                .id(season.getId())
                .name(season.getName())
                .build();
    }

    private TypeResponse toDTO(Type type) {
        return TypeResponse.builder()
                .id(type.getId())
                .name(type.getName())
                .build();
    }

    private ColourResponse toDTO(Colour colour) {
        return ColourResponse.builder()
                .id(colour.getId())
                .name(colour.getName())
                .colourcode(colour.getColourcode())
                .build();
    }

    @Override
    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ColourResponse> getAllColours() {
        return colourRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeasonResponse> getAllSeasons() {
        return seasonRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TypeResponse> getAllTypes() {
        return typeRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
