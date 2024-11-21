package Items.Types;

import java.util.Objects;

/**
 * Extendable sword type.  There shouldn't be a need to modify this class.
 */
public abstract class SwordType {
    protected int totalHealth, healthLeft, timeToClean, length, DPS, attackSpeed;
    protected String name, description, comments, style;

    /**
     * Constructs the object.  Fields are as named.
     */
    public SwordType(int totalHealth, int healthLeft, int timeToClean, int length, int DPS, int attackSpeed,
                     String name, String description, String comments, String style) {
        this.totalHealth = totalHealth;
        this.healthLeft = healthLeft;
        this.timeToClean = timeToClean;
        this.length = length;
        this.DPS = DPS;
        this.attackSpeed = attackSpeed;
        this.name = name;
        this.description = description;
        this.comments = comments;
        this.style = style;
    }

    //getters
    public int getTotalHealth() { return totalHealth; }
    public int getHealthLeft() { return healthLeft; }
    public int getTimeToClean() { return timeToClean; }
    public int getLength() { return length; }
    public int getDPS() { return DPS; }
    public int getAttackSpeed() { return attackSpeed; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getComments() { return comments; }
    public String getStyle() { return style; }

    //setters
    public void setTotalHealth(int totalHealth) { this.totalHealth = totalHealth; }
    public void setHealthLeft(int healthLeft) { this.healthLeft = healthLeft; }
    public void setTimeToClean(int timeToClean) { this.timeToClean = timeToClean; }
    public void setLength(int length) { this.length = length; }
    public void setDPS(int DPS) { this.DPS = DPS; }
    public void setAttackSpeed(int attackSpeed) { this.attackSpeed = attackSpeed; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setComments(String comments) { this.comments = comments; }
    public void setStyle(String style) { this.style = style; }

    /**
     * Default Intellij-generated equals function
     * @param o object to compare to
     * @return if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SwordType)) return false;
        SwordType swordType = (SwordType) o;
        return totalHealth == swordType.totalHealth && healthLeft == swordType.healthLeft
                && timeToClean == swordType.timeToClean && length == swordType.length
                && DPS == swordType.DPS && attackSpeed == swordType.attackSpeed
                && Objects.equals(name, swordType.name) && Objects.equals(description, swordType.description)
                && Objects.equals(comments, swordType.comments) && Objects.equals(style, swordType.style);
    }

    /**
     * Returns the hash code of this object, for internal use only
     * @return a hash code of the object
     */
    @Override
    public abstract int hashCode();

    /**
     * To string method for debugging
     * @return string version of object
     */
    @Override
    public String toString() {
        return "SwordType{" + "totalHealth=" + totalHealth + ", healthLeft=" + healthLeft +
                ", timeToClean=" + timeToClean + ", length=" + length + ", DPS=" + DPS +
                ", attackSpeed=" + attackSpeed + ", name='" + name + '\'' + ", description='" + description + '\'' +
                ", comments='" + comments + '\'' + ", style='" + style + '\'' + '}';
    }
}
