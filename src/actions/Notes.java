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
        inbox();
        activeMood = Modes.INBOX;
        flagIndex = 0;
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
            else if (arg.trim().startsWith("add") && activeMood == Modes.INBOX) add(arg.trim().substring(3).trim());
        }
        while (!arg.equals("exit"));
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
                prompt += "\n[" + (limit + 1) + "] " + reading.getTitles().get(flagIndex);

                flagIndex++;
                limit++;
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

    public enum Modes
    {
        INBOX, INSERT
    }
}
