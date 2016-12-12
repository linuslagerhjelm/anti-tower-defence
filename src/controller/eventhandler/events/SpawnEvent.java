package controller.eventhandler.events;

/**
 * Author: Linus Lagerhjelm
 * File: SpawnEvent
 * Created: 2016-12-07
 * Description:
 */
public class SpawnEvent extends LevelEvent {
    private String type;

    public SpawnEvent(String type) {
        this.type = type;
    }

    public String getTroupeType() {
        return type;
    }
}
