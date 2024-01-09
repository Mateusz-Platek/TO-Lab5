import userInterface.UserInterface;
import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        UserInterface userInterface = new UserInterface();
        userInterface.init();
        userInterface.run();
    }
}
