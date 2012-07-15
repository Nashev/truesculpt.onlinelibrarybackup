package main;

import java.io.IOException;
import java.net.MalformedURLException;

import backup.Backup;


public class Main {


	public static void main(String[] args) {
		
		Backup action = new Backup();
		try {
			action.Work();
		} catch (MalformedURLException e) {		
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}

	}

}
