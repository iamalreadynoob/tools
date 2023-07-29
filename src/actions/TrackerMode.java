package actions;

import fileReading.DataReading;
import fileReading.TinfReading;

import java.util.ArrayList;
import java.util.Scanner;

public class TrackerMode implements IAction
{

    private final Tracker.Space activeSpace;
    private final TrackerCategory.Category activeCategory;
    private Mode activeMode;
    private int flagIndex;
    private String location;

    protected TrackerMode(Tracker.Space activeSpace, TrackerCategory.Category activeCategory)
    {
        this.activeSpace = activeSpace;
        this.activeCategory = activeCategory;
        activeMode = Mode.INBOX;
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

        flagIndex = 0;
    }

    private void inbox()
    {
        String prompt = "INBOX\n\n";

        DataReading reading = new DataReading();
        reading.scan("save/tracker.csv");

        if (reading.getColumn("space").size() == 0) prompt += "Ups... This place is... quite empty...";
        else
        {
            prompt += "contents:\n";

            int limit = 0;

            System.out.println("flag index: " + flagIndex);

            while (flagIndex < reading.getColumn("space").size() && limit < 10)
            {
                //TODO: filter requested data only!!!
                //prompt += "\n[" + (flagIndex + 1) + "] " + reading.getTitles().get(flagIndex);

                limit++;

                /*if (flagIndex + 1 < reading.getTitles().size()) flagIndex++;
                else break;*/
            }
        }
    }

    private void add()
    {

    }

    private void move()
    {

    }

    private void remove()
    {

    }

    private void edit()
    {

    }

    private void help()
    {

    }

    private ArrayList<String> getColumn(String header)
    {
        ArrayList<String> column = new ArrayList<>();

        DataReading reading = new DataReading();
        reading.scan("save/tracker.csv");

        String space = null;
        String category = null;

        switch (activeSpace)
        {
            case TV: space = "tv"; break;
            case MOVIE: space = "movie"; break;
        }

        switch (activeCategory)
        {
            case WATCHLIST: category = "watchlist"; break;
            case ALL: category = "all"; break;
            case INTERRUPT: category = "interrupt"; break;
            case CONTINUE: category = "continue"; break;
            case DROPPED: category = "dropped"; break;
            case WAIT: category = "wait"; break;
            case FINISHED: category = "finished"; break;
        }


        return column;
    }

    private enum Mode {INBOX, DISPLAY}
}
