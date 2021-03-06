package edu.wsu.vancouver.ssdd.menu;

import java.util.ArrayList;
import java.util.Iterator;

import jig.Entity;
import jig.ResourceManager;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A Simple Game of Bounce.
 * 
 * The game has three states: StartUp, Playing, and GameOver, the game
 * progresses through these states based on the user's input and the events that
 * occur. Each state is modestly different in terms of what is displayed and
 * what input is accepted.
 * 
 * In the playing state, our game displays a moving rectangular "ball" that
 * bounces off the sides of the game container. The ball can be controlled by
 * input from the user.
 * 
 * When the ball bounces, it appears broken for a short time afterwards and an
 * explosion animation is played at the impact site to add a bit of eye-candy
 * additionally, we play a short explosion sound effect when the game is
 * actively being played.
 * 
 * Our game also tracks the number of bounces and syncs the game update loop
 * with the monitor's refresh rate.
 * 
 * Graphics resources courtesy of qubodup:
 * http://opengameart.org/content/bomb-explosion-animation
 * 
 * Sound resources courtesy of DJ Chronos:
 * http://www.freesound.org/people/DJ%20Chronos/sounds/123236/
 * 
 * 
 * @author wallaces
 * 
 */

import edu.wsu.vancouver.ssdd.menu.Main;

public class MainMenu extends BasicGameState{

	private final int ScreenWidth;
	private final int ScreenHeight;
	private int getID_state;
	int posX;
	int posY;

	/**
	 * Create the BounceGame frame, saving the width and height for later use.
	 * 
	 * @param title
	 *            the window's title
	 * @param width
	 *            the window's width
	 * @param height
	 *            the window's height
	 */
	public MainMenu(int state, int ScreenWidth, int ScreenHeight){
		this.ScreenHeight = ScreenHeight;
		this.ScreenWidth = ScreenWidth;

		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
		getID_state = state;
		

	}
	
	/**
	 * Render the game state.
	 */
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		ResourceManager.loadImage("images/menuStart.png");
		ResourceManager.loadImage("images/menuLevel.png");
		ResourceManager.loadImage("images/menuHelp.png");
		ResourceManager.loadImage("images/menuCredit.png");
		ResourceManager.loadImage("images/menuGoBack.png");
		
		g.drawImage(ResourceManager.getImage("images/menuStart.png"), 	ScreenWidth/2 - 115, 230);		
//		g.drawImage(ResourceManager.getImage("images/menuLevel.png"), 	ScreenWidth/2 - 115, 230 + 60);		
		g.drawImage(ResourceManager.getImage("images/menuHelp.png"), 	ScreenWidth/2 - 115, 230 + 60);		
		g.drawImage(ResourceManager.getImage("images/menuCredit.png"), 	ScreenWidth/2 - 115, 230 + 2*60);

	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		// TODO Auto-generated method stub	
	}
	
	/**
	 * Update the game state based on user input and events that transpire in
	 * this frame.
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		
		posX = Mouse.getX();
		posY = ScreenHeight - Mouse.getY();
		
		if ( (posX > 285 && posX < 515) && (posY > 230 && posY < 280) ){ // Menu start
			if (Mouse.isButtonDown(0))
				sbg.enterState(Main.stateID.MENU_START.ordinal() );
		}

		if ( (posX > 285 && posX < 515) && (posY > 290 && posY < 340) ){ // menuLevel
			if (Mouse.isButtonDown(0))
				//sbg.enterState(Main.stateID.MENU_LEVEL.ordinal() );
				sbg.enterState(Main.stateID.MENU_HELP.ordinal() );
		}

		if ( (posX > 285 && posX < 515) && (posY > 350 &&  posY < 400) ){ // menuHelp
			if (Mouse.isButtonDown(0))
				//sbg.enterState(Main.stateID.MENU_HELP.ordinal() );
				sbg.enterState(Main.stateID.MENU_CREDIT.ordinal() );
		}
/*
		if ( (posX > 285 && posX < 515) && (posY > 410 && posY < 460) ){ // menuCredit
			if (Mouse.isButtonDown(0)){
				sbg.enterState(Main.stateID.MENU_CREDIT.ordinal() );
			}
		}
	*/	
		
	}
	
   public int getID(){
	      return getID_state;
	   }
}