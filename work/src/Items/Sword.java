package Items;

import Items.Types.SwordType;

import java.util.Objects;

/**
 * Implements our SwordType class
 */
public class Sword extends SwordType {
    /**
     * Constructs the Sword object by referencing the parent class.  Fields are as named.
     */
    public Sword(int totalHealth, int healthLeft, int timeToClean, int length, int DPS, int attackSpeed,
                 String name, String description, String comments, String style) {
        super(totalHealth, healthLeft, timeToClean, length, DPS, attackSpeed, name, description, comments, style);
    }


    //hint: it might be helpful to implement a static function that returns a unique value based only on certain fields
    /* REMOVE FOR STUDENT COPY */
    //uniquely identified by a combination of: totalHealth, DPS, attackSpeed, style

    /**
     * Returns the hash code of an object based on the fields that uniquely identify a sword
     * @param totalHealth total health
     * @param DPS damage per second
     * @param attackSpeed attack speed
     * @param style style of sword
     * @return hashed value
     */
    public static int getHashValue(int totalHealth, int DPS, int attackSpeed, String style){
        return Objects.hash(totalHealth, DPS, attackSpeed, style);
    }//pass in the "few" fields required to make the hash
    /* END REMOVE */



    /**
     * Returns the hash code of this object, for internal use only
     *
     * <bold>251 Students: This function will not be tested directly, it is for your convenience only.</bold>
     * @return a hash code of the object
     */
    @Override
    public int hashCode() {
        /* REMOVE FOR STUDENT COPY */
        return Objects.hash(totalHealth, DPS, attackSpeed, style);
        /* END REMOVE */
//        return 0;
    }
}
