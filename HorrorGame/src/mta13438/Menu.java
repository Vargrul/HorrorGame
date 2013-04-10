package mta13438;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.io.IOException;

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

	public Menu() {
		menuNumber = 0;
		showMainMenu = true;
		showHelpMenu = false;
		initGL(800, 600);
		init();

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
		if (menuNumber != 0) {
			menuNumber--;
		}
	}

	public static void down() {
		if (menuNumber != 2) {
			menuNumber++;
		}
	}

	public static void play() {
		showMainMenu = false;
		showHelpMenu = false;
		Loader.playTutorialLevel();
	}

	public static void help() {
		showMainMenu = false;
		showHelpMenu = true;
	}

	public static void quit() {
		if (showMainMenu == true) {
			terminate();
		} else if (showHelpMenu == true) {
			showHelpMenu = false;
			showMainMenu = true;
		}
	}
	
	public static void terminate(){
		System.exit(0);
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
		}
		if (controls.getKEY_DOWN()) {
			down();
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
				quit();
				break;
			default:
				break;
			}
		}
		if (controls.getKEY_ESCAPE()) {
			terminate();
		}
	}
}
