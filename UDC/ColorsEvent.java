public enum ColorsEvent {
	
	BLUE, GREEN, GRAY;
	public String toString(){
		switch(this){
			case BLUE: return "blue";
			case GREEN: return "green";
			case GRAY: return "gray";
			default:	return "black";
		}
	}
}