package stem.gui;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
   
// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class Sounds extends JFrame {
	private static final long serialVersionUID = 1L;

// Constructor
   public Sounds(String songname) {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //this.setTitle("Test Sound Clip");
      //this.setSize(300, 200);
      this.setVisible(false);
   
      
    	  try {
    		  // Open an audio input stream.
    		  URL url = this.getClass().getResource(songname);
    		  AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
    		  // Get a sound clip resource.
    		  Clip clip = AudioSystem.getClip();
    		  // Open audio clip and load samples from the audio input stream.
    		  clip.open(audioIn);
    		  clip.start();
    	  } catch (UnsupportedAudioFileException e) {
    		  e.printStackTrace();
    	  } catch (IOException e) {
    		  e.printStackTrace();
    	  } catch (LineUnavailableException e) {
    		  e.printStackTrace();
    	  }
   } 
   
   public static void stopSound(Sounds sound) {
	   sound.dispose();
	   sound.setEnabled(false);
   }
   				
   public static void main(String[] args) {
	   new Sounds("themesong");
	   new Sounds("applause");
   }
}
