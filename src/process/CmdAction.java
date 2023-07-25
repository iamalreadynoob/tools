package process;

import actions.Calendar;
import fileReading.TextReading;

import java.util.ArrayList;
import java.util.Scanner;

public class CmdAction
{
    public CmdAction()
    {
        ArrayList<String> actions = TextReading.read("save/actions.txt");

        String request;
        String currentAction = "main";

        do
        {
            request = new Scanner(System.in).nextLine();

            ArrayList<String> pieces = new ArrayList<>();
            String[] arr = request.split(" ");
            for (String p: arr) pieces.add(p);

            String act = arr[0];
            if (!act.equals(currentAction) && actions.contains(act))
            {
                currentAction = act;
                pieces.remove(0);
            }

            forward(currentAction, pieces);

        }
        while (!request.equals("quit"));
    }

    private void forward(String action, ArrayList<String> words)
    {
        if (action.equals("calendar")) new Calendar(words).run();
    }

}
