
public enum Status {
	FREE, TAKEN;
	
	public String toString(){
		switch(this){
		case FREE: return "free";
		case TAKEN: return "taken";
		default: return "invalid";
		}
	}
}
