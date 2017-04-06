public enum Type {
	DOCTOR1, DOCTOR2, DOCTOR3;
	
	public String toString(){
		switch(this) {
			case DOCTOR1: return "doctor 1";
			case DOCTOR2: return "doctor 2";
			case DOCTOR3: return "doctor 3";
			default: return "invalid";
		}
	}
}