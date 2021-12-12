import resources.Properties;

public class Main { 

	public static void main(String[] args) {
		Properties.load();
		Scheduler.start();
	}
}
