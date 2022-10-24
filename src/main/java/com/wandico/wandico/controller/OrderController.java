package com.wandico.wandico.controller;

import com.wandico.wandico.entity.OrderSurmmary;
import com.wandico.wandico.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class OrderController {
    @Autowired
    private OrderDetailsService orderDetailsService;


    @GetMapping("/order")
    public String registration(Model model) {

        model.addAttribute("userForm", new OrderSurmmary());

        return "placeorder";
    }

    @PostMapping("/order")
    public String placeOrder(@ModelAttribute("userForm") Optional<OrderSurmmary> userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "placeorder";
        }

        orderDetailsService.saveOrderDetails(userForm);

        // securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/orders")
    public String viewAllOrders(Model model) {
        List<OrderSurmmary> orderDetailsList = orderDetailsService.getOrderDetailsListSorted();
        model.addAttribute("allorderlist", orderDetailsList);

        return "orderlist";
    }

    @GetMapping("/ordersList")
    public String orders(@ModelAttribute("userForm") OrderSurmmary userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "placeorder";
        }

        List<OrderSurmmary> orderDetailsList = orderDetailsService.getOrderDetailsListSorted();
        OrderSurmmary orderSurmmary = new OrderSurmmary();
        orderSurmmary.setStatus("PENDING");
        orderSurmmary.setCreatedOn(new Date());
        try {
            orderSurmmary.setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().plusDays(21).toString()));
            orderSurmmary.setEstTurnaroundTime(new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().plusDays(21).toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        orderSurmmary.setItemName("JACKETS");
        orderSurmmary.setName("Langsyde School");
        orderSurmmary.setId(1L);

        orderDetailsList.add(orderSurmmary);
        // securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

    @RequestMapping("/orderUpdate")
    public String empDelete(@ModelAttribute(value = "emp") OrderSurmmary emp, Map<String, Object> model) {
        OrderSurmmary orderSurmmary = orderDetailsService.getOrderSurmmary(emp.getId()).get();
        orderSurmmary.setStatus(emp.getStatus());
        if(emp.getQuantity() != 0) {
            orderSurmmary.setQuantity(emp.getQuantity());
            orderSurmmary.setEstTurnaroundTime(orderDetailsService.getTurnAroundTime(Optional.of(orderSurmmary)));
        }


        orderDetailsService.updateOrderSurmmary(orderSurmmary);

        List<OrderSurmmary> empL = orderDetailsService.getOrderDetailsListSorted();
        model.put("allorderlist", empL);

        return "redirect:orders";
    }

    @RequestMapping(value = "/orderUpdateToEdit", method = RequestMethod.POST)
    public String empUpdateEdit(@ModelAttribute(value = "emp") OrderSurmmary emp, Map<String, Object> model) {
        model.put("emp", emp);
        return "updateorder";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        OrderSurmmary order = orderDetailsService.getOrderSurmmary(id).get();
        if (order != null) {
            orderDetailsService.deleteOrderSurmmary(order);
        }

        return "redirect:/orders";
    }


    @GetMapping("/complete")
    public String completeOrders(Model model) {
        List<OrderSurmmary> orderDetailsList = orderDetailsService.getCompletedOrderDetailsListSorted();
        model.addAttribute("allorderlist", orderDetailsList);

        return "complete";
    }

    @PostMapping("/complete")
    public String complete(@ModelAttribute("userForm") OrderSurmmary userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "complete";
        }

        orderDetailsService.getCompletedOrderDetailsListSorted();

        // securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

}
