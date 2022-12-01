package BLL.util;

public class SecondsToMinute {

    public String secondsMinute(int seconds)
    {

        int hours=0;
        int minutes = 0;
        int secondRest=0;
        String hourString="";
        String minuteString="";
        String secondString="";


        secondRest=seconds;
        hours=secondRest/3600;

        if (hours>=1)
        {
            secondRest=seconds-3600*hours;
                     }
        minutes=secondRest/60;

        if (minutes>=1)
        {
            secondRest=secondRest-60*minutes;
        }

        if (hours<=10)
            hourString="0"+hours;
        else
            hourString=hours+"";

        if (minutes<=10)
            minuteString="0"+minutes;
        else
            minuteString=minutes+"";

        if (secondRest<=10)
            secondString="0"+secondRest;
        else
            secondString=secondRest+"";




        String time=  hourString+ ":"+minuteString+":"+ secondString;

        return time;
    }




}
