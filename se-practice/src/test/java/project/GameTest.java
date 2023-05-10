package project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class GameTest{


	@Test
	public void creationTest() throws IOException{
		Game game = new Game();
		game.manageGraphics();
		//assertThrows(IOException.class,()->{
		//	Game game = new Game("a");
		//});

		//Game game = new Game("Morphy.pgn");
	}
}