package com.lacsmx.chargingsessionstore.storage;


import com.lacsmx.chargingsessionstore.enums.StatusEnum;
import com.lacsmx.chargingsessionstore.models.ChargingSession;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.Instant;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InMemmoryTests {
	private static DataRepositoryInterface repository = new InMemoryStorage();

	@Test
	public void test1TimeSingleInsert() {
		ChargingSession cs = new ChargingSession("Test station 1");

		Instant start = Instant.now();
		ChargingSession fixedChargingSession = (ChargingSession) repository.save(cs.getId().toString(), cs);
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println(timeElapsed);

		//Compare  current and saved objects
		Assert.assertTrue(cs.getId().equals(fixedChargingSession.getId()));
		Assert.assertTrue(cs.getStationId().equals(fixedChargingSession.getStationId()));
		Assert.assertTrue(cs.getStartedAt().equals(fixedChargingSession.getStartedAt()));
		Assert.assertTrue(cs.getStatus().equals(fixedChargingSession.getStatus()));

	}

	@Test
	public void test2TimeInsertN() {
		Instant start = Instant.now();
		for(int i = 0; i < 10000; i++) {
			ChargingSession cs = new ChargingSession("Test station 1");
			repository.save(cs.getId().toString(), cs);
		}
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println(timeElapsed);
		Assert.assertTrue (repository.getAll().size() == 10001);
	}

	@Test
	public void test3getAllTest() {
		Instant start = Instant.now();
		List<ChargingSession> list = repository.getAll();
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println(timeElapsed);
		Assert.assertTrue (list.size() == 10001);
	}

	@Test
	public void test4GetById(){
		ChargingSession cs = (ChargingSession) repository.getAll().get(0);
		ChargingSession csInserted = (ChargingSession) repository.search(cs.getId().toString());

		System.out.println(csInserted);
		Assert.assertTrue(cs.getId().equals(csInserted.getId()));
		Assert.assertTrue(cs.getStationId().equals(csInserted.getStationId()));
		Assert.assertTrue(cs.getStartedAt().equals(csInserted.getStartedAt()));
		Assert.assertTrue(cs.getStatus().equals(csInserted.getStatus()));

	}

	@Test
	public void test5GetByIdNoFound(){
		ChargingSession csInserted = (ChargingSession) repository.search("InvalidID");
		Assert.assertTrue(csInserted == null);
	}

	@Test
	public void test6UpdateExistingElement(){
		ChargingSession cs = (ChargingSession) repository.getAll().get(0);
		cs.setStatus(StatusEnum.FINISHED);
		ChargingSession csInserted = (ChargingSession) repository.update(cs.getId().toString(),cs);

		Assert.assertTrue(cs.getId().equals(csInserted.getId()));
		Assert.assertTrue(cs.getStationId().equals(csInserted.getStationId()));
		Assert.assertTrue(cs.getStartedAt().equals(csInserted.getStartedAt()));
		Assert.assertTrue(csInserted.getStatus().equals(StatusEnum.FINISHED));

	}

}
