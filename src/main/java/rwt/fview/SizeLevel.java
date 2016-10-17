package rwt.fview;

/**
 * Delineate the available size levels for block sizes.
 * Created by richard todd on 10/11/2016.
 */
enum SizeLevel {
    BYTES(1), KB(1024), MB(1024*1024);

    private final int multiplier;

    int getMultiplier() { return multiplier; }

    SizeLevel(int m) {
        multiplier = m;
    }
}
