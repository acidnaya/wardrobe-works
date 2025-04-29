package com.diploma.socialservice.transfers;

import lombok.Data;

import java.util.List;

@Data
public class PostCreateRequest {
    private String text;
    private List<PostImageRequest> postImages;
}
