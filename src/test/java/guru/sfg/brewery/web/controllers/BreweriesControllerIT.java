package guru.sfg.brewery.web.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BreweriesControllerIT extends BaseIT {

    @Test
    void listBreweriesCUSTOMER() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
        .with(httpBasic("scott","tiger")))
                .andExpect(status().isForbidden());
    }

    @Test
    void listBreweriesADMIN() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("spring","guru")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void listBreweriesUSER() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("user","password")))
                .andExpect(status().is2xxSuccessful());
    }
}
