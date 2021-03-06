package com.era.tofate.payload.publication;

import com.era.tofate.payload.photo.PhotoId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicationPhoto {
    @ApiModelProperty(notes="id of publication entity")
    private Long id;
    @ApiModelProperty(notes="list of publication photos")
    private Set<PhotoId> photos = new HashSet<>();
}
