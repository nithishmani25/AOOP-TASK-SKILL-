import java.util.Scanner;

// Singleton: GameManager (unchanged)
class GameManager {
    private static GameManager instance;
    private Level currentLevel;
    private int score;

    private GameManager() {
        this.score = 0;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void startLevel(Level level) {
        this.currentLevel = level;
        System.out.println("Starting " + level.getName() + " with score: " + score);
    }
}

// Level Class (unchanged)
class Level {
    private String name;
    private int enemies;
    private int obstacles;

    public Level(String name, int enemies, int obstacles) {
        this.name = name;
        this.enemies = enemies;
        this.obstacles = obstacles;
    }

    public String getName() {
        return name;
    }
}

// Factory: LevelFactory (unchanged)
class LevelFactory {
    public static Level createLevel(String levelType, DifficultyFactory difficultyFactory) {
        switch (levelType) {
            case "Forest":
                return new Level("Forest Level", 
                    difficultyFactory.getSettings().getEnemies(), 
                    difficultyFactory.getSettings().getObstacles());
            case "Desert":
                return new Level("Desert Level", 
                    difficultyFactory.getSettings().getEnemies(), 
                    difficultyFactory.getSettings().getObstacles());
            default:
                throw new IllegalArgumentException("Unknown level type: " + levelType);
        }
    }
}

// Difficulty Settings Class (unchanged)
class DifficultySettings {
    private int enemies;
    private int obstacles;
    private int enemySpeed;

    public DifficultySettings(int enemies, int obstacles, int enemySpeed) {
        this.enemies = enemies;
        this.obstacles = obstacles;
        this.enemySpeed = enemySpeed;
    }

    public int getEnemies() { return enemies; }
    public int getObstacles() { return obstacles; }
}

// Abstract Factory: DifficultyFactory (unchanged)
interface DifficultyFactory {
    DifficultySettings getSettings();
}

class EasyDifficultyFactory implements DifficultyFactory {
    @Override
    public DifficultySettings getSettings() {
        return new DifficultySettings(5, 10, 1);
    }
}

class HardDifficultyFactory implements DifficultyFactory {
    @Override
    public DifficultySettings getSettings() {
        return new DifficultySettings(15, 25, 3);
    }
}

// Main Class with User Input
public class GameApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameManager game = GameManager.getInstance();

        System.out.println("Choose a level (Forest, Desert): ");
        String levelType = scanner.nextLine();

        System.out.println("Choose difficulty (Easy, Hard): ");
        String difficulty = scanner.nextLine();

        DifficultyFactory difficultyFactory;
        if (difficulty.equalsIgnoreCase("Easy")) {
            difficultyFactory = new EasyDifficultyFactory();
        } else if (difficulty.equalsIgnoreCase("Hard")) {
            difficultyFactory = new HardDifficultyFactory();
        } else {
            System.out.println("Invalid difficulty. Defaulting to Easy.");
            difficultyFactory = new EasyDifficultyFactory();
        }

        try {
            Level level = LevelFactory.createLevel(levelType, difficultyFactory);
            game.startLevel(level);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}