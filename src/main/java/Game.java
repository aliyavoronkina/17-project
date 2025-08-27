import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        boolean alreadyRegistered = registeredPlayers.stream()
                .anyMatch(p -> p.getId() == player.getId());

        if (!alreadyRegistered) {
            registeredPlayers.add(player);
        }
    }

    public int round(String playerName1, String playerName2) {
        Player player1 = findPlayerByName(playerName1)
                .orElseThrow(() -> new NotRegisteredException("Player " + playerName1 + " is not registered"));

        Player player2 = findPlayerByName(playerName2)
                .orElseThrow(() -> new NotRegisteredException("Player " + playerName2 + " is not registered"));

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

    private Optional<Player> findPlayerByName(String name) {
        return registeredPlayers.stream()
                .filter(player -> player.getName().equals(name))
                .findFirst();
    }
}