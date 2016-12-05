package org.starfleet.evaluator;

public class Mine {

	Integer row;
	Integer col;

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Mine) {
			Mine s = (Mine) obj;
			return row.equals(s.row) && col.equals(s.col);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (String.valueOf(row + col)).hashCode();
	}

}
