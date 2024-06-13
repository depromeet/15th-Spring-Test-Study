package com.depromeet.yunbeom;

// Request 구조화
public class CalculationRequest {

	// final로 추가하여 VO로 만든다.
	/** VO의 특징
	 * VO 안에 변수들은 값이 항상 유효하다.
	 * VO 안에 변수들은 변경되지 않는다.
	 *
	 */
	private final long num1;
	private final long num2;
	private final String operator;

	public CalculationRequest(String[] parts) {
		if (parts.length != 3) {
			throw new BadRequestException();
		}
		String operator = parts[1];
		if (isInvalidOperator(operator)) {
			throw new InvalidOperatorException();
		}
		this.num1 = Long.parseLong(parts[0]);
		this.num2 = Long.parseLong(parts[2]);
		this.operator = operator;
	}

	private boolean isInvalidOperator(String operator) {
		return !operator.equals("+") && !operator.equals("-") && !operator.equals("*") && !operator.equals("/");
	}

	public long getNum1() {
		return num1;
	}

	public long getNum2() {
		return num2;
	}

	public String getOperator() {
		return operator;
	}
}
