package com.lacsmx.chargingsessionstore.service;


import com.lacsmx.chargingsessionstore.enums.StatusEnum;
import com.lacsmx.chargingsessionstore.models.ChargingSession;
import com.lacsmx.chargingsessionstore.models.Summary;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChargingSessionServiceTests {

	@Autowired
	ChargingSessionService chargingSessionService;

	@Test
	public void test1CreateCS() {
		ChargingSession newCS = new ChargingSession("Test station 1");

		ChargingSession insertCS = chargingSessionService.create(newCS);

		//Compare  current and saved objects
		Assert.assertTrue(insertCS.getId().equals(newCS.getId()));
		Assert.assertTrue(insertCS.getStationId().equals(newCS.getStationId()));
		Assert.assertTrue(insertCS.getStartedAt().equals(newCS.getStartedAt()));
		Assert.assertTrue(insertCS.getStatus().equals(newCS.getStatus()));
	}


	@Test
	public void test2getAllTest() {
		List<ChargingSession> list = (List<ChargingSession>) chargingSessionService.getAll();
		Assert.assertTrue (list.size() == 1);
	}

	@Test
	public void test4GetById(){
		ChargingSession cs = ((List<ChargingSession>) chargingSessionService.getAll()).get(0);
		ChargingSession csInserted = chargingSessionService.getById(cs.getId().toString());
		Assert.assertTrue(cs.getId().equals(csInserted.getId()));
		Assert.assertTrue(cs.getStationId().equals(csInserted.getStationId()));
		Assert.assertTrue(cs.getStartedAt().equals(csInserted.getStartedAt()));
		Assert.assertTrue(cs.getStatus().equals(csInserted.getStatus()));

	}

	@Test
	public void test5GetByIdNoFound(){
		ChargingSession csInserted = chargingSessionService.getById("InvalidID");
		Assert.assertTrue(csInserted == null);
	}

	@Test
	public void test6UpdateExistingElement(){
		ChargingSession cs = ((List<ChargingSession>) chargingSessionService.getAll()).get(0);
		ChargingSession csInserted = chargingSessionService.update(cs.getId().toString(),null);

		Assert.assertTrue(cs.getId().equals(csInserted.getId()));
		Assert.assertTrue(cs.getStationId().equals(csInserted.getStationId()));
		Assert.assertTrue(cs.getStartedAt().equals(csInserted.getStartedAt()));
		Assert.assertTrue(cs.getStoppedAt() != null );

		Assert.assertTrue(csInserted.getStatus().equals(StatusEnum.FINISHED));

	}

	@Test
	public void test7UpdateUnknownElement(){
		ChargingSession csInserted = chargingSessionService.update("x",null);
		Assert.assertTrue(csInserted == null);

	}

	@Test
	public void test8Summary(){
		LocalDateTime currentTime = LocalDateTime.now();
		System.out.println(currentTime);
		ChargingSession cs1 = new ChargingSession(
				"Test station 2",
				currentTime.minusMinutes(10),
				currentTime.minusMinutes(5),
				StatusEnum.FINISHED
		);

		ChargingSession cs2 = new ChargingSession(
				"Test station 3",
				currentTime,
				null,
				StatusEnum.IN_PROGRESS
		);
		chargingSessionService.create(cs1);
		chargingSessionService.create(cs2);

		Summary summary = chargingSessionService.summary();
		System.out.println(summary);
		Assert.assertTrue(summary.getTotalCount() == 3);
		Assert.assertTrue(summary.getStartedCount() == 2);
		Assert.assertTrue(summary.getStoppedCount() == 1);


	}

}
