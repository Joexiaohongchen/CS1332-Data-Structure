import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ExtraTest {
    private ArrayDeque<Integer> array;
    private ArrayDeque<Integer> array2;
    private LinkedDeque<Integer> linked;
    private LinkedNode<Integer> lNode;

    public static final int TIMEOUT = 200;

    @Before
    public void setup() {
        array = new ArrayDeque<>();
        array2 = new ArrayDeque<>();
        linked = new LinkedDeque<>();
    }
    @Test(timeout = TIMEOUT)
    public void testArrayDequeNewBackingArray() {
        array.addLast(1);
        array.addLast(2);
        array.addLast(3);
        array.addLast(4);
        array.addLast(5);
        array.removeFirst();
        //array.addLast(6);
        array.addFirst(6);
        //array.addLast(7);
        Integer[] expected = new Integer[ArrayDeque.INITIAL_CAPACITY];

        expected[0] = 6;
        expected[1] = 2;
        expected[2] = 3;
        expected[3] = 4;
        expected[4] = 5;

        assertEquals(5, array.size());
        assertArrayEquals(expected, array.getBackingArray());

        array.removeFirst();
        array.removeFirst();
        array.removeLast();
        array.removeLast();

        expected[0] = null;
        expected[1] = null;
        expected[3] = null;
        expected[4] = null;

        assertEquals(1, array.size());
        assertArrayEquals(expected, array.getBackingArray());

    //new test
        array2.addLast(1);
        array2.addLast(2);
        array2.addLast(3);
        array2.addLast(4);
        array2.addLast(5);
        array2.removeFirst();
        array2.addLast(6);
        array2.addFirst(0);
        array2.addLast(7);
        array2.addLast(8);
        array2.addLast(9);
        array2.removeLast();
        array2.addLast(9);
        array2.addLast(10);
        array2.addLast(11);
        array2.addLast(12);
        array2.addLast(13);
        array2.addLast(14);
        array2.addLast(15);
        array2.removeFirst();
        array2.removeLast();
        array2.addFirst(0);

        Integer[] expected2 = new Integer[26];
        expected2[0] = 0;
        expected2[1] = 2;
        expected2[2] = 3;
        expected2[3] = 4;
        expected2[4] = 5;
        expected2[5] = 6;
        expected2[6] = 7;
        expected2[7] = 8;
        expected2[8] = 9;
        expected2[9] = 10;
        expected2[10] = 11;
        expected2[11] = 12;
        expected2[12] = 13;
        expected2[13] = 14;
        //expected2[14] = 15;

        assertEquals(14, array2.size());
        assertArrayEquals(expected2, array2.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void TestLinkedDeque () {
        linked.addLast(1);
        LinkedNode<Integer> current = linked.getHead();
        Integer data = current.getData();
        linked.removeLast();
//        linked.addLast(2);
//        linked.addLast(3);
//        linked.addLast(4);
//        linked.addLast(5);
        Integer[] expected = new Integer[1];
        expected[0] = new Integer(1);

        //assertEquals(null, linked.getHead().getPrevious());
        assertEquals(expected[0], current.getData());
    }
}
