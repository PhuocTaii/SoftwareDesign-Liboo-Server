package com.btv.app.features.payment.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequestMapping("api/payment")
@RestController
@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;

    // Chuyển hướng người dùng đến cổng thanh toán VNPAY
    @PostMapping("/submitOrder")
    public String submitOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request){
//        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String baseUrl =  "http://softwaredesign-liboo-server-production.up.railway.app:8002"
        System.out.println(baseUrl);
        String vnpayUrl = paymentService.createOrder(request, orderTotal, orderInfo, baseUrl);
        System.out.println(vnpayUrl);
        return vnpayUrl;
    }

    // Sau khi hoàn tất thanh toán, VNPAY sẽ chuyển hướng trình duyệt về URL này

}