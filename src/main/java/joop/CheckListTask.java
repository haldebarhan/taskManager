package joop;

class CheckListTask extends Task {

    private int amountCompleted;
    private final int target;
    private final int bonus;

    public CheckListTask(String name, String description, int points, int amountCompleted, int target, int bonus) {
        super(name, description, points);
        this.amountCompleted = amountCompleted;
        this.target = target;
        this.bonus = bonus;
    }

    @Override
    public void recordEvent() {
        if (amountCompleted < target) {
            amountCompleted++;
        } else {
            System.out.println("This task is Completed");
        }
    }

    @Override
    public String GetDetails() {
        StringBuilder sb = new StringBuilder();
        String message = super.GetDetails();
        String info = String.format("  --- currently completed: %d/%d", GetAmountCompleted(), GetTarget());
        return sb.append(message).append(info).toString();
    }

    @Override
    public boolean IsCompleted() {
        return true ? amountCompleted == target : false;
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
                .append(getBonus())
                .append(", ")
                .append(GetTarget())
                .append(", ")
                .append(GetAmountCompleted());
        return sb.toString();
    }

    public int GetAmountCompleted() {
        return amountCompleted;
    }

    @Override
    public int getPoint() {
        if (IsCompleted()) {
            return points + bonus;
        } else {
            return points;
        }
    }

    public int GetTarget() {
        return target;
    }

    public int getBonus() {
        return bonus;
    }

}
