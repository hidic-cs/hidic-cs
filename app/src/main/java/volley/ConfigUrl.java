package volley;

public class ConfigUrl {

    // Default config for emulator. Uncomment it if using emulator instead of real device
//    private static String baseUrlEmulator = "http://10.0.2.2/";

    // Default config for real devices
    private static String baseUrl =
            // ip of your machine
            "http://192.168.100.5/";

    // Server patient login url
    public static String URL_LOGIN = baseUrl + "hidic_cs/";

    // Server patient register url
    public static String URL_REGISTER = baseUrl + "hidic_cs/";
}
