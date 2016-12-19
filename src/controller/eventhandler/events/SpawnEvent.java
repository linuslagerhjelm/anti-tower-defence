package controller.eventhandler.events;

/**
 * Author: Linus Lagerhjelm
 * File: SpawnEvent
 * Created: 2016-12-07
 * Description: This event represents that a new troupe should be spawned.
 * The event contains information of what type of troupe to spawn
 */
public class SpawnEvent implements LevelEvent {
    private String type;

    public SpawnEvent() {}

    public SpawnEvent(String type) {
        this.type = type;
    }

    public String getTroupeType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
