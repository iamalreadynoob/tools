package actions;

import fileReading.TinfReading;
import fileWriting.TinfWriting;

import java.util.ArrayList;
import java.util.Scanner;

public class Notes implements IAction
{
    private Modes activeMood;
    private int flagIndex;
    public Notes()
    {
        flagIndex = 0;
        inbox();
        activeMood = Modes.INBOX;
    }

    @Override
    public void run()
    {
        String arg;

        do
        {
            System.out.print("\n***\n\n@note: ");
            arg = new Scanner(System.in).nextLine();

            if (arg.trim().equals("inbox") && activeMood != Modes.INBOX)
            {
                flagIndex = 0;
                inbox();
            }
            else if (arg.trim().startsWith("add") && activeMood == Modes.INBOX) add(arg.trim().substring(4).trim());
            else if (arg.trim().equals("n") && activeMood == Modes.INBOX) next();
            else if (arg.trim().equals("p") && activeMood == Modes.INBOX) previous();
            else if (arg.trim().startsWith("remove") && activeMood == Modes.INBOX) remove(arg.trim().substring(7).trim());
            else if (arg.trim().startsWith("edit") && activeMood == Modes.INBOX) edit(arg.trim().substring(5).trim());
            else if (arg.trim().equals("help")) help();

            //last resort
            else if (!arg.trim().equals("quit")) numbers(arg.trim());
        }
        while (!arg.equals("quit"));
    }

    private void inbox()
    {
        String prompt = "INBOX\n\n";

        TinfReading reading = new TinfReading();
        reading.scan("save/notes.tinf");

        if (reading.getTitles().size() == 0) prompt += "Ups... This place is... quite empty...";
        else
        {
            prompt += "contents:\n";

            int limit = 0;

            while (flagIndex < reading.getTitles().size() && limit < 10)
            {
                prompt += "\n[" + (flagIndex + 1) + "] " + reading.getTitles().get(flagIndex);

                limit++;

                if (flagIndex + 1 < reading.getTitles().size()) flagIndex++;
                else  break;
            }
        }

        System.out.println(prompt);
    }

    private void add(String title)
    {
        System.out.println(title.toUpperCase() + "\n\nyour note:");
        String text = new Scanner(System.in).nextLine();

        ArrayList<String> tempTitle = new ArrayList<>();
        tempTitle.add(title);

        ArrayList<String> tempText = new ArrayList<>();
        tempText.add(text);

        TinfWriting writing = new TinfWriting();
        writing.append("save/notes.tinf", tempTitle, tempText);

        flagIndex = 0;
        inbox();
    }

    private void numbers(String index)
    {
        try
        {
            int ind = Integer.parseInt(index) - 1;

            TinfReading reading = new TinfReading();
            reading.scan("save/notes.tinf");

            String prompt = reading.getTitles().get(ind).toUpperCase() + "\n";
            for (int i = 0; i < reading.getTitles().get(ind).length(); i++) prompt += "*";
            prompt += "\n\n" + reading.getTexts().get(ind);

            activeMood = Modes.DISPLAY;
            System.out.println(prompt);
        }
        catch (Exception e) {System.out.println("INVALID INDEX");}
    }

    private void next()
    {
        TinfReading reading = new TinfReading();
        reading.scan("save/notes.tinf");

        if (flagIndex < reading.getTitles().size()) inbox();
    }

    private void previous()
    {
        TinfReading reading = new TinfReading();
        reading.scan("save/notes.tinf");

        int addition = flagIndex % 10;

        if (flagIndex - 10 - addition >= 0)
        {
            flagIndex = flagIndex - 10 - addition;
            inbox();
        }
    }

    private void remove(String index)
    {
        try
        {
            int ind = Integer.parseInt(index) - 1;
            new TinfWriting().delete("save/notes.tinf", ind);
            flagIndex = 0;
            inbox();
        }
        catch (Exception e) {System.out.println("INVALID INDEX");}
    }

    private void edit(String index)
    {
        try
        {
            int ind = Integer.parseInt(index) - 1;

            TinfReading reading = new TinfReading();
            reading.scan("save/notes.tinf");

            String prompt = "old version of " + reading.getTitles().get(ind) + ":\n\n"
                    + reading.getTexts().get(ind) + "\n\nnew version: ";
            System.out.println(prompt);

            String version = new Scanner(System.in).nextLine();

            new TinfWriting().changeText("save/notes.tinf", ind, version);

            flagIndex = 0;
            inbox();
        }
        catch (Exception e) {System.out.println("INVALID INDEX");}
    }

    private void help()
    {
        activeMood = Modes.DISPLAY;

        String prompt = "These commands are valid in this action:\n\n" +
                "add {note-name} => adds new note\n" +
                "edit {index} => opens insert mode for any note\n" +
                "inbox => returns inbox screen\n" +
                "quit => closes action\n" +
                "remove {index} => removes note";

        System.out.println(prompt);
    }

    public enum Modes
    {
        INBOX, DISPLAY
    }
}
