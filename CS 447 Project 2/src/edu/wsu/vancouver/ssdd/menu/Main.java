package edu.wsu.vancouver.ssdd.menu;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import edu.wsu.vancouver.ssdd.levelOne;

public class Main extends StateBasedGame{
	
	private final static int ScreenHeight = 600;
	private final static int ScreenWidth = 800;
	
	public enum screenState{
		MAIN_MENU, MENU_START, MENU_LEVEL,MENU_HELP, MENU_CREDIT;
	}
	
	public static screenState stateID;
	public static final String gamename = "CS447 Project 2";
	
   public Main(String gamename){
      super(gamename);
      this.addState(new MainMenu( stateID.MAIN_MENU.ordinal(), ScreenWidth, ScreenHeight ));
      this.addState(new levelOne( stateID.MENU_START.ordinal(), ScreenWidth, ScreenHeight ));
      this.addState(new MenuLevel( stateID.MENU_LEVEL.ordinal(), ScreenWidth, ScreenHeight ));
      this.addState(new MenuCredit( stateID.MENU_CREDIT.ordinal(), ScreenWidth, ScreenHeight ));
      this.addState(new MenuHelp( stateID.MENU_HELP.ordinal(), ScreenWidth, ScreenHeight ));

   }
   
   public void initStatesList(GameContainer gc) throws SlickException{
      this.getState( stateID.MAIN_MENU.ordinal() ).init(gc, this);
      this.getState(stateID.MENU_START.ordinal()).init(gc, this);
      this.getState(stateID.MENU_LEVEL.ordinal()).init(gc, this);
	  this.getState(stateID.MENU_CREDIT.ordinal()).init(gc, this);
	  this.getState(stateID.MENU_HELP.ordinal()).init(gc, this);
	  
      this.enterState(stateID.MAIN_MENU.ordinal());
   }
   
   public static void main(String[] args) {
      AppGameContainer app;
      try{
         app = new AppGameContainer(new Main(gamename));
         app.setDisplayMode(ScreenWidth, ScreenHeight, false);
		 app.setVSync(true);
         app.start();
      }catch(SlickException e){
         e.printStackTrace();
      }
   }
}