package mta13438;

public class Wall extends Obs {
	
	private Sound collisionSound = new Sound(HANDSONWALL,getPos(),10,10);
	
	private void collision(){
		collisionSound.play();
	}
	
	@Override
	public String toString() {
		return "Wall [pos=" + getPos() + ", dx=" + getDx() + ", dz=" + getDz()
				+ ", lenght=" + getLenght() + ", sabins=" + getSabins() + "]";
	}
}
