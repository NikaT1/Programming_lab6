package sharedClasses;

import java.net.DatagramSocket;
import java.util.Scanner;

public interface IOInterface {
    Scanner getScanner();

    void setScanner(Scanner scanner);

    boolean readAnswer(String message);

    void output(String s);

    boolean getPrintMessages();

    void setPrintMessages(boolean printMessages);
}
