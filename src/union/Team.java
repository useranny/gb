package union;

import mainpackage.Competitor;
import mainpackage.Obstacle;
import obstacles.Cross;
import participants.Cat;
import participants.Dog;
import participants.Human;

public class Team {
    public Team(Obstacle[] course, Competitor[] competitors) {
        for (Competitor c : competitors) {
            for (Obstacle o : course) {
                o.doIt(c);
                if (!c.isOnDistance()) break;
            }
            c.info();

        }
    }

}