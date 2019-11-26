package server;

import protocol.Message;
import protocol.messages.AddCarMessage;
import protocol.messages.RequestInformationMessage;
import protocol.messages.RequestResponseMessage;
import protocol.messages.SellCarMessage;
import protocol.objects.Car;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public class TcpServerHandlers {
    private final Map<Integer, BiConsumer<TcpClientContext, Message>> handlers;

    public TcpServerHandlers() {
        this.handlers = new HashMap<>();
        handlers.put(AddCarMessage.protocolId, (ctx, msg) -> onCreate(ctx, (AddCarMessage) msg));
        handlers.put(SellCarMessage.protocolId, (ctx, msg) -> onSell(ctx, (SellCarMessage) msg));
        handlers.put(RequestInformationMessage.protocolId, (ctx, msg) -> onRequest(ctx, (RequestInformationMessage) msg));
    }

    private void onCreate(TcpClientContext context, AddCarMessage message) {
        SafeCarHolder.instance().addCars(message.getCars());
        StringBuilder builder = new StringBuilder();

        for (Car car: message.getCars()) {
            car.setRegistration(UUID.randomUUID().toString());
            car.setForSale(true);
            builder.append(String.format("%s added successfully\n", car.getMake().name()));
        }

        context.write(new RequestResponseMessage(builder.toString()));
    }

    private void onSell(TcpClientContext context, SellCarMessage message) {
        boolean success = SafeCarHolder.instance().sellCar(message.getReference());

        if (success)
            context.write(new RequestResponseMessage("%s sold successfully", message.getReference()));
        else
            context.write(new RequestResponseMessage("%s has already been sold", message.getReference()));
    }

    private void onRequest(TcpClientContext context, RequestInformationMessage message) {
        switch(message.type()) {
            case SEARCH_ONE:
                List<Car> carsMake = SafeCarHolder.instance().carsForSale(message.getMake());
                context.write(new RequestResponseMessage(carsMake));
                break;
            case SEARCH_TOTAL:
                List<Car> carsToCount = SafeCarHolder.instance().carsForSale();
                context.write(new RequestResponseMessage(carsToCount));
                break;
            case SEARCH_ALL:
                List<Car> cars = SafeCarHolder.instance().carsForSale();
                context.write(new RequestResponseMessage(cars));
                break;
        }
    }

    public void handle(TcpClientContext context, Object object) {
        if (!(object instanceof Message)) {
            System.out.println("Unknown message received");
            return;
        }

        Message message = (Message) object;
        handlers.get(message.protocolId()).accept(context, message);
    }
}
