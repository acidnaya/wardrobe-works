package com.diploma.wardrobeservice.service_interfaces;

import com.diploma.wardrobeservice.transfers.BrandResponse;
import com.diploma.wardrobeservice.transfers.ColourResponse;
import com.diploma.wardrobeservice.transfers.SeasonResponse;
import com.diploma.wardrobeservice.transfers.TypeResponse;
import java.util.List;

public interface ListService {
    List<BrandResponse> getAllBrands();

    List<ColourResponse> getAllColours();

    List<SeasonResponse> getAllSeasons();

    List<TypeResponse> getAllTypes();
}
