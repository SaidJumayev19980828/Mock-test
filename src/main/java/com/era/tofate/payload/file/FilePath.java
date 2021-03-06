package com.era.tofate.payload.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilePath {
    @ApiModelProperty(notes="path or url of file uploaded")
    private String filePath;
}
