import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        game = new Game();
        player1 = new Player(1, "Alice", 100);
        player2 = new Player(2, "Bob", 80);
    }

    // Тесты для класса Player
    @Test
    void testHashCode() {
        Player player1 = new Player(1, "Alice", 100);
        Player player2 = new Player(1, "Alice", 100);

        assertEquals(player1.hashCode(), player2.hashCode());
        int firstHash = player1.hashCode();
        assertEquals(firstHash, player1.hashCode());
    }

    @Test
    void testEqualsCompleteCoverage() {
        Player player1 = new Player(1, "Alice", 100);
        Player player2 = new Player(1, "Alice", 100);
        Player player3 = new Player(2, "Bob", 80);

        assertTrue(player1.equals(player1));
        assertTrue(player1.equals(player2));
        assertTrue(player2.equals(player1));
        assertFalse(player1.equals(player3));
        assertFalse(player1.equals(null));
        assertFalse(player1.equals("Some String"));
    }

    @Test
    void testEqualsWithSameObject() {
        Player player = new Player(1, "Alice", 100);
        assertTrue(player.equals(player));
    }

    @Test
    void testEqualsWithNull() {
        Player player = new Player(1, "Alice", 100);
        assertFalse(player.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        Player player = new Player(1, "Alice", 100);
        assertFalse(player.equals("Not a Player"));
    }

    // Тесты для класса Game
    @Test
    void testGameConstructor() {
        Game newGame = new Game();
        assertNotNull(newGame);
        assertEquals(0, newGame.getRegisteredPlayers().size());
    }

    @Test
    void testRegisterNullPlayer() {
        assertThrows(IllegalArgumentException.class, () -> game.register(null));
    }

    @Test
    void testRegisterPlayerWithSameId() {
        game.register(player1);
        Player sameIdPlayer = new Player(1, "DifferentName", 200);
        game.register(sameIdPlayer);

        // Должен остаться только первый игрок
        assertEquals(1, game.getRegisteredPlayers().size());
        assertEquals("Alice", game.getRegisteredPlayers().get(0).getName());
    }

    @Test
    void testGetRegisteredPlayersReturnsCopy() {
        game.register(player1);
        List<Player> players = game.getRegisteredPlayers();
        assertNotNull(players);
        assertEquals(1, players.size());
        assertEquals("Alice", players.get(0).getName());
    }

    @Test
    void testRoundFirstPlayerWins() {
        game.register(player1);
        game.register(player2);
        assertEquals(1, game.round("Alice", "Bob"));
    }

    @Test
    void testRoundSecondPlayerWins() {
        game.register(player1);
        game.register(player2);
        assertEquals(2, game.round("Bob", "Alice"));
    }

    @Test
    void testRoundDraw() {
        Player player3 = new Player(3, "Charlie", 100);
        game.register(player1);
        game.register(player3);
        assertEquals(0, game.round("Alice", "Charlie"));
    }

    @Test
    void testRoundFirstPlayerNotRegistered() {
        game.register(player2);
        Exception exception = assertThrows(NotRegisteredException.class, () -> game.round("Alice", "Bob"));
        assertTrue(exception.getMessage().contains("Alice"));
    }

    @Test
    void testRoundSecondPlayerNotRegistered() {
        game.register(player1);
        Exception exception = assertThrows(NotRegisteredException.class, () -> game.round("Alice", "Bob"));
        assertTrue(exception.getMessage().contains("Bob"));
    }

    @Test
    void testRoundBothPlayersNotRegistered() {
        Exception exception = assertThrows(NotRegisteredException.class, () -> game.round("Alice", "Bob"));
        assertTrue(exception.getMessage().contains("Alice"));
    }

    // Тесты для NotRegisteredException
    @Test
    void testNotRegisteredExceptionConstructor() {
        String message = "Test message";
        NotRegisteredException exception = new NotRegisteredException(message);

        assertEquals(message, exception.getMessage());
        assertNotNull(exception);
    }

    @Test
    void testNotRegisteredExceptionInheritance() {
        NotRegisteredException exception = new NotRegisteredException("test");
        assertTrue(exception instanceof RuntimeException);
    }

    // Тесты для методов Player
    @Test
    void testPlayerGetters() {
        Player player = new Player(123, "TestPlayer", 150);

        assertEquals(123, player.getId());
        assertEquals("TestPlayer", player.getName());
        assertEquals(150, player.getStrength());
    }

    @Test
    void testPlayerToString() {
        Player player = new Player(1, "Test", 100);
        String toStringResult = player.toString();

        assertTrue(toStringResult.contains("Test"));
        assertTrue(toStringResult.contains("1"));
        assertTrue(toStringResult.contains("100"));
    }

    @Test
    void testPlayerConstructor() {
        Player player = new Player(1, "Test", 100);
        assertNotNull(player);
        assertEquals(1, player.getId());
        assertEquals("Test", player.getName());
        assertEquals(100, player.getStrength());
    }

    // Дополнительные тесты
    @Test
    void testRegisterMultiplePlayers() {
        game.register(player1);
        game.register(player2);
        assertEquals(2, game.getRegisteredPlayers().size());
    }

    @Test
    void testEmptyGame() {
        Game emptyGame = new Game();
        assertEquals(0, emptyGame.getRegisteredPlayers().size());
    }

    @Test
    void testRoundWithSamePlayer() {
        game.register(player1);
        assertEquals(0, game.round("Alice", "Alice"));
    }

    @Test
    void testFindPlayerByNameIntegration() {
        game.register(player1);
        game.register(player2);

        // Косвенно тестируем findPlayerByName через round
        assertDoesNotThrow(() -> game.round("Alice", "Bob"));
    }

    @Test
    void testEqualsWithDifferentAttributes() {
        Player player1 = new Player(1, "Alice", 100);
        Player player2 = new Player(2, "Alice", 100); // другой id
        Player player3 = new Player(1, "Bob", 100);   // другое имя
        Player player4 = new Player(1, "Alice", 200); // другая сила

        assertFalse(player1.equals(player2));
        assertFalse(player1.equals(player3));
        assertFalse(player1.equals(player4));
    }

    @Test
    void testEqualsWithNullName() {
        Player player1 = new Player(1, null, 100);
        Player player2 = new Player(1, null, 100);
        Player player3 = new Player(1, "Alice", 100);

        assertTrue(player1.equals(player2));
        assertFalse(player1.equals(player3));
        assertFalse(player3.equals(player1));
    }

    @Test
    void testRegisterDuplicateNameDifferentId() {
        Player player1 = new Player(1, "SameName", 100);
        Player player2 = new Player(2, "SameName", 80);

        game.register(player1);
        game.register(player2);

        assertEquals(2, game.getRegisteredPlayers().size()); // Оба должны быть добавлены
    }
}