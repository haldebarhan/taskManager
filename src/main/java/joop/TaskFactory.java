package joop;

public class TaskFactory {

    public static Task createTaskFromString(String taskString) {
        if (taskString.isBlank() || taskString.isEmpty()) {
            throw new IllegalArgumentException("invalid Task string");
        }
        String[] items = taskString.split(":");
        if (items.length < 2) {
            throw new IllegalArgumentException("invalid Task string");
        }
        String type = items[0];
        String[] parts = items[1].split(", ");
        switch (type) {
            case "SimpleTask" -> {
                if (parts.length != 4) {
                    throw new IllegalArgumentException("invalid Task string");
                }
                return createSimpleTask(parts);
            }

            case "DailyTask" -> {
                if (parts.length != 3) {
                    throw new IllegalArgumentException("invalid Task string");
                }
                return createDailyTask(parts);
            }

            case "CheckListTask" -> {
                if (parts.length != 6) {
                    throw new IllegalArgumentException("invalid Task string");
                }
                return createCheckListTask(parts);
            }
            default ->
                throw new IllegalArgumentException("unknown Task type");
        }
    }

    private static SimpleTask createSimpleTask(String[] parts) {
        try {
            String name = parts[0];
            String description = parts[1];
            int points = Integer.parseInt(parts[2]);
            boolean completed = Boolean.parseBoolean(parts[3]);
            return new SimpleTask(name, description, points, completed);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Error creating SimpleTask." + ex);
        }
    }

    private static DailyTask createDailyTask(String[] parts) {
        try {
            String name = parts[0];
            String description = parts[1];
            int points = Integer.parseInt(parts[2]);
            return new DailyTask(name, description, points);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Error creating DailyTask." + ex);
        }
    }

    private static CheckListTask createCheckListTask(String[] parts) {
        try {
            String name = parts[0];
            String description = parts[1];
            int points = Integer.parseInt(parts[2]);
            int bonus = Integer.parseInt(parts[3]);
            int target = Integer.parseInt(parts[4]);
            int accomplish = Integer.parseInt(parts[5]);
            return new CheckListTask(name, description, points, accomplish, target, bonus);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Error creating DailyTask." + ex);
        }
    }
}
