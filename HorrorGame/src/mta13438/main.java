package mta13438;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.ArrayList;
import org.lwjgl.opengl.Display;


public class main {

	/**
	 * @param args
	 */
	public static int CurrentLevel = 0;
	public static Player player = new Player(new Point(15, 20, 4), 0, 0, 50);
	

	public static void main(String[] args) {
		Loader loader = new Loader();
   	 	loader.start();
		System.out.println("HelloWorld GitHub is working!!!");
		player.setPos(new Point(15, 20, 4));
		/*DebugInterface.Initialize(800, 600); // Width and Length of display
		DebugInterface.InitOpenGL(300, 300); // Width and Length inside the display (Scaling of perspective here)*/
		Render();
	}

	public static  void Render(){

		//Just setting up a list of Rooms for testing purpose
		/*Level levelTutorial = new Level(new ArrayList<Room>(), 0, 0, 0);
		levelTutorial.addRoomList(new Room(new Point(10, 10, 0), 20, 20, 8, new Point(), new Point(30, 16, 0), MATERIALS.ROCK));
		levelTutorial.addRoomList(new Room(new Point(30, 12, 0), 30, 10, 8, new Point(30, 17, 0), new Point(60, 17, 0), MATERIALS.ROCK));
		levelTutorial.addRoomList(new Room(new Point(60, 12, 0), 30, 30, 8, new Point(60, 16, 0), new Point(80, 42, 0), MATERIALS.ROCK));
		levelTutorial.addRoomList(new Room(new Point(75, 42, 0), 10, 40, 8, new Point(80, 43, 0), new Point(80, 82, 0), MATERIALS.ROCK));
		levelTutorial.addRoomList(new Room(new Point(70, 82, 0), 35, 25, 8, new Point(80, 83, 0), new Point(105, 103, 0), MATERIALS.ROCK));
		levelTutorial.addRoomList(new Room(new Point(105, 100, 0), 20, 6, 8, new Point(106, 103, 0), new Point(125, 103, 0), MATERIALS.ROCK));
		levelTutorial.addRoomList(new Room(new Point(125, 70, 0), 10, 40, 8, new Point(125, 104, 0), new Point(), MATERIALS.ROCK));*/
		
		//Set player pos as it don't work from the Constructor
		player.setPos(new Point(15, 20, 4));
		
		//Create the control object for catching key presses
		Controls controls = new Controls();
		//End of setting up Room list

		//Render/Update Loop
		while(!Display.isCloseRequested()) {
			
			//Game loop
			controls.takeInput();
			if(controls.getKEY_UP()){
				player.foward();
			}
			if(controls.getKEY_DOWN()){
				player.backward();
			}
			if(controls.getKEY_LEFT()){
				player.turnLeft();
			}
			if(controls.getKEY_RIGHT()){
				player.turnRight();
			}
			System.out.println(player);
			System.out.println(player.getPos());
			
			/*glClear(GL_COLOR_BUFFER_BIT); //Clears all former non-reinitialized color-bits
			player.draw(); //Works Note(40,40 replace with x and y)
			new Obs(new Point(40, 13, 0), 5, 10, 8, 0).draw();
			new Obs(new Point(75, 83, 0), 10, 6, 6, 0).draw();
			DebugInterface.Draw(levelTutorial);
			Display.update();
			Display.sync(60);*/
		}    
		//DebugInterface.Terminate();
	}


}
