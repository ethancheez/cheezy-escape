package stem.graphics;

import java.awt.image.BufferedImage;

import stem.graphics.Assets;
import stem.graphics.SpriteSheet;

public class Assets {
	
	private static final int width = 32, height = 32;
	
	public static BufferedImage player, dirt, grass, stone, tree, ninjaplayer, warriorplayer, mageplayer, door, brick;
	public static BufferedImage ball, button, gameLogo;
	public static BufferedImage spike, spikevertical, hotdog, burger, arrowright, arrowleft, arrowup, arrowdown;
	
	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("sheet.png"));
		
		tree = sheet.crop(0, 0, width, 64);
		dirt = sheet.crop(32, 0, width, height);
		grass = sheet.crop(64, 0, width, height);
		brick = sheet.crop(96, 0, width, height);
		stone = sheet.crop(0, 64, width, height);
		player = sheet.crop(128, 0, width, height);
		ninjaplayer = sheet.crop(0, 224, width, height);
		warriorplayer = sheet.crop(32, 224, width, height);
		mageplayer = sheet.crop(64, 224, width, height);
		door = sheet.crop(192, 192, 64, 64);
		
		
		SpriteSheet sheet2 = new SpriteSheet(ImageLoader.loadImage("sheet2.png"));
		ball = sheet2.crop(64, 0, 64, 64);
		button = sheet2.crop(128, 0, 64, 16);
		gameLogo = sheet2.crop(0, 96, 224, 128);
		
		
		SpriteSheet sheet3 = new SpriteSheet(ImageLoader.loadImage("sheet3.png"));
		spike = sheet3.crop(0, 0, 256, 20);
		spikevertical = sheet3.crop(0, 32, 17, 224);
		hotdog = sheet3.crop(32, 32, width, 96);
		burger = sheet3.crop(96, 32, 64, 64);
		arrowright = sheet3.crop(32, 160, 128, height);
		arrowleft = sheet3.crop(32, 192, 128, height);
		arrowup = sheet3.crop(224, 32, width, 128);
		arrowdown = sheet3.crop(192,  32, width, 128);
	}

}