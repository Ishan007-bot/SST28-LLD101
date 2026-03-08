package com.example.reports;

/**
 * Proxy responsibilities:
 * - Access control check
 * - Lazy loading (only when needed)
 * - Caching (load once per proxy)
 */
public class ReportProxy implements Report {

    private final String reportId;
    private final String title;
    private final String classification;
    private final AccessControl accessControl = new AccessControl();

    private RealReport cachedReport = null;

    public ReportProxy(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user) {
        if (!accessControl.canAccess(user, classification)) {
            System.out.println("ACCESS DENIED: " + user.getName()
                    + " (" + user.getRole() + ") cannot access "
                    + classification + " report [" + reportId + "]");
            return;
        }

        if (cachedReport == null) {
            System.out.println("[proxy] first access - loading real report...");
            cachedReport = new RealReport(reportId, title, classification);
        } else {
            System.out.println("[proxy] using cached report");
        }

        cachedReport.display(user);
    }
}