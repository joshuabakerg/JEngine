package Script;

import graphics.Renderer;

public interface Script {

	public void start();
	public void update();
	public void render(Renderer renderer);
	public void onClose();
}

