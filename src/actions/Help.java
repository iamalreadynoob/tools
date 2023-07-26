package actions;

import fileReading.TextReading;

import java.util.ArrayList;

public class Help extends AbsAction implements IAction
{

    public Help(ArrayList<String> words) {super(words);}

    @Override
    public void run()
    {
        if (getWords().size() == 0) init();
    }

    private void init()
    {
        String prompt = "These are the action classes:";
        for (String cl: TextReading.read("save/actions.txt")) prompt += "\n-> " + cl;
        prompt += "\n\n***\n\nThese are the global commands:";
        for (String gl: TextReading.read("save/global.txt")) prompt += "\n-> " + gl;
        prompt += "\n\n***\n\nIf you want to learn much more detail please write 'help name' command";
        System.out.println(prompt);
    }

}
