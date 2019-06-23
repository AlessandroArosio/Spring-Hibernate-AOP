package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

  @Autowired
  private CustomerService service;

  @GetMapping("/list")
  public String listCustomers(Model theModel) {
    List<Customer> theCustomers = service.getCustomers();
    theModel.addAttribute("customers", theCustomers);
    return "list-customers";
  }

  @GetMapping("/showFormForAdd")
  public String showFormForAdd(Model model) {
    Customer theCustomer = new Customer();
    model.addAttribute("customer", theCustomer);
    return "customer-form";
  }


  @PostMapping("/saveCustomer") // line 27 of JSP: this has to be the same as the action to be mapped correctly
  public String saveCustomer(@ModelAttribute("customer") // this must be the same as modelAttribute in JSP
                                   Customer customer) {
    service.saveCustomer(customer);
    return "redirect:/customer/list";
  }

  @GetMapping("/showFormForUpdate")
  public String updateForm(@RequestParam("customerId") int id, Model model) {
    Customer customer = service.getCustomer(id);
    model.addAttribute("customer", customer);
    return "customer-form";
  }

  @GetMapping("/delete")
  public String deleteCustomer(@RequestParam("customerId") int id) {
    service.deleteCustomer(id);
    return "redirect:/customer/list";
  }
}


