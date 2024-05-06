package com.btv.app.features.payment.services;

import com.btv.app.features.authentication.services.AuthenticationService;
import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.membership.services.MembershipService;
import com.btv.app.features.user.models.User;
import com.btv.app.features.user.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@AllArgsConstructor
public class VnPayReturnController {
    private PaymentService paymentService;
    private UserService userService;
    private AuthenticationService authenticationService;
    private MembershipService membershipService;

    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model){
        int paymentStatus = paymentService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        int fTotalPrice = Integer.parseInt(request.getParameter("vnp_Amount"))/100;

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", fTotalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        Membership memId = membershipService.getMembershipByPrice(fTotalPrice);
        User user = userService.getUserByID(Long.valueOf(orderInfo).longValue());
        if(paymentStatus == 1){
            userService.modifyUserMembership(user, memId);
            return "ordersuccess.html";
        } else{
            return "orderfail.html";
        }
    }
}
