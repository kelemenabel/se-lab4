package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TorpedoStoreTest {

    private TorpedoStore store;

    @BeforeEach
    void setUp() {
        // default store with 10 torpedos and 0.0 failure rate
        this.store = new TorpedoStore(10, 0.0);
    }

    @Test
    void getTorpedoCount_Default_ReturnsInitialValue() {
        assertEquals(10, store.getTorpedoCount());
    }

    @Test
    void isEmpty_WhenNotEmpty_ReturnsFalse() {
        assertFalse(store.isEmpty());
    }

    @Test
    void isEmpty_WhenZeroTorpedos_ReturnsTrue() {
        TorpedoStore emptyStore = new TorpedoStore(0, 0.0);

        assertTrue(emptyStore.isEmpty());
    }

    @Test
    void fire_Success_DecreasesTorpedoCount() {
        boolean result = store.fire(1);

        assertTrue(result);
        assertEquals(9, store.getTorpedoCount());
    }

    @Test
    void fire_MultipleTorpedos_SuccessAndDecreasesCount() {
        boolean result = store.fire(4);

        assertTrue(result);
        assertEquals(6, store.getTorpedoCount());
    }

    @Test
    void fire_AllTorpedos_BecomesEmpty() {
        boolean result = store.fire(10);

        assertTrue(result);
        assertEquals(0, store.getTorpedoCount());
        assertTrue(store.isEmpty());
    }

    @Test
    void fire_Failure_DoesNotDecreaseCount() {
        // store with 100% failure rate
        TorpedoStore failingStore = new TorpedoStore(5, 1.0);

        boolean result = failingStore.fire(1);

        // firing fails, torpedo count remains unchanged
        assertFalse(result);
        assertEquals(5, failingStore.getTorpedoCount());
    }

    @Test
    void fire_ZeroTorpedos_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> store.fire(0));
    }

    @Test
    void fire_NegativeTorpedos_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> store.fire(-2));
    }

    @Test
    void fire_MoreThanAvailable_ThrowsIllegalArgumentException() {
        // firing 11 torpedos when only 10 are available
        assertThrows(IllegalArgumentException.class, () -> store.fire(11));
    }
}
