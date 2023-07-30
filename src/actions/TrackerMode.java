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

            if (req.trim().equals("inbox") && activeMode != Mode.INBOX) inbox();
            else if (req.trim().equals("n")) next();
            else if (req.trim().equals("p")) previous();
            else if (req.trim().startsWith("add")) add(req.trim().substring(4).trim());
            else number(req.trim());

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

        inbox();
    }

    private void inbox()
    {
        String prompt = "INBOX\n\n";
        activeMode = Mode.INBOX;

        ArrayList<String> name = getColumn("name");

        if (name.size() == 0) prompt += "Ups... This place is... quite empty...";
        else
        {
            prompt += "contents:\n";

            int limit = 0;

            while (flagIndex < name.size() && limit < 10)
            {
                prompt += "\n[" + (flagIndex + 1) + "] " + name.get(flagIndex);

                limit++;

                if (flagIndex + 1 < name.size()) flagIndex++;
                else break;
            }

            System.out.println(prompt);
        }
    }

    private void add(String name)
    {
        //TODO: behave different due to category
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

    private void next()
    {
        ArrayList<String> name = getColumn("name");

        if (flagIndex < name.size()) inbox();
    }

    private void previous()
    {
        ArrayList<String> name = getColumn("name");

        int addition = flagIndex % 10;

        if (flagIndex - 10 - addition >= 0)
        {
            flagIndex = flagIndex - 10 - addition;
            inbox();
        }
    }

    private void number(String number)
    {
        try
        {
            int ind = Integer.parseInt(number) - 1;

            ArrayList<String> name = getColumn("name");
            ArrayList<String> score = getColumn("score");
            ArrayList<String> finished = getColumn("finished");
            ArrayList<String> remained = getColumn("remained");

            String prompt = name.get(ind).replaceAll("%comma%}", ",").toUpperCase()
                    + "\nscore: " + score.get(ind) + "/10"
                    + "\nfinished: " + finished.get(ind) + " episode(s)"
                    + "\nremained: " + remained.get(ind) + " episode(s)\n";


            System.out.println(prompt);
        }
        catch (Exception e) {System.out.println("INVALID INDEX");}
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

        ArrayList<String> spaceColumn = reading.getColumn("space");
        ArrayList<String> categoryColumn = reading.getColumn("category");
        ArrayList<String> reqColumn = reading.getColumn(header);

        if (category.equals("all"))
        {
            for (int i = 0; i < spaceColumn.size(); i++)
                if (spaceColumn.get(i).equals(space)) column.add(reqColumn.get(i));
        }
        else for (int i = 0; i < spaceColumn.size(); i++)
            if (spaceColumn.get(i).equals(space) &&
                    categoryColumn.get(i).equals(category))
                column.add(reqColumn.get(i));

        return column;
    }

    private enum Mode {INBOX, DISPLAY}
}
