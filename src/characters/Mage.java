package stem.characters;

public class Mage extends Character{
	
	public Mage(String name) {
		super(name.toUpperCase(), 80, 90, 80);
		super.specialMove = "teleport";
	}
	
	public String getMove() {
		return specialMove;
	}

}
