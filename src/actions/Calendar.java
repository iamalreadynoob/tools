package actions;

import fileReading.SavfReading;
import fileWriting.SavfWriting;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class Calendar extends AbsAction implements IAction
{

    public Calendar(ArrayList<String> words) {super(words);}

    @Override
    public void run()
    {
        if (getCommand().equals("day")) day();
        else if (getCommand().equals("date")) date();
        else if (getCommand().equals("format")) format();
        else if (getCommand().equals("set-format")) setFormat();
        else if (getCommand().equals("when")) when();
        else if (getCommand().equals("day-of")) dayOf();
        else if (getCommand().equals("format-list")) formatList();
    }

    private void day()
    {
        LocalDate local = LocalDate.now();
        DayOfWeek day = local.getDayOfWeek();
        String dayName = day.getDisplayName(TextStyle.FULL, Locale.getDefault()).toLowerCase();
        System.out.println(dayName);
    }

    private void format()
    {
        SavfReading reading = new SavfReading();
        reading.scan("save/features.savf");
        System.out.println(reading.getValue("date-format"));
    }

    private void setFormat()
    {
        if (getWords().size() == 3)
        {
            if (getWords().get(0).equals("mm") &&
                    getWords().get(1).equals("dd") &&
                    getWords().get(2).equals("yyyy"))
            {
                ArrayList<String> param = new ArrayList<>();
                param.add("date-format");
                ArrayList<String> value = new ArrayList<>();
                value.add("mm/dd/yyyy");

                SavfWriting.write("save/features.savf", param, value);
                System.out.println("Date format has been changed");
            }

            else if (getWords().get(0).equals("dd") &&
                    getWords().get(1).equals("mm") &&
                    getWords().get(2).equals("yyyy"))
            {
                ArrayList<String> param = new ArrayList<>();
                param.add("date-format");
                ArrayList<String> value = new ArrayList<>();
                value.add("dd/mm/yyyy");

                SavfWriting.write("save/features.savf", param, value);
                System.out.println("Date format has been changed");
            }

            else if (getWords().get(0).equals("yyyy") &&
                    getWords().get(1).equals("dd") &&
                    getWords().get(2).equals("mm"))
            {
                ArrayList<String> param = new ArrayList<>();
                param.add("date-format");
                ArrayList<String> value = new ArrayList<>();
                value.add("yyyy/dd/mm");

                SavfWriting.write("save/features.savf", param, value);
                System.out.println("Date format has been changed");
            }

            else if (getWords().get(0).equals("yyyy") &&
                    getWords().get(1).equals("mm") &&
                    getWords().get(2).equals("dd"))
            {
                ArrayList<String> param = new ArrayList<>();
                param.add("date-format");
                ArrayList<String> value = new ArrayList<>();
                value.add("yyyy/mm/dd");

                SavfWriting.write("save/features.savf", param, value);
                System.out.println("Date format has been changed");
            }
        }

        else if (getWords().size() == 1)
        {
            if (getWords().get(0).equals("0") || getWords().get(0).equals("def"))
            {
                ArrayList<String> param = new ArrayList<>();
                param.add("date-format");
                ArrayList<String> value = new ArrayList<>();
                value.add("mm/dd/yyyy");

                SavfWriting.write("save/features.savf", param, value);
                System.out.println("Date format has been changed");
            }

            else if (getWords().get(0).equals("1") || getWords().get(0).equals("alter"))
            {
                ArrayList<String> param = new ArrayList<>();
                param.add("date-format");
                ArrayList<String> value = new ArrayList<>();
                value.add("dd/mm/yyyy");

                SavfWriting.write("save/features.savf", param, value);
                System.out.println("Date format has been changed");
            }

            else if (getWords().get(0).equals("2") || getWords().get(0).equals("yfirst-def"))
            {
                ArrayList<String> param = new ArrayList<>();
                param.add("date-format");
                ArrayList<String> value = new ArrayList<>();
                value.add("yyyy/dd/mm");

                SavfWriting.write("save/features.savf", param, value);
                System.out.println("Date format has been changed");
            }

            else if (getWords().get(0).equals("3") || getWords().get(0).equals("yfirst-alter"))
            {
                ArrayList<String> param = new ArrayList<>();
                param.add("date-format");
                ArrayList<String> value = new ArrayList<>();
                value.add("yyyy/mm/dd");

                SavfWriting.write("save/features.savf", param, value);
                System.out.println("Date format has been changed");
            }
        }
    }

    private void date()
    {
        SavfReading reading = new SavfReading();
        reading.scan("save/features.savf");

        String format = reading.getValue("date-format")
                .replaceAll("/", "-")
                .replaceAll("m", "M");

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String date = time.format(formatter);
        System.out.println("Date: " + date);
    }

    private void when()
    {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        String date = time.format(formatter);
        System.out.println("Time: " + date);
    }

    private void dayOf()
    {
        String req = null;

        if (getWords().size() == 1 && getWords().get(0).length() == 10 && getWords().get(0).contains("-"))
            req = getWords().get(0);

        else if (getWords().size() == 1 && getWords().get(0).length() == 10 && getWords().get(0).contains("/"))
            req = getWords().get(0).replaceAll("/", "-");

        else if (getWords().size() == 3)
            for (String d: getWords())
                if (req == null) req = d;
                else req += "-" + d;

        SavfReading reading = new SavfReading();
        reading.scan("save/features.savf");

        String format = reading.getValue("date-format")
                .replaceAll("/", "-")
                .replaceAll("m", "M");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);

        try
        {
            LocalDate date = LocalDate.parse(req, dateTimeFormatter);
            DayOfWeek day = date.getDayOfWeek();
            System.out.println(day.toString().toLowerCase());
        }
        catch (Exception e) {System.out.println("EXCEPTION: INVALID DATE");}
    }

    private void formatList()
    {
        System.out.println("full-name\tcode\tshort-name\n" +
                "mm/dd/yyyy\t0\t\tdef\n" +
                "dd/mm/yyyy\t1\t\talter\n" +
                "yyyy/dd/mm\t2\t\tyfirst-def\n" +
                "yyyy/mm/dd\t3\t\tyfirst-alter");
    }
}
