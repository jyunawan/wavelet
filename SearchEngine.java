import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    private static final String DISPLAY_PATH = "/";
    private static final String ADD_PATH = "/add";
    private static final String ADD_MESSAGE = "Added %s";
    private static final String SEARCH_PATH = "/search";
    private static final String PARAMETER_NAME = "s";
    private static final String FOUND_MESSAGE = "Found:\n";


    ArrayList<String> words = new ArrayList<>();


    public String handleRequest(URI url) {
        String path = url.getPath();

        if (path.equals(DISPLAY_PATH)) {
            return String.join(", ", words);
        } else {
            String[] parameters = url.getQuery().split("=");
            System.out.println("Path: " + url.getPath());

            if (path.contains(ADD_PATH)) {
                if (parameters[0].equals(PARAMETER_NAME)) {
                    String newString = parameters[1];
                    words.add(newString);
                    System.out.println(words.toString());
                    return String.format(ADD_MESSAGE, newString);
                }
            } else if (path.contains(SEARCH_PATH)) {
                if (parameters[0].equals(PARAMETER_NAME)) {
                    String toSearch = parameters[1];
                    ArrayList<String> results = new ArrayList<>();

                    for (String word : words) {
                        if (word.contains(toSearch)) {
                            results.add(word);
                        }
                    }

                    String returnMessage = FOUND_MESSAGE + String.join(", ", results);
                    return returnMessage;
                }
            }

            return "404 Not Found!";
        }
    }
}


class SearchEngine {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
