package stem.characters;

public class Ninja extends Character{
	
	public Ninja(String name) {
		super(name.toUpperCase(), 50, 70, 100);
		super.specialMove = "invisible";
	}
	
	public String getMove() {
		return specialMove;
	}

}
