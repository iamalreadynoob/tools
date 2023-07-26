package process;

import actions.Calendar;
import actions.Help;
import actions.Notes;
import fileReading.TextReading;

import java.util.ArrayList;
import java.util.Scanner;

public class CmdAction
{
    private String currentAction, flag;

    public CmdAction()
    {
        ArrayList<String> actions = TextReading.read("save/actions.txt");
        ArrayList<String> globals = TextReading.read("save/global.txt");

        String request;
        currentAction = "main";

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
            else if (act.equals(currentAction)) pieces.remove(0);
            else if (globals.contains(act))
            {
                flag = currentAction;
                currentAction = act;
            }

            forward(currentAction, pieces);

            if (globals.contains(currentAction)) currentAction = flag;
        }
        while (!request.equals("quit"));
    }

    private void forward(String action, ArrayList<String> words)
    {
        if (TextReading.read("save/global.txt").contains(action))
        {
            if (action.equals("active")) System.out.println(flag);
            else if (action.equals("help")) new Help(words).run();
        }
        else if (action.equals("calendar")) new Calendar(words).run();
        else if (action.equals("note")) new Notes().run();
    }

}
