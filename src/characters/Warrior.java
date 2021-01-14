package stem.characters;

public class Warrior extends Character{
	
	public Warrior(String name) {
		super(name.toUpperCase(), 100, 120, 20);
		super.specialMove = "wall";
	}
	
	public String getMove() {
		return specialMove;
	}

}
