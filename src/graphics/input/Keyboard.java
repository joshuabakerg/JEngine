package graphics.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Keyboard implements KeyListener, Serializable {
	public List<KeyEvent> keyEvents = new ArrayList<KeyEvent>();
	
	public boolean isKeyPressed(int key){
		boolean result = false;
		for(int i = 0; i < keyEvents.size();i++){
			KeyEvent event = keyEvents.get(i);
			if(event == null) return false;
			if(event.getKeyCode() == key){
				result = true;
			}
		}
		return result;
	}
	
	public void keyPressed(KeyEvent e) {
		boolean exists = false;
		for(int i = 0;i < keyEvents.size();i++){
			if(keyEvents.get(i).getKeyCode() == e.getKeyCode())exists = true;
		}
		if(!exists)this.keyEvents.add(e);
	}

	public void keyReleased(KeyEvent e) {
		int id = -1;
		for(int i = 0; i < keyEvents.size();i++){
			if(keyEvents.get(i).getKeyCode() == e.getKeyCode()){
				keyEvents.remove(i);
			}
		}
	}

	public void keyTyped(KeyEvent e) {
	}

}
