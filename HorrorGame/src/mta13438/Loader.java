package mta13438;

import java.io.IOException;
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

	private Texture texturePlay, texturePlayCheck, textureExit,
			textureExitCheck, textureHelp, textureHelpCheck;

	private int menuNumber;

	private boolean showMainMenu, showHelpMenu;

	public void start() {
		menuNumber = 0;
		showMainMenu = true;
		showHelpMenu = false;
		initGL(800, 600);
		init();

		while (true) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			render();

			Display.update();
			Display.sync(100);

			if (Display.isCloseRequested()) {
				Display.destroy();
				System.exit(0);
			}
		}
	}

	public void loadLevel() {
		Level currentLevel = new Level();
		System.out.println("Level is loaded!");
	}

	private void initGL(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

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
					ResourceLoader.getResourceAsStream("res/Play.png"));
			texturePlayCheck = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("res/PlayCheck.png"));
			textureHelp = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("res/Help.png"));
			textureHelpCheck = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("res/HelpCheck.png"));
			textureExit = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("res/Exit.png"));
			textureExitCheck = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("res/ExitCheck.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void input() {
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
						loadLevel();
						showMainMenu = false;
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
			}
		}
	}

	public void render() {
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
				textureExit.bind(); // or GL11.glBind(texture.getTextureID());
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
	}

	public static void main(String[] argv) {
	}
}