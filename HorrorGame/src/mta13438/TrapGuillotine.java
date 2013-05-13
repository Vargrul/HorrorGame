package mta13438;

public class TrapGuillotine extends Trap{
	
	private boolean trapOn;

	public TrapGuillotine(){
		super();
		trapOn = true;
	}
	public TrapGuillotine(Point point,float dx,float dy,float dz,MATERIALS material) {
		super(point, dx,dy,dz,material);
		trapOn = true;
	}
	@Override
	public void collision(Player player, Level level, int currentRoom){
		System.out.println("Collision with guillotine trap");
		if(trapOn == true){
			player.kill(level);
		}
	}
	public void update(int delta){
		//trapSound.loop();
		System.out.println("delta");
	}
}
