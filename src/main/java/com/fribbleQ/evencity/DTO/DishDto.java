package com.fribbleQ.evencity.DTO;

import com.fribbleQ.evencity.entity.Dish;
import com.fribbleQ.evencity.entity.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDto extends Dish {
     private List<DishFlavor> flavors = new ArrayList<>();
     private String categoryName;
     private Integer copies;

     public DishDto(Dish dish){
          super(dish);
     }
}
