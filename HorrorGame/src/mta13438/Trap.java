package mta13438;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex2i;

public class Trap extends Obs {
	
	private Sound trapKillSound = new Sound(TRAPKILLSOUND,getPos(),10,10);
	private Sound trapSound = new Sound(TRAPSOUND,getPos(),10,10);
	
	public void update(){
		trapSound.loop();
	}
	public void collide(){
		trapKillSound.play();
	}
	@Override
	public void draw(){
		glColor4f(0.5f, 0.4f, 0.3f, 0.2f);
	   	 glLineWidth(1.2f);
	   	 glBegin(GL_LINES);
	   		 //Bottom Line
	   		 glVertex2i((int)getPos().getX(), (int)getPos().getY());
	   		 glVertex2i((int)(getPos().getX()+this.getDx()), (int)getPos().getY());
	   		 //Left Line
	   		 glVertex2i((int)getPos().getX(), (int)getPos().getY());
	   		 glVertex2i((int)getPos().getX(), (int)(getPos().getY()+this.getDy()));
	   		 //Top Line
	   		 glVertex2i((int)getPos().getX(), (int)(getPos().getY()+this.getDy()));
	   		 glVertex2i((int)(getPos().getX()+this.getDx()), (int)(getPos().getY()+this.getDy()));
	   		 //Right Line
	   		 glVertex2i((int)(getPos().getX()+this.getDx()), (int)(getPos().getY()+this.getDy()));
	   		 glVertex2i((int)(getPos().getX()+this.getDx()), (int)getPos().getY());
	   	 glEnd();
	}
	
	@Override
	public String toString() {
		return "Trap [pos=" + getPos() + ", dx=" + getDx() + ", dz=" + getDz()
				+ ", lenght=" + getDy() + ", sabins=" + getSabins() + "]";
	}
}
