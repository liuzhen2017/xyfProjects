package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDImageDto {

      private MultiValueMap<String, List<JDImage>> map;

}
