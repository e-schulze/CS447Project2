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

import edu.wsu.vancouver.ssdd.menu.Main;

public class MenuStart extends BasicGameState{
	


	private final int ScreenWidth;
	private final int ScreenHeight;
	private int getID_state;
	int posX;
	int posY;
	
	public MenuStart(int state, int ScreenWidth, int ScreenHeight){
		this.ScreenHeight = ScreenHeight;
		this.ScreenWidth = ScreenWidth;
		
		getID_state = state;
	}
	
	/**
	 * Render the game state.
	 */
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		ResourceManager.loadImage("images/menuGoBack.png");
		ResourceManager.loadImage("images/menuStart.png");
		
		g.drawImage(ResourceManager.getImage("images/menuGoBack.png"), 0, ScreenHeight - 50);
		g.drawImage(ResourceManager.getImage("images/menuStart.png"), 225, 230);
		
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
		
		if ( (posX > 0 && posX < 230) && (posY > ScreenHeight - 50 && posY < ScreenHeight ) ){ // Main Menu
			if (Mouse.isButtonDown(0))
				sbg.enterState(Main.stateID.MAIN_MENU.ordinal() );
		}
		
	}
	
   public int getID(){
	      return getID_state;
	   }
}