package mta13438;

public class Obs {
	private Point pos;
	private float dx,dz,lenght,sabins;
	
	public Obs(){
		setPos(new Point());
		setWidth(0);
		setHeight(0);
		setLenght(0);
		setSabins(0);
	}
	
	public Obs(Point point,float dx,float dz,float lenght,float sabins){
		setPos(point);
		setWidth(dx);
		setHeight(dz);
		setLenght(lenght);
		setSabins(sabins);
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	public float getWidth() {
		return dx;
	}

	public void setWidth(float dx) {
		this.dx = dx;
	}

	public float getHeight() {
		return dz;
	}

	public void setHeight(float dz) {
		this.dz = dz;
	}

	public float getLenght() {
		return lenght;
	}

	public void setLenght(float lenght) {
		this.lenght = lenght;
	}

	public float getSabins() {
		return sabins;
	}

	public void setSabins(float sabins) {
		this.sabins = sabins;
	}

	@Override
	public String toString() {
		return "Obs [pos=" + pos + ", dx=" + dx + ", dz=" + dz
				+ ", lenght=" + lenght + ", sabins=" + sabins + "]";
	}
	
	/*public boolean equals(Object obj){
		if (obj == null) return false;
		if (!(obj instanceof Obs)) return false;
		Obs obs = (Obs)obj;
		if (!getPos().equals(obs.getPos())) return false;
		if (!(getWidth() == obs.getWidth())) return false;
		if (!(getHeight() == obs.getHeight())) return false;
		if (!(getLenght() == obs.getLenght())) return false;
		if (!(getSabins() == obs.getSabins())) return false;
		return true;
	}
	
	public int compareTo(Obs obs){
		if (obs == null) return 1;
		if (this.equals(obs)) return 0;
		if (getLenght() > obs.getLenght()) return 1;
		if (getLenght() < obs.getLenght()) return -1;
		if (getHeight() > obs.getHeight()) return 1;
		if (getHeight() < obs.getHeight()) return -1;
		if (getWidth() > obs.getWidth()) return 1;
		if (getWidth() < obs.getWidth()) return -1;
		if (getPos().compareTo(obs.getPos()) > 0) return 1;
		if (getPos().compareTo(obs.getPos()) < 0) return -1;
		if (getSabins() > obs.getSabins()) return 1;
		return -1;
	}*/
}
