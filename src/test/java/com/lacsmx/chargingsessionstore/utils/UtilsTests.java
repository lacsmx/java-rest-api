package com.lacsmx.chargingsessionstore.utils;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UtilsTests {

	@Test
	public void test1DateWithinrWindow() {
		LocalDateTime current = LocalDateTime.now();

		Assert.assertTrue(Utils.isInTimeWindow(current.plusSeconds(1), current, current.plusMinutes(1)));
		Assert.assertTrue(Utils.isInTimeWindow(current.plusSeconds(5), current, current.plusMinutes(1)));
		Assert.assertTrue(Utils.isInTimeWindow(current.plusSeconds(10), current, current.plusMinutes(1)));
		Assert.assertTrue(Utils.isInTimeWindow(current.plusSeconds(60), current, current.plusMinutes(1)));
		Assert.assertTrue(Utils.isInTimeWindow(current, current.minusSeconds(30), current.plusSeconds(30)));

	}

	@Test
	public void test2DateNotWithinrWindow(){
		LocalDateTime current = LocalDateTime.now();
		Assert.assertFalse(Utils.isInTimeWindow(current.minusSeconds(61), current, current.plusMinutes(1)));
		Assert.assertFalse(Utils.isInTimeWindow(current.plusSeconds(61), current, current.plusMinutes(1)));

	}

}
