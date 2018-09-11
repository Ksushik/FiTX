package com.brus5.lukaszkrawczak.fitx.utils;

public class RestAPI
{
    public static final String SERVER_URL = "http://justfitx.xyz/";

    public static final String DIET = SERVER_URL + "Diet/";
    public static final String TRAINING = SERVER_URL + "Training/";
    public static final String CARDIO = SERVER_URL + "Cardio/";
    public static final String MAIN = SERVER_URL + "Main/";
    public static final String SETTINGS = SERVER_URL + "Settings/";

    public static final String URL_MAIN_INFORMATIONS = MAIN + "Main.php";


    public static final String URL_SETTINGS = SETTINGS + "GetSettings.php";
    public static final String URL_SETTINGS_GET = SETTINGS + "GetLast.php";
    public static final String URL_SETTINGS_INSERT = SETTINGS + "Insert.php";


    public static final String URL_DIET_DELETE_COUNTED_KCAL = DIET + "KcalResultDelete.php";
    public static final String URL_DIET_UPDATE_COUNTED_KCAL = DIET + "KcalResultUpdate.php";


    public static final String URL_DIET_PRODUCT_GET_INFORMATIONS = DIET + "GetProductInformations.php";

    public static final String URL_DIET_PRODUCT_DELETE = DIET + "ProductDelete.php";
    public static final String URL_DIET_PRODUCT_INSERT = DIET + "ProductInsert.php";
    public static final String URL_DIET_PRODUCT_SEARCH = DIET + "ProductsSearch.php";
    public static final String URL_DIET_PRODUCT_UPDATE_WEIGHT = DIET + "UpdateProductWeight.php";

    public static final String URL_DIET_PRODUCTS_SHOW_BY_USER = DIET + "ShowByUser.php";


    public static final String URL_TRAINING_NAME = TRAINING + "ShowName.php";
    public static final String URL_TRAINING_DETAILS = TRAINING + "ShowTrainingDetails.php";
    public static final String URL_TRAINING_SHOW = TRAINING + "ShowByUser.php";
    public static final String URL_TRAINING_SHOW_SHORT = TRAINING + "ShowByUserShort.php";
    public static final String URL_TRAINING_DESCRIPTION = TRAINING + "ShowTrainingDescription.php";
    public static final String URL_TRAINING_SEARCH_BY_TARGET = TRAINING + "SearchByTarget.php";
    public static final String URL_TRAINING_INSERT = TRAINING + "Insert.php";
    public static final String URL_TRAINING_DELETE = TRAINING + "Delete.php";
    public static final String URL_TRAINING_UPDATE = TRAINING + "Update.php";

    public static final String URL_CARDIO_GET_LIST = CARDIO + "Search.php";
    public static final String URL_CARDIO_SHOW = CARDIO + "Show.php";
    public static final String URL_CARDIO_INSERT = CARDIO + "Insert.php";
    public static final String URL_CARDIO_UPDATE = CARDIO + "Update.php";
    public static final String URL_CARDIO_DELETE = CARDIO + "Delete.php";



    /* Names of db_user */
    public final static String DB_USER_ID = "id";
    public final static String DB_USER_FIRSTNAME = "name";
    public final static String DB_USERNAME = "username";
    public final static String DB_USER_BIRTHDAY = "birthday";
    public final static String DB_PASSWORD = "password";
    public final static String DB_USER_EMAIL = "email";
    public final static String DB_USER_GENDER = "male";

    /* Names of db_userinfo */
    public final static String DB_USER_WEIGHT = "weight";
    public final static String DB_USER_HEIGHT = "height";
    public final static String DB_USER_SOMATOTYPE = "somatotype";

    public final static String DB_PROTEIN_RATIO = "proteinsratio";
    public final static String DB_FATS_RATIO = "fatsratio";
    public final static String DB_CARBS_RATIO = "carbsratio";

    /* Names of db_training */
    public final static String DB_EXERCISE_ID = "id";
    public final static String DB_EXERCISE_NAME = "name";
    public final static String DB_EXERCISE_DESCRITION = "description";

    /* Names of db_list_cardio */
    public final static String DB_CARDIO_ID = "id";
    public final static String DB_CARDIO_NAME = "name";
    public final static String DB_CARDIO_CALORIES = "calories";

    /* Names of db_user_cardio */
    public final static String DB_CARDIO_DONE = "done";
    public final static String DB_CARDIO_TIME = "time";
    public final static String DB_CARDIO_NOTEPAD = "notepad";
    public final static String DB_CARDIO_DATE = "date";

    /* Names of db_user_exercise */
    public final static String DB_EXERCISE_TARGET = "target";
    public final static String DB_EXERCISE_DONE = "done";
    public final static String DB_EXERCISE_REST_TIME = "rest";
    public final static String DB_EXERCISE_REPS = "reps";
    public final static String DB_EXERCISE_WEIGHT = "weight";
    public final static String DB_EXERCISE_DATE = "date";
    public final static String DB_EXERCISE_NOTEPAD = "notepad";

    /* Names of db_products */
    public final static String DB_PRODUCT_ID = "product_id";
    public final static String DB_PRODUCT_NAME = "name";
    public final static String DB_PRODUCT_WEIGHT = "weight";
    public final static String DB_PRODUCT_PROTEINS = "proteins";
    public final static String DB_PRODUCT_FATS = "fats";
    public final static String DB_PRODUCT_CARBS = "carbs";
    public final static String DB_PRODUCT_KCAL = "kcal";
    public final static String DB_PRODUCT_SATURATED_FATS = "fats_saturated";
    public final static String DB_PRODUCT_UNSATURATED_FATS = "fats_unsaturated";
    public final static String DB_PRODUCT_CARBS_FIBER = "carbs_fiber";
    public final static String DB_PRODUCT_CARBS_SUGAR = "carbs_sugar";
    public final static String DB_PRODUCT_MULTIPLIER_PIECE = "multiplier_piece";
    public final static String DB_PRODUCT_VERIFIED = "verified";

    /* Names of db_user_diet */
    public final static String DB_USER_DIET_ID = "id";
    public final static String DB_USER_DIET_USERNAME = "username";
    public final static String DB_USER_DIET_DATE = "date";
    public final static String DB_PRODUCT_DIET_WEIGHT_UPDATE = "updateweight";
    public final static String DB_USER_DIET_DATE_UPDATE = "updatedate";

    /* Names of db_kcalres */
    //    public final static String DB_KCAL_LIMIT = "RESULT";
    public final static String DB_KCAL = "kcal";
    public final static String DB_KCAL_LIMIT = "kcal_limit";

    /* Names of db_dietres */
    public final static String DB_KCAL_DAILY_RESULT = "RESULT";

    /* Used more often */
    public final static String DB_DATE = "date";

    public final static String DB_UPDATE_RESULT = "updateresult";


    public final static String DB_USER_ID_NO_PRIMARY_KEY = "user_id";

}
