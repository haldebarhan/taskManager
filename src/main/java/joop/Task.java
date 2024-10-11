package joop;

public abstract class Task {

    protected String name;
    protected String description;
    protected int points;

    public Task(final String name, final String description,final int points){
        this.name = name;
        this.description = description;
        this.points = points;
    }    

    public abstract void recordEvent();

    public abstract boolean IsCompleted();

    public abstract String GetStringRepresentation();

    public String GetDetails() {
        String detail = String.format("%s (%s)", name, description);
        StringBuilder sb = new StringBuilder();
        if (IsCompleted()) {
            sb.append("[").append("X").append("] ").append(detail);
        } else {
            sb.append("[").append("] ").append(detail);
        }
        String message = sb.toString();
        return message;
    }

    public String GetShortName() {
        return name;
    }

    public String GetDescription() {
        return description;
    }

    public int getPoint() {
        return points;
    }

    public String GetClassName() {
        return this.getClass().getSimpleName();
    }
}
