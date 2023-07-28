package actions;

import java.util.Scanner;

public class TrackerCategory implements IAction
{

    private String location;
    private final Tracker.Space space;

    protected TrackerCategory(Tracker.Space space)
    {
        this.space = space;
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

            if (req.trim().equals("finished")) new TrackerMode(space, Category.FINISHED).run();
            else if (req.trim().equals("all")) new TrackerMode(space, Category.ALL).run();
            else if (req.trim().equals("watchlist")) new TrackerMode(space, Category.WATCHLIST).run();
            else if (req.trim().equals("continue")) new TrackerMode(space, Category.CONTINUE).run();
            else if (req.trim().equals("dropped")) new TrackerMode(space, Category.DROPPED).run();
            else if (req.trim().equals("wait")) new TrackerMode(space, Category.WAIT).run();
            else if (req.trim().equals("interrupt")) new TrackerMode(space, Category.INTERRUPT).run();
            else if (req.trim().equals("help")) help();

        }
        while (!req.equals("quit"));
    }

    private void init()
    {
        if (space == Tracker.Space.TV) location = "@tracker::tv: ";
        else if (space == Tracker.Space.MOVIE) location = "@tracker::movie: ";
    }

    private void help()
    {

    }

    protected enum Category {FINISHED, ALL, WATCHLIST, CONTINUE, DROPPED, WAIT, INTERRUPT}
}
