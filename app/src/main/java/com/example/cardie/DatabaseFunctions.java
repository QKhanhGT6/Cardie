package com.example.cardie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseFunctions {
    ConnectDatabase connect;

    public DatabaseFunctions(Context context) {
        connect = new ConnectDatabase(context, "Cardie.sqlite", null, 1);
    }

    public void ConnectDB() {
        String commandUsers = "CREATE TABLE IF NOT EXISTS Users (" +
                "UserID TEXT PRIMARY KEY, " +
                "Username TEXT, " +
                "DeviceName TEXT, " +
                "ExperiencePoints INTEGER)";
        connect.WriteTable(commandUsers);

        String commandCardSet = "CREATE TABLE IF NOT EXISTS CardSet (" +
                "SetID TEXT PRIMARY KEY, " +
                "SetName TEXT, " +
                "SetImage TEXT, " +
                "UserID TEXT, " +
                "FOREIGN KEY(UserID) REFERENCES Users(UserID))";
        connect.WriteTable(commandCardSet);

        String commandCard = "CREATE TABLE IF NOT EXISTS Card (" +
                "CardID TEXT PRIMARY KEY, " +
                "CardImage TEXT, " +
                "CardPhrase TEXT, " +
                "CardType TEXT, " +
                "CardDefinition TEXT, " +
                "CardSound TEXT, " +
                "DifficultyLevel INTEGER, " +
                "SetID TEXT, " +
                "FOREIGN KEY(SetID) REFERENCES CardSet(SetID))";
        connect.WriteTable(commandCard);

        String commandPractice = "CREATE TABLE IF NOT EXISTS Practice (" +
                "PracticeID TEXT PRIMARY KEY, " +
                "UserID TEXT, " +
                "SetID TEXT, " +
                "Mode INTEGER, " +
                "CorrectAnswers INTEGER, " +
                "TotalQuestions INTEGER, " +
                "ExperienceEarned INTEGER, " +
                "Timestamp DATETIME, " +
                "FOREIGN KEY(UserID) REFERENCES Users(UserID), " +
                "FOREIGN KEY(SetID) REFERENCES CardSet(SetID))";
        connect.WriteTable(commandPractice);

        String insertAdminUser = "INSERT INTO Users VALUES(" +
                "'Admin', 'Admin', 'Samsung Galaxy Note 20', 1000)";
    }

    public void insertUser(String userId, String username, String deviceName, int experiencePoints) {
        String query = "INSERT INTO Users (UserID, Username, DeviceName, ExperiencePoints) " +
                "VALUES ('" + userId + "', '" + username + "', '" + deviceName + "', " + experiencePoints + ")";
        connect.WriteTable(query);
    }

    public List<UserClass> getAllUsers() {
        List<UserClass> userList = new ArrayList<>();
        String query = "SELECT * FROM Users";
        Cursor cursor = connect.ReadTable(query);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int userIdIndex = cursor.getColumnIndex("UserID");
                int usernameIndex = cursor.getColumnIndex("Username");
                int deviceNameIndex = cursor.getColumnIndex("DeviceName");
                int experiencePointsIndex = cursor.getColumnIndex("ExperiencePoints");

                do {
                    if (userIdIndex >= 0) {
                        String userId = cursor.getString(userIdIndex);
                        String username = cursor.getString(usernameIndex);
                        String deviceName = cursor.getString(deviceNameIndex);
                        int experiencePoints = cursor.getInt(experiencePointsIndex);

                        UserClass user = new UserClass(userId, username, deviceName, experiencePoints);
                        userList.add(user);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return userList;
    }

    public UserClass getAUser(String username) {
        String query = "SELECT * FROM Users WHERE Username = '" + username + "'";
        Cursor cursor = connect.ReadTable(query);

        UserClass user = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int userIdIndex = cursor.getColumnIndex("UserID");
                int deviceNameIndex = cursor.getColumnIndex("DeviceName");
                int experiencePointsIndex = cursor.getColumnIndex("ExperiencePoints");

                String userId = cursor.getString(userIdIndex);
                String deviceName = cursor.getString(deviceNameIndex);
                int experiencePoints = cursor.getInt(experiencePointsIndex);

                user = new UserClass(userId, username, deviceName, experiencePoints);
            }
            cursor.close();
        }
        return user;
    }

    public void insertCardSet(String setId, String setName, String setImage, String userId) {
        String query = "INSERT INTO CardSet (SetID, SetName, SetImage, UserID) " +
                "VALUES ('" + setId + "', '" + setName + "', '" + setImage + "', '" + userId + "')";
        connect.WriteTable(query);
    }

    public List<SetClass> getCardSetsForUser(String userId) {
        List<SetClass> cardSetList = new ArrayList<>();
        String query = "SELECT * FROM CardSet WHERE UserID = '" + userId + "'";
        Cursor cursor = connect.ReadTable(query);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int setIdIndex = cursor.getColumnIndex("SetID");
                int setNameIndex = cursor.getColumnIndex("SetName");
                int setImageIndex = cursor.getColumnIndex("SetImage");

                do {
                    if (setIdIndex >= 0) {
                        String setId = cursor.getString(setIdIndex);
                        String setName = cursor.getString(setNameIndex);
                        String setImage = cursor.getString(setImageIndex);

                        SetClass cardSet = new SetClass(setId, setName, setImage, userId);
                        cardSetList.add(cardSet);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return cardSetList;
    }

    public String getUserID(String username){
        String query = "SELECT * FROM Users WHERE Username = '" + username + "'";
        Cursor cursor = connect.ReadTable(query);
        String userID = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int userIDIndex = cursor.getColumnIndex("UserID");
                userID = cursor.getString(userIDIndex);
            }
            cursor.close();
        }
        return userID;
    }

    public String getUsername(String userID){
        String query = "SELECT * FROM Users WHERE UserID = '" + userID + "'";
        Cursor cursor = connect.ReadTable(query);
        String username = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int userNameIndex = cursor.getColumnIndex("Username");
                username = cursor.getString(userNameIndex);
            }
            cursor.close();
        }
        return username;
    }

    public int getUserLevel(String userID){
        String query = "SELECT * FROM Users WHERE UserID = '" + userID + "'";
        Cursor cursor = connect.ReadTable(query);
        String experience = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int userExpIndex = cursor.getColumnIndex("ExperiencePoints");
                experience = cursor.getString(userExpIndex);
            }
            cursor.close();
        }
        int level = 1 + Integer.parseInt(experience) / 10;
        return level;
    }
    public int getUserExp(String userID){
        String query = "SELECT * FROM Users WHERE UserID = '" + userID + "'";
        Cursor cursor = connect.ReadTable(query);
        String experience = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int userExpIndex = cursor.getColumnIndex("ExperiencePoints");
                experience = cursor.getString(userExpIndex);
            }
            cursor.close();
        }

        return Integer.parseInt(experience);
    }
    public void updateUserLevel(String userID, int plus_experience){
        String query = "SELECT * FROM Users WHERE UserID = '" + userID + "'";
        Cursor cursor = connect.ReadTable(query);
        int experience = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int userExpIndex = cursor.getColumnIndex("ExperiencePoints");
                experience = Integer.parseInt(cursor.getString(userExpIndex));
            }
            cursor.close();
        }
        int new_experience = experience + plus_experience;
        query = "UPDATE Users SET ExperiencePoints = " + new_experience + " WHERE UserID = '" + userID + "'";
        connect.WriteTable(query);
    }

    public List<SetClass> getAllCardSets() {
        List<SetClass> cardSetList = new ArrayList<>();
        String query = "SELECT * FROM CardSet";
        Cursor cursor = connect.ReadTable(query);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int setIdIndex = cursor.getColumnIndex("SetID");
                int setNameIndex = cursor.getColumnIndex("SetName");
                int setImageIndex = cursor.getColumnIndex("SetImage");
                int setUserID = cursor.getColumnIndex("UserID");
                do {
                    if (setIdIndex >= 0) {
                        String setId = cursor.getString(setIdIndex);
                        String setName = cursor.getString(setNameIndex);
                        String setImage = cursor.getString(setImageIndex);
                        String userID = cursor.getString(setUserID);
                        SetClass cardSet = new SetClass(setId, setName, setImage, userID);
                        cardSetList.add(cardSet);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return cardSetList;
    }

    public List<CardClass> getAllCards() {
        List<CardClass> cardList = new ArrayList<>();
        String query = "SELECT * FROM Card";
        Cursor cursor = connect.ReadTable(query);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int cardIdIndex = cursor.getColumnIndex("CardID");
                int cardImageIndex = cursor.getColumnIndex("CardImage");
                int cardPhraseIndex = cursor.getColumnIndex("CardPhrase");
                int cardTypeIndex = cursor.getColumnIndex("CardType");
                int cardDefinitionIndex = cursor.getColumnIndex("CardDefinition");
                int cardSoundIndex = cursor.getColumnIndex("CardSound");
                int difficultyLevelIndex = cursor.getColumnIndex("DifficultyLevel");
                int setIDIndex = cursor.getColumnIndex("SetID");
                do {
                    if (cardIdIndex >= 0) {
                        String cardId = cursor.getString(cardIdIndex);
                        String cardImage = cursor.getString(cardImageIndex);
                        String cardPhrase = cursor.getString(cardPhraseIndex);
                        String cardType = cursor.getString(cardTypeIndex);
                        String cardDefinition = cursor.getString(cardDefinitionIndex);
                        String cardSound = cursor.getString(cardSoundIndex);
                        int difficultyLevel = cursor.getInt(difficultyLevelIndex);
                        String setID = cursor.getString(setIDIndex);

                        CardClass card = new CardClass(cardId, cardImage, cardPhrase, cardType, cardDefinition, cardSound, difficultyLevel, setID);
                        cardList.add(card);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return cardList;
    }


    public void insertCard(String cardId, String cardImage, String cardPhrase, String cardType, String cardDefinition, String cardSound, int difficultyLevel, String setId) {
        String query = "INSERT INTO Card (CardID, CardImage, CardPhrase, CardType, CardDefinition, CardSound, DifficultyLevel, SetID) " +
                "VALUES ('" + cardId + "', '" + cardImage + "', '" + cardPhrase + "', '" + cardType + "', '" + cardDefinition + "', '" + cardSound + "', " + difficultyLevel + ", '" + setId + "')";
        connect.WriteTable(query);
    }

    public List<CardClass> getCardsForCardSet(String setId) {
        List<CardClass> cardList = new ArrayList<>();
        String query = "SELECT * FROM Card WHERE SetID = '" + setId + "'";
        Cursor cursor = connect.ReadTable(query);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int cardIdIndex = cursor.getColumnIndex("CardID");
                int cardImageIndex = cursor.getColumnIndex("CardImage");
                int cardPhraseIndex = cursor.getColumnIndex("CardPhrase");
                int cardTypeIndex = cursor.getColumnIndex("CardType");
                int cardDefinitionIndex = cursor.getColumnIndex("CardDefinition");
                int cardSoundIndex = cursor.getColumnIndex("CardSound");
                int difficultyLevelIndex = cursor.getColumnIndex("DifficultyLevel");

                do {
                    if (cardIdIndex >= 0) {
                        String cardId = cursor.getString(cardIdIndex);
                        String cardImage = cursor.getString(cardImageIndex);
                        String cardPhrase = cursor.getString(cardPhraseIndex);
                        String cardType = cursor.getString(cardTypeIndex);
                        String cardDefinition = cursor.getString(cardDefinitionIndex);
                        String cardSound = cursor.getString(cardSoundIndex);
                        int difficultyLevel = cursor.getInt(difficultyLevelIndex);

                        CardClass card = new CardClass(cardId, cardImage, cardPhrase, cardType, cardDefinition, cardSound, difficultyLevel, setId);
                        cardList.add(card);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return cardList;
    }

    public int getAverageDifficulty(String setId){
        double avgDifficultyLevel = -1.0;
        String query = "SELECT AVG(Cast(DifficultyLevel as float)) AS AvgDifficultyLevel " +
                "FROM Card WHERE SetID = '" + setId + "'";
        Cursor cursor = connect.ReadTable(query);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int difficultyLevelIndex = cursor.getColumnIndex("AvgDifficultyLevel");

                avgDifficultyLevel = cursor.getDouble(difficultyLevelIndex);
            }
            cursor.close();
        }
        return (int)Math.floor(avgDifficultyLevel);
    }

    public void insertPractice(String practiceId, String userId, String setId, int mode, int correctAnswers, int totalQuestions, int experienceEarned) {
        String query = "INSERT INTO Practice (PracticeID, UserID, SetID, Mode, CorrectAnswers, TotalQuestions, ExperienceEarned, Timestamp) " +
                "VALUES ('" + practiceId + "', '" + userId + "', '" + setId + "', " + mode + ", " + correctAnswers + ", " + totalQuestions + ", " + experienceEarned + ", datetime('now'))";
        connect.WriteTable(query);
    }

    public List<PracticeClass> getPracticeForUser(String userId) {
        List<PracticeClass> practiceList = new ArrayList<>();
        String query = "SELECT * FROM Practice WHERE UserID = '" + userId + "'";
        Cursor cursor = connect.ReadTable(query);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int practiceIdIndex = cursor.getColumnIndex("PracticeID");
                int setIdIndex = cursor.getColumnIndex("SetID");
                int modeIndex = cursor.getColumnIndex("Mode");
                int correctAnswersIndex = cursor.getColumnIndex("CorrectAnswers");
                int totalQuestionsIndex = cursor.getColumnIndex("TotalQuestions");
                int experienceEarnedIndex = cursor.getColumnIndex("ExperienceEarned");
                int timestampIndex = cursor.getColumnIndex("Timestamp");

                do {
                    if (practiceIdIndex >= 0) {
                        String practiceId = cursor.getString(practiceIdIndex);
                        String setId = cursor.getString(setIdIndex);
                        int mode = cursor.getInt(modeIndex);
                        int correctAnswers = cursor.getInt(correctAnswersIndex);
                        int totalQuestions = cursor.getInt(totalQuestionsIndex);
                        int experienceEarned = cursor.getInt(experienceEarnedIndex);

                        // Convert the timestamp string to a Date object
                        String timestampStr = cursor.getString(timestampIndex);
                        Date timestamp = parseDateFromString(timestampStr);

                        PracticeClass practice = new PracticeClass(practiceId, userId, setId, mode, correctAnswers, totalQuestions, experienceEarned, timestamp);
                        practiceList.add(practice);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return practiceList;
    }
    public double getAccuracyUser(String userId) {
        String query = "SELECT * FROM Practice WHERE UserID = '" + userId + "'";
        Cursor cursor = connect.ReadTable(query);

        int totalCorrect = 0;
        int totalQuestion = 0;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int practiceIdIndex = cursor.getColumnIndex("PracticeID");
                int correctAnswersIndex = cursor.getColumnIndex("CorrectAnswers");
                int totalQuestionsIndex = cursor.getColumnIndex("TotalQuestions");

                do {
                    if (practiceIdIndex >= 0) {
                        int correctAnswers = cursor.getInt(correctAnswersIndex);
                        int totalQuestions = cursor.getInt(totalQuestionsIndex);

                        totalQuestion+=totalQuestions;
                        totalCorrect+=correctAnswers;

                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        double acc = Double.valueOf(totalCorrect) / Double.valueOf(totalQuestion);
        return acc;
    }
    public int getPracticeCountForUser(String userID){
        String query = "SELECT * FROM Practice WHERE UserID = '" + userID + "'";
        Cursor cursor = connect.ReadTable(query);
        int count=0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    count++;
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return count;
    }

    // Helper function to parse a Date from a timestamp string
    private Date parseDateFromString(String timestampStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        try {
            return dateFormat.parse(timestampStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void AddData()
    {
        this.insertUser("U001", "Furina", "SamSung Galaxy S23",  0);
        this.insertUser("U002", "Melania", "Iphone 13",  0);
        this.insertUser("U003", "Kafka", "Oppo A58",  0);

        this.insertCardSet("S001", "Animal", "https://img.freepik.com/free-vector/set-wild-animals_1308-29055.jpg?size=626&ext=jpg&ga=GA1.1.2116175301.1700697600&semt=ais", "U001");
        this.insertCardSet("S002", "Weapon", "https://cdn.vectorstock.com/i/1000x1000/19/35/weapons-collection-icons-vector-9681935.webp", "U001");
        this.insertCardSet("S003", "Fruit", "https://www.healthyeating.org/images/default-source/home-0.0/nutrition-topics-2.0/general-nutrition-wellness/2-2-2-3foodgroups_fruits_detailfeature.jpg?sfvrsn=64942d53_4", "U002");
        this.insertCardSet("S004", "Teyvat City", "https://cdn.sforum.vn/sforum/wp-content/uploads/2022/03/Genshin-Impact-Mondstadt-art.jpg", "U003");
        this.insertCardSet("S005", "Country", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d8/Flickr_-_%E2%80%A6trialsanderrors_-_Johnson%27s_new_chart_of_national_emblems%2C_1868.jpg/370px-Flickr_-_%E2%80%A6trialsanderrors_-_Johnson%27s_new_chart_of_national_emblems%2C_1868.jpg", "U003");
        this.insertCardSet("S006", "Job", "https://www.worldconstructiontoday.com/wp-content/uploads/2020/11/constructon-worker-wellfare.jpg", "U003");

        // User 1 - Set 1
        this.insertCard("C001", "https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg", "Dog", "Animal", "Domesticated mammal, often kept as a pet or for work.", "mp3", 1, "S001");
        this.insertCard("C002", "https://cdn1.byjus.com/wp-content/uploads/blog/2023/03/17131610/STIM_Happy-Baby-Elephant-Running-scaled.jpeg", "Elephant", "Animal", "Large herbivorous mammal with a long trunk and tusks.", "mp3", 1, "S001");
        this.insertCard("C003", "https://cdn.britannica.com/40/75640-050-F894DD85/tiger-Siberian.jpg", "Tiger", "Animal", "Large carnivorous feline with distinctive orange fur and black stripes.", "mp3", 2, "S001");
        this.insertCard("C004", "https://cdn.britannica.com/41/941-004-D59E3112/Masai-giraffe.jpg", "Giraffe", "Animal", "Tall, long-necked herbivorous mammal native to African savannas", "mp3", 3, "S001");
        this.insertCard("C005", "https://cdn.britannica.com/70/192570-138-848FB7B3/penguin-species-places-Galapagos-Antarctica.jpg?w=800&h=450&c=crop", "Penguin", "Animal", "Flightless bird found in the Southern Hemisphere", "mp3", 3, "S001");
        // User 1 - Set 2
        this.insertCard("C006", "https://staticg.sportskeeda.com/editor/2022/01/1e40d-16412084161268-1920.jpg", "Polearm", "Weapon", "A long-handled weapon with a blade or point at one end", "mp3", 3, "S002");
        this.insertCard("C007", "https://static.wikia.nocookie.net/gensin-impact/images/6/60/Weapon_Mistsplitter_Reforged_Wish.png/revision/latest/scale-to-width-down/250?cb=20211222104124", "Sword", "Weapon", "A bladed weapon with a long metal blade and hilt", "mp3", 2, "S002");
        this.insertCard("C008", "https://traveler.gg/wp-content/uploads/Weapon_Amos27_Bow_2nd_3D.png", "Bow", "Weapon", "A ranged weapon that uses tension to propel arrows towards a target", "mp3", 1, "S002");

        // User 2 - Set 3
        this.insertCard("C009", "https://static.wikia.nocookie.net/reverse1999/images/f/fc/APPLe.png/revision/latest?cb=20231029183031", "Apple", "Fruit", "Regulus friend", "mp3", 1, "S003");
        this.insertCard("C010", "https://images.immediate.co.uk/production/volatile/sites/30/2017/01/Bunch-of-bananas-67e91d5.jpg?quality=90&resize=440,400", "Banana", "Fruit", "Long, curved fruit with a yellow skin", "mp3", 1, "S003");
        this.insertCard("C011", "https://cdn.britannica.com/24/174524-050-A851D3F2/Oranges.jpg", "Orange", "Fruit", "Citrus fruit known for its round shape, bright orange color", "mp3", 1, "S003");
        this.insertCard("C012", "https://static.vecteezy.com/system/resources/previews/005/104/874/non_2x/fresh-pineapple-illustration-suitable-for-decoration-sticker-icon-and-others-free-vector.jpg", "Pineapple", "Fruit", "Tropical fruit with a spiky exterior and juicy yellow flesh", "mp3", 2, "S003");

        // User 3 - Set 4
        this.insertCard("C013", "https://upload-os-bbs.hoyolab.com/upload/2020/07/14/1072831/0386851a028480eb95da974ccd2227ea_213412409097284740.png", "Mondstadt", "TeyvatCity", "A city known for its windmills and its statue of Barbatos, the Anemo Archon", "mp3", 3, "S004");
        this.insertCard("C014", "https://static.wikia.nocookie.net/gensin-impact/images/0/0c/Liyue_Harbor.png/revision/latest?cb=20220612170326", "Liyue", "TeyvatCity", "A city known for its trade, bustling markets and Jade Chamber", "mp3", 3, "S004");
        this.insertCard("C015", "https://cdn.sforum.vn/sforum/wp-content/uploads/2021/08/cach-den-inazuma.jpg", "Inazuma", "TeyvatCity", "A city known for its traditional Japanese architecture, cherry blossoms, and the Electro Archon", "mp3", 1, "S004");
        this.insertCard("C016", "https://cdn.sforum.vn/sforum/wp-content/uploads/2022/09/Sumeru_City.jpg", "Sumeru", "TeyvatCity", " The city is renowned as the City of Wisdom and is home to the prestigious Akademiya", "mp3", 2, "S004");
        this.insertCard("C017", "https://static.wikia.nocookie.net/gensin-impact/images/8/8d/Fontaine.png/revision/latest/scale-to-width-down/1200?cb=20230819002539", "Fontaine", "TeyvatCity", "A city is known for its advanced scientific and technological devices, with a steampunk-esque aesthetic", "mp3", 3, "S004");
        this.insertCard("C018", "https://scitechdaily.com/images/Giant-Volcano-Eruption-Mountains.jpg", "Natlan", "TeyvatCity", "A city is known for its volcanic landscapes, geysers, and scorching temperatures", "mp3", 3, "S004");
        // User 3 - Set 5
        this.insertCard("C019", "https://wise.com/imaginary-v2/f8cb5a1a10a1933a131e92ac18e398a8.jpg", "France", "Country", "European country known for its art, culture, and the Eiffel Tower.", "mp3", 2, "S005");
        this.insertCard("C020", "https://static.wixstatic.com/media/bcf5d6_ea54daf8024a43aaad3c70460c00f365~mv2.jpg/v1/fill/w_640,h_372,al_c,q_80,usm_0.66_1.00_0.01,enc_auto/bcf5d6_ea54daf8024a43aaad3c70460c00f365~mv2.jpg", "Japan", "Country", "East Asian nation known for its technology, cuisine, and rich traditions.", "mp3", 1, "S005");
        this.insertCard("C021", "https://cdn.pixabay.com/photo/2016/11/07/23/46/veteran-1807121_640.jpg", "United States", "Country", "A North American nation known for its diverse culture, landscapes, and the Statue of Liberty.", "mp3", 2, "S005");
        this.insertCard("C022", "https://thumbs.dreamstime.com/z/christ-redeemer-statue-flag-brazil-isolated-white-background-d-render-91046288.jpg", "Brazil", "Country", "South American country known for its vibrant culture, Amazon rainforest, and famous carnivals.", "mp3", 1, "S005");
        // User 3 - Set 6
        this.insertCard("C023", "https://i0.wp.com/www.engineeringandleadership.com/wp-content/uploads/2019/02/Engineer.png?fit=975%2C651&ssl=1", "Engineer", "Job", "Profession scientifically design and build machines, structures, and systems.", "mp3", 3, "S006");
        this.insertCard("C024", "https://www.hindustantimes.com/ht-img/img/2023/09/02/1600x900/teachers_day_1693652054152_1693652065719.jpg", "Teacher", "Job", "Profession focused on educating and instructing students in various subjects and skills.", "mp3", 1, "S006");
        this.insertCard("C025", "https://www.collinsdictionary.com/images/full/doctor_117169531.jpg", "Doctor", "Job", "Medical professional trained to diagnose, treat, and prevent illnesses and injuries.", "mp3", 1, "S006");
        this.insertCard("C026", "https://static.vecteezy.com/system/resources/thumbnails/007/660/089/small/watercolor-women-chef-serving-her-cooking-free-vector.jpg", "Chef", "Job", "Professional cook who is skilled in the art of preparing and presenting food.", "mp3", 1, "S006");
        this.insertCard("C027", "https://www.unitekemt.com/wp-content/uploads/2022/03/shutterstock_1607600596.jpg", "Firefighter", "Job", "Emergency responder trained to combat fires and rescue people", "mp3", 2, "S006");


        // Practice
        this.insertPractice("P001", "U001", "S002", 1,  3, 3, 30);
        this.insertPractice("P002", "U001", "S001", 1,  0, 5, 0);
        this.insertPractice("P003", "U001", "S001", 1,  3, 5, 30);
        this.insertPractice("P004", "U001", "S001", 1,  4, 5, 40);

        this.insertPractice("P005", "U002", "S003", 1,  2, 4, 20);
        this.insertPractice("P006", "U002", "S003", 1,  4, 4, 40);

        this.insertPractice("P007", "U003", "S004", 1,  4, 6, 40);
        this.insertPractice("P008", "U003", "S005", 1,  4, 4, 40);
        this.insertPractice("P009", "U003", "S004", 1,  6, 6, 60);
        this.insertPractice("P010", "U003", "S004", 1,  0, 6, 0);
        this.insertPractice("P011", "U003", "S006", 1,  3, 5, 30);
        this.insertPractice("P012", "U003", "S005", 1,  1, 4, 10);
    }

    @SuppressLint("DefaultLocale")
    public String generateNewCardSetID() {
        String Prefix = "S";
        String query = "SELECT MAX(CAST(SUBSTR(SetID, 2) AS INTEGER)) FROM CardSet";
        Cursor cursor = this.connect.ReadTable(query);

        int latestIDNumber = 0;
        if (cursor != null && cursor.moveToFirst()) {
            int latestIDIndex = cursor.getColumnIndex("MAX(CAST(SUBSTR(SetID, 2) AS INTEGER))");
            latestIDNumber = cursor.getInt(latestIDIndex);
            cursor.close();
        }
        latestIDNumber++;

        return Prefix + String.format("%03d", latestIDNumber);
    }
    @SuppressLint("DefaultLocale")
    public String generateNewCardID() {
        String Prefix = "C";
        String query = "SELECT MAX(CAST(SUBSTR(CardID, 2) AS INTEGER)) FROM Card";
        Cursor cursor = this.connect.ReadTable(query);

        int latestIDNumber = 0;
        if (cursor != null && cursor.moveToFirst()) {
            int latestIDIndex = cursor.getColumnIndex("MAX(CAST(SUBSTR(CardID, 2) AS INTEGER))");
            latestIDNumber = cursor.getInt(latestIDIndex);
            cursor.close();
        }
        latestIDNumber++;

        return Prefix + String.format("%03d", latestIDNumber);
    }

    @SuppressLint("DefaultLocale")
    public String generateNewUserID() {
        // Define the prefix for user IDs
        String userIdPrefix = "U";

        // Query to get the latest user ID from the database
        String query = "SELECT MAX(CAST(SUBSTR(UserID, 2) AS INTEGER)) FROM Users";
        Cursor cursor = this.connect.ReadTable(query);

        int latestUserIdNumber = 0;

        if (cursor != null && cursor.moveToFirst()) {
            int latestUserIdIndex = cursor.getColumnIndex("MAX(CAST(SUBSTR(UserID, 2) AS INTEGER))");
            latestUserIdNumber = cursor.getInt(latestUserIdIndex);
            cursor.close();
        }

        // Increment the latest user ID number
        latestUserIdNumber++;

        // Format the new user ID
        return userIdPrefix + String.format("%03d", latestUserIdNumber);
    }

    @SuppressLint("DefaultLocale")
    public String generateNewPracticeID() {
        String Prefix = "P";
        String query = "SELECT MAX(CAST(SUBSTR(PracticeID, 2) AS INTEGER)) FROM Practice";
        Cursor cursor = this.connect.ReadTable(query);

        int latestIDNumber = 0;
        if (cursor != null && cursor.moveToFirst()) {
            int latestIDIndex = cursor.getColumnIndex("MAX(CAST(SUBSTR(PracticeID, 2) AS INTEGER))");
            latestIDNumber = cursor.getInt(latestIDIndex);
            cursor.close();
        }
        latestIDNumber++;

        return Prefix + String.format("%03d", latestIDNumber);
    }

}