package Component;

import graphics.Renderer;

/**
 * Created by Joshua on 2016/05/27.
 */
public interface ComponentInterface{

    void Awake();
    void Start();
    void update();
    void render(Renderer renderer);



}
