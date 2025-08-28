import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Player> registeredPlayers;

    public Game() {
        this.registeredPlayers = new ArrayList<>();
    }

    public void register(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        // Проверяем, не зарегистрирован ли уже игрок с таким id
        for (Player p : registeredPlayers) {
            if (p.getId() == player.getId()) {
                return; // игрок уже зарегистрирован, ничего не делаем
            }
        }

        registeredPlayers.add(player);
    }

    public int round(String playerName1, String playerName2) {
        Player player1 = findPlayerByName(playerName1);
        Player player2 = findPlayerByName(playerName2);

        if (player1 == null) {
            throw new NotRegisteredException("Player " + playerName1 + " is not registered");
        }

        if (player2 == null) {
            throw new NotRegisteredException("Player " + playerName2 + " is not registered");
        }

        if (player1.getStrength() > player2.getStrength()) {
            return 1; // победа первого игрока
        } else if (player1.getStrength() < player2.getStrength()) {
            return 2; // победа второго игрока
        } else {
            return 0; // ничья
        }
    }

    public List<Player> getRegisteredPlayers() {
        return new ArrayList<>(registeredPlayers); // возвращаем копию для защиты от изменений
    }

    private Player findPlayerByName(String name) {
        for (Player player : registeredPlayers) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }
}