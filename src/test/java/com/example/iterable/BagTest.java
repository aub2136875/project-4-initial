package com.example.iterable;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bag<E> Unit Tests")
class BagTest {

    private Bag<String>  stringBag;
    private Bag<Integer> intBag;

    // ─────────────────────────────────────────────
    //  Setup
    // ─────────────────────────────────────────────

    @BeforeEach
    void setUp() {
        stringBag = new Bag<>();
        intBag    = new Bag<>();
    }

    // ─────────────────────────────────────────────
    //  1. Empty Bag
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("1. Empty Bag")
    class EmptyBagTests {

        @Test
        @DisplayName("New bag has size 0")
        void newBagHasSizeZero() {
            assertEquals(0, stringBag.size());
        }

        @Test
        @DisplayName("New bag isEmpty() returns true")
        void newBagIsEmpty() {
            assertTrue(stringBag.isEmpty());
        }

        @Test
        @DisplayName("contains() returns false on empty bag")
        void containsReturnsFalseOnEmptyBag() {
            assertFalse(stringBag.contains("ghost"));
        }

        @Test
        @DisplayName("remove() returns false on empty bag")
        void removeReturnsFalseOnEmptyBag() {
            assertFalse(stringBag.remove("ghost"));
        }

        @Test
        @DisplayName("Iterator on empty bag has no elements")
        void iteratorOnEmptyBagHasNoElements() {
            assertFalse(stringBag.iterator().hasNext());
        }
    }

    // ─────────────────────────────────────────────
    //  2. Single-Item Operations
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("2. Single-Item Operations")
    class SingleItemTests {

        @Test
        @DisplayName("Adding one item increases size to 1")
        void addOneItemSizeIsOne() {
            stringBag.add("apple");
            assertEquals(1, stringBag.size());
        }

        @Test
        @DisplayName("Bag is not empty after one add")
        void bagIsNotEmptyAfterOneAdd() {
            stringBag.add("apple");
            assertFalse(stringBag.isEmpty());
        }

        @Test
        @DisplayName("contains() finds the added item")
        void containsFindsAddedItem() {
            stringBag.add("apple");
            assertTrue(stringBag.contains("apple"));
        }

        @Test
        @DisplayName("contains() returns false for item not added")
        void containsReturnsFalseForAbsentItem() {
            stringBag.add("apple");
            assertFalse(stringBag.contains("banana"));
        }

        @Test
        @DisplayName("Removing the only item returns true and bag becomes empty")
        void removeOnlyItemBagBecomesEmpty() {
            stringBag.add("apple");
            assertTrue(stringBag.remove("apple"));
            assertTrue(stringBag.isEmpty());
            assertEquals(0, stringBag.size());
        }

        @Test
        @DisplayName("Removing absent item from single-item bag returns false")
        void removeAbsentItemReturnsFalse() {
            stringBag.add("apple");
            assertFalse(stringBag.remove("banana"));
            assertEquals(1, stringBag.size());
        }
    }

    // ─────────────────────────────────────────────
    //  3. Multi-Item Operations
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("3. Multi-Item Operations")
    class MultiItemTests {

        @Test
        @DisplayName("size() reflects number of items added")
        void sizeReflectsItemsAdded() {
            stringBag.add("a");
            stringBag.add("b");
            stringBag.add("c");
            assertEquals(3, stringBag.size());
        }

        @Test
        @DisplayName("contains() finds each added item")
        void containsFindsEachAddedItem() {
            String[] items = {"a", "b", "c", "d"};
            for (String item : items) stringBag.add(item);
            for (String item : items) assertTrue(stringBag.contains(item));
        }

        @Test
        @DisplayName("Bag allows duplicate entries")
        void bagAllowsDuplicates() {
            stringBag.add("dup");
            stringBag.add("dup");
            assertEquals(2, stringBag.size());
            assertTrue(stringBag.contains("dup"));
        }

        @Test
        @DisplayName("Removing one duplicate leaves the other")
        void removingOneDuplicateLeavesOther() {
            stringBag.add("dup");
            stringBag.add("dup");
            assertTrue(stringBag.remove("dup"));
            assertEquals(1, stringBag.size());
            assertTrue(stringBag.contains("dup"));
        }

        @ParameterizedTest(name = "Adding integer {0}")
        @ValueSource(ints = {1, 42, -7, 0, Integer.MAX_VALUE, Integer.MIN_VALUE})
        @DisplayName("Integer bag: add and contains for various values")
        void intBagAddAndContains(int value) {
            intBag.add(value);
            assertTrue(intBag.contains(value));
            assertEquals(1, intBag.size());
        }
    }

    // ─────────────────────────────────────────────
    //  4. Removal Scenarios
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("4. Removal Scenarios")
    class RemovalTests {

        @Test
        @DisplayName("Remove first item from multi-item bag")
        void removeFirstItem() {
            stringBag.add("first");
            stringBag.add("second");
            stringBag.add("third");
            assertTrue(stringBag.remove("first"));
            assertEquals(2, stringBag.size());
            assertFalse(stringBag.contains("first"));
        }

        @Test
        @DisplayName("Remove middle item from multi-item bag")
        void removeMiddleItem() {
            stringBag.add("first");
            stringBag.add("middle");
            stringBag.add("last");
            assertTrue(stringBag.remove("middle"));
            assertEquals(2, stringBag.size());
            assertFalse(stringBag.contains("middle"));
            assertTrue(stringBag.contains("first"));
            assertTrue(stringBag.contains("last"));
        }

        @Test
        @DisplayName("Remove last item from multi-item bag")
        void removeLastItem() {
            stringBag.add("first");
            stringBag.add("second");
            stringBag.add("last");
            assertTrue(stringBag.remove("last"));
            assertEquals(2, stringBag.size());
            assertFalse(stringBag.contains("last"));
        }

        @Test
        @DisplayName("Removing all items one-by-one leaves empty bag")
        void removeAllItemsOneByOne() {
            stringBag.add("a");
            stringBag.add("b");
            stringBag.add("c");
            stringBag.remove("a");
            stringBag.remove("b");
            stringBag.remove("c");
            assertTrue(stringBag.isEmpty());
            assertEquals(0, stringBag.size());
        }

        @Test
        @DisplayName("remove() returns false for null-like absent value")
        void removeAbsentValueReturnsFalse() {
            stringBag.add("present");
            assertFalse(stringBag.remove("absent"));
            assertEquals(1, stringBag.size());
        }

        @Test
        @DisplayName("Bag supports null elements")
        void bagSupportsNullElements() {
            stringBag.add(null);
            assertEquals(1, stringBag.size());
            assertTrue(stringBag.contains(null));
            assertTrue(stringBag.remove(null));
            assertTrue(stringBag.isEmpty());
        }
    }

    // ─────────────────────────────────────────────
    //  5. Iterator Behavior
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("5. Iterator Behavior")
    class IteratorTests {

        @Test
        @DisplayName("Iterator visits every added element")
        void iteratorVisitsAllElements() {
            stringBag.add("x");
            stringBag.add("y");
            stringBag.add("z");

            int count = 0;
            for (String s : stringBag) {
                assertNotNull(s);
                count++;
            }
            assertEquals(3, count);
        }

        @Test
        @DisplayName("Iterator count matches size()")
        void iteratorCountMatchesSize() {
            for (int i = 0; i < 10; i++) intBag.add(i);

            int count = 0;
            for (int ignored : intBag) count++;

            assertEquals(intBag.size(), count);
        }

        @Test
        @DisplayName("hasNext() returns false immediately on empty bag")
        void hasNextFalseOnEmptyBag() {
            Iterator<String> it = stringBag.iterator();
            assertFalse(it.hasNext());
        }

        @Test
        @DisplayName("hasNext() is idempotent — repeated calls don't advance iterator")
        void hasNextIsIdempotent() {
            stringBag.add("only");
            Iterator<String> it = stringBag.iterator();
            assertTrue(it.hasNext());
            assertTrue(it.hasNext());     // calling again must not advance
            assertEquals("only", it.next());
            assertFalse(it.hasNext());
        }

        @Test
        @DisplayName("next() on exhausted iterator throws NoSuchElementException")
        void nextOnExhaustedIteratorThrows() {
            stringBag.add("solo");
            Iterator<String> it = stringBag.iterator();
            it.next();                    // consume the only element
            assertThrows(NoSuchElementException.class, it::next);
        }

        @Test
        @DisplayName("Two independent iterators traverse bag simultaneously")
        void twoIndependentIterators() {
            stringBag.add("a");
            stringBag.add("b");

            Iterator<String> it1 = stringBag.iterator();
            Iterator<String> it2 = stringBag.iterator();

            // Advance it1 fully
            int count1 = 0;
            while (it1.hasNext()) { it1.next(); count1++; }

            // it2 should still be at the start
            int count2 = 0;
            while (it2.hasNext()) { it2.next(); count2++; }

            assertEquals(2, count1);
            assertEquals(2, count2);
        }

        @Test
        @DisplayName("For-each loop works correctly (Iterable contract)")
        void forEachLoopWorksCorrectly() {
            int[] values = {10, 20, 30};
            for (int v : values) intBag.add(v);

            int sum = 0;
            for (int v : intBag) sum += v;

            assertEquals(60, sum);
        }
    }

    // ─────────────────────────────────────────────
    //  6. Type-Safety & Generics
    // ─────────────────────────────────────────────

    @Nested
    @DisplayName("6. Type-Safety and Generics")
    class GenericTypeTests {

        @Test
        @DisplayName("Bag works with a custom object type")
        void bagWorksWithCustomObjects() {
            record Point(int x, int y) {}

            Bag<Point> pointBag = new Bag<>();
            Point p1 = new Point(1, 2);
            Point p2 = new Point(3, 4);

            pointBag.add(p1);
            pointBag.add(p2);

            assertEquals(2, pointBag.size());
            assertTrue(pointBag.contains(p1));
            assertTrue(pointBag.remove(p1));
            assertFalse(pointBag.contains(p1));
            assertTrue(pointBag.contains(p2));
        }

        @Test
        @DisplayName("Bag<Integer> and Bag<String> are independent instances")
        void differentTypeBagsAreIndependent() {
            intBag.add(99);
            stringBag.add("hello");

            assertEquals(1, intBag.size());
            assertEquals(1, stringBag.size());
            assertTrue(intBag.contains(99));
            assertTrue(stringBag.contains("hello"));
        }
    }
}