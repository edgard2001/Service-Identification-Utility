package service.util;

public class UtilMaths {
	
	public static int factorial(int n) {
		if (n < 0) return -1;
		int factorial = 1;
		for (int i = 2; i <= n; i++) {
			factorial *= i;
		}
		return factorial;
	}
	
	public static int combinations(int n, int r) {
		return factorial(n) / factorial(r) / factorial(n - r);
	}
	
}
