package kr.co.cofile.sbapivite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductAddResponse {

    List<String> uploadFileNames;
    private Long id;

}
