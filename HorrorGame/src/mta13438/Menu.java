package mta13438;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Menu {

	private static Texture texturePlay;
	private static Texture texturePlayCheck;
	private static Texture textureExit;
	private static Texture textureExitCheck;
	private static Texture textureHelp;
	private static Texture textureHelpCheck;

	private static int menuNumber;
	private static boolean showMainMenu;
	private static boolean showHelpMenu;
	private static Controls controls = new Controls();
	private static Sound menuMusic = new Sound(SOUNDS.MENU_MUSIC, new Point(0,0,0), true, false);

	public Menu() {
		menuNumber = 0;
		showMainMenu = true;
		showHelpMenu = false;
		initGL(800, 600);
		init();
		
		//The values in this float buffer is in relation to the AL_POSITION.
		FloatBuffer listenerOri = BufferUtils.createFloatBuffer(6).put(new float[] { 0, 0, 1, 0, 0, 1});
		listenerOri.flip();
		//Setting the alListener's position and orientation.
		AL10.alListener3f(AL10.AL_POSITION,   0, 0, 0);
		AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
		System.out.println(SOUNDS.MENU_MUSIC.getPath());
		menuMusic.play();
		

		while (true) {
			glClear(GL_COLOR_BUFFER_BIT); 
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			if(showMainMenu == true || showHelpMenu == true){
				input();
				render();
			} else {
				Loader.renderTutorialLevel();
			}

			Display.update();
			Display.sync(60);

			if (Display.isCloseRequested()) {
				System.exit(0);
			}
		}
	}

	public static void up() {
		if (menuNumber > 0 && menuNumber < 3) {
			menuNumber--;
		}
	}

	public static void down() {
		if (menuNumber < 2 && menuNumber > -1) {
			menuNumber++;
		}
	}
	
	public static void play() {
		showMainMenu = false;
		showHelpMenu = false;
		menuMusic.stop();
		Loader.playTutorialLevel();
	}

	public static void help() {
		menuNumber = 3;
		showMainMenu = false;
		showHelpMenu = true;
	}
	
	public static void back() {
		showMainMenu = true;
		showHelpMenu = false;
		menuNumber = 0;
	}
	
	public static void terminate(){
		System.exit(0);
	}
	// Initializes GL11 which makes it possible to use textures.
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
	// Initiates the different textures for the menu
	public void init() {
		try {
			// load texture from PNG file
			texturePlay = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("assets/images/menu/Buttons/Play/PlayStatic.png"));
			texturePlayCheck = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("assets/images/menu/Buttons/Play/PlayStaticFilled.png"));
			textureHelp = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("assets/images/menu/Buttons/Help/HelpStatic.png"));
			textureHelpCheck = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("assets/images/menu/Buttons/Help/HelpStaticFilled.png"));
			textureExit = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("assets/images/menu/Buttons/Quit/QuitStatic.png"));
			textureExitCheck = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("assets/images/menu/Buttons/Quit/QuitStaticFilled.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Renders the menu depending on the menu status.
	public static void render() {
		if (showMainMenu == true) {
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
			GL11.glVertex2f(10, 150);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(10 + texturePlay.getTextureWidth(), 150);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(10 + texturePlay.getTextureWidth(),
					150 + texturePlay.getTextureHeight());
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(10, 150 + texturePlay.getTextureHeight());
			GL11.glEnd();
			if (menuNumber != 2) {
				textureExit.bind();
			}
			if (menuNumber == 2) {
				textureExitCheck.bind();
			}
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(10, 250);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(10 + texturePlay.getTextureWidth(), 250);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(10 + texturePlay.getTextureWidth(),
					250 + texturePlay.getTextureHeight());
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(10, 250 + texturePlay.getTextureHeight());
			GL11.glEnd();
		}
		if (showHelpMenu == true && showMainMenu == false) {

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
		if (showHelpMenu == false && showMainMenu == false) {
			Loader.renderTutorialLevel();
		}
	}

	private static void input() {
		controls.takeInput();
		
		if (controls.getKEY_UP()) {
			up();
			controls.setKEY_UP(false);
		}
		if (controls.getKEY_DOWN()) {
			down();
			controls.setKEY_DOWN(false);
		}
		if (controls.getKEY_ENTER()) {
			switch (menuNumber) {
			case 0:
				play();
				break;
			case 1:
				help();
				break;
			case 2:
				terminate();
				break;
			case 3:
				back();
				break;
			default:
				break;
			}
			controls.setKEY_ENTER(false);
		}
		if (controls.getKEY_ESCAPE()) {
			terminate();
		}
	}
}
