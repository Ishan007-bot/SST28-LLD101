import java.util.*;

class AdminService {

    public boolean addShow(List<Show> shows, Show newShow) {

        long newStart = newShow.startTime;
        long newEnd = newStart + newShow.duration;

        for (Show s : shows) {

            // Only check same screen
            if (!s.screenId.equals(newShow.screenId))
                continue;

            long existingStart = s.startTime;
            long existingEnd = existingStart + s.duration;

            // Overlap condition
            if (newStart < existingEnd && newEnd > existingStart) {
                System.out.println("Time overlap on same screen! Cannot add show.");
                return false;
            }
        }

        shows.add(newShow);
        System.out.println("Show added successfully");
        return true;
    }
}