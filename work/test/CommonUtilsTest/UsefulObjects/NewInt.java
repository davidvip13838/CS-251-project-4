package CommonUtils.test.UsefulObjects;

/**
 * Dummy class to test reverse sorting
 */
public class NewInt implements Comparable<NewInt> {
    private final Integer thing;

    public NewInt(Integer temp) { thing = temp; }

    public Integer get() { return thing; }

    /**
     * Javadoc is too long
     * negative = <
     * 0 = equal
     * positive = >
     */
    @Override
    public int compareTo(NewInt o) {
        return o.thing.compareTo(thing);//because we don't care about type safety :)
    }
}
