package in.swats.springbootmongodb.exception;

public class TodoCollectionException extends RuntimeException{

    public TodoCollectionException(String msg) {
        super(msg);
    }

    public static String NotFoundException(String id) {
        return "Todo with" +id+" not found";
    }

    public static String TodoAlreadyExists() {
        return "Todo with given name already exists";
    }
}
