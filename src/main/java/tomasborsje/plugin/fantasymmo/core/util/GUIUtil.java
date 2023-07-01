package tomasborsje.plugin.fantasymmo.core.util;

public class GUIUtil {
    /**
     * Returns the slot number for the given x and y coordinates.
     * Note that x starts with 1 and y starts with 1.
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The slot number
     */
    public static int GetSlot(int x, int y) {
        return (y-1) * 9 + x-1;
    }
}
