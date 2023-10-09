package com.zerobase.tech.user.userDomain.customer;


import com.zerobase.tech.user.userDomain.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerDto {

    private Long id;
    private String email;

    public static CustomerDto from(Customer customer){
        return new CustomerDto(customer.getId(),customer.getEmail());
    }
}
