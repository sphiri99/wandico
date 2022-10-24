package com.wandico.wandico.service;

import com.wandico.wandico.entity.OrderSurmmary;
import com.wandico.wandico.entity.Product;
import com.wandico.wandico.entity.ProductionDetails;
import com.wandico.wandico.repo.OrderRepo;
import com.wandico.wandico.repo.ProductRepo;
import com.wandico.wandico.repo.ProductionRepo;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDetailsService {
    private OrderRepo orderRepo;
    private ProductionRepo productionRepo;
    private ProductRepo productRepo;

    public OrderDetailsService(OrderRepo orderRepo, ProductionRepo productionRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.productionRepo = productionRepo;
        this.productRepo = productRepo;
    }

    public String saveOrderDetails(Optional<OrderSurmmary> orderSurmmary) {
        OrderSurmmary orderSurmmaryInstance = orderRepo.findByNameAndItemName(orderSurmmary.get().getName(), orderSurmmary.get().getItemName());
        if (null == orderSurmmaryInstance) {
            orderSurmmary.get().setCreatedOn(new Date());

            try {
                orderSurmmary.get().setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().plusDays(21).toString()));

            } catch (ParseException e) {
                throw new RuntimeException("Failed to convert local date to date");
            }
            orderSurmmary.get().setEstTurnaroundTime(getTurnAroundTime(orderSurmmary));
            orderSurmmary.get().setStatus("PENDING");

            if(null != orderSurmmary.get().getEstTurnaroundTime()) {
                //saving the order to the database, meaning we have the product selected
                orderRepo.save(orderSurmmary.get());
                return "Successfully created an order for".concat(" ").concat(orderSurmmary.get().getName());

            }

        }
        return "Failed to save the order, we do not have the product selected";
    }

    public Date getTurnAroundTime(Optional<OrderSurmmary> orderSurmmary) {
        Date turnAroundTime = null;
        List<ProductionDetails> all = productionRepo.findAll();
        List<Product> products = productRepo.findAll();
        List<OrderSurmmary> orderSurmmaryList = getOrderDetailsListSorted();
        if (orderSurmmaryList != null & orderSurmmaryList.size() > 0) {
            Date date = null;

            if(orderSurmmaryList.size() > 1) {
                 date = orderSurmmaryList.get(orderSurmmaryList.size()-2).getEstTurnaroundTime();

            }else{
                date = orderSurmmaryList.get(0).getEstTurnaroundTime();
            }
            if (products != null && products.size() > 0) {
                for (Product product : products) {
                    if (product.getProductName().equalsIgnoreCase(orderSurmmary.get().getItemName())) {
                        ProductionDetails productionDetails = all.get(0);
                        int numberCanProduce = (int) ((productionDetails.getEmployees() * 8) / product.getProdTurnAroundTime());
                        float value = (float) orderSurmmary.get().getQuantity() / numberCanProduce;
                        int days = (int) Math.ceil(value);
                        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        localDate.plusDays(days);
                        try {
                            turnAroundTime = new SimpleDateFormat("yyyy-MM-dd").parse(localDate.plusDays(days).toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        } else {
            Date date = orderSurmmary.get().getCreatedOn();
            ProductionDetails productionDetails = all.get(0);
            if (products != null && products.size() > 0) {
                for (Product product : products) {
                    if (product.getProductName().equalsIgnoreCase(orderSurmmary.get().getItemName())) {
                        int numberCanProduce = (int) ((productionDetails.getEmployees() * 8) / product.getProdTurnAroundTime());
                        float value = (float) orderSurmmary.get().getQuantity() / numberCanProduce;
                        int days = (int) Math.ceil(value);
                        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        localDate.plusDays(days);
                        try {
                            turnAroundTime = new SimpleDateFormat("yyyy-MM-dd").parse(localDate.plusDays(days).toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

        }

        return turnAroundTime;

    }

    public List<OrderSurmmary> getOrderDetailsList() {
        return orderRepo.findAll();

    }

    public List<OrderSurmmary> getOrderDetailsListSorted() {
        List<OrderSurmmary> orderSurmmaryList = orderRepo.findAll();
        List<OrderSurmmary> all = new ArrayList<>();
        if (orderSurmmaryList != null && orderSurmmaryList.size() > 0) {
            for(OrderSurmmary orderSurmmary : orderSurmmaryList) {
               if (!orderSurmmary.getStatus().equalsIgnoreCase("COMPLETE")){
                   all.add(orderSurmmary);
               }
            }
            all.sort((OrderSurmmary s1, OrderSurmmary s2) -> (int) (s2.getId() - s1.getId()));

        }

        return all;
    }


    public List<OrderSurmmary> getCompletedOrderDetailsListSorted() {
        List<OrderSurmmary> orderSurmmaryList = orderRepo.findAll();
        List<OrderSurmmary> all = new ArrayList<>();
        if (orderSurmmaryList != null && orderSurmmaryList.size() > 0) {
            for(OrderSurmmary orderSurmmary : orderSurmmaryList) {
                if (orderSurmmary.getStatus().equalsIgnoreCase("COMPLETE")){
                    all.add(orderSurmmary);
                }
            }
            all.sort((OrderSurmmary s1, OrderSurmmary s2) -> (int) (s2.getId() - s1.getId()));

        }

        return all;
    }


    public Optional<OrderSurmmary> getOrderSurmmary(Long id) {
        return orderRepo.findById(id);
    }

    public void deleteOrderSurmmary(OrderSurmmary orderSurmmary) {
        orderRepo.delete(orderSurmmary);
    }

    public void updateOrderSurmmary(OrderSurmmary orderSurmmary) {
        orderRepo.save(orderSurmmary);
    }


}
