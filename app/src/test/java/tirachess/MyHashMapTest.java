package tirachess;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tirachess.datastructures.MyHashMap;


public class MyHashMapTest {
    
    MyHashMap<Integer, String> map;

    @Before
    public void setUp() {
        this.map = new MyHashMap<>();
    }

    @Test
    public void testPut1() {
        map.put(1, "a");
        assertEquals(1, map.size());
    }

    @Test
    public void testPut2() {
        map.put(2, "b");
        assertEquals("b", map.get(2));
    }

    @Test
    public void testPut3() {
        map.put(1, "a");
        map.put(1, "b");
        assertEquals("b", map.get(1));
    }

    @Test
    public void testGet1() {
        map.put(100, "c");
        map.put(101, "d");
        assertEquals("d", map.get(101));
    }

    @Test
    public void testGet2() {
        map.put(5, "five");
        map.put(10, "ten");
        assertEquals("five", map.get(5));
    }

    @Test
    public void testGet3() {
        assertEquals(null, map.get(5));
    }

    @Test
    public void  testSize() {
        for (int i = 0; i < 100; i++) {
            map.put(i, Integer.toString(i));
        }
        assertEquals(100, map.size());
    }

    @Test
    public void testIsEmpty1() {
        assertTrue(map.isEmpty());
    }

    @Test
    public void testIsEmpty2() {
        map.put(1, "one");
        assertFalse(map.isEmpty());
    }

    @Test
    public void testCollisions() {
        map = new MyHashMap<>(32);
        map.put(1, "one");
        map.put(33, "thirty-three");
        map.put(65, "sixty-five");
        map.put(65, "sixty-five");
        assertEquals("one", map.get(1));
        assertEquals("thirty-three", map.get(33));
        assertEquals("sixty-five", map.get(65));
        assertEquals(3, map.size());
    }
}
