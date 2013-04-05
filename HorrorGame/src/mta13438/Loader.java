package mta13438;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;



public class Loader {

	private static Texture texturePlay;
	private static Texture texturePlayCheck;
	private static Texture textureExit;
	private static Texture textureExitCheck;
	private static Texture textureHelp;
	private static Texture textureHelpCheck;

	private static int menuNumber;
	private static Obs waterPit, wall, trap;
	static Level tutorialLevel = new Level(new ArrayList<Room>(), 0, 0, 0);
	private static boolean showMainMenu;
	private static boolean showHelpMenu;
	private static Controls controls = new Controls();
	private static Player player = new Player(new Point(15,310,10),1,0,10);

	public void start() {
		menuNumber = 0;
		showMainMenu = true;
		showHelpMenu = false;
		DebugInterface.Initialize(800, 600); // Width and Length of display
		initGL(800,600);
		init();
		while (true) {
			glClear(GL_COLOR_BUFFER_BIT);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

			render();

			Display.update();
			Display.sync(60);

			if (Display.isCloseRequested()) {
				DebugInterface.Terminate();
				System.exit(0);
			}
		}
	}

	public static void loadTutorialLevel() {
		tutorialLevel.addRoomList(new Room(new Point(10, 300, 0), 10, 20, 20, new Point(10,305,0), new Point(19, 315, 0), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(new Point(20, 290, 0), 60, 50, 30, new Point(21,315,0), new Point(79, 295, 0), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(new Point(80, 290, 0), 50, 10, 20, new Point(81,295,0), new Point(129, 295, 0), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(new Point(130, 250, 0), 150, 90, 60, new Point(131,295,0), new Point(279, 315, 0), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(new Point(280, 260, 0), 110, 110, 40, new Point(281,315,0), new Point(389, 315, 0), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(new Point(390, 310, 0), 40, 10, 20, new Point(391,315,0), new Point(425, 311, 0), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(new Point(420, 260, 0), 10, 50, 20, new Point(425,309,0), new Point(425, 261, 0), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(new Point(390, 190, 0), 70, 70, 40, new Point(425,259,0), new Point(425, 191, 0), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(new Point(420, 170, 0), 10, 20, 20, new Point(425,189,0), new Point(425, 171, 0), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(new Point(400, 90, 0), 50, 80, 40, new Point(425,169,0), new Point(425, 91, 0), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(new Point(410, 70, 0), 30, 20, 40, new Point(425,89,0), new Point(425, 70, 0), MATERIALS.ROCK));
		waterPit = new Obs(new Point(160, 270, 0), 20, 50, 0, 0);
		wall = new Obs(new Point(330, 280, 0), 10, 70, 0, 0);
		trap = new Obs(new Point(410, 210, 0), 30, 30, 0, 0);
	}

	private void initGL(int width, int height) {

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// enable alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glViewport(0, 0, width, height);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void init() {
		try {
			// load texture from PNG file
			texturePlay = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("assets/images/menu/Play.png"));
			texturePlayCheck = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("assets/images/menu/PlayCheck.png"));
			textureHelp = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("assets/images/menu/Help.png"));
			textureHelpCheck = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("assets/images/menu/HelpCheck.png"));
			textureExit = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("assets/images/menu/Exit.png"));
			textureExitCheck = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("assets/images/menu/ExitCheck.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void input() {

		if(showHelpMenu || showMainMenu){
			while (Keyboard.next()) { // looping through the different controls
				if (Keyboard.getEventKeyState()) { // nested if statements checking
					// to see if buttons are pressed
					if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
						if (menuNumber != 0 && showMainMenu == true) {
							menuNumber -= 1;
						} 
						if (menuNumber < 0 | menuNumber > 2 && showMainMenu == true){
							menuNumber = 0;
						}
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
						if (menuNumber != 2  && showMainMenu == true) {
							menuNumber += 1;
						}
						if (menuNumber < 0 | menuNumber > 2 && showMainMenu == true){
							menuNumber = 0;
						}
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
						if(showHelpMenu == true){
							showHelpMenu = false;
							showMainMenu = true;
						}
						if (menuNumber == 0 ) {
							showHelpMenu = false;
							showMainMenu = false;
							Display.destroy();
							DebugInterface.Initialize(800, 600); // Width and Length of display
							DebugInterface.InitOpenGL(500,500); // Width and Length inside the display (Scaling of perspective here)
							loadTutorialLevel();
						}
						if (menuNumber == 1) {
							showMainMenu = false;
							showHelpMenu = true;
							menuNumber = -1;
						}
						if (menuNumber == 2) {
							System.exit(0);
						}
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
						System.exit(0);
					}
				}
			}
		}else{
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
		}
		
		
		
	}

	public static void render() {
		if (showMainMenu == true) {
			input();
			if (menuNumber != 0) {
				texturePlay.bind();
			}
			if (menuNumber == 0) {
				texturePlayCheck.bind();
			}
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(10, 50);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(10 + texturePlay.getTextureWidth(), 50);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(10 + texturePlay.getTextureWidth(),
					50 + texturePlay.getTextureHeight());
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(10, 50 + texturePlay.getTextureHeight());
			GL11.glEnd();
			if (menuNumber != 1) {
				textureHelp.bind();
			}
			if (menuNumber == 1) {
				textureHelpCheck.bind();
			}
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(10, 100);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(10 + texturePlay.getTextureWidth(), 100);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(10 + texturePlay.getTextureWidth(),
					100 + texturePlay.getTextureHeight());
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(10, 100 + texturePlay.getTextureHeight());
			GL11.glEnd();
			if (menuNumber != 2) {
				textureExit.bind(); 
			}
			if (menuNumber == 2) {
				textureExitCheck.bind();
			}
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(10, 150);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(10 + texturePlay.getTextureWidth(), 150);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(10 + texturePlay.getTextureWidth(),
					150 + texturePlay.getTextureHeight());
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(10, 150 + texturePlay.getTextureHeight());
			GL11.glEnd();
		}
		if (showHelpMenu == true && showMainMenu == false) {
			input();
			textureExitCheck.bind();

			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(10, 150);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(10 + texturePlay.getTextureWidth(), 150);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(10 + texturePlay.getTextureWidth(),
					150 + texturePlay.getTextureHeight());
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(10, 150 + texturePlay.getTextureHeight());
			GL11.glEnd();
		}
		if(showHelpMenu == false && showMainMenu == false){
			input();
			DebugInterface.Draw(tutorialLevel);
			waterPit.draw();
			wall.draw();
			trap.draw();
			player.draw();
		}
	}

	public static void main(String[] argv) {
	}
}