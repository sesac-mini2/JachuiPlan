package com.trace.jachuiplan.regioncd;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegioncdApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @DisplayName("시도 단위 리스트를 불러온다.")
    @Test
    public void getSidoList() throws Exception {
        // given
        final String url = "/api/regioncd";

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        for (int i = 0; i < 1; i++)
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$["+i+"].sggCd").value("000"))
                    .andExpect(jsonPath("$["+i+"].umdCd").value("000"));
    }

    @DisplayName("시군구 단위 리스트를 불러온다.")
    @Test
    public void getSggList() throws Exception {
        // given
        final String sidocd = "11";
        final String url = "/api/regioncd/" + sidocd;

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        for (int i = 0; i < 1; i++)
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$["+i+"].sidoCd").value(sidocd))
                    .andExpect(jsonPath("$["+i+"].sggCd").value(Matchers.not("000")))
                    .andExpect(jsonPath("$["+i+"].umdCd").value("000"));
    }
}
