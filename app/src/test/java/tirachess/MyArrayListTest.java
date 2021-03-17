package tirachess;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tirachess.datastructures.MyArrayList;

public class MyArrayListTest {

    MyArrayList<Integer> list;
    
    @Before
    public void setUp() throws Exception {
        list = new MyArrayList<>();
    }

    @Test
    public void testAdd1() {
        list.add(1);
        assertEquals(1, list.size());
    }

    @Test
    public void testAdd2() {
        list.add(2);
        assertTrue(2 == list.get(0));
    }

    @Test
    public void testGet1() {
        list.add(10);
        assertTrue(10 == list.get(0));
    }

    @Test
    public void testGet2() {
        list.add(10);
        list.add(100);
        assertTrue(100 == list.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet3() {
        list.get(10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet4() {
        list.get(-1);
    }

    @Test
    public void testIsEmpty1() {
        assertTrue(list.isEmpty());
    }

    @Test
    public void testIsEmpty2() {
        list.add(1);
        assertFalse(list.isEmpty());
    }

    @Test
    public void testClear1() {
        list.add(1);
        list.add(2); 
        list.clear();
        assertTrue(list.isEmpty());
    }


    @Test
    public void testClear2() {
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        list.clear();
        assertEquals(0, list.size());
    }


    @Test
    public void testSize1() {
        assertEquals(0, list.size());
    }

    @Test
    public void testSize2() {
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        assertEquals(100, list.size());
    }

    @Test
    public void testContains1() {
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        assertTrue(list.contains(5));

    }

    @Test
    public void testContains2() {
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        assertFalse(list.contains(100));

    }
}
