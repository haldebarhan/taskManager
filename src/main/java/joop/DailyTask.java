package joop;

public class DailyTask extends Task {

    public DailyTask(String name, String description, int points) {
        super(name, description, points);
    }

    @Override
    public void recordEvent() {
    }

    @Override
    public boolean IsCompleted() {
        return false;
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
                .append(getPoint());
        return sb.toString();
    }

    @Override
    public String GetDetails() {
        return super.GetDetails();
    }

}
