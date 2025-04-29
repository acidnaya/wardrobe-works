package com.diploma.wardrobeservice.services;

import com.diploma.wardrobeservice.entities.Brand;
import com.diploma.wardrobeservice.entities.Colour;
import com.diploma.wardrobeservice.entities.Season;
import com.diploma.wardrobeservice.entities.Type;
import com.diploma.wardrobeservice.exceptions.ResourceNotFoundException;
import com.diploma.wardrobeservice.repositories.BrandRepository;
import com.diploma.wardrobeservice.repositories.ColourRepository;
import com.diploma.wardrobeservice.repositories.SeasonRepository;
import com.diploma.wardrobeservice.repositories.TypeRepository;
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
public class ListService {
    private final BrandRepository brandRepository;
    private final ColourRepository colourRepository;
    private final SeasonRepository seasonRepository;
    private final TypeRepository typeRepository;

    Brand getBrandOrThrow(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
    }

    Colour getColourOrThrow(Long id) {
        return colourRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colour not found"));
    }

    Season getSeasonOrThrow(Long id) {
        return seasonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Season not found"));
    }

    Type getTypeOrThrow(Long id) {
        return typeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type not found"));
    }

    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(BrandResponse::from)
                .collect(Collectors.toList());
    }

    public List<ColourResponse> getAllColours() {
        return colourRepository.findAll().stream()
                .map(ColourResponse::from)
                .collect(Collectors.toList());
    }

    public List<SeasonResponse> getAllSeasons() {
        return seasonRepository.findAll().stream()
                .map(SeasonResponse::from)
                .collect(Collectors.toList());
    }

    public List<TypeResponse> getAllTypes() {
        return typeRepository.findAll().stream()
                .map(TypeResponse::from)
                .collect(Collectors.toList());
    }
}
