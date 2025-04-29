package com.diploma.socialservice.transfers;

import lombok.Data;

@Data
public class PostImageRequest {
    private String imagePath;
    private Short position;
    private Long outfitId;
}
