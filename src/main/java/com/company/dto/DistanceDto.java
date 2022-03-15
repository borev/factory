package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistanceDto {
   private String locationA;
   private String locationB;
   private Long miles;

}
