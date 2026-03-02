import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;

import java.util.List;

/**
 * Demo showing immutability in action.
 */
public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        // Create ticket
        IncidentTicket original = service.createTicket(
                "TCK-1001",
                "reporter@example.com",
                "Payment failing on checkout");
        System.out.println("Created: " + original);

        // "Update" returns NEW ticket - original unchanged
        IncidentTicket assigned = service.assign(original, "agent@example.com");
        IncidentTicket escalated = service.escalateToCritical(assigned);

        System.out.println("\nAfter updates:");
        System.out.println("  Original : " + original);
        System.out.println("  Assigned : " + assigned);
        System.out.println("  Escalated: " + escalated);

        // Try external tag mutation - will fail
        List<String> tags = escalated.getTags();
        try {
            tags.add("HACKED_FROM_OUTSIDE");
            System.out.println("\nBUG: Mutation succeeded!");
        } catch (UnsupportedOperationException e) {
            System.out.println("\nExternal mutation blocked!");
        }

        // No setters exist - won't compile:
        // original.setPriority("LOW");
    }
}