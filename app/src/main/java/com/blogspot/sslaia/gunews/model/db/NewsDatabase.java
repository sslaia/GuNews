package com.blogspot.sslaia.gunews.model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {News.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {

    String newsId = "world/2019/sep/05/italys-coalition-enemies-open-way-matteo-salvini-return";
    String section = "World news";
    String date = "2019-09-05T09:05:54Z";
    String weburl = "https://www.theguardian.com/world/2019/sep/05/italys-coalition-enemies-open-way-matteo-salvini-return";
    String apiurl = "https://content.guardianapis.com/world/2019/sep/05/italys-coalition-enemies-open-way-matteo-salvini-return";
    String headline = "Italy\'s new coalition sworn in as analysts cast doubt over longevity";
    String byline = "Lorenzo Tondo Palermo";
    String shorturl = "https://gu.com/p/c9t6x";
    String thumbnail = "https://media.guim.co.uk/9373fd511cc25cb2e91eadd595c421e78378a6d4/0_115_3967_2381/500.jpg";
    String body = "Italy’s new coalition government between the centre-left Democratic party and the anti-establishment Five Star Movement has been sworn in, with many analysts casting doubt on how long it will last.\n" +
            "\n" +
            "The two parties are longstanding enemies. Should they fail, fresh elections might lead to them being punished and open the way for a comeback by Matteo Salvini, the leader of the far-right League who on Thursday was placed under investigation on suspicion of defaming the captain of a German migrant rescue ship.\n" +
            "\n" +
            "The coalition looks likely to break from some of the hardline immigration measures pushed by Salvini, who was the interior minister in the last government.\n" +
            "\n" +
            "Italy was plunged into chaos last month when Salvini withdrew the League from its fractious alliance with the Five Star Movement (M5S), as he sought to exploit his party’s popularity to force a snap election and seize the prime ministership. But Salvini, whose tactics have dented his popularity in recent weeks, had not banked on M5S teaming up with the Democratic party (PD).\n" +
            "\n" +
            "After two weeks of negotiations, on Wednesday the prime minister, Giuseppe Conte, announced his team of ministers. The list includes Roberto Gualtieri, an influential PD member of the European parliament, as economy minister. The M5S leader, Luigi Di Maio, will be foreign minister. Luciana Lamorgese, a veteran of the interior ministry, in charge of planning refugee and migrant reception centres in northern Italy, has succeeded Salvini as interior minister.";
    boolean saved = false;
    boolean hidden = false;

    private static NewsDatabase instance;

    public abstract NewsDao newsDao();

    public static synchronized NewsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NewsDatabase.class, "news_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}