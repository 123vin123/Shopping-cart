package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.dto.OrderDto;
import com.dailycodework.dream_shops.exceptions.ResourceNotFounException;
import com.dailycodework.dream_shops.model.Order;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse>createOrder(@RequestParam Long userId){
        try {
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Item Order Success", orderDto));
        } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occured",e.getMessage()));
        }
    }
@GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse>getOrderById(@PathVariable Long orderId){
        try {
            OrderDto order =  orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Item Order Successfully" , order));
        } catch (ResourceNotFounException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("OPPs!",e.getMessage()));
        }

    }

@GetMapping("/{userId}/order")
public ResponseEntity<ApiResponse>getUserOrder(@PathVariable Long userId){
    try {
        List<OrderDto> order =  orderService.getUserOrders(userId);
        return ResponseEntity.ok(new ApiResponse("Item Order Successfully" , order));
    } catch (ResourceNotFounException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("OPPs!",e.getMessage()));
    }

}



}
