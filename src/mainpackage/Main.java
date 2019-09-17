package mainpackage;

import obstacles.Cross;
import obstacles.Wall;
import participants.Cat;
import participants.Dog;
import participants.Human;
import union.Team;

public class Main {
    public static void main(String[] args) {
        Competitor[] competitors = {new Human("Боб"), new Cat("Барсик"), new Dog("Бобик")};
        Obstacle[] course = {new Cross(80), new Wall(2), new Wall(1), new Cross(120)};


        Team t1 = new Team(course, competitors);

    }
}

////если целочисленный литерал (012) начинается с 0, тогда java примет его восьмеричную нотацию: (0 + 8 + 2 = 10). 10х20=200;