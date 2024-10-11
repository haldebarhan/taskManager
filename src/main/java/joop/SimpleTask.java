package joop;

public class SimpleTask extends Task {

    private boolean IsCompleted;

    public SimpleTask(final String name, final String description, final int points, final boolean IsCompleted) {
        super(name, description, points);
        this.IsCompleted = IsCompleted;
    }

    @Override
    public void recordEvent() {
        IsCompleted = true;
    }

    @Override
    public String GetDetails() {
        return super.GetDetails();
    }

    @Override
    public boolean IsCompleted() {
        return this.IsCompleted;
    }

    @Override
    public String GetStringRepresentation() {

        StringBuilder sb = new StringBuilder();
                sb.append(GetClassName())
                .append(": ")
                .append(GetShortName())
                .append(", ")
                .append(GetDescription())
                .append(", ")
                .append(getPoint())
                .append(", ")
                .append(IsCompleted());
        return sb.toString();
    }

}
