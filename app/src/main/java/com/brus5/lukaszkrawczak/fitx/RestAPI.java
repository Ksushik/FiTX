package com.brus5.lukaszkrawczak.fitx;

public class RestAPI
{
    public static final String URL = "http://justfitx.xyz/";

    public static final String URL_DIET = URL + "Diet/";
    public static final String URL_TRAINING = URL + "Training/";
    public static final String URL_MAIN = URL + "Main/";



    public static final String URL_MAIN_DIET = URL_MAIN + "Diet.php";

    public static final String URL_DIET_GET_PRODUCT_INFORMATIONS = URL_DIET + "GetProductInformations.php";
    public static final String URL_DIET_DELETE_COUNTED_KCAL = URL_DIET + "KcalResultDelete.php";
    public static final String URL_DIET_UPDATE_COUNTED_KCAL = URL_DIET + "KcalResultUpdate.php";
    public static final String URL_DIET_DELETE_PRODUCT = URL_DIET + "ProductDelete.php";
    public static final String URL_DIET_INSERT_PRODUCT = URL_DIET + "ProductInsert.php";
    public static final String URL_DIET_SEARCH_PRODUCT = URL_DIET + "ProductsSearch.php";
    public static final String URL_DIET_SHOW_BY_USER = URL_DIET + "ShowByUser.php";
    public static final String URL_DIET_UPDATE_WEIGHT_PRODUCT = URL_DIET + "UpdateProductWeight.php";

    public static final String URL_SHOW_TRAINING_DETAILS = URL_TRAINING + "ShowTrainingDetails.php";
    public static final String URL_SHOW_TRAINING = URL_TRAINING + "ShowByUser.php";
    public static final String URL_SHOW_TRAINING_SHORT = URL_TRAINING + "ShowByUserShort.php";
    public static final String URL_SHOW_TRAINING_DESCRIPTION = URL_TRAINING + "ShowTrainingDescription.php";
    public static final String URL_TRAINING_SEARCH_BY_TARGET = URL_TRAINING + "SearchByTarget.php";
    public static final String URL_TRAINING_INSERT = URL_TRAINING + "Insert.php";
    public static final String URL_TRAINING_DELETE = URL_TRAINING + "Delete.php";
    public static final String URL_TRAINING_UPDATE = URL_TRAINING + "Update.php";

    public static final String USER_CANCEL = "Zakończono przez użytkownika";
    public static final String LOGIN_ERROR = "Nieudana próba połączenia";

    public static final String NEW_ACCOUNT = "Witamy nowego użytkownika";
    public static final String EXISTING_ACCOUNT = "Witaj ponownie";

    public static final String CONNECTION_INTERNET_FAILED = "Błąd połączenia";


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
