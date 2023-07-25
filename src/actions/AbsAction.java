package actions;

import java.util.ArrayList;

public abstract class AbsAction
{

    private ArrayList<String> words;
    private String command;

    protected AbsAction(ArrayList<String> words)
    {
        this.words = new ArrayList<>();

        command = words.get(0);
        words.remove(0);
        for (String w: words) this.words.add(w);
    }

    protected String getCommand() {return command;}
    protected ArrayList<String> getWords() {return words;}

}
