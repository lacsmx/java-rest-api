package com.lacsmx.chargingsessionstore.rest;


import com.lacsmx.chargingsessionstore.enums.StatusEnum;
import com.lacsmx.chargingsessionstore.models.ChargingSession;
import com.lacsmx.chargingsessionstore.service.ChargingSessionService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChargingSessionControllerTests {

	private static final String BASE_URL = "/api/v1/chargingSessions/";

	@Autowired
	private MockMvc mvc;

	@Autowired
	ChargingSessionService chargingSessionService;

	private ChargingSession fixexChargingSession;

	@Before
	public void initTests() {
		fixexChargingSession = new ChargingSession("Test station 1");
		chargingSessionService.create(fixexChargingSession);
	}

	@Test
	public void test1OneSessionRegistered() throws Exception {
		MvcResult result = allChargingSessions()
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].stationId", is("Test station 1")))
				.andExpect(jsonPath("$[0].status", is("IN_PROGRESS")))
				.andReturn();
	}

	@Test
	public void test2NoFoundChargingSessions() throws Exception {
		MvcResult result = chargingSessionById("XX")
				.andExpect(status().isNotFound())
				.andReturn();
	}

	@Test
	public void test3FoundChargingSession() throws Exception {
		MvcResult result = chargingSessionById(fixexChargingSession.getId().toString())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(fixexChargingSession.getId().toString())))
				.andExpect(jsonPath("$.stationId", is("Test station 1")))
				.andExpect(jsonPath("$.status", is("IN_PROGRESS")))
				.andReturn();
	}

	@Test
	public void test4BadNewChargingSession() throws Exception {
		MvcResult result = registerChargingSession("")
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	public void test5NewChargingSession() throws Exception {
		MvcResult result = registerChargingSession("ABC-12345")
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void test6UpdateChargingSession() throws Exception {
		MvcResult result = updateChargingSession(fixexChargingSession.getId().toString())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(fixexChargingSession.getId().toString())))
				.andExpect(jsonPath("$.stationId", is("Test station 1")))
				.andExpect(jsonPath("$.status", is("FINISHED")))
				.andReturn();
	}

	@Test
	public void test7SummaryChargingSession() throws Exception {
		MvcResult result = getSummaryChargingSession()
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalCount", is(10)))
				.andExpect(jsonPath("$.startedCount", is(8)))
				.andExpect(jsonPath("$.stoppedCount", is(2)))
				.andReturn();
	}

	private ResultActions allChargingSessions() throws Exception {
		return mvc.perform(get(BASE_URL).accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions chargingSessionById(String id) throws Exception {
		return mvc.perform(get(BASE_URL + id)
				.accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions registerChargingSession(String stationId) throws Exception {
		return mvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
				.content("{\"stationId\":\""+stationId+"\"}"));
	}

	private ResultActions updateChargingSession(String id) throws Exception {
		return mvc.perform(put(BASE_URL+id)
				.accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions getSummaryChargingSession() throws Exception {
		LocalDateTime currentTime = LocalDateTime.now();
		ChargingSession cs1 = new ChargingSession(
				"Test station 2",
				currentTime.minusMinutes(10),
				currentTime,
				StatusEnum.FINISHED
		);

		ChargingSession cs2 = new ChargingSession(
				"Test station 3",
				currentTime.minusMinutes(50),
				null,
				StatusEnum.IN_PROGRESS
		);

		ChargingSession cs3 = new ChargingSession(
				"Test station 4",
				currentTime.minusMinutes(45),
				currentTime.minusMinutes(20),
				StatusEnum.FINISHED
		);

		chargingSessionService.create(cs1);
		chargingSessionService.create(cs2);
		chargingSessionService.create(cs3);

		return mvc.perform(get(BASE_URL+"/summary")
				.accept(MediaType.APPLICATION_JSON));
	}
}
