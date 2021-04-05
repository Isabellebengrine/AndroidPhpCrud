package org.isa.androidphpcrud;

public class Constants {

    // la base url de note api (renseigner l'adresse ip de votre localhost car émulateur android ne connaît pas localhost)
    //From the emulator, 127.0.0.1 refers to the emulator itself - not your local machine. You need to use ip 10.0.2.2, which is bridged to your local machine:
    private static final String ROOT_URL = "http://10.0.2.2/tutoAPIvolley/v1/";

    // le nom du fichier utilisé pour inscription:
    public static final String URL_REGISTER = ROOT_URL+"registerUser.php";

    // le nom du fichier utilisé pour login:
    public static final String URL_LOGIN = ROOT_URL+"userLogin.php";

}
