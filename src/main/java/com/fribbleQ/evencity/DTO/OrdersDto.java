package com.fribbleQ.evencity.DTO;


import com.fribbleQ.evencity.entity.OrderDetail;
import com.fribbleQ.evencity.entity.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;

}
