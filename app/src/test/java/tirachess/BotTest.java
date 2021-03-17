package tirachess;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tirachess.domain.Bot;
import tirachess.domain.Position;

public class BotTest {

    Bot bot;
    
    @Before
    public void setUp() throws Exception {
        bot = new Bot();
    }

    @Test
    public void testBotPlayMethod1() {
        Position p = new Position();
        String fen = "r2qkb1r/pp2nppp/3p4/2pNN1B1/2BnP3/3P4/PPP2PPP/R2bK2R w KQkq - 1 0";
        p.importFEN(fen);

        assertTrue(p.getMoves().contains(bot.play(p)));
        
    }

    @Test
    public void testBotPlayMethod2() {
        Position p = new Position();
        String fen = "6k1/pp4p1/2p5/2bp4/8/P5Pb/1P3rrP/2BRRN1K b - - 0 1";
        p.importFEN(fen);

        assertTrue(p.getMoves().contains(bot.play(p)));
    }
}
