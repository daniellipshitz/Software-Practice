package project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class GameTest{

	//Game game = new Game("Morphy.pgn");

	@Test
	public void creationTest() throws IOException{
		//assertThrows(IOException.class,()->{
		//	Game game = new Game("a");
		//});

		Game game = new Game("Morphy.pgn");
	}
}