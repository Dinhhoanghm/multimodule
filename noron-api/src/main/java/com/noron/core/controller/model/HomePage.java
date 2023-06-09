package com.noron.core.controller.model;

import com.noron.core.data.response.core.PostResponse;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class HomePage {
    private List<PostResponse> postResponseList;
}
