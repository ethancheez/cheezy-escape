package stem.characters;

public abstract class Character {
	
	private String name;
	protected String specialMove;
	private int defense, health, speed;
	
	public Character(String name, int defense, int health, int speed) {
		this.name = name;
		this.defense = defense;
		this.health = health;
		this.speed = speed;
		specialMove = "";
	}
	
	public String getMove() {
		return specialMove;
	}
	

}
