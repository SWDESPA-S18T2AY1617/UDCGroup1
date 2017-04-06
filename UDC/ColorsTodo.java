public enum ColorsTodo {
	
	RED, YELLOW, ORANGE;
	public String toString(){
		switch(this){
			case RED: return "red";
			case YELLOW: return "yellow";
			case ORANGE: return "orange";
			default:	return "black";
		}
	}
}