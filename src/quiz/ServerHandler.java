package quiz;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerHandler implements Runnable{

    ObjectOutputStream writer;
    Gui gui;
    List<CategoryName> categoryList;

    public void setGui(Gui gui) {
        this.gui = gui;
    }

    @Override
    public void run() {

        try(Socket socket = new Socket("localhost",5929)) {

            writer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream serverReader = new ObjectInputStream(socket.getInputStream());

            String fromUser;
            BufferedReader userReader = new BufferedReader(new InputStreamReader(System.in));

            Object serverInput;

            while ((serverInput = serverReader.readObject()) != null) {

                if (serverInput instanceof Question) {
                    gui.clearButtonChanges();
                    Question question = (Question) serverInput;
                    gui.updateQuestion(question);
                    System.out.println(question.getQuestionText());
                    System.out.println(question.getAnswerOne());
                    System.out.println(question.getAnswerTwo());
                    System.out.println(question.getAnswerThree());
                    System.out.println(question.getAnswerCorrect());
                    gui.setQuestionPanel();
//                    fromUser = userReader.readLine();
//                    if (fromUser != null) {
//                        writeStringToServer(fromUser);
//                    }
                } else if (serverInput instanceof String) {
                    gui.setInfoPanel(serverInput.toString());

                    System.out.println(serverInput);

            } else if (serverInput instanceof List<?>) {
                    categoryList = (List<CategoryName>) serverInput;
                    System.out.println("Välj kategori");
                    System.out.println(categoryList.get(0));
                    System.out.println(categoryList.get(1));
                    System.out.println(categoryList.get(2));
                    System.out.println(categoryList.get(3));
                    gui.setCategoryPanel(categoryList.get(0).toString(),categoryList.get(1).toString(),
                            categoryList.get(2).toString(),categoryList.get(3).toString());

            }

            }
        } catch (Exception e) {
            System.out.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void writeStringToServer(String text) {
        try {
            writer.writeObject(text);
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
    public void sendCategory(String category) {

        for (CategoryName categoryName:categoryList) {
            if (categoryName.toString().equalsIgnoreCase(category)) {
                try {
                    writer.writeObject(categoryName);
                }catch (IOException e) {
                    System.out.println(e.getMessage());
                }

            }
        }
    }
}
