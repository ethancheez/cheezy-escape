package stem.gui;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Stopwatch {
	
	private int sec = 0, min = 0;
	private Timer stopwatch;
	private TimerTask task;
	
	public Stopwatch() {
		stopwatch = new Timer();
		task = new TimerTask() {
			public void run() {
				sec++;
				if(sec/60 == 1) {
					min++;
					sec = 0;
				}
			}
		};
	}
	
	public void start() {
		stopwatch.scheduleAtFixedRate(task, 1000, 1000);
	}
	
	public void startmilli() {
		stopwatch.scheduleAtFixedRate(task, 9, 850);
	}
	
	public void startFASTER() {
		stopwatch.scheduleAtFixedRate(task, 0, 420);
	}
	
	
	public int secPassed() {
		return sec;
	}
	public int minPassed( ) {
		return min;
	}
	public void resetTimer() {
		min = 0;
		sec = 0;
	}
	public void setTimer(int time) {
		sec = time;
	}
}
