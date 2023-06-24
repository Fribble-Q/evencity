package com.fribbleQ.evencity.DTO;


import com.fribbleQ.evencity.entity.Setmeal;
import com.fribbleQ.evencity.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
