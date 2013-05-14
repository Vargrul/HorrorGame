package mta13438;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex2i;

public class Event extends Obs {
	private boolean active = true;
	private boolean trigger = false;
	private Sound scareSound = new Sound(SOUNDS.SCARE, new Point(0,0,0), false, true);
	
	public Event(){
		super();
	}
	
	public Event(Point point,float dx,float dy,float dz,MATERIALS material) {
		super(point, dx,dy,dz,material);
		
	}
	@Override
	public void collision(Player player, Level level, int currentRoom){
		System.out.println("Collision with Event Object");
		trigger = true;
	}
	
	@Override
	public void draw(){
		if(active==true){
			glColor4f(0.9f, 0.1f, 0.3f, 0.2f);
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
		
	}
	
	public void event(){
		System.out.println("Specific event happens");
		active = false;
		//Making a sub class for each event needed for the game
		//Event object should be move to new Point();
	}

	public boolean isTrigger() {
		return trigger;
	}
	
	public Sound getScareSound(){
		return this.scareSound;
	}

	public void setTrigger(boolean trigger) {
		this.trigger = trigger;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
