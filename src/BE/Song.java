package BE;

import BLL.util.SecondsToMinute;

public class Song {

    private int id;
    private String title;
    private String artist;
    private String category;
    private int seconds;
    private String filePath;

    public Song(int id, String title, String artist, String category, int seconds, String filePath) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.seconds = seconds;
        this.filePath=filePath;
    }
    public int getId(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getCategory(){return category;}
    public int getSeconds() {return seconds;}
    public String getFilePath() {return filePath;}

    @Override
    public String toString() {
        //Her kaldes klassen ind som objekt til at lave normal tidsangivelse.
        SecondsToMinute secondsToMinute=new SecondsToMinute();

        // Her returnes en streng med tidsangivelse i formatet 00:00:00
        String time= secondsToMinute.secondsMinute(seconds);

        // return makeStringEvenLength();
        return  id + ": " +  title + " " + artist + " " + category+ " " + time;
    }
    /**
    public String makeStringEvenLength()
    {
        int lengthString= title.length();
        for (int i=lengthString; i<30; i++)
            title+=" ";


        lengthString= artist.length();
        for (int i=lengthString; i<30; i++)
            artist+=" ";


        lengthString= category.length();
        for (int i=lengthString; i<30; i++)
            category+=" ";


        String secondsToString = seconds + " sek";
        lengthString=secondsToString.length();

        for (int i=lengthString; i<10; i++)
            secondsToString+=" ";

        String idString="";
        if (id<10) {
            idString =id +"  : ";
        }
        else if (id>=10 && id<100)
            idString =id +" : ";
        else
            idString =id +": ";

        return idString +  title +  artist+ category+ secondsToString;
    }
     */
}
