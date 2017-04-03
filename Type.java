public enum Type {
	EVENT, TO_DO;
	public String toString(){
		switch(this) {
			case EVENT: return "event";
			case TO_DO: return "to do";
			default: return "invalid";
		}
	}
}