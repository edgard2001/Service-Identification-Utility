package service.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilMathsTest {

	@Test
	void testErroneous() {
		assertEquals(-1, UtilMaths.factorial(-1));
	}
	
	@Test
	void testBoundary() {
		assertEquals(1, UtilMaths.factorial(0));
	}
	
	@Test
	void testNormal() {
		assertEquals(1, UtilMaths.factorial(1));
		assertEquals(2, UtilMaths.factorial(2));
		assertEquals(6, UtilMaths.factorial(3));
		assertEquals(24, UtilMaths.factorial(4));
		assertEquals(120, UtilMaths.factorial(5));
		assertEquals(720, UtilMaths.factorial(6));
		assertEquals(5040, UtilMaths.factorial(7));
	}
}
