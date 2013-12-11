package edu.wsu.vancouver.ssdd.menu;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Main extends StateBasedGame{
	public enum screenState{
		MAIN_MENU, MENU_START, MENU_HELP, MENU_CREDIT, ;
	}
	
	public static screenState stateID;
	public static final String gamename = "Break Out";


   

   
   public Main(String gamename){
      super(gamename);
      this.addState(new MainMenu( stateID.MAIN_MENU.ordinal() ));
      this.addState(new MenuStart( stateID.MENU_START.ordinal() ));
      this.addState(new MenuCredit( stateID.MENU_CREDIT.ordinal() ));
      this.addState(new MenuHelp( stateID.MENU_HELP.ordinal() ));

   }
   
   public void initStatesList(GameContainer gc) throws SlickException{
      this.getState( stateID.MAIN_MENU.ordinal() ).init(gc, this);
      this.getState(stateID.MENU_START.ordinal()).init(gc, this);
	  this.getState(stateID.MENU_CREDIT.ordinal()).init(gc, this);
	  this.getState(stateID.MENU_HELP.ordinal()).init(gc, this);
	  
      this.enterState(stateID.MAIN_MENU.ordinal());
   }
   
   public static void main(String[] args) {
      AppGameContainer app;
      try{
         app = new AppGameContainer(new Main(gamename));
         app.setDisplayMode(800, 600, false);
		 app.setVSync(true);
         app.start();
      }catch(SlickException e){
         e.printStackTrace();
      }
   }
}