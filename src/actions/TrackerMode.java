package actions;

import java.util.Scanner;

public class TrackerMode implements IAction
{

    private final Tracker.Space activeSpace;
    private final TrackerCategory.Category activeCategory;
    private String location;

    protected TrackerMode(Tracker.Space activeSpace, TrackerCategory.Category activeCategory)
    {
        this.activeSpace = activeSpace;
        this.activeCategory = activeCategory;
        init();
    }

    @Override
    public void run()
    {
        String req;

        do
        {
            System.out.print(location);
            req = new Scanner(System.in).nextLine();


        }
        while (!req.trim().equals("quit"));
    }

    private void init()
    {
        location = "@tracker::";
        if (activeSpace == Tracker.Space.TV) location += "tv::";
        else if (activeSpace == Tracker.Space.MOVIE) location += "movie::";

        switch (activeCategory)
        {
            case FINISHED: location += "finished: "; break;
            case ALL: location += "all: "; break;
            case WAIT: location += "wait: "; break;
            case DROPPED: location += "dropped: "; break;
            case CONTINUE: location += "continue: "; break;
            case INTERRUPT: location += "interrupt: "; break;
            case WATCHLIST: location += "watchlist: "; break;
        }
    }

    private enum Mode {INBOX, DISPLAY}
}
