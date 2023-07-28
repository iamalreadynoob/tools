package actions;

import java.util.Scanner;

public class Tracker implements IAction
{

    private Space activeSpace;
    public Tracker()
    {
        activeSpace = Space.VOID;
    }

    @Override
    public void run()
    {
        String req;
        do
        {
            System.out.print("@tracker: ");
            req = new Scanner(System.in).nextLine();

            if (req.trim().equals("tv")) new TrackerCategory(Space.TV).run();
            else if (req.trim().equals("movie")) new TrackerCategory(Space.MOVIE).run();
            else if (req.trim().equals("help")) help();

        }
        while (!req.equals("quit"));
    }

    private void help()
    {

    }

    protected enum Space {VOID, TV, MOVIE}

}
