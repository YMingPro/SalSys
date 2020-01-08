package systemFrame;

import java.awt.EventQueue;

public class Application {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Login_DB();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
