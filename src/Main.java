import client.TcpClient;
import protocol.messages.AddCarMessage;
import protocol.messages.RequestInformationMessage;
import protocol.messages.RequestResponseMessage;
import protocol.messages.SellCarMessage;
import protocol.objects.Car;
import protocol.objects.CarMake;
import questionTwo.MyArrayList;
import questionTwo.MyList;
import server.TcpServer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //questionOne();
        questionTwo();
    }

    private static void questionTwo() {
        MyList<String> list = new MyArrayList<>();

        Runnable assignmentTask = () -> {
            list.add("0");                                          // add 1 element
            list.add(Arrays.asList("1", "2", "3", "4", "5"));       // adds 5 elements
            list.count(x -> Integer.parseInt(x) > 4);               // returns 1
            list.exists(x -> x.equals("1"));                        // returns true
            list.forAll(Objects::nonNull);                          // returns true
            list.filter(x -> Integer.parseInt(x) == 0);             // new list of 1 element

            MyList<Integer> intList = list.map(Integer::parseInt);  // new list converted to type Integer
            MyList<Integer> intList2 = list.mapFilter(
                    Integer::parseInt,
                    x -> Integer.parseInt(x) > 3                    // filter numbers > 3
            );                                                      // new list converted to type Integer containing only 4 and 5
        };

        // test functions on one thread
        assignmentTask.run();

        // test functions on 10 threads
        for (int i=0; i < 10; ++i)
            new Thread(assignmentTask).start();
    }

    private static final int SERVER_PORT = 5557;

    private static final Consumer<RequestResponseMessage> responseHandler = response -> {
        if (response.getMessage() != null)
            System.out.println(response.getMessage());

        if (response.getCars() != null)
            response.getCars().forEach(System.out::println);
        System.out.println();
    };

    private static void questionOne() throws InterruptedException, IOException, ClassNotFoundException {
        TcpServer server = new TcpServer(SERVER_PORT, 50, 2);
        server.start();

        Thread.sleep(500);

        testOneClient();
        //testMultipleClient(100);
    }

    private static void testOneClient() throws IOException, ClassNotFoundException {
        TcpClient client = new TcpClient(SERVER_PORT);

        client.write(new AddCarMessage(
                new Car(CarMake.Ferrari, 20000, 100000),
                new Car(CarMake.Ford_AMax, 20000, 100000),
                new Car(CarMake.Ford_AMax, 1111111, 100000),
                new Car(CarMake.Ford_Mustang, 20000, 2222222),
                new Car(CarMake.Toyota_Avensis, 20000, 1003000),
                new Car(CarMake.Ford_Ford_Mondea, 20000, 333333),
                new Car(CarMake.Ford_Focus, 20000, 100000),
                new Car(CarMake.Ford_BMax, 20000, 111111111)
        ), responseHandler);

        client.write(new RequestInformationMessage(RequestInformationMessage.RequestType.SEARCH_ALL, null), responseHandler);
        client.write(new RequestInformationMessage(RequestInformationMessage.RequestType.SEARCH_ONE, CarMake.Ford_AMax), responseHandler);
        client.write(new RequestInformationMessage(RequestInformationMessage.RequestType.SEARCH_TOTAL, null), response -> {
            System.out.println(response.getCars().stream().mapToInt(Car::getPrice).sum() + "\n");

            String randomReferenceToBuy = response.getCars().stream()
                    .filter(x -> x.getMake() == CarMake.Ford_AMax)
                    .findFirst()
                    .get().getRegistration();

            try {
                client.write(new SellCarMessage(randomReferenceToBuy), responseHandler);
            } catch (Exception e) {}
        });

        client.write(new RequestInformationMessage(RequestInformationMessage.RequestType.SEARCH_ONE, CarMake.Ford_AMax), responseHandler);
    }

    private static void testMultipleClient(int clientNumber) throws IOException, ClassNotFoundException {
        for (int i=0; i < clientNumber; ++i) {
            new Thread(() -> {
                try {
                    testOneClient();
                } catch (Exception e) {}
            }).start();
        }
    }
}
