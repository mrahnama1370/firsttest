package com.chapar.firsttest;

import com.chapar.firsttest.model.Customer;
import com.chapar.firsttest.service.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerServiceImpl userService;

    @Test
    public void checkGetCustomrResponse() throws Exception {

        Customer customer = new Customer();
        customer.setId("2");
        customer.setDescription("description");
        customer.setFirstName("mohammadreza");
        customer.setLastName("rahnama");
        customer.setEmail("rahnama.m1370@gmail.com");

        when(userService.findCustomerById("2"))
                .thenReturn(customer);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/customers/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("des"));
    }
}
