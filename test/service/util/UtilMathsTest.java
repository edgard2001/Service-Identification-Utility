package service.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilMathsTest {

	@Test
	void testFactorialErroneous() {
		assertEquals(-1, UtilMaths.factorial(-1));
	}
	
	@Test
	void testFactorialBoundary() {
		assertEquals(1, UtilMaths.factorial(0));
	}
	
	@Test
	void testFactorialNormal() {
		assertEquals(1, UtilMaths.factorial(1));
		assertEquals(2, UtilMaths.factorial(2));
		assertEquals(6, UtilMaths.factorial(3));
		assertEquals(24, UtilMaths.factorial(4));
		assertEquals(120, UtilMaths.factorial(5));
		assertEquals(720, UtilMaths.factorial(6));
		assertEquals(5040, UtilMaths.factorial(7));
	}
	
	@Test
	void testCombinationsNormal() {
		assertEquals(1, UtilMaths.combinations(2,2));
		assertEquals(3, UtilMaths.combinations(3,2));
		assertEquals(6, UtilMaths.combinations(4,2));
		assertEquals(10, UtilMaths.combinations(5,2));
		assertEquals(15, UtilMaths.combinations(6,2));
		assertEquals(21, UtilMaths.combinations(7,2));
		assertEquals(28, UtilMaths.combinations(8,2));
		assertEquals(36, UtilMaths.combinations(9,2));
		assertEquals(45, UtilMaths.combinations(10,2));
		assertEquals(55, UtilMaths.combinations(11,2));
	}
	
	
}
