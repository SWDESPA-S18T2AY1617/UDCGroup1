public enum Months {
	JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, 
	AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;

	public int toInt(){
		switch(this) {
			case JANUARY: return 0;
			case FEBRUARY: return 1;
			case MARCH: return 2;
			case APRIL: return 3;
			case MAY: return 4;
			case JUNE: return 5;
			case JULY: return 6; 
			case AUGUST: return 7;
			case SEPTEMBER: return 8;
			case OCTOBER: return 9;
			case NOVEMBER: return 10;
			case DECEMBER: return 11;
			default: return 12;
		}
	}

	public String toString(){
		switch(this) {
			case JANUARY: return "JANUARY";
			case FEBRUARY: return "FEBRUARY";
			case MARCH: return "MARCH";
			case APRIL: return "APRIL";
			case MAY: return "MAY";
			case JUNE: return "JUNE";
			case JULY: return "JULY"; 
			case AUGUST: return "AUGUST";
			case SEPTEMBER: return "SEPTEMBER";
			case OCTOBER: return "OCTOBER";
			case NOVEMBER: return "NOVEMBER";
			case DECEMBER: return "DECEMBER";
			default: return "NULL";
		}
	}
}