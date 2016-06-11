package Component;

/**
 * Created by Joshua on 2016/05/30.
 */
public class LiveDurationComponent extends Component {

    private float duration;
    private double startTime = 0;

    public LiveDurationComponent(float duration){
        super("liveDuration");
        this.duration = duration;
    }

    public LiveDurationComponent(float duration, boolean startImmediately){
        super("liveDuration");
        this.duration = duration;
        if(startImmediately){
            start();
        }
    }

    public void start(){
        startTime = System.currentTimeMillis();
    }

    public void update(){
        if(startTime != 0){
            if(System.currentTimeMillis()-startTime>duration*1000){
                gameObject.delete();
            }
        }
    }

}
